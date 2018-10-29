package com.defaultcompany.wih.approvalhandler;

import java.text.SimpleDateFormat;
import java.util.*;

import org.uengine.kernel.*;
import org.uengine.util.ActivityForLoop;
import org.uengine.util.dao.ConnectiveDAO;
import org.uengine.util.dao.IDAO;



public class HumanApprovalLineService {
	
	public ProcessInstance instance;
	public HumanApprovalLineActivity humanApprovalLineActivity; 
	public HumanApprovalActivity thisActivity;
	public RoleMapping loggedRoleMapping;
	public boolean isRacing;
	int isRejectActivityIndex = 0;
	
	public List<HashMap> getApproverList(ProcessInstance instance,
				HumanApprovalLineActivity humanApprovalLineActivity, 
				HumanApprovalActivity thisActivity,
				RoleMapping loggedRoleMapping,
				boolean isRacing) throws Exception{	
			
		this.instance = instance;
		this.humanApprovalLineActivity = humanApprovalLineActivity;
		this.thisActivity =  thisActivity;
		this.loggedRoleMapping = loggedRoleMapping;
		this.isRacing = isRacing;
			
		List<HashMap> approverList = getApprovalLine();
		List<HashMap> commentList = getCommentList();
			
		if(isRejectActivityIndex ==0){
			return approverList;
		}else{
			List<HashMap> returnList = new ArrayList();
				
            for(int i=0; i<=isRejectActivityIndex; i++) {
               	HashMap approverMap = (HashMap)commentList.get(i);
               	returnList.add(approverMap);
            }
                
            for(int i=0; i<approverList.size(); i++) {
               	HashMap approverMap = (HashMap)approverList.get(i);
               	returnList.add(approverMap);
            }
    		return returnList;		
		}
	}

	public StringBuffer approverListHTMLString;
		public StringBuffer getApproverListHTMLString() {
			return approverListHTMLString;
		}
		public void setApproverListHTMLString(StringBuffer approverListHTMLString) {
			this.approverListHTMLString = approverListHTMLString;
		}
	
	public HumanApprovalLineService(){
		this.approverListHTMLString = new StringBuffer();
	}
	
