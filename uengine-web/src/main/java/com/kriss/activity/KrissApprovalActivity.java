/**
 * 
 */
/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package com.kriss.activity; 

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.uengine.contexts.TextContext;
import org.uengine.kernel.Activity;
import org.uengine.kernel.FlagActivity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ResultPayload;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleMapping;
import org.uengine.processmanager.TransactionContext;
import org.uengine.util.dao.ConnectiveDAO;
import org.uengine.util.dao.IDAO;

/**
 * <pre>
 * com.kriss.activity 
 *    |_ KrissApprovalActivity.java
 * 
 * </pre>
 * @date : 2016. 8. 4. 오후 5:43:05
 * @version : 
 * @author : mkbok_Enki
 */
/**
 * <pre>
 * com.kriss.activity 
 * KrissApprovalActivity.java
 * 
 * </pre>
 * @date : 2016. 8. 4. 오후 5:43:05
 * @version : 
 * @author : mkbok_Enki
 */
public class KrissApprovalActivity extends KrissHumanActivity {


	/**
	 * 
	 */
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;



	/**
	 * 
	 */
	public KrissApprovalActivity() {
		// TODO Auto-generated constructor stub
	}
	
	public static final String IS_APPROVAL_LINE_EXECUTED = "isApprovalLineExecuted";
    public static final String IS_AUTO_COMPLETE_APPROVAL = "isAutoCompleteApproval";
    
    

    @Override
	public void executeActivity(ProcessInstance instance) throws Exception {
    	makeApprovalLine(instance);
		super.executeActivity(instance);
	}
    
    private void makeApprovalLine(ProcessInstance instance) throws Exception {
    	
    	KrissApprovalLineActivity parent = getApprovalLineActivity();
    	
        if ( getApprovalLineActivity().getDraftActivity().equals(this) ) {
            // 상신 액티비티 완료 후 결재선 생성(경합의 경우 어떤 롤의 결재선을 가져올지 모르므로 실 처리자의 결재선을 가져온다.)
            //1. 상신자를 가져온다.
            Role drafterRole = parent.getDrafter();
            RoleMapping drafterRm = null;
            if ( drafterRole == null || drafterRole.getMapping(instance) == null || drafterRole.getMapping(instance).size() == 0 ) {
                drafterRm = parent.getDraftActivity().getRole().getMapping(instance);
            } else {
                drafterRm = drafterRole.getMapping(instance);
            }
            if ( drafterRm == null ) {
            	drafterRm = (RoleMapping) instance.getProcessTransactionContext().getProcessManager().getGenericContext().get(HumanActivity.GENERICCONTEXT_CURR_LOGGED_ROLEMAPPING);
            }
            drafterRm.beforeFirst();
            String drafter = drafterRm.getEndpoint();
            //1.1 전결규정을 가져온다.
            String preConfirmPvVal = parent.getPreConfirmPv()==null?null:(String) instance.get("",parent.getPreConfirmPv().getName());
            //1.2 결재 상태코드를 가져온다.
            String approvalStatusCode=parent.getApprovalLineStatusCode();
            //2. 조직도에서 결재선을 가져온다.
            List<String> approvalLine = getApprovalLine(instance.getProcessTransactionContext(), drafter, preConfirmPvVal);
            //Approval Line Dynamic Change
            ProcessDefinition clonedPd = (ProcessDefinition) instance.getProcessDefinition().clone();
            KrissApprovalSequenceActivity approvalLineSeqActivity = (KrissApprovalSequenceActivity) clonedPd.getActivity(parent.getApprovalSequenceActivity().getTracingTag());
            
            for ( int i = 0; i < approvalLineSeqActivity.getChildActivities().size(); i++ ) {
                Activity child = (Activity)approvalLineSeqActivity.getChildActivities().get(i);
                if ( !(child instanceof FlagActivity) ) {
                    approvalLineSeqActivity.removeChildActivity(child);
                }
            }
            
            List<HumanActivity> completedOrRunningHumanActivities = instance.getAllRunningOrCompletedHumanActivities(instance.getProcessDefinition());
            for (Iterator<String> i = approvalLine.iterator();i.hasNext();){
                KrissApprovalActivity clonedApprovalActivity = (KrissApprovalActivity) parent.getDraftActivity().clone();
                clonedApprovalActivity.setTracingTag(null);
                clonedApprovalActivity.setRole(null);
                clonedApprovalActivity.setExtValue1(approvalStatusCode);
                
                //결재 권한 세팅
                clonedApprovalActivity.setApprove(false);
                clonedApprovalActivity.setSave(true);
                clonedApprovalActivity.setDelegate(true);
                clonedApprovalActivity.setCollect(true);
                clonedApprovalActivity.setReject(true);
                clonedApprovalActivity.setComplete(true);
                clonedApprovalActivity.setDelete(false);
                
                approvalLineSeqActivity.addChildActivity(clonedApprovalActivity);
                clonedApprovalActivity.setStatus(instance, Activity.STATUS_READY);
                clonedApprovalActivity.setName(GlobalContext.getLocalizedMessage("activitytypes.com.kriss.activity.krissapprovalactivity.approve.message", "approval"));
                RoleMapping rm = RoleMapping.create();
                rm.setEndpoint(i.next());
                rm.fill(instance);
                clonedApprovalActivity.setApprover(instance,rm);
                
                //완료된 업무의 경우 결재를 자동완료
                for (HumanActivity humAct:completedOrRunningHumanActivities){
                    if ( getApprovalLineActivity().isAutoCompleteWhenPreiviosCompletedActivityByRole && humAct.getStatus(instance).equals(Activity.STATUS_COMPLETED) ) {
                        RoleMapping completedRm = humAct.getActualMapping(instance);
                        completedRm.beforeFirst();
                        if ( completedRm.getEndpoint().equals(rm.getEndpoint()) ) {
                            instance.setProperty(clonedApprovalActivity.getTracingTag(), IS_AUTO_COMPLETE_APPROVAL, true);
                        }
                    }
                }
                
                
            }
            instance.getProcessTransactionContext().getProcessManager().changeProcessDefinition(instance.getInstanceId(), clonedPd);
            
        }
		
	}


