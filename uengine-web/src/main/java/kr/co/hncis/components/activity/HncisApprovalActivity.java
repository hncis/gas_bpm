package kr.co.hncis.components.activity; 

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
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.ProcessVariableValue;
import org.uengine.kernel.ResultPayload;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleMapping;
import org.uengine.processmanager.TransactionContext;
import org.uengine.util.UEngineUtil;
import org.uengine.util.dao.ConnectiveDAO;
import org.uengine.util.dao.IDAO;

public class HncisApprovalActivity extends HncisHumanActivity {

	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;

	public HncisApprovalActivity() {
		// TODO Auto-generated constructor stub
	}
	
	public static final String IS_APPROVAL_LINE_EXECUTED = "isApprovalLineExecuted";
    public static final String IS_AUTO_COMPLETE_APPROVAL = "isAutoCompleteApproval";

    public static final String APPROVALLINE = "APPROVALLINE"; //결재라인 프로세스변수명
    public static final String GASROLE = "GASROLE"; //역할코드 PREFIX
    public static final String GASBZ = "GASBZ"; //액티비티코드 PREFIX
    public static final int APPROVAL_ACTIVITY_POTFIX = 9100; //동적 결재 액티비티 POSTFIX

    private String processName; //프로세스별 4자리 패딩
    

    @Override
	public void afterComplete(ProcessInstance instance) throws Exception {
    	makeApprovalLine(instance);
		super.afterComplete(instance);
	}
    
    @Override
	public void executeActivity(ProcessInstance instance) throws Exception {
    	//makeApprovalLine(instance);
		super.executeActivity(instance);
	}
    
