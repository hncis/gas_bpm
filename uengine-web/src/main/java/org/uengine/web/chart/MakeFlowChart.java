package org.uengine.web.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.MappingActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.SQLActivity;
import org.uengine.kernel.ScriptActivity;
import org.uengine.kernel.UEngineException;
import org.uengine.kernel.viewer.ViewerOptions;
import org.uengine.processmanager.ProcessDefinitionRemote;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.security.AclManager;
import org.uengine.ui.list.datamodel.DataMap;
import org.uengine.util.UEngineUtil;

/**
 * <pre>
 *  Make Flow Chart
 * </pre>
 * 
 * @author Sung-yeol Jung
 *
 */
public class MakeFlowChart {
    private final String _WEB_ROOT_ = GlobalContext.WEB_CONTEXT_ROOT + "/wih/wihDefaultTemplate";
    
    /************************************************/
    /*                  VARIABLE                    */
    /************************************************/
    private String chartType;
    private String viewInstDefVerId;
    private String chart = "no chart is available";
    private String title;
    private String viewType;
    private String viewOption;
    private Vector<String> vtMainPi;
    private Vector<String> vtTitle;
    private Vector<Boolean> vtIsProcessDefinition;
    private HashMap<Character, Boolean> permission;
    private String pdrId;
    private String lastInstanceId;
    /************************************************/
    
    /************************************************/
    /*               GETTER.SETTER                  */
    /************************************************/
    public String getChartType() {
        return chartType;
    }
    public String getViewInstDefVerId() {
        return viewInstDefVerId;
    }
    public String getChart() {
        return chart;
    }
    public String getTitle() {
        return title;
    }
    public String getViewType() {
        return viewType;
    }
    public String getViewOption() {
        return viewOption;
    }
    public Vector<String> getVtMainPi() {
        return vtMainPi;
    }
    private void setVtMainPi(String mainPi) {
        this.vtMainPi.add(mainPi);
    }
    public Vector<String> getVtTitle() {
        return vtTitle;
    }
    private void setVtTitle(String title) {
        this.vtTitle.add(title);
    }
    public Vector<Boolean> getVtIsProcessDefinition() {
        return vtIsProcessDefinition;
    }
    private void setVtIsProcessDefinition(boolean isProcessDefinition) {
        this.vtIsProcessDefinition.add(isProcessDefinition);
    }
    public HashMap<Character, Boolean> getPermission() {
        return permission;
    }
    public String getPdrId() {
        return pdrId;
    }
    public String getLastInstanceId() {
        return lastInstanceId;
    }
    /************************************************/

