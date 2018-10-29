package com.defaultcompany.external.activities;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.metaworks.Type;
import org.metaworks.inputter.RadioInput;
import org.uengine.contexts.TextContext;
import org.uengine.kernel.Activity;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleMapping;
import org.uengine.kernel.ValidationContext;

import com.defaultcompany.external.model.Approver;
import com.defaultcompany.external.repository.ApproverDAO;

public class ExternalApprovalActivity extends HumanActivity {
	
	public final static String KEY_APPR_STATUS = "KEY_APPR_STATUS";
	
	public final static String SIGN_DRAFT = "SIGN_DRAFT";
	public final static String SIGN_APPROVED = "SIGN_APPROVED";
	public final static String SIGN_REJECT = "SIGN_REJECT";
	public final static String SIGN_ARBITRARY_APPROVED = "SIGN_ARBITRARY_APPROVED";
	
	final static public String APPROVAL_TYPE_DRAFT = "DRAFT";
	final static public String APPROVAL_TYPE_APPROVAL = "APPROVAL";
	final static public String APPROVAL_TYPE_COOPERATION = "COOPERATION";
	final static public String APPROVAL_TYPE_ARBITRARY_APPROVAL = "ARBITRARY_APPROVAL";
	
	final static private int APPROVAL_TYPE_DRAFT_CODE = 1;
	final static private int APPROVAL_TYPE_APPROVAL_CODE = 2;
	final static private int APPROVAL_TYPE_COOPERATION_CODE = 3;
	final static private int APPROVAL_TYPE_ARBITRARY_APPROVAL_CODE = 4;
	
	private static final long serialVersionUID = 1L;
	
	public static void metaworksCallback_changeMetadata(Type type) {
//		type.getFieldDescriptor("ExtValue1").setDisplayName("주 화면 아이디");
//		type.getFieldDescriptor("ExtValue2").setDisplayName("서브 화면 아이디");
		type.getFieldDescriptor("ApprovalType").setInputter(
				new RadioInput(
						new String[] { APPROVAL_TYPE_DRAFT, APPROVAL_TYPE_APPROVAL, APPROVAL_TYPE_COOPERATION, APPROVAL_TYPE_ARBITRARY_APPROVAL }, 
						new Integer[] { APPROVAL_TYPE_DRAFT_CODE, APPROVAL_TYPE_APPROVAL_CODE, APPROVAL_TYPE_COOPERATION_CODE, APPROVAL_TYPE_ARBITRARY_APPROVAL_CODE }
						)
				);
	}

	public ExternalApprovalActivity() {
		super();
		setName("Approval Work");
		setDuration(5);
		setTool("externalApprovalHandler");
		setApprovalType(APPROVAL_TYPE_DRAFT_CODE);
	}

	int approvalType;
		public int getApprovalType() {
			return approvalType;
		}	
		public void setApprovalType(int approvalType) {
			this.approvalType = approvalType;
		}
		public void setApprovalTypeByString(String approvalType) {
			int approvalTypeCode = 0;
			if (APPROVAL_TYPE_DRAFT.equalsIgnoreCase(approvalType)) {
				approvalTypeCode = APPROVAL_TYPE_DRAFT_CODE;
				
			} else if (APPROVAL_TYPE_APPROVAL.equalsIgnoreCase(approvalType)) {
				approvalTypeCode = APPROVAL_TYPE_APPROVAL_CODE;
				
			} else if (APPROVAL_TYPE_COOPERATION.equalsIgnoreCase(approvalType)) {
				approvalTypeCode = APPROVAL_TYPE_COOPERATION_CODE;
				
			} else if (APPROVAL_TYPE_ARBITRARY_APPROVAL.equalsIgnoreCase(approvalType)) {
				approvalTypeCode = APPROVAL_TYPE_ARBITRARY_APPROVAL_CODE;
			}
			
			this.approvalType = approvalTypeCode;
		}
		
		public RoleMapping getActualMapping(ProcessInstance instance) throws Exception {
			// TODO Auto-generated method stub
			if(getApprover(instance)==null) return super.getActualMapping(instance);
			
			return getApprover(instance);
		}
		
	protected String createApproverRoleName() {
//		if (getRole() != null)
//			return getRole().getName();
		return "approver_" + getApprovalLineActivity().getTracingTag() + "_" + getTracingTag();
	}
	