	public static List<String> getApprovalLine(TransactionContext tc, String drafter, String preConfirmPvVal) throws Exception {
        ArrayList<String> approvalLine = new ArrayList<String>();
        StringBuffer sql = new StringBuffer();
        
        sql.append("SELECT LEVEL,                                                           \n");
        sql.append("  CONNECT_BY_ISLEAF AS ISLEAF,                                          \n");
        sql.append("  T.EMPCODE         AS EMPCODE,                                         \n");
        sql.append("  T.EMPNAME         AS EMPNAME,                                         \n");
        sql.append("  T.PARTNAME        AS PARTNAME,                                        \n");
        sql.append("  T.JIKCODE         AS JIKCODE,                                        \n");
        sql.append("  T.CHILD_PARTCODE  AS PARTCODE                                         \n");
        sql.append("FROM                                                                    \n");
        sql.append("  (SELECT E.*,                                                          \n");
        sql.append("    P.PARTNAME,                                                         \n");
        sql.append("    P.PARENT_PARTCODE,                                                  \n");
        sql.append("    P.PARTCODE AS CHILD_PARTCODE                                        \n");
        sql.append("  FROM PARTTABLE P                                                      \n");
        sql.append("  INNER JOIN EMPTABLE E                                                 \n");
        sql.append("  ON E.EMPCODE=P.TOP_EMPNO                                              \n");
        sql.append("  WHERE 1     =1                                                        \n");
        sql.append("  ) T                                                                   \n");
        sql.append("  START WITH T.CHILD_PARTCODE=                                          \n");
        sql.append("  (SELECT PARTCODE FROM EMPTABLE WHERE EMPCODE=?EMPCODE)                \n");
        sql.append("  CONNECT BY PRIOR T.PARENT_PARTCODE =T.CHILD_PARTCODE                  \n");
        
        //전결규정 로드
        
        
        IDAO idao = ConnectiveDAO.createDAOImpl(tc, sql.toString(), IDAO.class);
        idao.set("EMPCODE", drafter);
        idao.select();
        String preEmpcode = drafter;
        preConfirmPvVal=preConfirmPvVal==null?"":preConfirmPvVal;
        while ( idao.next() ) {
            //이전 결재자와 결재선이 같은 경우 넘어간다.
            if ( !preEmpcode.equals(idao.getString("EMPCODE")) ) {
                approvalLine.add(idao.getString("EMPCODE"));
                preEmpcode = idao.getString("EMPCODE");
                //전결규정에 걸리는지 확인
                if ( preConfirmPvVal.startsWith("next") && ("next"+Integer.toString(approvalLine.size())).equals(preConfirmPvVal) )
                    break;
                if ( preConfirmPvVal.equals(idao.getString("JIKCODE")) )
                    break;
            }
        }
        
        return approvalLine;
    }

	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : afterExecute
	 * @date : 2016. 8. 5.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 8. 5.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @see org.uengine.kernel.HumanActivity#afterExecute(org.uengine.kernel.ProcessInstance)
	 * @param instance
	 * @throws Exception
	 */ 	
	@Override
	public void afterExecute(ProcessInstance instance) throws Exception {
		super.afterExecute(instance);
		// 결재선이 이전 완료처리자와 같을때 자동완료
		if ( getApprovalLineActivity().isAutoCompleteWhenPreiviosCompletedActivityByRole() && getStatus(instance).equals(Activity.STATUS_RUNNING) && instance.getProperty(getTracingTag(), IS_AUTO_COMPLETE_APPROVAL) != null ) {
			boolean isAutoComplete = (Boolean) instance.getProperty(getTracingTag(), IS_AUTO_COMPLETE_APPROVAL);
			if ( isAutoComplete ) {
				fireReceived(instance, new ResultPayload());
				instance.setProperty(getTracingTag(), IS_AUTO_COMPLETED, true);
			}
		}
	}
	
