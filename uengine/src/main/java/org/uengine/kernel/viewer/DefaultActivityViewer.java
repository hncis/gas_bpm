package org.uengine.kernel.viewer;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.uengine.components.activityfilters.TimeDeterminationFilter;
import org.uengine.contexts.TextContext;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.EJBProcessInstance;
import org.uengine.kernel.ExecutionScopeContext;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.RoleMapping;
import org.uengine.kernel.SubProcessActivity;
import org.uengine.persistence.worklist.WorklistDAOType;
import org.uengine.processmanager.TransactionContext;
import org.uengine.ui.list.exception.UEngineException;
import org.uengine.util.UEngineUtil;
import org.uengine.util.dao.ConnectiveDAO;
import org.uengine.util.dao.IDAO;

/**
 * @author Jinyoung Jang
 */

public class DefaultActivityViewer implements ActivityViewer{

	public static final String VIEWOPTION_SHOW_HIDDEN_ACTIVITY = "show hidden activity";

	static int unfocusedOpacity = 100;
	static Hashtable viewerPool = new Hashtable();
	static Properties statusColors = new Properties();
		static{
			statusColors.setProperty(Activity.STATUS_READY, 	"#5F6061");
			statusColors.setProperty(Activity.STATUS_COMPLETED, "#888888");
			statusColors.setProperty(Activity.STATUS_RUNNING, 	"#027dbc");
			statusColors.setProperty(Activity.STATUS_FAULT, 	"#ee2020");
			statusColors.setProperty(Activity.STATUS_SKIPPED, 	"#2020aa");
			statusColors.setProperty(Activity.STATUS_SUSPENDED, "#20cccc");
			statusColors.setProperty(Activity.STATUS_TIMEOUT, 	"#aaaa20");
			statusColors.setProperty(Activity.STATUS_RETRYING, 	"#ee2020");
		}
	
	static ActivityViewer instance;
		public static ActivityViewer getInstance() {
			if (instance == null)
				instance = new DefaultActivityViewer();

			return instance;
		}
	
	public static String getActivityName(Activity activity, ProcessInstance instance, Map options) {
		String idHeader = null;
		String namespace = SubProcessActivityViewer.getAbsoluteParentTracingTag(options, instance, null);
		
//		if (UEngineUtil.isNotEmpty(namespace)) {
//			namespace += "_";
//		}
		
		if (instance instanceof EJBProcessInstance) {
			idHeader = "instance" + instance.getInstanceId();
		} else {
			idHeader = "definistion" + activity.getProcessDefinition().getId();
		}

		return namespace + "act" + activity.getTracingTag();
//		return idHeader + "_act_" + activity.getTracingTag();
	}
	
	public static String getActivityName(String tracingTag, ProcessInstance instance, Map options) throws Exception {
		String idHeader = null;
		String namespace = SubProcessActivityViewer.getAbsoluteParentTracingTag(options, instance, null);
		
		if (UEngineUtil.isNotEmpty(namespace)) {
			namespace += "_";
		}
		
		if (instance instanceof EJBProcessInstance) {
			idHeader = "instance" + instance.getInstanceId();
		} else {
			idHeader = "definistion" + instance.getProcessDefinition().getId();
		}

		return namespace + idHeader + "act" + tracingTag;
	}
	
