package org.uengine.workflow.springservice;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.ProcessVariableValue;
import org.uengine.kernel.RoleMapping;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.ui.list.datamodel.DataList;
import org.uengine.ui.list.datamodel.DataMap;
import org.uengine.ui.list.datamodel.QueryCondition;
import org.uengine.ui.list.exception.UEngineException;
import org.uengine.ui.list.util.DAOListCursorUtil;
import org.uengine.util.UEngineUtil;
import org.uengine.util.dao.DefaultConnectionFactory;
import org.uengine.workflow.model.NextTask;
import org.uengine.workflow.model.WorkList;

public class WorkflowServiceImpl implements WorkflowService {

	public List getWorkList(String endpoint, String filter, int pageCount, int currentPage, ProcessManagerRemote pm) {
		
		String statusType = "";
		if (filter.equals("1")) {
			statusType = "(wl.status = 'NEW' or wl.status = 'CONFIRMED' or wl.status = 'DRAFT')";
		} else if (filter.equals("2")) {
			statusType = "(wl.status = 'COMPLETED')";
		} else if (filter.equals("3")) {
			statusType = "(wl.status = 'Cancelled')";
		}

		QueryCondition condition = new QueryCondition();
		condition.setOnePageCount(pageCount);
		condition.setPageNo(currentPage);

		int totalCount = 0;

		DataList dl = null;
		Connection con = null;
		ArrayList al = new ArrayList();

		String sqlStmt = " select inst.name as procinstnm, inst.info,inst.defname ,wl.* from bpm_procinst inst, bpm_worklist wl" + " where (wl.endpoint='" + endpoint + "'"
					   + " or (select count(1) from bpm_roleMapping rm where rm.instId=wl.instId" + " and (rm.roleName=wl.roleName or rm.roleName=wl.refRoleName) " + " and rm.endpoint='" + endpoint
					   + "') > 0 )" + " and " + statusType + " and inst.isdeleted=0 and inst.instid = wl.instid order by wl.startdate desc";
		
		try {
			con = DefaultConnectionFactory.create().getConnection();
			dl = DAOListCursorUtil.executeList(sqlStmt, condition, new ArrayList(), con);
			totalCount = (int) dl.getTotalCount();
			al.add((String) String.valueOf(totalCount));
		} catch (UEngineException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
				}
		}

		if (totalCount > 0) {
			for (int i = 0; i < dl.size(); i++) {
				DataMap tmpMap = (DataMap) dl.get(i);

				WorkList wl = new WorkList();

				wl.setEndpoint(endpoint);
				wl.setInstanceId("" + tmpMap.getInt("INSTID", 0));
				wl.setRootInstanceId("" + tmpMap.getInt("ROOTINSTID", 0));
				wl.setTaskId("" + tmpMap.getInt("TASKID", 0));
				wl.setTracingTag(tmpMap.getString("TRCTAG", ""));
				wl.setInfo(tmpMap.getString("INFO", ""));
				wl.setTitle(tmpMap.getString("TITLE", ""));
				wl.setDefName(tmpMap.getString("DEFNAME", ""));

				String _startDate = tmpMap.getString("startdate", "");
				wl.setStartDate(UEngineUtil.isNotEmpty(_startDate) ? _startDate.substring(0, 19) : "");

				int endpointCountFromRoleName = getEndpointCountFromRoleName(tmpMap.getString("ROLENAME", ""), wl.getInstanceId());
				wl.setDuplicateTaskCount(String.valueOf(endpointCountFromRoleName));

				wl.setRoleName(tmpMap.getString("ROLENAME", ""));

				al.add((WorkList) wl);
			}
		}
		