    /**
     * <pre>
     *  Mak Flow Chart Initiation
     * </pre>
     *
     * @param pm
     * @param reqMap
     * @param loggedUserLocale
     * @param loggedUserId
     * @return
     * @throws Exception
     * 2011. 1. 31.  Sung-yeol Jung
     */
    @SuppressWarnings("rawtypes")
    public boolean initFlowChart(ProcessManagerRemote pm, DataMap reqMap, String loggedUserLocale, String loggedUserId) throws Exception{
        boolean result = true;
        ProcessDefinitionRemote pdr = null;
        
        String instanceId = reqMap.getString("instanceId", "");
        String processDefinition = reqMap.getString("processDefinition", "");
        String definitionVersionId = reqMap.getString("definitionVersionId", "");
        this.lastInstanceId = reqMap.getString("lastInstanceId", "");
        String parentPdver = reqMap.getString("parentPdver", "");
        String viewInstId = reqMap.getString("viewInstId", "");
        
        String arrayInstDefVerId[] = null;
        
        try {
            if(UEngineUtil.isNotEmpty(instanceId)) {
                this.chartType = "instance";
                arrayInstDefVerId = instanceId.split(";");
                
                if (!UEngineUtil.isNotEmpty(viewInstId)) {
                    this.viewInstDefVerId = arrayInstDefVerId[0];
                } else {
                    this.viewInstDefVerId = viewInstId;
                }
                
                pdr = pm.getProcessDefinitionRemoteWithInstanceId(getViewInstDefVerId());
                
                setArrayParentId(arrayInstDefVerId, instanceId, getViewInstDefVerId());
                getArrayChildId(arrayInstDefVerId, instanceId, getViewInstDefVerId());
            } else if (UEngineUtil.isNotEmpty(definitionVersionId)) {
                this.chartType = "definition";
                
                arrayInstDefVerId = definitionVersionId.split(";");
                
                if (!UEngineUtil.isNotEmpty(viewInstId)) {
                    this.viewInstDefVerId = arrayInstDefVerId[0];
                } else {
                    this.viewInstDefVerId = viewInstId;
                }
                
                pdr = pm.getProcessDefinitionRemote(getViewInstDefVerId());
                
                setArrayParentId(arrayInstDefVerId, definitionVersionId, getViewInstDefVerId());
                getArrayChildId(arrayInstDefVerId, definitionVersionId, getViewInstDefVerId());
            } else if (UEngineUtil.isNotEmpty(processDefinition)) {
                this.chartType = "definition";
                definitionVersionId = pm.getProcessDefinitionProductionVersion(processDefinition);
                arrayInstDefVerId = definitionVersionId.split(";");
                
                if (!UEngineUtil.isNotEmpty(viewInstId)) {
                    this.viewInstDefVerId = arrayInstDefVerId[0];
                } else {
                    this.viewInstDefVerId = viewInstId;
                }
                
                pdr = pm.getProcessDefinitionRemote(getViewInstDefVerId());
                
                setArrayParentId(arrayInstDefVerId, definitionVersionId, getViewInstDefVerId());
                getArrayChildId(arrayInstDefVerId, definitionVersionId, getViewInstDefVerId());
            } else {
                return false;
            }
            
            this.pdrId = pdr.getId();
            
            if(pdr.getRoles().length > 1) {
                this.viewType = reqMap.getString("viewType", ViewerOptions.SWIMLANE);
                this.viewOption = reqMap.getString("viewOption", ViewerOptions.VERTICAL);
            } else {
                this.viewType = reqMap.getString("viewType", ViewerOptions.VERTICAL);
                this.viewOption = viewType;
            }
            
            //Chart Make Script
            ViewerOptions options = new ViewerOptions();
            
            options.setViewType(getViewType(), getViewOption());
            
            options.put(options.LOCALE, loggedUserLocale);
            options.put(options.IMAGE_PATH_ROOT, GlobalContext.WEB_CONTEXT_ROOT+"/processmanager/");
            options.put(options.NOWRAP, Boolean.TRUE);
            options.put("decorated", Boolean.TRUE);
            options.put("subProcessDrillingDown_By_PopingUp", Boolean.TRUE);
            options.put("enableUserEvent_locateWorkItem", "Work Item Handler");
            options.put(options.VIEW_SUBPROCESS, Boolean.TRUE);
            //options.put("show hidden activity", Boolean.TRUE);
            //options.put("ShowAllComplexActivities", Boolean.TRUE);

            // Activity is Hidden List Class(Flowchart not view)
            List<Class> hiddenActivityTypes = new ArrayList<Class>();
            hiddenActivityTypes.add(MappingActivity.class);
            hiddenActivityTypes.add(SQLActivity.class);
            hiddenActivityTypes.add(ScriptActivity.class);
            
            options.setHiddenActivityTypes(hiddenActivityTypes);
            
            if (UEngineUtil.isNotEmpty(instanceId)) {
                this.lastInstanceId = viewInstDefVerId;
                this.chart = pm.viewProcessInstanceFlowChart(getViewInstDefVerId(), options);
                
                ProcessDefinitionRemote processDefinitionRemote = pm.getProcessDefinitionRemoteWithInstanceId(getViewInstDefVerId());
                this.title = processDefinitionRemote.getName().toString();
                
                definitionVersionId = processDefinitionRemote.getId();
            } else if (UEngineUtil.isNotEmpty(definitionVersionId)) {
                this.chart = pm.viewProcessDefinitionFlowChart(getViewInstDefVerId(), options);
                this.title = pm.getProcessDefinition(getViewInstDefVerId()).getName().toString();
            }
            
            this.vtMainPi = new Vector<String>();
            this.vtTitle = new Vector<String>();
            this.vtIsProcessDefinition = new Vector<Boolean>();

            if (UEngineUtil.isNotEmpty(instanceId)) {
                ProcessInstance instance = pm.getProcessInstance(getViewInstDefVerId());
                String mainPi = instance.getMainProcessInstanceId();
                
                while(UEngineUtil.isNotEmpty(mainPi)) {
                    instance = pm.getProcessInstance(mainPi);
                    ProcessDefinitionRemote tempPDR = pm.getProcessDefinitionRemoteWithInstanceId(mainPi);
                    
                    setVtMainPi(mainPi);
                    setVtTitle(tempPDR.getName().getText());
                    setVtIsProcessDefinition(false);

                    mainPi = instance.getMainProcessInstanceId();
                }
            } else {
                if (UEngineUtil.isNotEmpty(definitionVersionId)) {   
                    String parentPds[] = parentPdver.split(";");
                    
                    for (int i = parentPds.length - 1; i > -1; i--) {
                        String parentPd = parentPds[i];
                        if (UEngineUtil.isNotEmpty(parentPd)) {
                            setVtMainPi(parentPd);
                            setVtTitle(pm.getProcessDefinitionRemote(parentPd).getName().getText());
                            setVtIsProcessDefinition(true);
                        }
                    }
                }
                
                if (UEngineUtil.isNotEmpty(getLastInstanceId())) {
                    String tempInstanceId = getLastInstanceId();
                    getVtTitle().add(pm.getProcessDefinitionRemoteWithInstanceId(tempInstanceId).getName().getText());
                    getVtMainPi().add(tempInstanceId);
                    getVtIsProcessDefinition().add(false);
                    
                    while(true) {
                        ProcessInstance instance = pm.getProcessInstance(tempInstanceId);
                        String mainPi = instance.getMainProcessInstanceId();
                        
                        if (UEngineUtil.isNotEmpty(mainPi)) {
                            setVtMainPi(mainPi);
                            setVtTitle(instance.getName());
                            setVtIsProcessDefinition(false);
                            tempInstanceId = mainPi;
                        } else {
                            break;
                        }
                    }
                }
            }

            AclManager acl = AclManager.getInstance();
            this.permission = acl.getPermission(Integer.parseInt(pdr.getBelongingDefinitionId()), loggedUserId);
        } catch(Exception e) {
            throw new UEngineException("MakeFlowChart Error", e);
        }
        
        return result;
    }
    
    
    /**
     * Parent Activity Chart List
     */
    private String[] arrayParentId;
    public String[] getArrayParentId() {
        return this.arrayParentId;
    }
    public void setArrayParentId(String[] arrayId, String strArrayId, String viewId) {
        if (arrayId.length > 1) {
            //parent
            String strDummy = strArrayId.substring(0, strArrayId.indexOf(viewId));
            
            if (strDummy.indexOf(viewId+";") > -1) {
                strDummy = strDummy.replaceAll(viewId + ";", "");
            } else {
                strDummy = strDummy.replaceAll(viewId, "");
            }
            
            if (!strDummy.equals("")) {
                this.arrayParentId = strDummy.split(";");
            }
        }
    }
    