	public StringBuilder render(Activity activity, ProcessInstance instance, Map options) {
		
		String imagePathRoot = getImagePathRoot(options);
		boolean isNoWrap = (options != null && options.containsKey("nowrap"));
		boolean isDecorated = (options != null && options.containsKey("decorated"));
		boolean definitionView = false;
		
		try {
			definitionView = (instance == null || Activity.STATUS_READY.equals(instance.getStatus()));
		} catch (Exception e1) { }
		
		StringBuilder sb = new StringBuilder();
		
		String statusColorAttr="";
		String status="";
//		String strActivityPaddingSize = "3";
		String strSideImg = "<img src=\"#\" class='flowchart-activity-sideimg' />";
		
//		if (!(activity instanceof ComplexActivity)) {
//			sb.append(
//					"<div id='" + getActivityName(activity, instance, options) + "'>"
//			);
//		}

		if (!definitionView) {
			status = "unresolved";
			
			try {
				status = instance.getStatus(activity.getTracingTag());
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			statusColorAttr = " style='background-color: " + getStatusColor(status) + ";'";
		}
			
		String ActivityStatusString = "";
		if (UEngineUtil.isNotEmpty(status)) {
			ActivityStatusString = status;
		}
		
		if (options.containsKey("highlight") && options.get("highlight").equals(activity.getTracingTag())) {
			sb.append("<div style='text-align:center;'><img style=\"cursor: pointer;\" src=\"" + imagePathRoot + "images/blinkarrow.gif\" border='0' /><br /></div>");
		}
		
		boolean showcount = (options != null && options.containsKey("show active activity count"));
		
		if (showcount) {
    		int actCount = getActiveTaskCount(instance, activity.getProcessDefinition().getId(), activity.getTracingTag());
            if (actCount > 0) {
                sb.append("count:" + actCount);
            }
		}
		
		if (isDecorated) {
			sb.append(
					"<table cellpadding='0' cellspacing='0' bgcolor='white'>"
					+ "<tr>"
						+ "<td class='flowchart-activity-tl'>" + strSideImg + "</td>"
						+ "<td class='flowchart-activity-t'></td>"
						+ "<td class='flowchart-activity-tr'>" + strSideImg + "</td>"
					+ "</tr>"
					+ "<tr>"
						+ "<td class='flowchart-activity-l' height='*'></td>"
						+ "<td class='flowchart-activity-c flowchart-activity-status-" + ActivityStatusString + "'>"
			);
		} else {
			sb.append("<div" +statusColorAttr+ ">");
			
			String boxBgColor = (activity.isHidden() ?  "#dddddd" : "white");
			sb.append("<div style='background-color: " + boxBgColor + "; text-align: center;'>");
		}
		
//		sb.append("<center>");
		
		if (options.containsKey("flowControl") && instance!=null) {
			if (appendFlowController(activity, instance, sb, options))
				sb.append("<br />");
		}

		if (options.containsKey("average processing time")) {
			try {
				long totalTime=0;
				long occurrence=0;

				Long[] totalTimeAndOccurrence = TimeDeterminationFilter.getTotalTimeAndOccurrence(activity);
				if (totalTimeAndOccurrence!=null) {
					totalTime=totalTimeAndOccurrence[0].longValue();
					occurrence=totalTimeAndOccurrence[1].longValue();
				}
				
				long elapsedTime = totalTime / occurrence;
				long hour = elapsedTime / 3600000L;
				long min = (elapsedTime % 3600000L) / 60000L;
				long sec = (elapsedTime % 60000L) / 1000L;
				
				String averageTime = 
					  (hour > 0 ? (hour + " hr "):"") 
					+ (min > 0 ? (min + " min "):"")
					+ (sec > 0 ? (sec + " sec"):"")
				;
				
				sb.append("<font color='gray'>APT:" + averageTime + "</font><br>");
				
			} catch(Exception e) { }
		}

		String locale = (String)options.get("locale");
		boolean isGrayOne = (!definitionView && Activity.STATUS_READY.equals(status));

		String tooltip="";
		try {
			tooltip = getToolTip(activity,instance,options);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		sb.append(
//				"<table cellpadding='0' cellspacing='0'><tr><td width=\"" + strActivityPaddingSize + "\"></td></tr></table>"
				"<img src=" + getIconImageURL(activity, instance, options) + tooltip +" border='0' "
				+ (isGrayOne ? " style='filter:alpha(opacity=50);'" : " " + "onerror='setDefaultActivityImage(this);'")
		);
		
		String prefferedWidthAttr = options.containsKey("prefferedActivityIconWidth") ? 
				" width="+options.get("prefferedActivityIconWidth") : "";
		String prefferedHeightAttr = options.containsKey("prefferedActivityIconHeight") ? 
				" height="+options.get("prefferedActivityIconHeight") : "";
			
		sb.append(prefferedWidthAttr + prefferedHeightAttr);
		
		String onClick = getOnClick(activity, instance, options);
		if (onClick!=null)
			sb.append(" onclick=\"" + onClick + "\"");
		
		sb.append(">");
		
		if (isDecorated) {
			sb.append(
					"<br />" 
					+ (isNoWrap ? "<div><nobr>":"") 
					+ (isGrayOne ? "<font color='#676666'>&nbsp;":"&nbsp;") 
					+ getLabel(activity, instance, options) 
					+ (isGrayOne ? "&nbsp;</font>":"&nbsp;") 
					+ (isNoWrap ? "</nobr></div>":"")
//					+ "<table cellpadding='0' cellspacing='0'><tr><td width=\"" + strActivityPaddingSize + "\"></td></tr></table>"
			);
		}
		
		if (instance != null) {
			if (!isNoWrap)
				sb.append("<br />");
			try {
				String bizStatus = activity.getBusinessStatus(instance);
			
				if (bizStatus != null)
					sb.append("<font size='-2'>" + bizStatus + "</font><br>");
			} catch(Exception e) { }
			
			sb.append("<font size='-2' color=" + getStatusColor(status) + "><strong>");
			
			//show faults
			try {
				if (instance.isFault(activity.getTracingTag())) {
					sb.append(instance.getFault(activity.getTracingTag()));
					
				} else if (!Activity.STATUS_READY.equals(status) &&  isDecorated) {
					String localStatus = GlobalContext.getLocalizedMessageForWeb(
							"status_" + status, String.valueOf(options.get("locale")),
							status
					);
					
					if (Activity.STATUS_RUNNING.equals(status)) {
						sb.append("<blink>"+localStatus+"</blink>");
					} else {
						if ("".equals(status) && options.containsKey("current running count")) {
							try {
								TransactionContext tc =  instance.getProcessTransactionContext();
								String defVerId = activity.getProcessDefinition().getId();
								int count = WorklistDAOType.getCurrnetRunningCount(tc, defVerId, activity.getTracingTag());
								sb.append("("+count+")");
							} catch(Exception e) { }
						} else {
							sb.append(localStatus);
						}
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
				sb.append("can't load fault");
			}
			
			sb.append("</strong></font>");
			
		}
		
		//show details
		String activityDetailDivId = getDetailDivId(activity, instance, options);
		sb.append("<div id='" + activityDetailDivId + "' class='flowchart-activity-detail'>" + getDetails(activity, instance, options) + "</div>");
		
//		sb.append("</center>");
		if (isDecorated) {
			sb.append(
				"</td>"
				+ "<td class='flowchart-activity-r'></td>" 
			+ "</tr><tr> "
				+ "<td class='flowchart-activity-bl'></td>"
				+ "<td class='flowchart-activity-b'></td>"
				+ "<td class='flowchart-activity-br'></td>"
			+ "</tr>"
			+ "</table>"
			);
		} else {
			sb.append("</div></div>");
		}
		
		/**
		 * if this activity has a thread connected to an execution scope, render the 
		 * execution scope activities.
		 */
		if (options.containsKey("executionScopeHT")) {
			Map executionScopeHT = (Map)options.get("executionScopeHT");
			
			if (executionScopeHT.containsKey(activity.getTracingTag())) {
				List<ExecutionScopeContext> executionScopeList = (List<ExecutionScopeContext>)executionScopeHT.get(activity.getTracingTag());
				
				ExecutionScopeContext originalExecutionScopeContext = instance.getExecutionScopeContext();
				for (int i=0; i<executionScopeList.size(); i++) {
					
					
					ExecutionScopeContext esc = (ExecutionScopeContext)executionScopeList.get(i);
					Activity executionScopeActivity = activity.getProcessDefinition().getActivity(esc.getRootActivityTracingTag());
					esc.setRootActivityInTheScope(executionScopeActivity);
					
					instance.setExecutionScopeContext(esc);
					ActivityViewer viewerForExecutionScope = createViewer(executionScopeActivity);
					
					boolean thisIsHighlightedExecutionScope = originalExecutionScopeContext!=null && originalExecutionScopeContext.getExecutionScope().equals(esc.getExecutionScope());
					String tempHighlight = null;
					if(options.containsKey("highlight") && !thisIsHighlightedExecutionScope){
						tempHighlight = (String)options.get("highlight");
						options.remove("highlight");
					}

					String statusColorForExecutionScopeActivity = "black";
					try {
						statusColorForExecutionScopeActivity = getStatusColor(executionScopeActivity.getStatus(instance));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					StringBuilder viewerHtmlOfExecutionScope = viewerForExecutionScope.render(executionScopeActivity, instance, options);
	
					String idForExecutionScopeDiv = "executionScope_" + esc.getExecutionScope();
					String eventOnClick = "openDetailActivity('" + idForExecutionScopeDiv + "'); drawLoopLines();";
					
						sb.insert(0,
								"<div style='text-align:center;' onclick=\"" + eventOnClick 
								+ "\"><nobr><img align='middle' src=\""
								+ imagePathRoot + "images/event.gif\"><font size='-3' color='"
								+ statusColorForExecutionScopeActivity + "'>"
								+ (esc.getName()!=null ? esc.getName() : executionScopeActivity.getName().getText(locale)) 
								+ "</font><img src=" + imagePathRoot + "images/more-arrow.triangle.gif></nobr></div>"

								+ "<div id=" + idForExecutionScopeDiv 
								+ (thisIsHighlightedExecutionScope ? "" : " style='display:none;'") + ">"
								+ "<img align='middle' src=" + imagePathRoot + "images/arrow_event_dotted.gif><br>"
						);
							
							if (executionScopeActivity instanceof ComplexActivity) { 
								sb.append("<div class='flowchart-activity-complex'>" + viewerHtmlOfExecutionScope + "</div>");
							} else {
								sb.append(viewerHtmlOfExecutionScope);
							}
							
						sb.append("</div>");
					
					instance.setExecutionScopeContext(originalExecutionScopeContext);
					if (tempHighlight != null) {
						options.put("highlight", tempHighlight);
					}
				}
			}
		}
		
//		if (!(activity instanceof ComplexActivity)) {
//			sb.append("</div>");
//		}
		
		return sb;
	}
	
	protected String getToolTip(Activity activity ,ProcessInstance instance, Map options) throws Exception{
		String tooltip="";
		String local = options.get("locale")==null ? GlobalContext.DEFAULT_LOCALE : (String) options.get("locale");
		
		if (options.containsKey("mouseOverPopup")) {
			String pupupContentType = (String) options.get("mouseOverPopup");
			if ("description".equals(pupupContentType)) {
				String description = activity.getDescription().getText(local);
				
				if (!UEngineUtil.isNotEmpty(description)) {
					description = "No description";
				}
				
				tooltip = " activityDescription=\""
//					+ activity.getName() + ";"
					+ description
					+ "\"";
			}
		} else {
		
			if (activity instanceof HumanActivity) {
					
				Map map = activity.getActivityDetails(instance, (String) options.get("locale"));
				Vector<String> vector = new Vector<String>();
				if (map!=null) {
					for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
						String key = iter.next();
						Object value = map.get(key);
						if (value instanceof String) {
							vector.add(key + "==" + value);
						} else if (value instanceof TextContext) {
							vector.add(key + "==" + ((TextContext)value).getText(local));
						}
					}
				}
				String endpoint = "";
				StringBuffer resourceName = new StringBuffer();
				String imgPath = "";
				StringBuffer desc = new StringBuffer();
				String otherValue = "";
				String actName = "";
				
				if (
						Activity.STATUS_COMPLETED.equals(activity.getStatus(instance)) ||
						Activity.STATUS_RUNNING.equals(activity.getStatus(instance))
				) {
					RoleMapping rm = ((HumanActivity)activity).getActualMapping(instance);
					endpoint = rm.getEndpoint();
					
					if(!UEngineUtil.isNotEmpty(rm.getUserPortrait())) 
						rm.fill(instance);
					
					imgPath = "img=="+rm.getUserPortrait();
					resourceName.append("username=="+rm.getResourceName());
						
				} else {
					for (int i=0;i<vector.size();i++) {
						String actInfo = (String)vector.get(i);
						
						if (	actInfo.indexOf("actual worker") > -1 || actInfo.indexOf("처리자") > -1) {
							String userString = actInfo.split("==")[1];
							
							if (actInfo.indexOf("/") > -1) {
								String[] userInfo =  userString.split("/");
								resourceName.append("username==" + userInfo[0]);
								endpoint = userInfo[1];
							}
						}
					}
		
					if ("".equals(endpoint)) {
						resourceName.setLength(0);
						resourceName.append("username==unknown");
						imgPath = "img==" + GlobalContext.WEB_CONTEXT_ROOT + "resources/images/portraits/unknown_user.gif";
							
					} else {
						RoleMapping rm = RoleMapping.create();
						rm.setEndpoint(endpoint);
						rm.fill(instance);
						imgPath = "img=="+rm.getUserPortrait();
					}
				}
				
				desc.append("description==");
				for (int i = (vector.size() - 1); i >= 0  ; i--) {
					String values = "" + vector.get(i);
					if (values.indexOf("actual worker") > -1 || values.indexOf("처리자") > -1) {
						continue;
					}
					if (values.indexOf("started date") > -1) {
						desc.setLength(0);
						desc.append("description==");
					}
					if (UEngineUtil.isNotEmpty(values)) 
						otherValue += values + ";";
				}
				
				desc.append(activity.getDescription().getText(local)!=null ? activity.getDescription().getText(local) : activity.getName().getText(local));
				actName = "actname==" + activity.getName().getText(local);
				
				tooltip = " titles=\"" 
						+ imgPath + ";" 
						+ resourceName + ";" 
						+ actName + ";" 
						+ desc + ";" 
						+ otherValue + "\""
				;
			} else {
				tooltip = " title=" + activity.getName().getText(local);
			}
		}
		return tooltip;
	}
	
	protected String getDetailDivId(Activity activity, ProcessInstance instance, Map options){
		String activityDetailDivId = "act_dt_" 
			//+ (instance!=null ? instance.getInstanceId() : activity.getProcessDefinition().getId()) //old way for getting unique tracingtag space
			//+ "_" + activity.getTracingTag()
			+ SubProcessActivityViewer.getAbsoluteParentTracingTag(options, instance, activity.getTracingTag())
		;
		
		return activityDetailDivId;
	}
	
	private StringBuffer getActivityClickedScript(ProcessInstance instance, Activity activity, Map options) {

		StringBuffer propStrBuf = new StringBuffer();
		
		try {
			String defId = activity.getProcessDefinition().getBelongingDefinitionId();
			String defVersionId = activity.getProcessDefinition().getId();
			
			propStrBuf.append(
					"; onActivityClicked('"	+ defId + "', '" + defVersionId + "', '"
					+ activity.getTracingTag()+ "', "
					+ (instance != null ? "'" + instance.getInstanceId() + "'" : "null")
					+ ", '" + getActivityPropertyString(activity, instance, options) + "')"
			);

		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return propStrBuf;
	}
	
	public String getOnClick(Activity activity, ProcessInstance instance, Map options){
	//	String instanceId = (instance!=null ? ("\"" + instance.getInstanceId() + "\"") : "null");
		StringBuffer onClickJS = new StringBuffer();

		try{
			String activityDetailDivId = getDetailDivId(activity, instance, options);
			
			if(options.containsKey("decorated"))
			{
				onClickJS.append("openDetailActivity('" + activityDetailDivId + "')");
				ViewerOptions viewerOptions = new ViewerOptions();
				if (options.containsKey(viewerOptions.SWIMLANE)) {
					onClickJS.append("; setRowHeight(this, '" + options.get(viewerOptions.SWIMLANE) + "')");
				}
				
				if (options.containsKey("enableEvent_onActivityClicked")) {
					onClickJS.append(getActivityClickedScript(instance, activity, options));
				}
				onClickJS.append("; " + "drawAll();");
			}
		} catch(Exception e) {}
		
		return onClickJS.toString();
	}
	
	public String getLabel(Activity activity, ProcessInstance instance, Map options) {
		String locale 				= (String) options.get("locale");
		String actName 			= null;
		
		if (activity instanceof SubProcessActivity && !(activity.getRootActivity() instanceof ProcessDefinition)) {
			Activity actTemp = activity.getRootActivity();
			actName = actTemp.getName().getText(locale);
		} else {
			actName = activity.getName().getText(locale);
		}
		
		if (UEngineUtil.isNotEmpty(actName)) {
			actName = actName.trim();
		} else {
			actName = "";
		}
		
		int intMaxLength = (Integer) (options.containsKey("intMaxLabelLength") ? options.get("intMaxLabelLength") : -1);
		String strSeperator = "\n";
		
		if ( 0 < intMaxLength) {
			
			String[] listStrActivityLabel = actName.split("\\s");
			String tmpActivityLabel = "";
			for (int i = 0; i < listStrActivityLabel.length; i++) {
				String strTmp = listStrActivityLabel[i];
				
				if (strTmp.length() > 1) {
					int intTmpLength = strTmp.length();
					
					if (intTmpLength > intMaxLength) {
						int iPointer = intTmpLength / 2;
						strTmp = strTmp.substring(0, iPointer) + strSeperator + strTmp.substring(iPointer, intTmpLength);
					} 
					tmpActivityLabel += strTmp + strSeperator;
				} else {
					tmpActivityLabel += strTmp + " ";
				}
			}
			actName = tmpActivityLabel;

		}
		
		String strActivitylabel = actName.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll(strSeperator, "<br />");
		
		return strActivitylabel;
	}
	
	public String getIconImageURL(Activity activity, ProcessInstance instance, Map options) {
		String imagePathRoot = getImagePathRoot(options);
		
		if (UEngineUtil.isNotEmpty(activity.getActivityIcon())) 
			return activity.getActivityIcon();
		
		Class activityCls = activity.getClass();
		String clsName = activityCls.getName().substring( activityCls.getName().lastIndexOf(".")+1);	

		return imagePathRoot + "images/"+ clsName +".png";
	}
	
	protected String getDetails(Activity activity, ProcessInstance instance, Map options){
		StringBuffer sb = new StringBuffer();
		try {
			Map map = activity.getActivityDetails(instance, (String) options.get("locale"));
			if (map != null) {
				sb.append("<table width=\"100%\" border=\"0\"  cellpadding='0' cellspacing='0'><tr height=\"1\"><td align=\"center\" colspan=\"3\" height=\"1\" class=\"bgcolor_head_underline\"></td></tr>");			

				for (Iterator iter = map.keySet().iterator(); iter.hasNext(); ) {
					String key = (String)iter.next();
					sb.append(
							"<tr height=\"30\">"
								+ "<td nowrap='nowrap' align=\"left\" bgcolor=\"#f4f4f4\" width=\"68\">"
									+ "&nbsp;<font size=\"-2\">" + key +"</font></td>"
								+ "<td width=\"*\" align=\"left\" bgcolor=\"white\" >"
								+ "<nobr>" + map.get(key) + "</nobr></td>"
							+ "</tr><tr height=1>"
								+ "<td align=\"center\" colspan=\"2\" height=\"1\" class=\"bgcolor_head_underline\"></td>"
							+ "</tr>"
					);
				}

				StringBuffer parameterBuf = null;
				for(Iterator iter = options.keySet().iterator(); iter.hasNext(); ){
					String key = (String)iter.next();
					
					String userEventPrefix = "enableUserEvent_";
					
					if(key.startsWith(userEventPrefix)){
						String eventName = null;
						String className = null;
						String[] parts = key.split("_");
						
						if(parts.length > 1){
							eventName = parts[1];
						}
						
						if(parts.length > 2){
							className = parts[2]; //ex) "enableUserEvent_refreshMultipleInstances_org.uengine.kernel.SubProcessActivity";
							Class theActivityClass = Class.forName(className);
							if(!theActivityClass.isAssignableFrom(activity.getClass())){
								continue;
							}
						}
						
						String eventLabel = ""+options.get(key);
						if(parameterBuf==null){
							parameterBuf = new StringBuffer();
							try{
								String defId = activity.getProcessDefinition().getBelongingDefinitionId();
								String defVersionId = activity.getProcessDefinition().getId();
								
								parameterBuf.append(
										"(\"" + defId + "\", \"" 
										+ defVersionId + "\", \"" 
										+ activity.getTracingTag() + "\", "
										+ (instance!=null ? "\"" + instance.getInstanceId() + "\"" : "null")
										+ ", \"" + getActivityPropertyString(activity, instance, options) + "\");"
								);
							}catch(Exception e){
							}
						}
						
						sb.append(
								"<tr height=\"30\">"
								+ "<td colspan=\"3\">"
								+ "<a href='javascript:" + eventName + parameterBuf + "'>" + eventLabel + "</a>"
								+ "</td>"
							+ "</tr>"
							+ "<tr height=\"1\"><td align=\"center\" colspan=\"3\" height=\"1\" class=\"bgcolor_head_underline\"></td></tr>"
						);			
					}
				}
				
				sb.append("</table>");
				return sb.toString();
			}			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return "";
	}
	
	public static ActivityViewer createViewer(Activity act){
		Class activityCls = act.getClass();
		//String clsName = UEngineUtil.getComponentClassName(activityCls, "viewer");
		
		ActivityViewer viewer = null;
		
		if(viewerPool.containsKey(activityCls))
			return (ActivityViewer)viewerPool.get(activityCls);
		
/*		try{		
			Class viewerCls = Class.forName(clsName);
			viewer = (ActivityViewer)viewerCls.newInstance();
		
		}catch(Exception e){
			//e.printStackTrace();
			
			viewer = DefaultActivityViewer.getInstance();
		}*/
		
		viewer = (ActivityViewer)(UEngineUtil.getComponentByEscalation(activityCls, "viewer", DefaultActivityViewer.getInstance()));
		viewerPool.put(activityCls, viewer);
		
		return viewer;
	}
	
	public static String getStatusColor(String status){		
		return statusColors.getProperty(status);
	}
	
	public static boolean appendFlowController(Activity activity, ProcessInstance instance, StringBuilder sb, Map options){
		String imagePathRoot = getImagePathRoot(options);
		
		String status ="unresolved";
		try{
			status = instance.getStatus(activity.getTracingTag());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		boolean bAppended = false;
			
		if(Activity.isCompensatable(status)){
			sb.append("<a href=\"javascript:compensate('"+activity.getTracingTag()+"')\"><img src=" + imagePathRoot + "images/rewind.gif border=0></a>");
			bAppended = true;
		}

		if(Activity.isSkippable(status)){
			sb.append("<a href=\"javascript:skip('"+activity.getTracingTag()+"')\"><img src=" + imagePathRoot + "images/forward.gif border=0></a>");
			bAppended = true;
		}

		if(Activity.isResumable(status)){
			sb.append("<a href=\"javascript:resume('"+activity.getTracingTag()+"')\"><img src=" + imagePathRoot + "images/resume.gif border=0></a>");
			bAppended = true;
		}
		else if(Activity.isSuspendable(status)){
			sb.append("<a href=\"javascript:suspend('"+activity.getTracingTag()+"')\"><img src=" + imagePathRoot + "images/suspend.gif border=0></a>");
			bAppended = true;
		}
		
		return bAppended;		
	}
	
	public static String getImagePathRoot(Map options){
		String strImgPathRoot = "";
		try {
			strImgPathRoot = ((ViewerOptions) options).getImagePathRoot();
		} catch (Exception e) {
			strImgPathRoot = (String) (options.containsKey("imagePathRoot") ? options.get("imagePathRoot") : "");
		}
		return strImgPathRoot;
	}

	public StringBuffer getActivityPropertyString(Activity activity, ProcessInstance instance, Map options) throws Exception{ 
		StringBuffer sbActivityPropertyString = new StringBuffer();
	
//		웹에서 사용하는 곳도 없는 의미없는 PropertyString 이다. ' 와 " 로 인해 괜한 자바스크립트 에러만 일으킴으로 주석처리
//		sbActivityPropertyString.append("instanceName=").append(
//				instance != null ? instance.getName() : "").append(",");

//		sbActivityPropertyString.append("activityName=").append(activity.getName().getText()).append(",");

		sbActivityPropertyString.append("activityType=").append(activity.getClass().getName()).append(",");

//		sbActivityPropertyString.append("definitionName=").append(
//				activity.getProcessDefinition().getName().getText()).append(",");

		sbActivityPropertyString.append("status=").append(activity.getStatus(instance)).append(",");
		return sbActivityPropertyString;
	}
	
	private int getActiveTaskCount(ProcessInstance instance, String defId, String trctag) {
        int resultCount = 0;
        StringBuffer selectSQL = new StringBuffer();
        
        selectSQL.append(" SELECT  COUNT(*) AS CNT         ").append("\n")
                 .append("   FROM  BPM_PROCINST PI         ").append("\n")
                 .append("        ,BPM_WORKLIST WL         ").append("\n")
                 .append("  WHERE  PI.DEFVERID = ?defverid ").append("\n")
                 .append("    AND  PI.INSTID = WL.INSTID   ").append("\n")
                 .append("    AND  WL.TRCTAG = ?trctag     ").append("\n")
                 .append("    AND WL.STATUS <> 'COMPLETED' ").append("\n");
        
        try {
            IDAO idao = ConnectiveDAO.createDAOImpl(instance.getProcessTransactionContext(), selectSQL.toString(), IDAO.class);
            idao.set("defverid", defId);
            idao.set("trctag", trctag);
            idao.select();
            
            if (idao.next()) {
                resultCount = idao.getInt("CNT");
            }
        } catch (Exception e) {
            e.printStackTrace();
            new UEngineException(e, "setTag Exception");
        }
        
        return resultCount;
    }

}