	private List<HashMap> getCommentList(){
		List<HashMap> commentList = new ArrayList<HashMap>();
		
		try {
			IDAO commentDAO = ConnectiveDAO.createDAOImpl(instance.getProcessTransactionContext().getConnectionFactory(),                             
				"select * from doc_comments where instance_id=?instanceId order by id",   
				IDAO.class);                       
			commentDAO.set("instanceId",instance.getInstanceId());
			commentDAO.select();
			
			int cnt = 0;
			while(commentDAO.next()){
				HashMap dmap = new HashMap();
				String apprStat = "SIGN_"+commentDAO.getString("OPT_TYPE").toUpperCase();
				String tracingTag = commentDAO.getString("tracingTag");
				String endpoint = commentDAO.getString("empno");
				String resourceName = commentDAO.getString("empname");

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String approveDate = sdf.format(commentDAO.getDate("created_date"));
				
				String title = commentDAO.getString("emptitle");
				
				if(HumanApprovalActivity.SIGN_REJECT.equals(apprStat) ){
					this.isRejectActivityIndex = cnt;
				}
				
				String stepName = commentDAO.getString("apprtitle");
				dmap.put("stepName",stepName);
				dmap.put("approveDate", notNull(approveDate));
				dmap.put("approveStep", apprStat);
				dmap.put("endpoint", endpoint);
				dmap.put("jikCheck", title);
				dmap.put("resourceName", resourceName);
				dmap.put("activityTracingTag", tracingTag);
				dmap.put("taskId", "");
				dmap.put("activityStatus", Activity.STATUS_COMPLETED);
				
				commentList.add(dmap);
				cnt++;
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return commentList;
	}
	
	private List<HashMap> getApprovalLine() throws Exception{
		
		List<HashMap> approverList = new ArrayList<HashMap>();
		
		List<Activity> vChildActivities = humanApprovalLineActivity.getChildActivities();
		
		for(int i=0; i<vChildActivities.size(); i++){
			boolean isDraft =false;
			if(i ==0){
				isDraft = true;
			}
		
			if( vChildActivities.get(i) instanceof HumanApprovalActivity ){
				HumanApprovalActivity approveStep = (HumanApprovalActivity)vChildActivities.get(i);
				setApproverList( approveStep, isDraft ,approverList);
			}else if( vChildActivities.get(i) instanceof AllActivity ){
				 List allActChild= ((ComplexActivity)vChildActivities.get(i)).getChildActivities();
				 for(int j=0 ; j< allActChild.size() ; j++ ){
				 	if( allActChild.get(j) instanceof HumanApprovalActivity ){
						HumanApprovalActivity approveStep = (HumanApprovalActivity)allActChild.get(j);
						setApproverList( approveStep,isDraft ,approverList);
				 	}
				}	
			}
		}
		
		return approverList;
	}

	public void setApproverList(
			HumanApprovalActivity approveStep,
			boolean isDraft,
			List<HashMap> approverList) throws Exception{
		
		String approveDate = "";
		
		Calendar calendar = approveStep.getEndTime(instance);
		
		String taskId = (String)instance.getProperty(approveStep.getTracingTag(), "_task id");
		
		if(calendar != null && (Activity.STATUS_COMPLETED).equals(approveStep.getStatus(instance))){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			approveDate = sdf.format(calendar.getTime());
		}
		
		String activityStatus = approveStep.getStatus(instance);
		
		String apprStat = approveStep.getApprovalStatus(instance);

		//후결, 동의, 결재 여부
		String type = "결재";
		if(approveStep.isNotificationWorkitem()){
			type = "후결";
		}
		Activity parentAct = approveStep.getParentActivity();
		if(parentAct instanceof AllActivity){
			type = "동의";
		}
		
		//전결 가능 여부
		Boolean isArbitraryApprovable = approveStep.isArbitraryApprovable();
		String approveType = "no";
		if(isArbitraryApprovable){
			approveType = "yes";
		}else if("후결".equals(type) &&  Activity.STATUS_COMPLETED.equals(activityStatus)){
			approveType = "CompletedNotification";
		}
		
		String apprStatus = null;
		 if(HumanApprovalActivity.SIGN_DRAFT.equals(apprStat) 
				 && (Activity.STATUS_COMPLETED).equals(approveStep.getStatus(instance))) {
             apprStatus = "완료";
         }else if(HumanApprovalActivity.SIGN_APPROVED.equals(apprStat) 
        		  && (Activity.STATUS_COMPLETED).equals(approveStep.getStatus(instance))) {
             apprStatus = "승인";
         }else if(HumanApprovalActivity.SIGN_REJECT.equals(apprStat)
        		 && (Activity.STATUS_COMPLETED).equals(approveStep.getStatus(instance))) {
             apprStatus = "반송";
         }else if((Activity.STATUS_RUNNING).equals(approveStep.getStatus(instance)) || (Activity.STATUS_TIMEOUT).equals(approveStep.getStatus(instance))){
        	 apprStatus = "진행중";
         }else{
        	 apprStatus = "대기";
         }

		RoleMapping approver = null;
		
		if(humanApprovalLineActivity.getDraftActivity().getTracingTag().equals(approveStep.getTracingTag()) && isRacing){				
			approver = (RoleMapping)loggedRoleMapping;
			approver.fill(instance);
		}else{
			approver = (RoleMapping)approveStep.getRole().getMapping(instance);
		}
		
		if( approver != null ){
			approver.fill(instance);
			if(approver.getEndpoint()!=null ){
				String approverString ="";

				if(!isDraft && !thisActivity.getTracingTag().equals(humanApprovalLineActivity.getDraftActivity().getTracingTag())){
					this.approverListHTMLString.append("<option value=\""+
							approver.getEndpoint()+";"+
							approver.getResourceName()+";"+
							approver.getGroupName()+";"+
							approver.getTitle()+";"+
							type+";"+
							approveType+";"+
							apprStatus+";"+
							approver.getCompanyId()+";"+
							//approver.getTel_no()+"\">"+
							approver.getEmailAddress()+"\">"+
							//approver.getJikMu()+"</option>");
							//pprover.getResourceName()+","+type+","+approveType
							approver.getEndpoint()+";"+
							approver.getResourceName()+";"+
							approver.getGroupName()+";"+
							approver.getTitle()+";"+
							type+";"+
							approveType+";"+
							apprStatus+";"+
							approver.getCompanyId()+";"+
							//approver.getTel_no()+"\">"+
							approver.getEmailAddress()
							+ "</option>");
				}

				HashMap dmap = new HashMap();
					
				if(HumanApprovalActivity.SIGN_DRAFT.equals(apprStat) ||
					approveStep.getTracingTag().equals(humanApprovalLineActivity.getDraftActivity().getTracingTag())){
					dmap.put("stepName", approver.getResourceName() + " (상신)");
				}else{
					dmap.put("stepName", approveStep.getName());
				}						
				dmap.put("approveDate", notNull(approveDate));
				dmap.put("approveStep", notNull(approveStep.getApprovalStatus(instance)));
				dmap.put("endpoint", approver.getEndpoint());
				dmap.put("jikCheck", approver.getTitle());
				dmap.put("resourceName", approver.getResourceName());
				dmap.put("activityTracingTag", approveStep.getTracingTag());
				dmap.put("taskId", taskId);
				dmap.put("activityStatus", activityStatus);
				approverList.add(dmap);
			}
		}
	}
	
	public Object notNull(Object value) throws Exception{
		if(value!=null) return value;
		else return "-";
	}

	
}
