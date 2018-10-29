package org.uengine.kernel.viewer.gantt;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.uengine.kernel.Activity;
import org.uengine.kernel.AllActivity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.Role;
import org.uengine.kernel.SwitchActivity;
import org.uengine.kernel.viewer.ActivityViewer;

public class GanttActivityViewer implements ActivityViewer {

	Vector activityList = new Vector();
	
	public StringBuilder render(Activity activity, ProcessInstance instance,
			Map options) {
		int maxOfTo = traverse(activity,0,(Hashtable)options);
		StringBuilder sb = new StringBuilder();
		
		//���̺� ���
		sb.append("<table ><td bgcolor=#6699CC>");
		sb.append("<table border=0 cellspacing=1 cellpadding=1 ><tr>");
		sb.append("<td bgcolor=#F4F4F4 rowspan=3 align=center><b>Activity</td>");
		sb.append("<td bgcolor=#F4F4F4 rowspan=3 align=center><b>Resource</td>");
		
		int month = (int)(maxOfTo/30);
		int monthAdd = (maxOfTo%30);
		for(int i=0 ; i <month;i++ )
			sb.append("<td bgcolor=#F4F4F4 colspan=30 align=center><b>M"+(i+1)+"</td>");

		if(monthAdd !=0) sb.append("<td bgcolor=#F4F4F4 colspan="+monthAdd+" align=center><b>M"+(month+1)+"</td>");
		sb.append("</tr><tr>");
		
		int week = (int)(maxOfTo/7);
		int weekAdd = maxOfTo%7;
		for(int i=0 ; i < week ; i++)
			sb.append("<td bgcolor=#F4F4F4 colspan=7 align=center><b>W"+(i+1)+"</td>");
		
		if(weekAdd !=0) sb.append("<td bgcolor=#F4F4F4 colspan="+weekAdd+" align=center><b>W"+(week+1)+"</td>");
		sb.append("</tr><tr>");
		
		for(int i=0 ; i < maxOfTo ; i++ )
			sb.append("<td bgcolor=white width =15><center><font size=-2><b>"+(i+1)+"</font></td>");

		sb.append("</tr>");
		
		//����
		for(int i=0 ; i < activityList.size() ;  i++){
			ActivityPosition ap = (ActivityPosition)activityList.get(i);
			int from = ap.getFrom();
			int to = ap.getTo();
			Activity act = ap.getActivity();
			String actNAme = act.getName().toString();
			Role role = ((HumanActivity) act).getRole();
			String roleName = role.getDisplayName().getText();
			
			sb.append("<tr><td bgcolor=#F4F4F4>"+actNAme+"</td><td bgcolor=#F4F4F4>"+roleName+"</td>");
			for(int j=0 ; j < maxOfTo ;  j++){
				if((j >= from)&&(j < to )){
					sb.append("<td bgcolor=#aaaaff width =20 font size=-2> </td>");
				}else{
					sb.append("<td bgcolor=white width =20 font size=-2> </td>");
				}
			}
			sb.append("</tr>");
		}
		
		sb.append("</table>");
		sb.append("</td></table>");
		return sb;
	}
	
	
	protected int traverse(Activity act, int currentFrom, Hashtable context ){
		if(act instanceof HumanActivity){
			HumanActivity humanActivity = ((HumanActivity)act);
			int duration = humanActivity.getDuration();
			activityList.add(new ActivityPosition(humanActivity, currentFrom, currentFrom+duration));
			return duration;
		}else if(act instanceof AllActivity || act instanceof SwitchActivity){
			ComplexActivity complexActivity = (ComplexActivity)act;
			int maxOfDuration = 0;
			for(int i=0; i<complexActivity.getChildActivities().size(); i++){
				Activity child = (Activity) complexActivity.getChildActivities().get(i);
				int durationOfChild = traverse(child, currentFrom, context);
				if(durationOfChild > maxOfDuration) maxOfDuration = durationOfChild;				
			}
			return maxOfDuration;
		}else if(act instanceof ComplexActivity){
			int initialCurrentFrom = currentFrom;
			ComplexActivity complexActivity = (ComplexActivity)act;
			for(int i=0; i<complexActivity.getChildActivities().size(); i++){
				Activity child = (Activity) complexActivity.getChildActivities().get(i);
				currentFrom += traverse(child, currentFrom, context);
			}
			
			return currentFrom - initialCurrentFrom;
		}
		
		return 0;		
	}
	
	class ActivityPosition{
		Activity activity;
		int from;
		int to;
		
		public ActivityPosition(Activity activity, int from, int to){
			setActivity(activity);
			setFrom(from);
			setTo(to);
		}
		
		public Activity getActivity() {
			return activity;
		}
		public void setActivity(Activity activity) {
			this.activity = activity;
		}
		public int getFrom() {
			return from;
		}
		public void setFrom(int from) {
			this.from = from;
		}
		public int getTo() {
			return to;
		}
		public void setTo(int to) {
			this.to = to;
		}	
	}


}