	RoleMapping approver;
	public RoleMapping getApprover(ProcessInstance instance) {
		RoleMapping rm;
		try {
			rm = instance.getRoleMapping(createApproverRoleName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		if(rm != null)
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
	
	protected String createApproverRoleName(){
		if(role!=null) return role.getName();
		return "approver_"+getApprovalSequenceActivity().getTracingTag()+"_"+getTracingTag();
	}
	
	KrissApprovalSequenceActivity approvalSequenceActivity;
	public KrissApprovalSequenceActivity getApprovalSequenceActivity() {
		if (approvalSequenceActivity != null) {
			//return formApprovalLineActivity;
		}

		Activity tracing = this;

		do {
			if (KrissApprovalSequenceActivity.class.isAssignableFrom(tracing.getClass())) {
				approvalSequenceActivity = (KrissApprovalSequenceActivity) tracing;

				return approvalSequenceActivity;
			}

			tracing = tracing.getParentActivity();
		} while (tracing != null);

		return null;
	}

	KrissApprovalLineActivity approvalLineActivity;
	public KrissApprovalLineActivity getApprovalLineActivity() {
				
		Activity tracing = this;
		
		do {
			if (KrissApprovalLineActivity.class.isAssignableFrom(tracing.getClass())) {
				approvalLineActivity = (KrissApprovalLineActivity) tracing;
				
				return approvalLineActivity;
			}
			
			tracing = tracing.getParentActivity();
		} while (tracing != null);
		
		return null;
	}

	@Override
	public Role getRole() {
		return approver==null ? super.getRole() : new Role(){
			
			/**
			 * 
			 */
			private static final long serialVersionUID = KrissApprovalActivity.serialVersionUID;

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

	@Override
	public RoleMapping getActualMapping(ProcessInstance instance) throws Exception {
		if(getApprover(instance)==null) return super.getActualMapping(instance);
		
		return getApprover(instance);
	}
	
	
}