    private void makeApprovalLine(ProcessInstance instance) throws Exception {
    	
    	HncisApprovalLineActivity parent = getApprovalLineActivity();
    	
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
            processName = parent.getProcessName();
            //System.out.println(processName);
            
            //2. 결재선을 가져온다.
            // - SQL 에서 가져온다.
           // List<String> approvalLine = getApprovalLine(instance.getProcessTransactionContext(), drafter, preConfirmPvVal);
            // - 프로세스변수 에서 가져온다.
            List<String> approvalLine = getApprovalLineFromProcessVariable(instance, APPROVALLINE);
            
            if (approvalLine.size() > 0) { //결재선이 있는 경우에만 동적으로 결재 액티비티 생성
                //Approval Line Dynamic Change
                ProcessDefinition clonedPd = (ProcessDefinition) instance.getProcessDefinition().clone();
                HncisApprovalSequenceActivity approvalLineSeqActivity = (HncisApprovalSequenceActivity) clonedPd.getActivity(parent.getApprovalSequenceActivity().getTracingTag());
                
                /*
                 * 작성일: 2018-07-02
                 * 작성자: 박종훈
                 * 작성내용: HncisApprovalActivity 결재선 초기화 할때 기존에 있던 HncisApprovalActivity이
                 * 남아있는 오류 수정
                 */
                
                // 2018-07-02 start
                int approvalLineSeqActivitySize = approvalLineSeqActivity.getChildActivities().size();
                for ( int i = 0; approvalLineSeqActivitySize > 0 ; ) {
                	approvalLineSeqActivitySize = approvalLineSeqActivitySize- 1;
                    Activity child = (Activity)approvalLineSeqActivity.getChildActivities().get(approvalLineSeqActivitySize);
                    if ( !(child instanceof FlagActivity) ) {
                        approvalLineSeqActivity.removeChildActivity(child);
                    }
                }
                // 2018-07-02 End
                List<HumanActivity> completedOrRunningHumanActivities = instance.getAllRunningOrCompletedHumanActivities(instance.getProcessDefinition());
                //int n = 0;
                for (Iterator<String> i = approvalLine.iterator();i.hasNext();){
                	//n++;
                    HncisApprovalActivity clonedApprovalActivity = (HncisApprovalActivity) parent.getDraftActivity().clone();
                    clonedApprovalActivity.setTracingTag(null);
                    clonedApprovalActivity.setRole(null);
                    //clonedApprovalActivity.setExtValue1(approvalStatusCode);
                    
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
                    clonedApprovalActivity.setName(GlobalContext.getLocalizedMessage("activitytypes.kr.co.hncis.components.activity.hncisapprovalactivity.approve.message", "approval"));
                    RoleMapping rm = RoleMapping.create();
                    rm.setEndpoint(i.next());
                    rm.fill(instance);
                    String roleCode = clonedApprovalActivity.setApprover(instance,rm);
                    
                    //ActivityCode = "GASBZ" + 0121(프로세스별 4자리 패딩) + 0010(액티비티 순번 4자리 패딩);
                    clonedApprovalActivity.setExtValue1(roleCode.replaceAll(GASROLE,GASBZ));
                    
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
	}


	public static List<String> getApprovalLine(TransactionContext tc, String drafter, String preConfirmPvVal) throws Exception {
        ArrayList<String> approvalLine = new ArrayList<String>();
/*        StringBuffer sql = new StringBuffer();
        
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
        }*/
        
        approvalLine.add("test1");
        approvalLine.add("test2");
        approvalLine.add("test3");
        return approvalLine;
    }

	public static List<String> getApprovalLineFromProcessVariable(ProcessInstance instance, String key) throws Exception {
        ArrayList<String> approvalLine = new ArrayList<String>();		
        ProcessVariable pv = instance.getProcessDefinition().getProcessVariable(key);
        if (UEngineUtil.isNotEmpty(key) && pv != null) {
    		ProcessVariableValue pvv = pv.getMultiple(instance, "", pv.getName());
    		if (pvv.getValue() != null) {
    			pvv.beforeFirst();
    			do {
    				approvalLine.add(pvv.getValue().toString());
    			} while(pvv.next());
    			pvv.beforeFirst();			
    		}
        }
        return approvalLine;
    }
	
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
		
		if (rm != null) {
			return rm;
		} else {
			return approver;
		}
	}
	
	public String setApprover(ProcessInstance instance, RoleMapping approver) {
		String roleCode = null;
		this.approver = approver;
		try {
			roleCode = createApproverRoleName();
			instance.putRoleMapping(roleCode, approver);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return roleCode;
	}
	
	protected String createApproverRoleName(){
        //RoleCode = "GASROLE" + 0121(프로세스별 4자리 패딩) + 0010(액티비티 순번 4자리 패딩)
		String roleCode = null;
		if (role!=null) {
			roleCode = role.getName();
		}
		else {
			roleCode = GASROLE + processName + (APPROVAL_ACTIVITY_POTFIX+Integer.parseInt(getApprovalSequenceActivity().getTracingTag())+Integer.parseInt(getTracingTag()));
		}
		return roleCode;
		
		//if(role!=null) return role.getName();
		//return "approver_"+getApprovalSequenceActivity().getTracingTag()+"_"+getTracingTag();
	}
	
	HncisApprovalSequenceActivity approvalSequenceActivity;
	public HncisApprovalSequenceActivity getApprovalSequenceActivity() {
		if (approvalSequenceActivity != null) {
			//return formApprovalLineActivity;
		}

		Activity tracing = this;

		do {
			if (HncisApprovalSequenceActivity.class.isAssignableFrom(tracing.getClass())) {
				approvalSequenceActivity = (HncisApprovalSequenceActivity) tracing;

				return approvalSequenceActivity;
			}

			tracing = tracing.getParentActivity();
		} while (tracing != null);

		return null;
	}

	HncisApprovalLineActivity approvalLineActivity;
	public HncisApprovalLineActivity getApprovalLineActivity() {
				
		Activity tracing = this;
		
		do {
			if (HncisApprovalLineActivity.class.isAssignableFrom(tracing.getClass())) {
				approvalLineActivity = (HncisApprovalLineActivity) tracing;
				
				return approvalLineActivity;
			}
			
			tracing = tracing.getParentActivity();
		} while (tracing != null);
		
		return null;
	}

	@Override
	public Role getRole() {
		return approver==null ? super.getRole() : new Role(){

			private static final long serialVersionUID = HncisApprovalActivity.serialVersionUID;

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
		if(getApprover(instance)==null) {
			return super.getActualMapping(instance);
		}
		
		return getApprover(instance);
	}
	
	
}
