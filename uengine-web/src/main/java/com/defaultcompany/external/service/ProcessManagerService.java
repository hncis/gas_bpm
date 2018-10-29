package com.defaultcompany.external.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.util.StringUtils;
import org.uengine.kernel.Activity;
import org.uengine.kernel.AllActivity;
import org.uengine.kernel.BackActivity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.EndActivity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.KeyedParameter;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariableValue;
import org.uengine.kernel.RoleMapping;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.util.ActivityForLoop;
import org.uengine.util.dao.DefaultConnectionFactory;

import com.defaultcompany.external.activities.ExternalApprovalActivity;
import com.defaultcompany.external.activities.ExternalApprovalLineActivity;
import com.defaultcompany.external.model.ApprovalLine;
import com.defaultcompany.external.model.Approver;
import com.defaultcompany.external.model.ProcessVariable;
import com.defaultcompany.external.model.Role;
import com.defaultcompany.external.model.TaskInfo;
import com.defaultcompany.external.model.stdmsg.ActivityCompleteMsg;
import com.defaultcompany.external.model.stdmsg.ApprovalActivityCompleteMsg;
import com.defaultcompany.external.model.stdmsg.ApprovalDraftActivityCompleteMsg;
import com.defaultcompany.external.model.stdmsg.ApprovalLineChangeMsg;
import com.defaultcompany.external.model.stdmsg.BaseStdMsg;
import com.defaultcompany.external.model.stdmsg.DelegateWorkItemMsg;
import com.defaultcompany.external.model.stdmsg.InitializeProcessInstanceMsg;
import com.defaultcompany.external.model.stdmsg.ProcessStartMsg;
import com.defaultcompany.external.model.stdmsg.ProcessStopMsg;
import com.defaultcompany.external.model.stdmsg.TaskInfoMsg;
import com.defaultcompany.external.model.stdmsg.WorkItemAcceptMsg;
import com.defaultcompany.external.repository.ApprovalLineDAO;
import com.defaultcompany.external.repository.ApproverDAO;
import com.defaultcompany.external.repository.TaskInfoDAO;


public class ProcessManagerService {
	
	private ProcessManagerRemote pm;
	private HttpServletRequest request;
		
	public ProcessManagerService(ProcessManagerRemote pm, HttpServletRequest request) {
		this.pm = pm;
		this.request = request;
	}
	
