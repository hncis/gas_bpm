package org.uengine.kernel.viewer.swimlane;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.Condition;
import org.uengine.kernel.EJBProcessInstance;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleMapping;
import org.uengine.kernel.SubProcessActivity;
import org.uengine.kernel.SwitchActivity;
import org.uengine.kernel.viewer.ActivityViewer;
import org.uengine.kernel.viewer.DefaultActivityViewer;
import org.uengine.kernel.viewer.SubProcessActivityViewer;
import org.uengine.kernel.viewer.ViewerOptions;
import org.uengine.processpublisher.graph.GraphActivity;
import org.uengine.processpublisher.graph.SwimLaneCoordinate;
import org.uengine.processpublisher.graph.SwimLanePoint;


public class SwimlaneViewer implements GraphActivityViewer {
	String[] sBackGroundColor = {
			"#CBD7EA", "#FFFFFF"
	};
	
	Hashtable roleList;
    Vector<GraphActivity> graphList;
	Map noDecoratedOption = new Hashtable();

	private int roleCount;
	private int distanceY = 80;
	private String imageRootPath = "";
	
	private StringBuilder getRoleInformationCell(Role role, ProcessInstance instance) {
		StringBuilder sb = new StringBuilder();
		RoleMapping rm = null;
		String sEp = "unknown_user";
		String sName = "";

		try {
			if (instance instanceof EJBProcessInstance) {
				EJBProcessInstance epi =  (EJBProcessInstance) instance;
				rm = epi.getRoleMapping(role.getName());
			} else {
				rm = role.getMapping(instance);
			}
		} catch (Exception e) {}

		
		if (rm != null) {
			sName = rm.getResourceName();
			sEp = rm.getEndpoint();
		}
		
		sb.append(
				"<td>"
				+ "<table style='border:1px solid #d0d0d0' width='100%' border='0' cellpadding='0' cellspacing='0'>"
				+ "<col span='1' width='8'>"
				+ "<col span='1' width='*'>"
				+ "<col span='1' width='8'>"
				+ "<tr>"
				+ "<td height='8'><img src='" + imageRootPath + "images/swinlinetop_01.gif' width='8' height='8' /></td>"
				+ "<td height='8' background='" + imageRootPath + "images/swinlinetop_02.gif'></td>"
				+ "<td height='8'><img src='" + imageRootPath + "images/swinlinetop_03.gif' width='8' height='8' /></td>"
				+ "</tr><tr>"
					+ "<td background='" + imageRootPath + "images/swinlinetop_04.gif'></td>"
					+ "<td align='center' valign='bottom' background='" + imageRootPath + "images/swinlinetop_05.gif'>"
					+ "<table width='100%' height='100%' border='0' cellspacing='2' cellpadding='0'>"
					+ "<tr height='45'><td align='center' width='35'><img src='" + GlobalContext.WEB_CONTEXT_ROOT + "resources/images/portraits/" + sEp + ".gif' width='35' height='45' onerror='setUnkwonImage(this);' />" 
					+ "</td><td align='center'>" + sName + "<br />(" + sEp + ")</td>"
					+ "</tr><tr>"
					+ "<td align='center' colspan='2'><table width='100%' border='0' cellspacing='0' cellpadding='0' style='border:1px solid #dddddd; background:#f7f7f7;'>"
					+ "<tr><td align='center' style=' padding:2px;'><img src='" + imageRootPath + "images/i_dot01.gif'/>&nbsp;<strong>"+role.getDisplayName()+"</strong></td></tr></table></td></tr></table></td>"
					+ "<td background='" + imageRootPath + "images/swinlinetop_06.gif'></td>"
				+ "</tr><tr>"
				+ "<td height='8'><img src='" + imageRootPath + "images/swinlinetop_07.gif' /></td>"
				+ "<td height='8' background='" + imageRootPath + "images/swinlinetop_08.gif'></td>"
				+ "<td height='8'><img src='" + imageRootPath + "images/swinlinetop_09.gif' /></td>"
				+ "</tr></table>"
				+ "</td>"
		);
		
		return sb;
	}
	
	public String getActivityWebId(GraphActivity graphAct, ProcessInstance instance, Map options) {
		String instId = null;
		String divId = null;
		Activity activity = graphAct.getReferenceActivity();
		
		if (instance instanceof EJBProcessInstance) {
			instId = "instance_" + instance.getInstanceId();
		} else {
			instId = "definistion_" + activity.getProcessDefinition().getId();
		}
		
		instId += "_act_" + SubProcessActivityViewer.getAbsoluteParentTracingTag(options, instance, null);
		
		if (graphAct.isStartGraphActivity()) {
			divId = instId + "start";
		} else if (graphAct.isEndGraphActivity()) {
			divId = instId + "end";
		} else {
			divId = instId + activity.getTracingTag();
		}
		
		return divId;
	}
	
