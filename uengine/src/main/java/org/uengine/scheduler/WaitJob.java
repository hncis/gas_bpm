package org.uengine.scheduler;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.RemoveException;

import org.quartz.StatefulJob;

import org.quartz.JobExecutionContext;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.TransactionListener;
import org.uengine.kernel.WaitActivity;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.processmanager.TransactionContext;
import org.uengine.util.dao.DefaultConnectionFactory;

public class WaitJob implements StatefulJob {
	
	public void execute(JobExecutionContext context) {
		
//		System.out.println("scheduler WaitJob execute() start...");
		
		ProcessManagerFactoryBean pmfb = new ProcessManagerFactoryBean();
		
		Calendar now = Calendar.getInstance();
		
		List<SchedulerItem> schedulerItems = this.getAllSchedule();
		
		for (final SchedulerItem item : schedulerItems) {
			
			if (!(item.getStartDate().getTime() <= now.getTimeInMillis())) {
				continue;
			}
			
			ProcessManagerRemote pm = null;
			ProcessInstance instance = null;
			
			try {
				pm = pmfb.getProcessManager();
				try {
					instance = pm.getProcessInstance(item.getInstanceId());
				} catch (Exception e) {
				}
				
				boolean isError = true;
				
				if (instance != null) {
					Activity act = instance.getProcessDefinition().getActivity(item.getTracingTag());

					if (act != null && act instanceof WaitActivity) {
						String status = act.getStatus(instance);
						if (Activity.STATUS_RUNNING.equals(status) || Activity.STATUS_TIMEOUT.equals(status)) {

							instance.getProcessTransactionContext().addTransactionListener(new TransactionListener() {

								public void beforeRollback(TransactionContext tx) throws Exception {
								}

								public void beforeCommit(TransactionContext tx) throws Exception {
								}

								public void afterRollback(TransactionContext tx) throws Exception {
								}

								public void afterCommit(TransactionContext tx) throws Exception {
									deleteSchedule(item.getIdx());
								}
							});

							act.fireComplete(instance);
							isError = false;

						} else if (Activity.STATUS_FAULT.equals(status)
								|| Activity.STATUS_READY.equals(status) 
								|| Activity.STATUS_STOPPED.equals(status) || Activity.STATUS_CANCELLED.equals(status) ) {
							
							continue;
							
						}
					}
				}
				
				if (isError) {
					deleteSchedule(item.getIdx());
				}
				
				pm.applyChanges();	
			
			} catch (Exception e) {
				try {
					pm.cancelChanges();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} finally{
				try {
					pm.remove();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (RemoveException e) {
					e.printStackTrace();
				}
			}
		}
		
//		System.out.println("scheduler WaitJob execute() end...");
	}
	
	public List<SchedulerItem> getAllSchedule() {
		
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<SchedulerItem> schedulerItems = new ArrayList<SchedulerItem>();
        
        try {
            conn = DefaultConnectionFactory.create().getConnection();
            stmt = conn.createStatement();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT "); 
            sql.append("	SCHEDULE_TABLE.SCHE_IDX, "); 
            sql.append("	SCHEDULE_TABLE.INSTID, "); 
            sql.append("	SCHEDULE_TABLE.TRCTAG, "); 
            sql.append("	SCHEDULE_TABLE.STARTDATE, "); 
            sql.append("	BPM_PROCINST.STATUS, "); 
            sql.append("	BPM_PROCINST.ISDELETED "); 
            sql.append("FROM SCHEDULE_TABLE JOIN BPM_PROCINST ON SCHEDULE_TABLE.INSTID = BPM_PROCINST.INSTID "); 
            sql.append("WHERE "); 
            sql.append("	BPM_PROCINST.ISDELETED = 0 "); 
            sql.append("	AND BPM_PROCINST.STATUS = 'Running' ");
            
            rs = stmt.executeQuery(sql.toString());
            
            while (rs.next()) {
            	SchedulerItem item = new SchedulerItem();
            	
            	item.setIdx(rs.getInt("SCHE_IDX"));
            	item.setInstanceId(rs.getString("INSTID"));
            	item.setTracingTag(rs.getString("TRCTAG"));
            	item.setStartDate(rs.getTimestamp("STARTDATE"));
            	
            	schedulerItems.add(item);
            }

        } catch (Exception e){
        	e.printStackTrace();
        } finally {
        	if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) { }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) { }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { }
            }
        }
     
        return schedulerItems;
	}
	
	/**
	 * Delete schedule in DB.
	 * 
	 * @param instance the instance
	 * @param tracintTag the tracing tag
	 */
	public void deleteSchedule(int idx) throws Exception {
		// delete schedule information from DB. 
		Connection conn = null;
        Statement stmt = null;
        
        try {
            conn = DefaultConnectionFactory.create().getConnection();
            stmt = conn.createStatement();
            
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM SCHEDULE_TABLE WHERE SCHE_IDX=").append(idx);
            
            stmt.executeUpdate(sql.toString());

        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) { }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { }
            }
        }
	}
	
}