	public void initializeProcessInstance(InitializeProcessInstanceMsg ipiMsg) throws Exception {
		ProcessInstance instance = pm.getProcessInstance(ipiMsg.getInstanceId());
		this.bindProcessInfo(instance, ipiMsg);
		instance.getActualInitiatorHumanActivity().getActivity().compensateToThis(instance);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String processStart(ProcessStartMsg psMsg) throws Exception {
		RoleMapping loggedRoleMapping = RoleMapping.create();
		loggedRoleMapping.setEndpoint(psMsg.getInitiator());
		loggedRoleMapping.fill(null);

		Map genericContext = new HashMap();
		genericContext.put(HumanActivity.GENERICCONTEXT_CURR_LOGGED_ROLEMAPPING, loggedRoleMapping);
		genericContext.put("request", request);
		pm.setGenericContext(genericContext);

		String defVerId = pm.getProcessDefinitionProductionVersionByAlias(psMsg.getProcAlias());
		String instanceId = pm.initializeProcess(defVerId);
		ProcessInstance instance = pm.getProcessInstance(instanceId);
		if (StringUtils.hasText(psMsg.getInstanceName())) {
			instance.setName(psMsg.getInstanceName());
		}
		this.bindProcessInfo(instance, psMsg);
		
		HumanActivity initiatorHumanActivity = (HumanActivity) instance.getProcessDefinition().getInitiatorHumanActivityReference(instance).getActivity();
		
		if (initiatorHumanActivity instanceof ExternalApprovalActivity) {
			
			pm.executeProcess(instanceId);
			
			// 결재선 정보 가져오기
			ApproverDAO approverDAO = new ApproverDAO(instance.getProcessTransactionContext());
			ApprovalLineDAO approvalLineDAO = new ApprovalLineDAO(instance.getProcessTransactionContext());
			ApprovalLine al = approvalLineDAO.get(psMsg.getApprovalKey());
			al.setLineStatus(ExternalApprovalActivity.SIGN_DRAFT);
			al.setInstanceId(Integer.parseInt(instanceId));
			
			List<Approver> approverList = approverDAO.getList(psMsg.getApprovalKey());
			List<HumanActivity> approvalActs = new ArrayList<HumanActivity>();
			
			// 결재선 다이나믹 체인지 Start
			ProcessDefinition tempPd = (ProcessDefinition) instance.getProcessDefinition().clone();
			HumanActivity draftAct = (HumanActivity) tempPd.getInitiatorHumanActivityReference(instance).getActivity();
			if (StringUtils.hasText(psMsg.getMainParam())) {
				draftAct.setExtValue1(psMsg.getMainParam());
			}
			if (StringUtils.hasText(psMsg.getSubParam())) {
				draftAct.setExtValue2(psMsg.getSubParam());
			}
			Activity approvalLineAct = draftAct.getParentActivity();
			while (!(approvalLineAct instanceof ExternalApprovalLineActivity)) {
				approvalLineAct = approvalLineAct.getParentActivity();
			}
			
			ComplexActivity parent = (ComplexActivity) approvalLineAct;
			if (parent.getChildActivities().size() > 1) {
				parent.getChildActivities().clear();
				parent.addChildActivity(draftAct);
			}
			
			String preApprovalType = null;
			for (Approver approver : approverList) {
				if (ExternalApprovalActivity.APPROVAL_TYPE_DRAFT.equalsIgnoreCase(approver.getType())) {
					approver.setTracingTag(draftAct.getTracingTag());
					approver.setTaskId(draftAct.getTaskIds(instance)[0]);
					approver.setTruthEmpCode(approver.getEmpCode());
					approver.setStatus(ExternalApprovalActivity.SIGN_DRAFT);
					approver.setEndDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					approver.setComment(psMsg.getComment());
					approvalActs.add(draftAct);
					continue;
				}
				ExternalApprovalActivity childAct = (ExternalApprovalActivity) draftAct.clone();
				childAct.setName("결재");
				childAct.setTracingTag(null);
				childAct.setRole(null);
				childAct.setApprovalTypeByString(approver.getType());
				
				RoleMapping rm = RoleMapping.create();
				rm.setEndpoint(approver.getEmpCode());
				rm.fill(instance);
				
				// 결재 -> 합의
				if (!ExternalApprovalActivity.APPROVAL_TYPE_COOPERATION.equals(preApprovalType) && ExternalApprovalActivity.APPROVAL_TYPE_COOPERATION.equals(approver.getType())) {
					AllActivity allAct = new AllActivity();
					allAct.setTracingTag(null);
					
					parent.addChildActivity(allAct);
					parent = allAct;
				// 합의 -> 결재
				} else if (ExternalApprovalActivity.APPROVAL_TYPE_COOPERATION.equals(preApprovalType) && !ExternalApprovalActivity.APPROVAL_TYPE_COOPERATION.equals(approver.getType())) {
					parent = (ComplexActivity) approvalLineAct;
				}
				
				parent.addChildActivity(childAct);
				childAct.setApprover(instance, rm);
				
				approver.setTracingTag(childAct.getTracingTag());
				
				preApprovalType = approver.getType();
				approvalActs.add(childAct);
			}
			
//			tempPd.registerToProcessDefinition(false, false);
			pm.changeProcessDefinition(instanceId, tempPd);
			// 결재선 다이나믹 체인지 End
			
			instance.setProperty(approvalLineAct.getTracingTag(), ExternalApprovalLineActivity.KEY_APPR_KEY, psMsg.getApprovalKey());
			approvalLineDAO.update(al);
			approverDAO.update(approverList);
			
			if (psMsg.isFirstTaskCompleted()) {
				pm.completeWorkitem(instanceId, draftAct.getTracingTag(), null, new org.uengine.kernel.ResultPayload());
			}
			
		} else {
			
			if (psMsg.isFirstTaskCompleted()) {
				pm.executeProcessByWorkitem(instanceId, new org.uengine.kernel.ResultPayload());
			} else {
				pm.executeProcess(instanceId);
			}
			
		}
		
		return instanceId;
	}
	
	public void processStop(ProcessStopMsg psMsg) throws Exception {
		ProcessInstance instance = pm.getProcessInstance(psMsg.getInstanceId());
		
		EndActivity endActivity = new EndActivity();
		endActivity.setEscalate(true);
		
		String instanceStatus = psMsg.getInstanceStatus();
		if (ProcessStopMsg.INSTANCE_STATUS_CANCELLED.equalsIgnoreCase(instanceStatus)) {
			endActivity.setStatus(EndActivity.STATUS_CANCELLED);
			
		} else if (ProcessStopMsg.INSTANCE_STATUS_COMPLETED.equalsIgnoreCase(instanceStatus)) {
			endActivity.setStatus(EndActivity.STATUS_COMPLETED);
			
		} else if (ProcessStopMsg.INSTANCE_STATUS_FAULT.equalsIgnoreCase(instanceStatus)) {
			endActivity.setStatus(EndActivity.STATUS_FAULT);
			
		} else if (ProcessStopMsg.INSTANCE_STATUS_STOPPED.equalsIgnoreCase(instanceStatus)) {
			endActivity.setStatus(EndActivity.STATUS_STOPPED);
			
		} else {
			endActivity.setStatus(EndActivity.STATUS_COMPLETED);
		}
		
		endActivity.executeActivity(instance);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void activityComplete(ActivityCompleteMsg acMsg) throws Exception {
		Map genericContext = new HashMap();
		genericContext.put("request", request);
	
		ProcessInstance instance = pm.getProcessInstance(acMsg.getInstanceId());
		this.bindProcessInfo(instance, acMsg);
		
		HumanActivity hm = (HumanActivity) pm.getProcessInstance(acMsg.getInstanceId()).getProcessDefinition().getActivity(acMsg.getTracingTag());
		RoleMapping roleMapping = RoleMapping.create();
		roleMapping.setEndpoint(acMsg.getEndpoint());
		roleMapping.setName(hm.getRole().getName());
		roleMapping.fill(instance);
//		pm.putRoleMapping(acMsg.getInstanceId(), roleMapping);
//		pm.delegateRoleMapping(acMsg.getInstanceId(), roleMapping);
		
		pm.setGenericContext(genericContext);
		pm.completeWorkitem(acMsg.getInstanceId(), acMsg.getTracingTag(), acMsg.getTaskId(), new org.uengine.kernel.ResultPayload());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void approvalDraftActivityComplete(ApprovalDraftActivityCompleteMsg adacMsg) throws Exception {
		
		RoleMapping loggedRoleMapping = RoleMapping.create();
		loggedRoleMapping.setEndpoint(adacMsg.getEndpoint());
		loggedRoleMapping.fill(null);
		
		Map genericContext = new HashMap();
		genericContext.put(HumanActivity.GENERICCONTEXT_CURR_LOGGED_ROLEMAPPING, loggedRoleMapping);
		genericContext.put("request", request);
		pm.setGenericContext(genericContext);
		
		ProcessInstance instance = pm.getProcessInstance(adacMsg.getInstanceId());
		this.bindProcessInfo(instance, adacMsg);
		
		// 결재선 다이나믹 체인지 Start
		ApproverDAO approverDAO = new ApproverDAO(instance.getProcessTransactionContext());
		List<Approver> approverList = approverDAO.getList(adacMsg.getApprovalKey());
		
		ApprovalLineDAO approvalLineDAO = new ApprovalLineDAO(instance.getProcessTransactionContext());
		ApprovalLine approvalLine = approvalLineDAO.get(adacMsg.getApprovalKey());
		approvalLine.setLineStatus(ExternalApprovalActivity.SIGN_DRAFT);
		
		
		ProcessDefinition tempPd = (ProcessDefinition) instance.getProcessDefinition().clone();
		HumanActivity draftAct = (HumanActivity) tempPd.getActivity(adacMsg.getTracingTag());
		if (StringUtils.hasText(adacMsg.getMainParam())) {
			draftAct.setExtValue1(adacMsg.getMainParam());
		}
		if (StringUtils.hasText(adacMsg.getSubParam())) {
			draftAct.setExtValue2(adacMsg.getSubParam());
		}
		Activity approvalLineAct = draftAct.getParentActivity();
		while (!(approvalLineAct instanceof ExternalApprovalLineActivity)) {
			approvalLineAct = approvalLineAct.getParentActivity();
		}
		
		ComplexActivity parent = (ComplexActivity) approvalLineAct;
		if (parent.getChildActivities().size() > 1) {
			parent.getChildActivities().clear();
			parent.addChildActivity(draftAct);
		}
		
		String preApprovalType = null;
		for (Approver approver : approverList) {
			if (ExternalApprovalActivity.APPROVAL_TYPE_DRAFT.equalsIgnoreCase(approver.getType())) {
				approver.setTracingTag(draftAct.getTracingTag());
				approver.setTaskId(adacMsg.getTaskId());
				approver.setTruthEmpCode(approver.getEmpCode());
				approver.setStatus(ExternalApprovalActivity.SIGN_DRAFT);
				approver.setEndDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				approver.setComment(adacMsg.getComment());
				continue;
			}
			ExternalApprovalActivity childAct = (ExternalApprovalActivity) draftAct.clone();
			childAct.setName("결재");
			childAct.setTracingTag(null);
			childAct.setRole(null);
			childAct.setApprovalTypeByString(approver.getType());
			
			RoleMapping rm = RoleMapping.create();
			rm.setEndpoint(approver.getEmpCode());
			rm.fill(instance);
			
			// 결재 -> 합의
			if (!ExternalApprovalActivity.APPROVAL_TYPE_COOPERATION.equals(preApprovalType) && ExternalApprovalActivity.APPROVAL_TYPE_COOPERATION.equals(approver.getType())) {
				AllActivity allAct = new AllActivity();
				allAct.setTracingTag(null);
				
				parent.addChildActivity(allAct);
				parent = allAct;
			// 합의 -> 결재
			} else if (ExternalApprovalActivity.APPROVAL_TYPE_COOPERATION.equals(preApprovalType) && !ExternalApprovalActivity.APPROVAL_TYPE_COOPERATION.equals(approver.getType())) {
				parent = (ComplexActivity) approvalLineAct;
			}
			
			parent.addChildActivity(childAct);
			childAct.setApprover(instance, rm);
			
			approver.setTracingTag(childAct.getTracingTag());
			
			preApprovalType = approver.getType();
		}
		
		pm.changeProcessDefinition(adacMsg.getInstanceId(), tempPd);
		// 결재선 다이나믹 체인지 End
		
		instance.setProperty(approvalLineAct.getTracingTag(), ExternalApprovalLineActivity.KEY_APPR_KEY, adacMsg.getApprovalKey());
		approvalLineDAO.update(approvalLine);
		approverDAO.update(approverList);
		
		pm.completeWorkitem(adacMsg.getInstanceId(), adacMsg.getTracingTag(), adacMsg.getTaskId(), new org.uengine.kernel.ResultPayload());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void approvalActivityComplete(ApprovalActivityCompleteMsg aacMsg) throws Exception {
		RoleMapping loggedRoleMapping = RoleMapping.create();
		loggedRoleMapping.setEndpoint(aacMsg.getEndpoint());
		loggedRoleMapping.fill(null);
		
		Map genericContext = new HashMap();
		genericContext.put(HumanActivity.GENERICCONTEXT_CURR_LOGGED_ROLEMAPPING, loggedRoleMapping);
		genericContext.put("request", request);
		pm.setGenericContext(genericContext);
	
		ProcessInstance instance = pm.getProcessInstance(aacMsg.getInstanceId());
		this.bindProcessInfo(instance, aacMsg);
		
		// 결재선 업데이트
		ApprovalLineDAO approvalLineDAO = new ApprovalLineDAO(instance.getProcessTransactionContext());
		ApprovalLine approvalLine = approvalLineDAO.get(aacMsg.getApprovalKey());
		approvalLine.setLineStatus(aacMsg.getApprovalStatus());
		approvalLineDAO.update(approvalLine);
		
		// 승인
		if (ExternalApprovalActivity.SIGN_APPROVED.equalsIgnoreCase(aacMsg.getApprovalStatus())) {
			
			ExternalApprovalActivity approvalAct = (ExternalApprovalActivity) instance.getProcessDefinition().getActivity(aacMsg.getTracingTag());
			approvalAct.setApprovalStatus(instance, ExternalApprovalActivity.SIGN_APPROVED);
			
			pm.completeWorkitem(aacMsg.getInstanceId(), aacMsg.getTracingTag(), aacMsg.getTaskId(), new org.uengine.kernel.ResultPayload());
			
		// 반려
		} else if (ExternalApprovalActivity.SIGN_REJECT.equalsIgnoreCase(aacMsg.getApprovalStatus())) {
			
			ExternalApprovalActivity approvalAct = (ExternalApprovalActivity) instance.getProcessDefinition().getActivity(aacMsg.getTracingTag());
			approvalAct.setApprovalStatus(instance, ExternalApprovalActivity.SIGN_REJECT);
			
			instance.getWorkList().completeWorkItem(aacMsg.getTaskId(), new KeyedParameter[]{}, instance.getProcessTransactionContext());
			
			//반려시에는 ExternalApprovalActivity 의 afterComplete 를 타지 않는다.(바로 윗줄에 보면 worklist 테이블만 update 시킨다.) 따라서 수동 update
			Calendar endTime = GlobalContext.getNow(instance.getProcessTransactionContext());
			
			ApproverDAO approverDAO = new ApproverDAO(instance.getProcessTransactionContext());
			Approver approver = approverDAO.getByInstanceIdAndTracingTag(aacMsg.getApprovalKey(), Integer.parseInt(instance.getInstanceId()), aacMsg.getTracingTag());
			approver.setTruthEmpCode(aacMsg.getEndpoint());
			approver.setTaskId(approvalAct.getTaskIds(instance)[0]);
			approver.setStatus(ExternalApprovalActivity.SIGN_REJECT);
			approver.setEndDate(new Timestamp(endTime.getTimeInMillis()));
			approver.setComment(aacMsg.getComment());
			approverDAO.update(approver);
			
			
			//반려 로직
			ExternalApprovalLineActivity approvalLineAct = approvalAct.getApprovalLineActivity();
			int rejectOption = approvalLineAct.getRejectOption();
			
			// 기안 단계로 반려
			if (rejectOption == ExternalApprovalLineActivity.LOOPING_OPTION_BACK_TO_DRAFT) {
				approvalLineAct.getDraftActivity().compensateToThis(instance);
				
				// 결재 DB update
				List<Approver> _approverList = approverDAO.getList(aacMsg.getApprovalKey());
				int version = _approverList.get(0).getVersion() + 1;
				for (int i = 0; i < _approverList.size(); i++) {
					Approver _approver = _approverList.get(i);
					_approver.setVersion(version);
					_approver.setComment(null);
					_approver.setEndDate(null);
					_approver.setTruthEmpCode(null);
					_approver.setStatus(null);
					
					if (i > 0) {
						_approver.setTracingTag(null);
						_approver.setTaskId(null);
					}
				}
				approvalLine.setVersion(version);
				approvalLineDAO.update(approvalLine);
				approverDAO.add(_approverList);
			
			// 바로 이전 결재로 반려
			} else if (rejectOption == ExternalApprovalLineActivity.LOOPING_OPTION_BACK_TO_PREV) {
				List<Activity> childActs = approvalLineAct.getChildActivities();
				Activity currentAct = instance.getProcessDefinition().getActivity(aacMsg.getTracingTag());
				int prevIndex = 0;
				if (currentAct.getParentActivity() instanceof AllActivity) {
					prevIndex = childActs.indexOf(currentAct.getParentActivity()) - 1;	
				} else {
					prevIndex = childActs.indexOf(approvalAct) - 1;
				}
				
				Activity prevAct = childActs.get(prevIndex);
				
				Approver prevApprover = approverDAO.getByInstanceIdAndTracingTag(aacMsg.getApprovalKey(), Integer.parseInt(instance.getInstanceId()), prevAct.getTracingTag());
				
				prevAct.compensateToThis(instance);
				
				// ExternalApprovalActivity 의 afterExecute() 를 타기 때문에 강제로 다시 update
				approverDAO.update(prevApprover);
				
				
				// 결재 DB update
				List<Approver> _approverList = approverDAO.getList(aacMsg.getApprovalKey());
				int version = _approverList.get(0).getVersion() + 1;
				for (int i = 0; i < _approverList.size(); i++) {
					Approver _approver = _approverList.get(i);
					_approver.setVersion(version);
					
					if ( aacMsg.getTracingTag().equalsIgnoreCase(_approver.getTracingTag()) && 
							_approver.getTaskId().equalsIgnoreCase(aacMsg.getTaskId()) ) {
						
						// 현재 결재자
						_approver.setComment(null);
						_approver.setEndDate(null);
						_approver.setTruthEmpCode(null);
						_approver.setStatus(null);
						_approver.setTaskId(null);
						
						// 이전 결재자
						Approver _prevApprover = _approverList.get(i - 1);
						_prevApprover.setComment(null);
						_prevApprover.setEndDate(null);
						_prevApprover.setTruthEmpCode(null);
						_prevApprover.setStatus(null);
						
					}
				}
				approvalLine.setVersion(version);
				approvalLineDAO.update(approvalLine);
				approverDAO.add(_approverList);
			
			// 플래그로 반려
			} else if (rejectOption == ExternalApprovalLineActivity.LOOPING_OPTION_BACK_TO_FLAG) {
				BackActivity backActivity = new BackActivity() {
					@Override
					public void fireComplete(ProcessInstance instance) throws Exception {
						
					}
					
				};
				backActivity.setTargetSource(BackActivity.Flag);
				backActivity.setFlag(approvalLineAct.getFlag());
				backActivity.executeActivity(instance);
				
			// 결재선 종료
			} else if (rejectOption == ExternalApprovalLineActivity.LOOPING_OPTION_FINISH) {
				approvalAct.setEndTime(instance, endTime);
				approvalAct.getApprovalLineActivity().fireComplete(instance);
				
			}
			
		
		// 전결
		} else if (ExternalApprovalActivity.SIGN_ARBITRARY_APPROVED.equalsIgnoreCase(aacMsg.getApprovalStatus())) {
			
			ExternalApprovalActivity approvalAct = (ExternalApprovalActivity) instance.getProcessDefinition().getActivity(aacMsg.getTracingTag());
			approvalAct.setApprovalStatus(instance, ExternalApprovalActivity.SIGN_ARBITRARY_APPROVED);
			
			String taskId = approvalAct.getTaskIds(instance)[0];
			Calendar endTime = GlobalContext.getNow(instance.getProcessTransactionContext());
			instance.getWorkList().completeWorkItem(taskId, new KeyedParameter[]{}, instance.getProcessTransactionContext());
			approvalAct.setEndTime(instance, endTime);

			//전결시에는 NavienApprovalActivity 의 afterComplete 를 타지 않는다.(바로 윗줄에 보면 worklist 테이블만 update 시킨다.) 따라서 수동 update
			ApproverDAO approverDAO = new ApproverDAO(instance.getProcessTransactionContext());
			Approver approver = approverDAO.getByInstanceIdAndTracingTag(aacMsg.getApprovalKey(), Integer.parseInt(instance.getInstanceId()), aacMsg.getTracingTag());
			approver.setTruthEmpCode(aacMsg.getEndpoint());
			approver.setTaskId(taskId);
			approver.setStatus(ExternalApprovalActivity.SIGN_ARBITRARY_APPROVED);
			approver.setEndDate(new Timestamp(endTime.getTimeInMillis()));
			approver.setComment(aacMsg.getComment());
			approverDAO.update(approver);
			
			approvalAct.getApprovalLineActivity().fireComplete(instance);
			
		}		
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void approvalLineChange(ApprovalLineChangeMsg alcMsg) throws Exception {
		Map genericContext = new HashMap();
		genericContext.put("request", request);
		pm.setGenericContext(genericContext);
		
		ProcessInstance instance = pm.getProcessInstance(alcMsg.getInstanceId());
		this.bindProcessInfo(instance, alcMsg);
		
		ApproverDAO approverDAO = new ApproverDAO(instance.getProcessTransactionContext());
		List<Approver> approverList = approverDAO.getList(alcMsg.getApprovalKey());
	
		ProcessDefinition tempPd = (ProcessDefinition) instance.getProcessDefinition().clone();
		Activity draftAct = tempPd.getActivity(approverList.get(0).getTracingTag());
		Activity approvalLineAct = draftAct.getParentActivity();
		while (!(approvalLineAct instanceof ExternalApprovalLineActivity)) {
			approvalLineAct = approvalLineAct.getParentActivity();
		}
		
		ComplexActivity parent = (ComplexActivity) approvalLineAct;
		final Vector childActs = new Vector();
		ActivityForLoop forLoop = new ActivityForLoop() {
			public void logic(Activity act) {
				try {
					if (act instanceof ExternalApprovalActivity) {
						childActs.add(act);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
		forLoop.run(parent);
		
		int readyIndex = -1;
		if (approverList.size() > childActs.size()) {
			readyIndex = childActs.size();
		} else {
			for (int i = 0; i < childActs.size(); i++) {
				Activity approvalAct = (Activity) childActs.get(i);
				if (Activity.STATUS_READY.equalsIgnoreCase(approvalAct.getStatus(instance))) {
					if (readyIndex == -1) {
						readyIndex = i;
					}
					parent.removeChildActivity(approvalAct);
				}
			}	
		}
		
		if (readyIndex > 0) {
			String preApprovalType = null;
			for (int i = readyIndex; i < approverList.size(); i++) {
				Approver approver = approverList.get(i);
				
				ExternalApprovalActivity childAct = (ExternalApprovalActivity) draftAct.clone();
				childAct.setName("결재");
				childAct.setTracingTag(null);
				childAct.setRole(null);
				childAct.setApprovalTypeByString(approver.getType());
				
				RoleMapping rm = RoleMapping.create();
				rm.setEndpoint(approver.getEmpCode());
				rm.fill(instance);
				
				// 결재 -> 합의
				if (!ExternalApprovalActivity.APPROVAL_TYPE_COOPERATION.equals(preApprovalType) && ExternalApprovalActivity.APPROVAL_TYPE_COOPERATION.equals(approver.getType())) {
					AllActivity allAct = new AllActivity();
					allAct.setTracingTag(null);
					
					parent.addChildActivity(allAct);
					parent = allAct;
				// 합의 -> 결재
				} else if (ExternalApprovalActivity.APPROVAL_TYPE_COOPERATION.equals(preApprovalType) && !ExternalApprovalActivity.APPROVAL_TYPE_COOPERATION.equals(approver.getType())) {
					parent = (ComplexActivity) approvalLineAct;
				}
				
				parent.addChildActivity(childAct);
				childAct.setApprover(instance, rm);
				
				approver.setTracingTag(childAct.getTracingTag());
				
				preApprovalType = approver.getType();
			}
			
			pm.changeProcessDefinition(alcMsg.getInstanceId(), tempPd);
			approverDAO.update(approverList);
		}
		
	}
	
	public void workItemAccept(WorkItemAcceptMsg wiaMsg) throws Exception {
		ProcessInstance instance = pm.getProcessInstance(wiaMsg.getInstanceId());
		this.bindProcessInfo(instance, wiaMsg);
		
		RoleMapping rm = RoleMapping.create();
		rm.setEndpoint(wiaMsg.getEndpoint());

		pm.delegateWorkitem(instance.getInstanceId(), wiaMsg.getTracingTag(), rm);
	}
	
	public void delegateWorkItem(DelegateWorkItemMsg dwMsg) throws Exception {
		ProcessInstance instance = pm.getProcessInstance(dwMsg.getInstanceId());
		this.bindProcessInfo(instance, dwMsg);
		
		RoleMapping rm = RoleMapping.create();
		if (dwMsg.getEndpoints().size() > 0) {
			for (String endpoint : dwMsg.getEndpoints()) {
				rm.setEndpoint(endpoint);
				rm.fill(instance);
				rm.moveToAdd();
			}
			rm.beforeFirst();
			
			pm.delegateWorkitem(dwMsg.getInstanceId(), dwMsg.getTracingTag(), rm);
		}
	}
	
	private void bindProcessInfo(ProcessInstance instance, BaseStdMsg baseStdMsg) throws Exception {
		if (StringUtils.hasText(baseStdMsg.getExternalKey())) {
			instance.setInfo(baseStdMsg.getExternalKey());
		}
		this.bindProcessVariable(instance, baseStdMsg.getProcessVariables());
		this.bindRoleMapping(instance, baseStdMsg.getRoles());
	}
	
	private void bindRoleMapping(ProcessInstance instance, List<Role> roles) throws Exception {
		String instanceId = instance.getInstanceId();
		for (Role role : roles) {
			RoleMapping roleMapping = RoleMapping.create();
			roleMapping.setName(role.getName());
			for (String endpoint : role.getEndpoints()) {
				roleMapping.setEndpoint(endpoint);
				roleMapping.fill(instance);
				roleMapping.moveToAdd();
			}
			roleMapping.beforeFirst();

			pm.putRoleMapping(instanceId, roleMapping);
		}
	}

	private void bindProcessVariable(ProcessInstance instance, List<ProcessVariable> processVariables) throws Exception {
		for (ProcessVariable pv : processVariables) {
			List<String> values = pv.getValues();
			Serializable val = null;
			
			if (values.size() == 1) {
				val = values.get(0);
			} else {
				ProcessVariableValue pvv = new ProcessVariableValue();
				for (String value : values) {
					pvv.setValue(value);
					pvv.moveToAdd();
				}
				pvv.beforeFirst();
				val = pvv;
			}
			
			instance.set("", pv.getName(), val);
		}
	}
	
	public List<TaskInfo> getTaskInfo(String instanceId, String endpoint) {
		TaskInfoMsg taskInfoMsg = new TaskInfoMsg();
		taskInfoMsg.setEndpoint(endpoint);
		taskInfoMsg.setInstanceId(instanceId);
		return getTaskInfo(taskInfoMsg);
	}
	
	public List<TaskInfo> getTaskInfo(TaskInfoMsg taskInfoMsg) {
		TaskInfoDAO resDAO = new TaskInfoDAO(((DefaultConnectionFactory) DefaultConnectionFactory.create()).getDataSource());
		return resDAO.getWorkItemInfoBySearchContext(taskInfoMsg);
	}
	
}