	RoleMapping approver;
		public RoleMapping getApprover(ProcessInstance instance) {
			RoleMapping rm;
			try {
				rm = instance.getRoleMapping(createApproverRoleName());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	
			if (rm != null)
				return rm;
			else
				return approver;
		}
		public void setApprover(ProcessInstance instance, RoleMapping approver) {
			this.approver = approver;
			try {
				instance.putRoleMapping(createApproverRoleName(), approver);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	public Role getRole() {
		return approver == null ? super.getRole() : new Role() {
			
			public String getName() {
				return getDisplayName().getText();
			}

			public TextContext getDisplayName() {
				TextContext tc = TextContext.createInstance();

				tc.setText(createApproverRoleName());
				return tc;
			}

			public RoleMapping getMapping(ProcessInstance inst, String tracingTag) throws Exception {

				return getApprover(inst);
			}

		};
	}
	
	ExternalApprovalLineActivity approvalLineActivity;
	public ExternalApprovalLineActivity getApprovalLineActivity() {
		if (approvalLineActivity != null) {
			//return formApprovalLineActivity;
		}

		Activity tracing = this;

		do {
			if (ExternalApprovalLineActivity.class.isAssignableFrom(tracing.getClass())) {
				approvalLineActivity = (ExternalApprovalLineActivity) tracing;

				return approvalLineActivity;
			}

			tracing = tracing.getParentActivity();
		} while (tracing != null);

		return null;
	}
	
	public void setApprovalStatus(ProcessInstance instance, String status) throws Exception {
		instance.setProperty(getTracingTag(), KEY_APPR_STATUS, status);
		getApprovalLineActivity().setApprovalLineStatus(instance, status);
	}
	
	public String getApprovalStatus(ProcessInstance instance) throws Exception {
		if (instance == null)
			return null;

		return (String) instance.getProperty(getTracingTag(), KEY_APPR_STATUS);
	}
	
	@Override
	public void afterExecute(ProcessInstance instance) throws Exception {
		
		if (this.getApprovalType() > 1) {
			ApproverDAO alDAO = new ApproverDAO(instance.getProcessTransactionContext());
			String approvalKey = (String) instance.getProperty(approvalLineActivity.getTracingTag(), ExternalApprovalLineActivity.KEY_APPR_KEY);
			Approver approver = alDAO.getByInstanceIdAndTracingTag(approvalKey, Integer.parseInt(instance.getInstanceId()), getTracingTag());
			if (approver != null) {
				approver.setTaskId(getTaskIds(instance)[0]);
				approver.setStatus(null);
				alDAO.update(approver);	
			}
		}
		
		super.afterExecute(instance);
	}
	
	

	protected void afterComplete(ProcessInstance instance) throws Exception {
		// is Draft
		if (this == getApprovalLineActivity().getDraftActivity()) {
			setApprovalStatus(instance, SIGN_DRAFT);
		// is approve
		} else {
			if (!isNotificationWorkitem())
				setApprovalStatus(instance, SIGN_APPROVED);
		}
		
		if (this.getApprovalType() > 1) {
			ApproverDAO alDAO = new ApproverDAO(instance.getProcessTransactionContext());
			String approvalKey = (String) instance.getProperty(approvalLineActivity.getTracingTag(), ExternalApprovalLineActivity.KEY_APPR_KEY);
			Approver approver = alDAO.getByInstanceIdAndTracingTag(approvalKey, Integer.parseInt(instance.getInstanceId()), getTracingTag());
			if (approver != null) {
				approver.setTruthEmpCode(getRole().getMapping(instance).getEndpoint());
				approver.setTaskId(getTaskIds(instance)[0]);
				approver.setStatus(ExternalApprovalActivity.SIGN_APPROVED);
				approver.setEndDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				approver.setComment(instance.getProcessTransactionContext().getServletRequest().getParameter("comment"));
				alDAO.update(approver);
			}
		}
		
		super.afterComplete(instance);

//		EventMessagePayload emp = new EventMessagePayload();
//		emp.setTriggerTracingTag(getTracingTag());
//
//		if (this == getApprovalLineActivity().getDraftActivity()) {
//			getApprovalLineActivity().fireEventHandlers(instance, EventHandler.TRIGGERING_BY_DRAFTED, emp);
//		} else {
//			getApprovalLineActivity().fireEventHandlers(instance, EventHandler.TRIGGERING_BY_APPROVED, emp);
//		}
	}

	@Override
	public ValidationContext validate(Map options) {
		ValidationContext vc = super.validate(options);
		
		if (getApprovalType() == 0) {
			vc.add("select approval type");
		}

		return vc;
	}

}