	private StringBuffer getActivityContent(GraphActivity graphAct, ProcessInstance instance, Map options, boolean isVirtical) {
		StringBuffer sb = new StringBuffer();

		ActivityViewer sav = null;
		Activity act = graphAct.getReferenceActivity();
		String divId = getActivityWebId(graphAct, instance, options);
		

		if (act instanceof SubProcessActivity) {
			sav = DefaultActivityViewer.createViewer(act);
		} else {
			sav = new DefaultActivityViewer();
		}
		
		sb.append("<table id='" + divId + "' cellspacing='0' cellpadding='0'><tr>");
		if (isVirtical) {
			sb.append("<td align='center'>");
		} else {
			sb.append("<td>");
		}
		
		if (graphAct.isStartGraphActivity()) {
			sb.append("<img src='"+imageRootPath+"images/start.gif'>");
		} else if (graphAct.isEndGraphActivity()) {
			sb.append("<img src='"+imageRootPath+"images/end.gif'>");
		} else {
			Vector<GraphActivity> incommings = graphAct.getPrevious();
			
			if (incommings.get(0).getReferenceActivity() instanceof SwitchActivity) {
				SwitchActivity switchActivity = (SwitchActivity) incommings.get(0).getReferenceActivity();
				List<Activity> children = switchActivity.getChildActivities();
				
				appendSwitchActivityDescription(act, switchActivity, children, sb);
			}
			
			sb.append(sav.render(act,instance, (act instanceof ComplexActivity ? noDecoratedOption : options)));
		}

		sb.append("</td></tr></table>");
		return sb;
	}
	
	private StringBuilder renderVirtical(GraphActivity graph, SwimLaneCoordinate coodinate,ProcessInstance instance, Map options) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<table cellpadding='0' cellspacing='0' style='margin:0 auto; margin-top: 5px;'><tr>");
		
		for (int i =0; i < roleCount; i++) {
			Role role = (Role) roleList.get(new Integer(i+1));
			sb.append(getRoleInformationCell(role, instance));
		}
		
		sb.append("</tr><tr>");
		
		for (int i =0; i < roleCount; i++) {
			Vector<GraphActivity> graphXListTemp = new Vector<GraphActivity>();
			sb.append(
					"<td style='border:1px solid #d0d0d0;' align='center' valign='top'>"
					+ "<table border='0' height='100%' id='tableUserRole' cellpadding='10' cellspacing='10'>"
			);
			
			for (int j = 0; j < graphList.size(); j++) {
				SwimLanePoint sp = ((GraphActivity)graphList.get(j)).getSLPoint();
				if (sp.pointX == i + 1) {
					graphXListTemp.add(graphList.get(j));
				}
			}
			
			graphXListTemp = OrderByPointY(graphXListTemp);
			int preY=0;
			
			for (int j=0; j < graphXListTemp.size(); j++) {
				SwimLanePoint sp = ((GraphActivity) graphXListTemp.get(j)).getSLPoint();
				
				if(preY >= sp.pointY)	continue;
				Vector<GraphActivity> graphXYListTemp = new Vector<GraphActivity>();
				
				for (int k=0; k < graphXListTemp.size(); k++) {
					SwimLanePoint sp2 = ((GraphActivity)graphXListTemp.get(k)).getSLPoint();
					if (sp.pointY == sp2.pointY) {
						graphXYListTemp.add(graphXListTemp.get(k));
					}
				}
				
				for (int k = preY; k < sp.pointY-1; k++) {
					sb.append("\n<tr>");
					//for (int l = 0; l < graphXYListTemp.size(); l++) {
						sb.append("<td width='100%' height='" + distanceY + "' ></td>");
					//}
					sb.append("</tr>");
				}

				//���
				sb.append("\n<tr>");
				
				for (int k = 0; k < graphXYListTemp.size(); k++)
				{
					GraphActivity graphAct = graphXYListTemp.get(k);
					
					for (int pointIndexX = k + 1; pointIndexX < graphAct.getSLPoint().pointIndexX; pointIndexX++)
					{
						sb.append("\n\t<td height=\""+distanceY+"\"></td>");
					}
					
					sb.append("\n\t<td height=\""+distanceY+"\" valign=\"top\" align='center'>");
					sb.append(getActivityContent(graphAct, instance, options, true));
					sb.append("\n\t\t</td>");
				}
				sb.append("\n\t</tr>");
				preY=sp.pointY;
			}
			sb.append("</table></td>");
		}
	
