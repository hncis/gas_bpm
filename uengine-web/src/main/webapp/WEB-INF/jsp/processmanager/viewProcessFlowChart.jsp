<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.uengine.kernel.RoleMapping"%>
<%@page import="org.uengine.kernel.Role"%>
<%@page import="org.uengine.contexts.HtmlFormContext"%>
<%@page import="java.io.Serializable"%>
<%@page import="org.uengine.kernel.ProcessVariable"%>
<%@page import="org.uengine.kernel.GlobalContext"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.uengine.security.AclManager"%>
<%@page import="org.uengine.kernel.ProcessInstance"%>
<%@page import="org.uengine.util.UEngineUtil"%>
<%@page import="org.uengine.processmanager.ProcessDefinitionRemote"%>
<%@page import="org.uengine.processmanager.ProcessManagerRemote"%>
<%@page import="org.uengine.kernel.viewer.ViewerOptions"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>

<jsp:useBean id="processManagerFactory" scope="application" class="org.uengine.processmanager.ProcessManagerFactoryBean" />

<%
ProcessManagerRemote pm = null;

try{
    pm = processManagerFactory.getProcessManagerForReadOnly();

    String pd = (request.getParameter("processDefinition"));
    String pdVer = (request.getParameter("processDefinitionVersionID"));
    String folder = (request.getParameter("folder"));
    //  String pi = (request.getParameter("instanceId"));
    String pi = null;   //  SubProcess ? ??.
    String instanceIds = (String)request.getAttribute("instId");
    String selectedInstanceId = (request.getParameter("selectedInstanceId"));
    String[] arrInstanceIds = null;
    if (instanceIds != null && instanceIds.indexOf(";") > -1) {
        arrInstanceIds = instanceIds.split(";");
        if (selectedInstanceId != null && !"".equals(selectedInstanceId)) {
            pi = selectedInstanceId;
        } else {
            pi = arrInstanceIds[0];
        }
    } else {
        pi = instanceIds;
    }

    String locale = (request.getParameter("locale"));
    if (!UEngineUtil.isNotEmpty(pi)) pi = null;
    if (!UEngineUtil.isNotEmpty(locale)) locale = (String)session.getAttribute("loggedUserLocale");
    String chart ="no chart is available";
    
    /***********************************************************************/
    /*                            Get version list                         */
    /***********************************************************************/

    ProcessDefinitionRemote[] arrPdr = null;
    if (pi == null) {
        if (pd == null) {
            return;
        }
        
        arrPdr = pm.findAllVersions(pd);
        
        if (arrPdr != null) {
            String versionID = null;
            int version = -1;
            for (int i=0; i<arrPdr.length; i++) {
                versionID = arrPdr[i].getId();
                version = arrPdr[i].getVersion();
                if (arrPdr[i].isProduction()) {
                    if (pdVer == null || "".equals(pdVer) || "null".equals(pdVer)) {
                        pdVer = versionID;
                    }
                }
            }
            if (pdVer == null || "".equals(pdVer) || "null".equals(pdVer)) {
                pdVer = versionID;
            }
        }
    }
    
    ProcessDefinitionRemote pdr;    
    if (pi != null) {
        pdr = pm.getProcessDefinitionRemoteWithInstanceId(pi);
    } else {
        pdr = pm.getProcessDefinitionRemote(pdVer);
    }
    
    ProcessInstance instance = null;
    if (pi != null) {
        instance = pm.getProcessInstance(pi);
    }
    
    String title = pdr.getName().getText(locale);
    
    ViewerOptions options = new ViewerOptions();
    
    options.setViewType(options.VERTICAL, options.VERTICAL);
    options.put("flowControl", new Boolean(true));
//  options.put("dontCollapseScopes", new Boolean(true));
    options.put("decorated", new Boolean(true));
    options.put("show hidden activity", new Boolean(true));
    options.put("ShowAllComplexActivities", new Boolean(true));
//  options.put("enableEvent_onActivityClicked", new Boolean(true));
    options.put("align", "center");
    options.put("locale", (String)session.getAttribute("loggedUserLocale"));
    
    if (pi != null) {
        options.put("enableUserEvent_compensateTo", "Back to here");
        options.put("enableUserEvent_refreshMultipleInstances_org.uengine.kernel.SubProcessActivity", "Refresh Multiple Instances");
        options.put("enableUserEvent_showLog", "See Execution Log");
        //options.put("enableUserEvent_locateWorkItem", "Work Item Handler");
        options.put("enableUserEvent_locateWorkItem_org.uengine.kernel.ReceiveActivity", "Work Item Handler");
    }
    
    options.put("enableUserEvent_viewFormDefinition_org.uengine.kernel.FormActivity", "View Form Definition");
    options.put("enableUserEvent_drillInto_org.uengine.kernel.SubProcessActivity", "Drill Into");
    //options.put("show active activity count", Boolean.TRUE);
    if (locale != null)
        options.put("locale", locale);
    
    if (pi != null) {
        chart = pm.viewProcessInstanceFlowChart(pi, options);
    } else {
        if (pdVer != null) {
            chart = pm.viewProcessDefinitionFlowChart(pdVer, options);
        }
    }

    String pin = request.getParameter("pin"); //process instance name
    String belongingDefId = pdr.getBelongingDefinitionId();
    String srAlias = pdr.getAlias();
    AclManager acl = AclManager.getInstance();
    HashMap permission = acl.getPermission(Integer.parseInt(pdr.getBelongingDefinitionId()), (String)session.getAttribute("loggedUserId"));
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript">
var WEB_CONTEXT_ROOT = "<%=GlobalContext.WEB_CONTEXT_ROOT%>";

var tmp = [
           {title : "<%=GlobalContext.getMessageForWeb("Flowchart", (String)session.getAttribute("loggedUserLocale")) %>", onclick : "changeDisplayDraw(true)"},
           {title : "<%=GlobalContext.getMessageForWeb("Process Variables",(String)session.getAttribute("loggedUserLocale")) %>", onclick : "changeDisplayDraw(false)"},
           {title : "<%=GlobalContext.getMessageForWeb("Roles", (String)session.getAttribute("loggedUserLocale")) %>", onclick : "changeDisplayDraw(false)"}
        ];
        
$(document).ready(function() {
	enableTooltips();
	drawAll();
	setTimeout("drawAll()", 1000);
	
	$(window).bind("resize", function() {
		// ìë ê°ì ì ì¬ì§ê° íì
		// resizeì drawë¡ ì¸íì¬ ìëê° í¬ê² ì íë¨. 
		drawAll();
	}).trigger("resize");
	
	$('blink').blink();
});

function changeDisplayDraw(booleanValue) {
    if (booleanValue) {
        drawAll();
    } else {
        cleanAll();
    }
}

function viewTaskInfo(pdVer) {
	var option = "width=900,height=550,scrollbars=yes,resizable=yes";
	var url = "initiateForm.jsp?processDefinition=" + pdVer;
	window.open(url, "taskInfo", option);
}

function viewProcessVariableInXML(variableName){
	window.open('viewProcessVariableInXML.jsp?instanceId=<%=pi%>&variableName='+variableName, 'processVariable', 'width=700,height=500,scrollbars=yes,resizable=yes');
}
function showProcessVariableChangeForm(variableName, variableType){
	window.open('processVariableChangeForm.jsp?instanceId=<%=pi%>&variableName='+variableName+ '&dataClassName=' + variableType, 'processVariable', 'width=700,height=500,scrollbars=yes,resizable=yes');
}

function showRoleMappingChangeForm(roleName, oldValue){
    window.open('roleMappingChangeForm.jsp?multiple=true&instanceId=<%=pi%>&roleName=' + roleName + '&oldValue=' + oldValue, 'roleMapping', 'width=700,height=500,scrollbars=yes,resizable=yes');
}

function showActivityDetails(processDefinition, instanceID, tracingTag){
	window.open('viewActivityDetails.jsp?processDefinition=' + processDefinition + '&instanceID=' + instanceID + '&tracingTag=' + tracingTag,'activitydetails','width=500,height=400,scrollbars=yes,resizable=yes');
}

function resume(tracingTag) {
	result = confirm("Are you sure to resume this step?");
	if(result==true){
		flowControl('resume', tracingTag);
	}
}

function suspend(tracingTag) {
	result = confirm("Are you sure to suspend this step?");
	if(result==true){
		flowControl('suspend', tracingTag);
	}
}

function skip(tracingTag) {
	result = confirm("Are you sure to skip this step?");
	if(result==true){
		flowControl('skip', tracingTag);
	}
}

function compensate(tracingTag) {
	result = confirm("Do you really compensate this step?");
	if(result==true){
		flowControl('compensate', tracingTag);
	}
}

function flowControl(command, tracingTag) {
	go('flowControl.jsp?returnPage=viewProcessFlowChart.jsp&command='+ command + '&instanceId=<%=pi%>' + '&tracingTag=' + tracingTag);
}

function go(urlToGo) {
 	location = urlToGo;
}

function drillInto(defId, defVersionId, tracingTag, instanceId, propertyString) {
	propertyKeyAndValues = propertyString.split(",");
	properties = new Array();
	for (i = 0; i<propertyKeyAndValues.length; i++) {
		if(propertyKeyAndValues[i]!=null){
			keyAndValue = propertyKeyAndValues[i].split("=");
			properties[keyAndValue[0]] = keyAndValue[1];
		}
	}
	//var status = properties["status"];
	var subProcessIds = properties["subInstanceId"];
	var subDefinitionId = properties["subDefinitionId"];
	if ( subProcessIds != null ) {
		go('viewProcessInformation.jsp?instanceId=' + subProcessIds);
	} else if( subDefinitionId != null ) {
		go('viewProcessInformation.jsp?processDefinition=' + subDefinitionId);
	}
}


function showLog(defId, defVersionId, tracingTag, instanceId, propertyString) {
	propertyKeyAndValues = propertyString.split(",");
	properties = new Array();
	for(i = 0; i<propertyKeyAndValues.length; i++){
		if(propertyKeyAndValues[i]!=null){
			keyAndValue = propertyKeyAndValues[i].split("=");
			properties[keyAndValue[0]] = keyAndValue[1];
		}
	}

	var option = "width=500,height=400,scrollbars=yes,resizable=yes";
	
	window.open('showExecutionLog.jsp?filePath=log/' + instanceId + '/' + tracingTag, "wihspace", option);
}

function locateWorkItem(defId, defVersionId, tracingTag, instanceId, propertyString){
	propertyKeyAndValues = propertyString.split(",");
	properties = new Array();
	for(i = 0; i<propertyKeyAndValues.length; i++){
		if(propertyKeyAndValues[i]!=null){
			keyAndValue = propertyKeyAndValues[i].split("=");
			properties[keyAndValue[0]] = keyAndValue[1];
		}
	}

	var option = "width=800,height=550,scrollbars=yes,resizable=yes";
	
	window.open('../processparticipant/worklist/workitemHandler.jsp?instanceId=' + instanceId + '&tracingTag=' + tracingTag + '&isViewMode=true', "wihspace", option);
}



function viewFormDefinition(defId, defVersionId, tracingTag, instanceId, propertyString){
	propertyKeyAndValues = propertyString.split(",");
	properties = new Array();
	for(i = 0; i<propertyKeyAndValues.length; i++){
		if(propertyKeyAndValues[i]!=null){
			keyAndValue = propertyKeyAndValues[i].split("=");
			properties[keyAndValue[0]] = keyAndValue[1];
		}
	}
	//var status = properties["status"];
	var formDefinitionId = properties["formDefinitionId"];
	if( formDefinitionId != null ){
		go('viewFormDefinition.jsp?objectDefinitionId=' + formDefinitionId);
	}
}

function compensateTo(defId, defVersionId, tracingTag, instanceId, propertyString){
	propertyKeyAndValues = propertyString.split(",");
	properties = new Array();
	for(i = 0; i<propertyKeyAndValues.length; i++){
		if(propertyKeyAndValues[i]!=null){
			keyAndValue = propertyKeyAndValues[i].split("=");
			properties[keyAndValue[0]] = keyAndValue[1];
		}
	}
	var status = properties["status"];

	if(status!="Completed"){
		alert("Only completed activities can be target points for compensation");
	}
	
	go('flowControl.jsp?returnPage=viewProcessFlowChart.jsp&command=compensateTo&instanceId=' + instanceId + '&tracingTag=' + tracingTag);
}

function refreshMultipleInstances(defId, defVersionId, tracingTag, instanceId, propertyString){
	propertyKeyAndValues = propertyString.split(",");
	properties = new Array();
	for(i = 0; i<propertyKeyAndValues.length; i++){
		if(propertyKeyAndValues[i]!=null){
			keyAndValue = propertyKeyAndValues[i].split("=");
			properties[keyAndValue[0]] = keyAndValue[1];
		}
	}
	
	var status = properties["status"];
	
	go('refreshMulipleInstances.jsp?returnPage=viewProcessFlowChart.jsp&instanceId=' + instanceId + '&tracingTag=' + tracingTag);
}
	
function showDefinition(processDefinitionVersionId, language) {
    window.open('showDefinitionInLanguage.jsp?versionId=' + processDefinitionVersionId + '&language=' + language,'definitionInLanguage','width=700,height=500,scrollbars=yes,resizable=yes');
}

function showWSDL(location) {
    window.open(location, 'wsdl', 'width=700,height=500,scrollbars=yes,resizable=yes')
}

function removeDefinition(definition) {
    var answer = confirm("There may be a running instance with this process definition.\nAre you sure to remove?");
    if (answer) {
        location="removeProcessDefinition.jsp?processDefinition=" + definition;
    }
}

function makeProduction(){
    location="makeProduction.jsp?processDefinition=<%=pdVer == null ? "" : pdVer.replace(' ', '+')%>";
}

function refresh(){
    var pdv = document.all.processDefinitionVersionID.value;
    location="?processDefinition=<%=pd%>&folder=<%=folder%>&processDefinitionVersionID=" + pdv;
}
</script>


<script type="text/javascript" src="<%=GlobalContext.WEB_CONTEXT_ROOT %>/scripts/tabs.js"></script>
<style type="text/css"> 
    body{ overflow:auto;}
</style>
<title><%=GlobalContext.getMessageForWeb("Process Definition", (String)session.getAttribute("loggedUserLocale")) %>-<%=pdr.getName().getText()%>(<%=GlobalContext.getMessageForWeb("Version", (String)session.getAttribute("loggedUserLocale")) %>:<%=pdr.getVersion()%>/<%=GlobalContext.getMessageForWeb("Modified Date", (String)session.getAttribute("loggedUserLocale")) %>:<%=pdr.getStrModifiedDate() %>)</title>
</head>
<body>
<div id="canvasForLoopLines" style="position:absolute;left:0px;top:0px;z-index: -1"></div>

<div style=" padding:15px;  width:98%">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="middle"><img src="../images/Common/I_info.gif" align="middle" border="0" style="margin-top:-2px;"> <strong ><%=GlobalContext.getMessageForWeb("Process Information", (String)session.getAttribute("loggedUserLocale")) %></strong></td>
        <td>
<% 
if (pdVer != null) { 
%>
            <form name="actionForm">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td align="right" valign="middle">
                            <table cellSpacing="0" cellpadding="0" align="right"  style="margin-bottom:4px;">
                                <tbody>
                                    <tr>
                                        <td class="gBtn">
<%
    if ((boolean)session.getAttribute("loggedUserIsAdmin")) {
      	permission.put(AclManager.PERMISSION_MANAGEMENT, true);
    }
      
    if (
		   permission.containsKey(AclManager.PERMISSION_VIEW)
		|| permission.containsKey(AclManager.PERMISSION_INITIATE)
		|| permission.containsKey(AclManager.PERMISSION_EDIT)
        || permission.containsKey(AclManager.PERMISSION_MANAGEMENT)
    ) {
%>
                                        	<a href="javascript: viewTaskInfo('<%=pdVer%>')"> <span> <%=GlobalContext.getMessageForWeb("Initiate", (String)session.getAttribute("loggedUserLocale")) %> </span> </a>
<% 
    }
                                        
    if (
          permission.containsKey(AclManager.PERMISSION_EDIT)
        || permission.containsKey(AclManager.PERMISSION_MANAGEMENT)
    ) {
%>
	                                        <a href="javascript: location='ProcessDesigner.jnlp?defVerId=<%=pdVer%>&folderId=<%=folder%>&defId=<%=belongingDefId%>';"> 
	                                        <span><%=GlobalContext.getMessageForWeb("Improve", (String)session.getAttribute("loggedUserLocale")) %></span></a>
<%
    }
                                        
    if (pdr.isProduction()) {
%>
                                            <span style="line-height:23px; color:#F00">[<%=GlobalContext.getMessageForWeb("This version is procuction", (String)session.getAttribute("loggedUserLocale")) %>]</span>
<%
    } else if ((boolean)session.getAttribute("loggedUserIsAdmin")) {
%>
                                            <a href="javascript: makeProduction();"> <span> <%=GlobalContext.getMessageForWeb("Set as production", (String)session.getAttribute("loggedUserLocale"))%> </span> </a>
<%
    }
%>
										</td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </table>
            </form>
<%
}
%>
        </td>
        <td valign="middle" align="right" width="130">
            <table style="margin-bottom:4px;">
	            <tr>
	                <td class="gBtn">
<%
if (permission.containsKey(AclManager.PERMISSION_MANAGEMENT)) {
%>
                        <a href="javascript: window.location.href='processInstanceListDetail.jsp?defId=<%=pdr.getBelongingDefinitionId() %>'"> <span> <%=GlobalContext.getMessageForWeb("Instance List", (String)session.getAttribute("loggedUserLocale")) %> </span> </a>
<% 
} 
%>
                	</td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td class="formheadline" height="2"></td>
    </tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<%
if (pdVer == null) { 
%>
    <tr height="30" >
	    <td class="formtitle" width="200">&nbsp;<b>Instance Id</b></td>
	    <td width="10"></td>
	    <td width="*" align="left">
<%
    if( arrInstanceIds != null ) {
		for( int i=0; i<arrInstanceIds.length; i++ ){
			if ( i > 0 ) {
				out.print(", ");
			}
			if ( pi.equals(arrInstanceIds[i]) ) {
%>
            <font color="#FF0000"><b><%=pi%></b></font>
<%
            } else {
%>
            <a href="javascript:go('viewProcessInformation.jsp?instanceId=<%=instanceIds%>&selectedInstanceId=<%=arrInstanceIds[i]%>');"><%=arrInstanceIds[i]%></a>
<%
			}
		}
	} else {
%>
            <font color="#FF0000"><b><%=(pin !=null ? pin:"")%> <%=pi%></b></font>
<%
	}
%>
        </td>
    </tr>
    <tr>
        <td colspan="3" class="formline" height="1"></td>
    </tr>
<%
}
%>
    <tr height="30" >
        <td class="formtitle" width="200">&nbsp;<b><%=GlobalContext.getMessageForWeb("Definition", (String)session.getAttribute("loggedUserLocale")) %></b></td>
        <td width="10"></td>
        <td width="*" align="left"><%=pdr.getName().getText()%> ( <%=GlobalContext.getMessageForWeb("ID", (String)session.getAttribute("loggedUserLocale")) %> : <%=pdr.getBelongingDefinitionId()%> , <%=GlobalContext.getMessageForWeb("Alias", (String)session.getAttribute("loggedUserLocale")) %> : <%=srAlias %> ) </td>
    </tr>
    <tr>
        <td colspan="3" class="formline" height="1"></td>
    </tr>
<%
if (arrPdr != null) {
%>
    <tr height="30" >
        <td class="formtitle">&nbsp;<strong><%=GlobalContext.getMessageForWeb("Version", (String)session.getAttribute("loggedUserLocale")) %></strong></td>
        <td width="10"></td>
        <td width="*" align="left">
            <select name="processDefinitionVersionID" id="processDefinitionVersionID" onchange="refresh();">
<%	
	String versionID = null;
	String version = "-1";
	String strModifiedDate = "";
	String sStyle = "";
	
	for(int i=0; i<arrPdr.length; i++){
		strModifiedDate = arrPdr[i].getStrModifiedDate();
		versionID = arrPdr[i].getId();
		version = "Ver : " + arrPdr[i].getVersion();
		
		if (arrPdr[i].isProduction()) {
			version =  version + "*";
			sStyle = " style=\"color: #FF0000;\" ";
		} else {
			sStyle = "";
		}
%>
                <option value="<%=versionID%>" <%=sStyle %> <%=(versionID.equals(pdVer)) ? "selected=\"selected\" " : ""%>><%=version%></option>
<%
    } 
%>
            </select>
            ( <%=GlobalContext.getMessageForWeb("ID", (String)session.getAttribute("loggedUserLocale")) %> : <%=pdVer%> , <%=GlobalContext.getMessageForWeb("Modified Date", (String)session.getAttribute("loggedUserLocale")) %> : <%=pdr.getStrModifiedDate() %> ) 
        </td>
    </tr>
    <tr>
        <td colspan="3" class="formline" height="1"></td>
    </tr>
<%
}

if (pdVer!=null) { 
	String desc = (pdr.getDescription()!=null ? pdr.getDescription().getText(locale) : null);
	if ( desc == null ) desc = "";
%>
    <tr height="30" >
        <td class="formtitle">&nbsp;<b><%=GlobalContext.getMessageForWeb("Description", (String)session.getAttribute("loggedUserLocale")) %></b></td>
        <td width="10"></td>
        <td width="*" align="left"><%=desc%></td>
    </tr>
<%
}
%>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td class="formheadline"></td>
    </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td height="15"></td>
    </tr>
    <tr>
        <td class="formheadline"></td>
    </tr>
</table>
<script type="text/javascript">
    createTabs(tmp);
</script>
<div id="divTabItem_<%=GlobalContext.getMessageForWeb("Flowchart", (String)session.getAttribute("loggedUserLocale")) %>" style="overflow: hidden;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr height="20" >
            <td width="10"></td>
            <td width="*" align="left" class="padding15">
            	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td align="right">
						    <br />
	                        <table align="right">
	                            <tr>
	                               <td class="gBtn">
<%
if ( pdVer == null) {
	StringBuilder tempLocation = new StringBuilder();
	StringBuilder tempString = new StringBuilder();
	if(pdr.isAdhoc()) { 
%>
                                        <a onClick="location='ProcessDesigner.jnlp?instanceId=<%=pi%>&defId=<%=pdr.getId()%>'"><span><%=GlobalContext.getMessageForWeb("Instance Level Definition Change", (String)session.getAttribute("loggedUserLocale")) %></span></a>
                            
<%
    }
										
	if(instance.isRunning("")){
		tempLocation.append("stopProcessInstance.jsp?processInstance=" + pi);
		tempString.append("Stop");
	}else{
		tempLocation.append("startProcessInstance.jsp?processInstance=" + pi);
		tempString.append("Start");
	}
%>
                                        <a onClick="location='<%=tempLocation %>'"><span><%=tempString %></span></a>
<%
}
%>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
<!-- Flow Chart Start -->
<%
if(instance != null && instance.getMainProcessInstanceId()!=null){
%>
                            <a href="?instanceId=<%=instance.getMainProcessInstanceId()%>"><img src="../images/up_back.gif"></a>
<%	
} 
%>
                            <%=chart%>
							<select onchange="changeLocale(this)">
								<option value=""><%=GlobalContext.getMessageForWeb("Language", (String)session.getAttribute("loggedUserLocale")) %></option>
								<option value="ko"><%=GlobalContext.getMessageForWeb("Korean", (String)session.getAttribute("loggedUserLocale")) %></option>
								<option value="en"><%=GlobalContext.getMessageForWeb("English", (String)session.getAttribute("loggedUserLocale")) %></option>
								<option value="jp"><%=GlobalContext.getMessageForWeb("Japanese", (String)session.getAttribute("loggedUserLocale")) %></option>
								<option value="zh"><%=GlobalContext.getMessageForWeb("Chinese", (String)session.getAttribute("loggedUserLocale")) %></option>
							</select>
							<script type="text/javascript">
							function changeLocale(option){
								<%if(pi != null){%>
								go("viewProcessFlowChart.jsp?instanceId=<%=pi%>&locale=" + option.value);
								<%} else {%>
								go("viewProcessFlowChart.jsp?processDefinition=<%=pd%>&processDefinitionVersionID=<%=pdVer%>&locale=" + option.value);
								<%}%>
							}
							</script>
<!-- Flow Chart End -->
                        </td>
                    </tr>
                </table>
			</td>
        </tr>
        <tr height="1">
            <td colspan="3" class="formline" height="1"></td>
        </tr>
    </table>
<%
if (pdVer!=null) { 
%>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr height="30" >
            <td class="formtitle" width="200">&nbsp;<b><%=GlobalContext.getMessageForWeb("See definition in", (String)session.getAttribute("loggedUserLocale")) %></b></td>
            <td width="10"></td>
            <td width="*" align="left"  class="padding15">
                <!--  a href="javascript:showDefinition('<%=pdVer%>', 'BPEL')">BPEL4WS</a -->
                <a href="javascript:showDefinition('<%=pdVer%>', 'Bean')">Bean</a>
                <!-- a href="javascript:showDefinition('<%=pdVer%>', 'WSCI')">WSCI</a-->
            </td>
        </tr>
    </table>
<%
}
%>
</div>
<div id="divTabItem_<%=GlobalContext.getMessageForWeb("Process Variables", (String)session.getAttribute("loggedUserLocale")) %>" style="display: none;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr height="30" >
            <td width="5" align="left"></td>
            <td width="*" align="left"  class="padding15"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableborder">
        <tr height="20" >
            <td width="200" align="center" bgcolor="#DAE9F9">Name/Type</td>
            <td width="*" align="left" bgcolor="#DAE9F9">Value</td>
        </tr>
        <tr height="1">
            <td colspan="3" height="1" class="bgcolor_head_underline"></td>
        </tr>
<%
	ProcessVariable[] pvdrs = pdr.getProcessVariableDescriptors();
	if (pvdrs != null) {
		for(int i=0; i<pvdrs.length; i++) {
			ProcessVariable pvd = pvdrs[i];
%>
                    <tr height="20" >
                        <td width="200" align="center"><%=pvd.getDisplayName().getText(locale)%>/<%=pvd.getType() == null ? "" : pvd.getType().getName()%></td>
                        <td width="*" align="left"><%
			if (pi!=null) {
				Serializable data = pm.getProcessVariable(pi, "", pvd.getName());
%>
                            <input type="button" value='XML' onclick="viewProcessVariableInXML('<%=pvd.getName()%>')">
                            <input type="button" value='change' onclick="showProcessVariableChangeForm('<%=pvd.getName()%>', '<%=pvd.getType() == null ? "" : pvd.getType().getName()%>')">
<%
				if(data instanceof HtmlFormContext){
					HtmlFormContext formContext = (HtmlFormContext)data;
%>
                            <a href='showFormInstance.jsp?instanceId=<%=pi%>&variableName=<%=pvd.getName()%>&filePath=<%=formContext.getFilePath()%>' target=_blank><%=formContext.getFilePath() %></a>
<%
				}else{
				    out.println(data);
				}
					
			}
%>
                        </td>
                    </tr>
                    <tr height="1">
                        <td align="center" colspan="3" height="1" class="bgcolor_head_underline"></td>
                    </tr>
<%
		}
		
		if ( pvdrs.length == 0 ) {
%>
                    <tr height="20" >
                        <td align="left" colspan="3"> No process variables declared. </td>
                    </tr>
                    <tr height="1">
                        <td align="center" colspan="3" height="1" class="bgcolor_head_underline"></td>
                    </tr>
<%
		}
	} 
%>

                </table></td>
        </tr>
        <tr height="1">
            <td align="center" colspan="3" height="1"class="formline" height="1"></td>
        </tr>
    </table>
</div>
<div id="divTabItem_<%=GlobalContext.getMessageForWeb("Roles", (String)session.getAttribute("loggedUserLocale")) %>" style="display: none;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr height="30" >
         
            <td width="5" align="left"></td>
            <td width="*" align="left"  class="padding15"><table width="100%" border="0" cellspacing="0" cellpadding="0"  class="tableborder">
                    <col span="1" width="200" align="center" />
                    <col span="1" width="*" align="left" />
                    <tr height="20" >
                        <td bgcolor="#DAE9F9">Name</td>
                        <td bgcolor="#DAE9F9">Binding</td>
                    </tr>
                    <tr height="1">
                        <td align="center" colspan="2" height="1" class="bgcolor_head_underline"></td>
                    </tr>
                    <%
    Role[] roles =null;
    if(instance != null){
    	roles = instance.getProcessDefinition().getRoles();
    }else{
		roles = pdr.getRoles();
    }
    
	if (roles != null ) {
		for(int i=0; i<roles.length; i++) {
			Role role = roles[i];
%>
                    <tr height="20" >
                        <td><%=role.getDisplayName().getText(locale)%></td>
                        <td><%
			if ( pi != null ) {
				RoleMapping roleMapping = pm.getRoleMappingObject(pi, role.getName());				
%>
                            <input type="button" value='<%=GlobalContext.getMessageForWeb("Change", (String)session.getAttribute("loggedUserLocale")) %>' 
						onclick="showRoleMappingChangeForm('<%=role.getName()%>', '<%=(roleMapping != null ? roleMapping.getEndpoint() : "")%>')">
                            <%=roleMapping%>
                            <%
			}
%></td>
                    </tr>
                    <tr height="1">
                        <td colspan="2" height="1" class="bgcolor_head_underline"></td>
                    </tr>
                    <%
		}
		if ( roles.length == 0 ) {
%>
                    <tr height="20" >
                        <td align="left" colspan="2"> No Roles declared. </td>
                    </tr>
                    <tr height="1">
                        <td colspan="2" height="1" class="bgcolor_head_underline"></td>
                    </tr>
<%
		}
	} 
	
} finally {
	if(pm!=null){
		try{
			pm.remove();
		}catch(Exception e){}
	}
}
%>

                </table></td>
        </tr>
    </table>
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td class="formheadline"></td>
    </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td height="10"></td>
    </tr>
</table>
</div>
</body>
</html>