    /**
     * Child Activity Chart List
     */
    private String[] arrayChildId;
    public String[] getArrayChildId() {
        return this.arrayChildId;
    }
    public void getArrayChildId(String[] arrayId, String strArrayId, String viewId) {
        if (arrayId.length > 1) {
            String strDummy = strArrayId.substring(strArrayId.indexOf(viewId));
            
            if (strDummy.indexOf(viewId+";") > -1) {
                strDummy = strDummy.replaceAll(viewId + ";", "");
            } else {
                strDummy = strDummy.replaceAll(viewId, "");
            }
            
            if (!strDummy.equals("")) {
                this.arrayChildId = strDummy.split(";");
            }
        }
    }

    /**
     * <pre>
     *  Flow chart first row
     * </pre>
     *
     * @param rowId
     * @param rowTitle
     * @param onclickScript
     * @param isView
     * @return
     * 2011. 1. 31.  Sung-yeol Jung
     */
    public String getBeginningRow(String rowId, String rowTitle, String onclickScript, boolean isView) {
        StringBuilder sbHtml = new StringBuilder();
        
        sbHtml.append("<tr>")
        .append("<td width='8' height='7'><img src='" + _WEB_ROOT_ + "/images/Common/fc_mo01.gif' width='8' height='7' /></td>")
        .append("<td background='" + _WEB_ROOT_ + "/images/Common/fc_lineBG_top.gif'></td>")
        .append("<td width='8'><img src='" + _WEB_ROOT_ + "/images/Common/fc_mo02.gif' width='8' height='7' /></td>")
        .append("</tr>")
        .append("<tr onClick=\"" + onclickScript + "\" style='cursor: pointer;'>")
        .append("<td background='" + _WEB_ROOT_ + "/images/Common/fc_lineBG_left.gif'></td>")
        .append("<td id='cellTitle_" + rowId + "'><span class='flowcharttitle'>" + rowTitle + "</span></td>")
        .append("<td background='" + _WEB_ROOT_ + "/images/Common/fc_lineBG_right.gif'></td>")
        .append("</tr>");

          
        if(isView) {
            sbHtml.append("<tr id='rowFlowchartCanvas_" + rowId + "' style='display: none;'>")
                  .append("<td background='" + _WEB_ROOT_ + "/images/Common/fc_lineBG_left.gif'></td>")
                  .append("<td id='cellFlowchartCanvas_" + rowId + "'></td>")
                  .append("<td background='" + _WEB_ROOT_ + "/images/Common/fc_lineBG_right.gif'></td>")
                  .append("</tr>");
        }
        return sbHtml.toString();
    }

