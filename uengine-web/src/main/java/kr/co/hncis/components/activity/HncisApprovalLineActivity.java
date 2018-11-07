package kr.co.hncis.components.activity; 

import java.util.Iterator;
import java.util.Map;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.metaworks.inputter.SelectInput;
import org.uengine.kernel.Activity;
import org.uengine.kernel.FlagActivity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.Role;
import org.uengine.kernel.SequenceActivity;
import org.uengine.kernel.ValidationContext;
import org.uengine.util.ActivityForLoop;

public class HncisApprovalLineActivity extends SequenceActivity {

	private static final long serialVersionUID = 757883519001060826L;

	public static void metaworksCallback_changeMetadata(Type type){
		FieldDescriptor fd;
		
		fd = type.getFieldDescriptor("ProcessName");
		fd.setInputter(new SelectInput(
			new String[]{
				"회원가입","휴양소","근무복","선물","도서","교육신청","경조사","명함","전산용품","사무용품","통근버스","물품관리","출장-국내출장","출장-해외출장","픽업","교통비","차량신청","주유비","운행일지","방문","방문-차량출입","방문-IT장비반입","회의실","증명서","연차"
			},
			new String[]{
				"0111","0121","0122","0123","0124","0125","0126","0131","0132","0133","0134","0135","0141","0142","0143","0144","0145","0146","0147","0151","0152","0153","0154","0155","0156"
			}
		));
	}

	String processName;
		public String getProcessName() {
			return processName;
		}
		public void setProcessName(String processName) {
			this.processName = processName;
		}
	
	boolean isAutoCompleteWhenPreiviosCompletedActivityByRole;
		public boolean isAutoCompleteWhenPreiviosCompletedActivityByRole() {
			return isAutoCompleteWhenPreiviosCompletedActivityByRole;
		}
		public void setAutoCompleteWhenPreiviosCompletedActivityByRole(
				boolean isAutoCompleteWhenPreiviosCompletedActivityByRole) {
			this.isAutoCompleteWhenPreiviosCompletedActivityByRole = isAutoCompleteWhenPreiviosCompletedActivityByRole;
		}

	ProcessVariable preConfirmPv;
		public ProcessVariable getPreConfirmPv() {
			return preConfirmPv;
		}
		public void setPreConfirmPv(ProcessVariable preConfirmPv) {
			this.preConfirmPv = preConfirmPv;
		}

	String approvalLineStatusCode;
		public String getApprovalLineStatusCode() {
			return approvalLineStatusCode;
		}
		public void setApprovalLineStatusCode(String approvalLineStatusCode) {
			this.approvalLineStatusCode = approvalLineStatusCode;
		}
	
	Role drafter;
		public Role getDrafter() {
			return drafter;
		}
		public void setDrafter(Role drafter) {
			this.drafter = drafter;
		}
	
	public HncisApprovalLineActivity() {
		HncisApprovalActivity draftActivity = new HncisApprovalActivity();
		draftActivity.setName(getProcessName()+GlobalContext.getLocalizedMessage("activitytypes.kr.co.hncis.components.activity.hncisapprovalactivity.draft.message", "Form Draft"));
		addChildActivity(draftActivity);
		//결재선 위치
		HncisApprovalSequenceActivity approvalSequenceActivity = new HncisApprovalSequenceActivity();
		FlagActivity approvalFlagActivity = new FlagActivity();
		approvalFlagActivity.setName(GlobalContext.getLocalizedMessage("activitytypes.kr.co.hncis.components.activity.hncisapprovalflagactivity.message", "approval line position"));
		approvalSequenceActivity.addChildActivity(approvalFlagActivity);
		addChildActivity(approvalSequenceActivity);
	}
	
	HncisApprovalActivity draftActivity;
		public HncisApprovalActivity getDraftActivity() {
			HncisApprovalActivity draftActivity = null;
			for(Iterator<Activity> i = getChildActivities().iterator(); i.hasNext(); ){
				Activity act = i.next();
				if(act instanceof HncisApprovalActivity){
					draftActivity = (HncisApprovalActivity) act;
					break;
				}
			}
			
			this.draftActivity = draftActivity; 
			
			return draftActivity;
		}
	
	HncisApprovalSequenceActivity approvalSequenceActivity;
		public HncisApprovalSequenceActivity getApprovalSequenceActivity() {
			if(approvalSequenceActivity!=null) {
				return approvalSequenceActivity;				
			}
			
			ActivityForLoop findingLoop = new ActivityForLoop(){
				public void logic(Activity activity){
					if(activity instanceof HncisApprovalSequenceActivity){
						stop(activity);
					}
				}
			};
			
			findingLoop.run(this);		
			this.approvalSequenceActivity = (HncisApprovalSequenceActivity) findingLoop.getReturnValue(); 
			
			return approvalSequenceActivity;
		}
		@Override
		public ValidationContext validate(Map options) {
			// TODO Auto-generated method stub
			ValidationContext valCtx = super.validate(options);
			
/*			if(getDrafter()==null)
				valCtx.add(getActivityLabel() + " " + GlobalContext.getLocalizedMessage("activitytypes.kr.co.hncis.components.activity.hncisapprovallineactivity.fields.drafter.displayname", "drafter") + " Role is not specified");
			
			if(getApprovalLineStatusCode()==null)
				valCtx.add(getActivityLabel() + " " + GlobalContext.getLocalizedMessage("activitytypes.kr.co.hncis.components.activity.hncisapprovallineactivity.fields.approvallinestatuscode.displayname", "approvalline status code") + " is not specified");
			
			if(getPreConfirmPv()==null)
				valCtx.addWarning(getActivityLabel() + " " + GlobalContext.getLocalizedMessage("activitytypes.kr.co.hncis.components.activity.hncisapprovallineactivity.fields.preconfirmpv.displayname", "preConfirm Process Variable") + " is not specified");*/
			
			if(getProcessName()==null) {
				valCtx.add(getActivityLabel() + " " + GlobalContext.getLocalizedMessage("activitytypes.kr.co.hncis.components.activity.hncisapprovallineactivity.fields.processname.displayname", "Process Name") + " is not specified");
			}
			
			return valCtx;
		}
		
		

}