		return al;

	}
	
	private int getEndpointCountFromRoleName(String roleName, String instanceId) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append(" select											");
		sbQuery.append("	count(*) as endpointcount					");
		sbQuery.append(" from											");
		sbQuery.append("	bpm_rolemapping								");
		sbQuery.append(" where instid=").append(instanceId).append("	");
		sbQuery.append(" and rolename='").append(roleName).append("'	");
		
		int endpointCount = 0;

		try {
			conn = DefaultConnectionFactory.create().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sbQuery.toString());

			if (rs.next()) {
				endpointCount = rs.getInt("endpointcount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return endpointCount;
	}
	
	private void setVariableValue(ProcessInstance instance, HashMap processVariableMap) throws Exception {
		
		if (processVariableMap != null) {
			for (Iterator iter = processVariableMap.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				Object value = (Object) processVariableMap.get(key);

				if (instance.getProcessDefinition().getProcessVariable(key) != null) {
					if (value instanceof String) {
						instance.set("", key, (String) value);

					} else if (value instanceof ArrayList) {
						ArrayList items = (ArrayList) value;
						ProcessVariableValue pvv = new ProcessVariableValue();
						for (int i = 0; i < items.size(); i++) {
							Object item = (Object) items.get(i);
							if (item instanceof String) {
								pvv.setValue(item);
								if (i != items.size() - 1)
									pvv.moveToAdd();
							}
						}
						instance.set("", key, (ProcessVariableValue) pvv);
					}
				}
			}
		}

	}
	
	public HashMap getProcessVariable(String instanceId, ProcessManagerRemote pm) {
		HashMap processVariableMap = null;
		ProcessInstance instance = null;

		try {
			instance = pm.getProcessInstance(instanceId);
			ProcessVariable[] processVariables = instance.getProcessDefinition().getProcessVariables();
			
			processVariableMap = new HashMap();
			
			if (processVariables != null) {
				for (int i = 0; i < processVariables.length; i++) {
					ProcessVariable processVariable = processVariables[i];
					String key = processVariable.getName();
					Object value = processVariable.get(instance, "", key);
					if (value instanceof String) {
						processVariableMap.put(key, value != null ? (String) value : null);
					} else if (value instanceof ProcessVariableValue) {
						processVariableMap.put(key, value != null ? (ProcessVariableValue) value : null);
					} else {
						processVariableMap.put(key, value != null ? (String) String.valueOf(value) : null);
					}
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return processVariableMap;
	}

	public String completeWorkitem(String endpoint, String instanceId, String taskId, String tracingTag, HashMap processVariableMap, ProcessManagerRemote pm) {

		String workedTaskId = null;
		
		try {
			RoleMapping loggedRoleMapping = RoleMapping.create();
			loggedRoleMapping.setEndpoint(endpoint);

			Map genericContext = new HashMap();
			genericContext.put(HumanActivity.GENERICCONTEXT_CURR_LOGGED_ROLEMAPPING, loggedRoleMapping);

			ProcessInstance instance = pm.getProcessInstance(instanceId);

			setVariableValue(instance, processVariableMap);

			pm.setGenericContext(genericContext);
			pm.completeWorkitem(instanceId, tracingTag, taskId, new org.uengine.kernel.ResultPayload());

			workedTaskId = taskId;

		} catch (Exception e) {
			e.getStackTrace();
		}

		return workedTaskId;
	}
	
	public String startProcess(String type, String alias, String initiator, HashMap processVariableMap, ProcessManagerRemote pm) {
		
		String instanceId = null;
		
		try {
			RoleMapping loggedRoleMapping = RoleMapping.create();
			loggedRoleMapping.setEndpoint(initiator);

			Map genericContext = new HashMap();
			genericContext.put(HumanActivity.GENERICCONTEXT_CURR_LOGGED_ROLEMAPPING, loggedRoleMapping);

			pm.setGenericContext(genericContext);

			String defVerId = pm.getProcessDefinitionProductionVersionByAlias(alias);
			instanceId = pm.initializeProcess(defVerId);
			ProcessInstance instance = pm.getProcessInstance(instanceId);

			setVariableValue(instance, processVariableMap);

			if ("executeProcessByWorkitem".equals(type) || !UEngineUtil.isNotEmpty(type)) {
				pm.executeProcessByWorkitem(instanceId, new org.uengine.kernel.ResultPayload());
			} else if ("executeProcess".equals(type)) {
				pm.executeProcess(instanceId);
			}
			
		} catch (Exception e) {
			e.getStackTrace();
		}

		return instanceId;
	}
	
	public String setProcessVariable(String instanceId, HashMap processVariableMap, ProcessManagerRemote pm) {

		String workedInstanceId = null;
		
		try {
			
			ProcessInstance instance = pm.getProcessInstance(instanceId);
			setVariableValue(instance, processVariableMap);

			workedInstanceId = instanceId;
			
		} catch (Exception e) {
			e.getStackTrace();
		}

		return workedInstanceId;
	}
	
	public List nextTask(String requestInstanceId) {
		if (!UEngineUtil.isNotEmpty(requestInstanceId)) {
			return null;
		}
		
		StringBuffer sqlStmt = new StringBuffer();

		sqlStmt.append("	SELECT							");
		sqlStmt.append("		rolem.endpoint,				");
		sqlStmt.append("		wl.instid,					");
		sqlStmt.append("		wl.rootinstid,				");
		sqlStmt.append("		wl.trctag,					");
		sqlStmt.append("		wl.taskid,					");
		sqlStmt.append("		wl.rolename,				");
		sqlStmt.append("		inst.info					");
		sqlStmt.append("	FROM							");
		sqlStmt.append("		bpm_procinst inst,			");
		sqlStmt.append("		bpm_worklist wl,			");
		sqlStmt.append("		bpm_rolemapping rolem		");
		sqlStmt.append("	WHERE inst.rootinstid=(SELECT rootinstid FROM bpm_procinst WHERE instid=").append(requestInstanceId).append(")	");
		sqlStmt.append("	AND inst.status='Running' AND inst.instid=wl.instid AND wl.status='NEW' AND inst.isdeleted=0					");
		sqlStmt.append("	AND rolem.rolename=wl.rolename AND rolem.instid=wl.instid														");

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		List _nextTask = null;

		try {
			conn = DefaultConnectionFactory.create().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStmt.toString());

			_nextTask = new ArrayList();

			while (rs.next()) {
				NextTask nextTask = new NextTask();
				nextTask.setEndpoint(rs.getString("ENDPOINT"));
				nextTask.setInstanceId(String.valueOf(rs.getInt("INSTID")));
				nextTask.setRootInstanceId(String.valueOf(rs.getInt("ROOTINSTID")));
				nextTask.setTracingTag(String.valueOf(rs.getString("TRCTAG")));
				nextTask.setTaskId(String.valueOf(rs.getInt("TASKID")));
				nextTask.setInfo(rs.getString("INFO"));
				
				_nextTask.add(nextTask);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}

		return _nextTask;
	}
	
	/*
	public List nextTask(String requestInstanceId, ProcessManagerRemote pm) {
		if (!UEngineUtil.isNotEmpty(requestInstanceId)) {
			return null;
		}
		
		String sqlStmt = null;
		
		sqlStmt = "select wl.*, inst.info, inst.rootinstid from bpm_procinst inst, bpm_worklist wl";
		sqlStmt += " where inst.rootinstid=(select rootinstid from bpm_procinst where instid=" + requestInstanceId + ")"
				+ " and inst.status='Running' and inst.instid=wl.instid and wl.status='NEW' and inst.isdeleted=0";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		List workList = null;
		List _nextTask = null;

		try {
			conn = DefaultConnectionFactory.create().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStmt);

			workList = new ArrayList();

			while (rs.next()) {
				WorkList wl = new WorkList();
				wl.setEndpoint(String.valueOf(rs.getString("endpoint")));
				wl.setInstanceId(String.valueOf(rs.getInt("INSTID")));
				wl.setRootInstanceId(String.valueOf(rs.getInt("ROOTINSTID")));
				wl.setTracingTag(String.valueOf(rs.getString("TRCTAG")));
				wl.setTaskId(String.valueOf(rs.getInt("TASKID")));
				wl.setInfo(String.valueOf(rs.getString("INFO")));
				wl.setRoleName(String.valueOf(rs.getString("ROLENAME")));
				workList.add(wl);
			}
			
			rs.close();
//			stmt.close();
//			conn.close();

			_nextTask = new ArrayList();

			for (int i = 0; i < workList.size(); i++) {
				WorkList _wl = (WorkList) workList.get(i);

				sqlStmt = "select ENDPOINT from bpm_rolemapping where rolename='" + _wl.getRoleName() + "' and instid=" + _wl.getInstanceId();
//				conn = DefaultConnectionFactory.create().getConnection();
//				stmt = conn.createStatement();
				rs = stmt.executeQuery(sqlStmt);

				while (rs.next()) {
					String endpoint = String.valueOf(rs.getString("ENDPOINT"));
					NextTask nextTask = new NextTask();
					nextTask.setEndpoint(endpoint);
					nextTask.setInstanceId(_wl.getInstanceId());
					nextTask.setRootInstanceId(_wl.getRootInstanceId());
					nextTask.setTaskId(_wl.getTaskId());
					nextTask.setTracingTag(_wl.getTracingTag());

					nextTask.setInfo(_wl.getInfo());

					_nextTask.add(nextTask);
				}
				
				rs.close();
//				stmt.close();
//				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}

		return _nextTask;
	}
	*/

	public String workItemAccept(String instanceId, String tracingTag, String endPoint, ProcessManagerRemote pm) {
		String workItemAcceptedEndpoint = null;
		
		try {
			RoleMapping rm = RoleMapping.create();
			rm.setEndpoint(endPoint);

			String[] taskIds = pm.delegateWorkitem(instanceId, tracingTag, rm);

			workItemAcceptedEndpoint = endPoint;
		} catch (Exception e) {
			e.getStackTrace();
		}

		return workItemAcceptedEndpoint;
	}
	
	public String getRoleMapping(String in0, String in1) {
		return null;
	}
	
	public String initializeProcess(String in0, String in1) {
		return null;
	}

	public boolean putRoleMapping(String instanceId, RoleMapping roleMapping, ProcessManagerRemote pm) {
		boolean isSuccess = false;
		try {
			pm.putRoleMapping(instanceId, roleMapping);
			pm.delegateRoleMapping(instanceId, roleMapping);
			isSuccess = true;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

}