    /**
     * <pre>
     *  Flow chart last row
     * </pre>
     *
     * @return
     * 2011. 1. 31.  Sung-yeol Jung
     */
    public String getLastRow() {
        StringBuilder sbHtml = new StringBuilder();
        sbHtml.append(
                "<tr>"
                + "<td height='7'><img src='" + _WEB_ROOT_ + "/images/Common/fc_mo04.gif' width='8' height='7' /></td>"
                + "<td background='" + _WEB_ROOT_ + "/images/Common/fc_lineBG_bottom.gif'></td>"
                + "<td><img src='" + _WEB_ROOT_ + "/images/Common/fc_mo03.gif' width='8' height='7' /></td>"
                + "</tr>"
        );
        
        return sbHtml.toString();
    }
    
    /**
     * Javascript Function getProcessFlowchart() - make
     */
    private String scriptFncFlowChart;
    public String getScriptFncFlowChart() {
        return scriptFncFlowChart;
    }
    public void setScriptFncFlowChart(String instId, String defId, String defVerId, String parentDefVerId, 
            String viewType, String viewOption, String lastInstId, String initiate, String viewInstId) {
        StringBuilder sbFunction = new StringBuilder();
        sbFunction.append("getProcessFlowchart(")
                  .append(" '").append(instId).append("' ")
                  .append(", '").append(defId).append("' ")
                  .append(", '").append(defVerId).append("' ")
                  .append(", '").append(parentDefVerId).append("' ")
                  .append(", '").append(viewType).append("' ")
                  .append(", '").append(viewOption).append("' ")
                  .append(", '").append(lastInstId).append("' ")
                  .append(", '").append(initiate).append("' ")
                  .append(", '").append(viewInstId).append("' ")
                  .append(");");
        
        this.scriptFncFlowChart = sbFunction.toString();
    }
    