		sb.append("</tr></table>");
		return sb;
	}
	
	private StringBuilder renderHorizontal(GraphActivity graph, SwimLaneCoordinate coodinate,ProcessInstance instance, Map options) {
		StringBuilder sb = new StringBuilder();
		int maxY = 0;
		
		for (GraphActivity tempGraph : graphList)
		{
			int pointY = tempGraph.getSLPoint().pointY;
			if (pointY > maxY)
			{
				maxY = pointY;
			}
		}

		sb.append("<table cellpadding='0' cellspacing='0' style='border: 1px solid #d0d0d0; margin: 5px;'>");
		int preY = 0;
		for (int i =0; i < roleCount; i++) {
			sb.append("<tr>");
			
			Role role = (Role) roleList.get(new Integer(i+1));
			sb.append(getRoleInformationCell(role, instance));

			Vector<GraphActivity> graphXListTemp = new Vector<GraphActivity>();
			for (int j = 0; j < graphList.size(); j++) {
				SwimLanePoint sp = graphList.get(j).getSLPoint();
				
				if (sp.pointX == i + 1) {
					graphXListTemp.add(graphList.get(j));
				}
			}
			
			graphXListTemp = OrderByPointY(graphXListTemp);
			preY = 0;
			sb.append("<td valign='middle'>");
			
			for (int j=0; j < graphXListTemp.size(); j++) {
				SwimLanePoint sp = graphXListTemp.get(j).getSLPoint();
				if(preY >= sp.pointY) continue;
				
				Vector<GraphActivity> graphXYListTemp = new Vector<GraphActivity>();
				
				for (int k=0; k < graphXListTemp.size(); k++) {
					SwimLanePoint sp2 = ((GraphActivity) graphXListTemp.get(k)).getSLPoint();
					
					if (sp.pointY == sp2.pointY) {
						graphXYListTemp.add(graphXListTemp.get(k));
					}
				}
				
				while (preY < sp.pointY) {
					sb.append("</td><td valign='middle'>");
					preY++;
				}

				for (GraphActivity graphAct : graphXYListTemp) {
					sb.append("<div style='margin:15px 25px;'>" + getActivityContent(graphAct, instance, options, false) + "</div>");
				}
			}
			
			sb.append("</td><td colspan='" + (maxY - preY + 1) + "'>");
			preY = maxY;

			sb.append("</td></tr>");
			if(i != roleCount - 1) {
				sb.append("<tr><td height='1' bgcolor='#d0d0d0' colspan='" + (maxY + 2) + "'></td></tr>");
			}
		}
		
		sb.append("</table>");
		return sb;
	}
	
	private String appendSwitchActivityDescription(Activity activity, SwitchActivity switchActivity, List<Activity> children, StringBuffer sb) {
		String description = "";
		
		for (int childCount = 0; childCount < children.size(); childCount++) {
			Activity childActivity = children.get(childCount);

			if (isFirstSwitchActivity(activity, childActivity)) {
				Condition condition = switchActivity.getConditions()[childCount];
				sb.append("[ "+condition.getDescription()+" ]");
				break;
			}
		}
		return description;
	}
	
	private boolean isFirstSwitchActivity(Activity activity, Activity childActivity) {
		boolean isFirst = false;
		
		if (activity == childActivity) {
			isFirst = true;
		} else if (childActivity instanceof ComplexActivity) {
			List<Activity> childrenOfActivity = ((ComplexActivity) childActivity).getChildActivities();
			
			for (int i = 0; i < childrenOfActivity.size(); i++) {
				
				if (isFirstSwitchActivity(activity, childrenOfActivity.get(i))) {
					isFirst = true;
					break;
				}
			}
		}
		
		return isFirst;
	}
	
	public Vector OrderByPointY(Vector<GraphActivity> v) {
		Hashtable ht = new Hashtable();
		int vSize = v.size();
		
		for (int i=0; i <vSize ; i++) {
			GraphActivity ga= v.get(i);
			Vector alignV;
			int y = (ga.getSLPoint()).pointY;
			
			if (ht.containsKey(new Integer(y))) {
				alignV = (Vector)ht.get(new Integer(y));
			} else {
				alignV = new Vector();
			}
			alignV.add(ga);
			ht.put(new Integer(y),alignV);
		}
		
		int i=0, y=0;
		v.removeAllElements();
		
		while (true) {
			if(ht.containsKey(new Integer(i))){
				Vector alignV = (Vector) ht.get(new Integer(i));
				
				for (int k=0; k < alignV.size();k++) {
					GraphActivity ga = (GraphActivity)alignV.get(k);
					v.add(ga);
					y++;
				}
			}
			if ((y) == vSize) break;
			i++;
		}
		
		return v;
	}

	
	public StringBuilder render(GraphActivity graph, SwimLaneCoordinate coodinate,ProcessInstance instance, Map options) {
		StringBuilder sb = new StringBuilder();
		
        roleList = coodinate.getRoleList();
        graphList = coodinate.getGraphList();
		roleCount= roleList.size();
		
		if (roleCount == 0) {
			sb.append("This process doesn't have any role.");
		} else {
			noDecoratedOption.putAll(options);
			noDecoratedOption.remove("decorated");

			ViewerOptions viewerOptions = new ViewerOptions();

			//imagePathRoot
			if(options.containsKey(viewerOptions.IMAGE_PATH_ROOT))
				imageRootPath =  (String)options.get(viewerOptions.IMAGE_PATH_ROOT);
			
			if(viewerOptions.HORIZONTAL.equals(options.get(viewerOptions.SWIMLANE))) {
				sb.append(renderHorizontal(graph, coodinate, instance, options));
			} else {
				sb.append(renderVirtical(graph, coodinate, instance, options));
			}
		}
		
		
		return sb;
	}
}