    /**
     * <pre>
     *  SubProcess view type Multiple Make
     * </pre>
     *
     * @param reqMap
     * @param loggedUserId
     * @param loggedUserLocale
     * @return
     * 2011. 1. 31.  Sung-yeol Jung
     */
    @SuppressWarnings({ "static-access", "rawtypes" })
    public String getMultipleSubProcess(ProcessManagerFactoryBean processManagerFactory, DataMap reqMap, String loggedUserId, String loggedUserLocale) throws Exception {
        String instanceId = reqMap.getString("instanceId", "");
        String definitionVersionId = reqMap.getString("definitionVersionId", "");
        String parentTracingTag = reqMap.getString("parentTracingTag", "");
        
        StringBuilder resultChart = new StringBuilder();
        String viewType = null;
        String viewOption = null;
        String[] instanceIds = instanceId.split(";");
        
        ProcessManagerRemote pm = null;
        
        try {
            pm = processManagerFactory.getProcessManagerForReadOnly();
        
            ProcessDefinitionRemote pdr;
            if (UEngineUtil.isNotEmpty(instanceId)) {
                pdr = pm.getProcessDefinitionRemoteWithInstanceId(instanceIds[0]);
            } else if (UEngineUtil.isNotEmpty(definitionVersionId)) {
                pdr = pm.getProcessDefinitionRemote(definitionVersionId);
            } else {
                return null;
            }

            ViewerOptions options = new ViewerOptions();
            
            if(pdr.getRoles().length > 1) {
                viewType = reqMap.getString("viewType", options.SWIMLANE);
            } else {
                viewType = reqMap.getString("viewType", options.HORIZONTAL);
            }
            
            viewOption = reqMap.getString("viewOption", options.HORIZONTAL);
            options.setViewType(viewType, viewOption);
            
            Vector<String> parents = new Vector<String>();
            String[] tracingTags = parentTracingTag.split("_");
            
            for (String tracingTag : tracingTags) {
                parents.add(tracingTag);
            }
            
            options.put("parentTracingTag", parents);
            
            options.put(options.LOCALE, loggedUserLocale);
            options.put(options.IMAGE_PATH_ROOT, GlobalContext.WEB_CONTEXT_ROOT+"/processmanager/");
            options.put(options.NOWRAP, Boolean.TRUE);
            options.put("decorated", Boolean.TRUE);
            options.put("subProcessDrillingDown_By_PopingUp", Boolean.TRUE);
            options.put("enableUserEvent_locateWorkItem", "Work Item Handler");
            options.put(options.VIEW_SUBPROCESS, Boolean.TRUE);
            options.put("show hidden activity", Boolean.TRUE);
            options.put("ShowAllComplexActivities", Boolean.TRUE);
        
            List<Class> hiddenActivityTypes = new ArrayList<Class>();
            hiddenActivityTypes.add(MappingActivity.class);
            hiddenActivityTypes.add(SQLActivity.class);
            hiddenActivityTypes.add(ScriptActivity.class);
            
            options.setHiddenActivityTypes(hiddenActivityTypes);
            
            if (UEngineUtil.isNotEmpty(instanceId)) {
                for (String tempInstanceId : instanceIds) {
                    if (resultChart.length() > 0) resultChart.append("<br /><br />"); 
                    resultChart.append("<h3>").append(pm.getProcessDefinitionRemoteWithInstanceId(tempInstanceId).getName()).append("</h3>");
                    resultChart.append(pm.viewProcessInstanceFlowChart(tempInstanceId, options));
                }
            } else {
                AclManager acl = AclManager.getInstance();
                HashMap<Character, Boolean> permission = acl.getPermission(Integer.parseInt(pdr.getBelongingDefinitionId()), loggedUserId);
                
                if (permission.containsKey(AclManager.PERMISSION_VIEW)
                        || permission.containsKey(AclManager.PERMISSION_INITIATE)
                        || permission.containsKey(AclManager.PERMISSION_EDIT)
                        || permission.containsKey(AclManager.PERMISSION_MANAGEMENT)) {
                    resultChart.append("<h2>").append(pm.getProcessDefinition(definitionVersionId).getName()).append("</h2>");
                    resultChart.append(pm.viewProcessDefinitionFlowChart(definitionVersionId, options));
                } else {
                    resultChart.append(GlobalContext.getMessageForWeb("&nbsp; You have not permission &nbsp;", loggedUserLocale));
                }
            }
        } catch (Exception e) {
            throw new UEngineException("Multiful Exception", e);
        } finally {
            try { 
                pm.remove(); 
            } catch(Exception e) {
                throw new UEngineException("ProcessManagerRemote remove() Exception", e);
            }
        }
        
        return resultChart.toString();
    }
}
