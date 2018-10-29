package org.uengine.web.chart;

import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.UEngineException;
import org.uengine.persistence.dao.WorkListDAO;
import org.uengine.persistence.processinstance.ProcessInstanceDAO;
import org.uengine.processmanager.ProcessDefinitionRemote;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.processmanager.SimpleTransactionContext;
import org.uengine.ui.list.datamodel.DataMap;
import org.uengine.ui.list.datamodel.QueryCondition;
import org.uengine.util.UEngineUtil;
import org.uengine.util.dao.DefaultConnectionFactory;
import org.uengine.util.dao.GenericDAO;

public class ProcessFlowChart {
    
    private String processInstId;
    private String processVersionId;
    private ProcessDefinitionRemote pdr;
    private ProcessInstance instance;
    
    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public ProcessDefinitionRemote getPdr() {
        return pdr;
    }

    public void setPdr(ProcessDefinitionRemote pdr) {
        this.pdr = pdr;
    }

    public ProcessInstance getInstance() {
        return instance;
    }

    public void setInstance(ProcessInstance instance) {
        this.instance = instance;
    }

    public String getProcessVersionId() {
        return processVersionId;
    }

    public void setProcessVersionId(String processVersionId) {
        this.processVersionId = processVersionId;
    }

    public ProcessDefinitionRemote view(ProcessManagerRemote pm, DataMap reqMap, String lastInstanceId) throws Exception {
        ProcessDefinitionRemote pdr = null;
        
        try {
            /***********************************************************************/
            /*                            Define                                   */
            /***********************************************************************/

            QueryCondition condition = new QueryCondition();
            
            int intPageCnt = 10;
            int currentPage = reqMap.getInt("CURRENTPAGE", 1);

            /***********************************************************************/
            /*                            Check & Set Parameter                    */
            /***********************************************************************/
            condition.setMap(reqMap);
            condition.setOnePageCount(intPageCnt);
            condition.setPageNo(currentPage);

            String pd = reqMap.getString("processDefinition", "");
            setProcessVersionId(reqMap.getString("definitionVersionId", ""));
            setProcessInstId(reqMap.getString("instanceId", ""));

            //replace with production version
            if (UEngineUtil.isNotEmpty(getProcessInstId())) {
                ProcessInstance instance = pm.getProcessInstance(getProcessInstId());
                setProcessVersionId(instance.getProcessDefinition().getId());
            } else if (!UEngineUtil.isNotEmpty(getProcessVersionId()) && UEngineUtil.isNotEmpty(pd)) {
                setProcessVersionId(pm.getProcessDefinitionProductionVersion(pd));
            }

            if (UEngineUtil.isNotEmpty(lastInstanceId)) {
                setProcessInstId(lastInstanceId);
            }
            
            if (UEngineUtil.isNotEmpty(getProcessInstId())) {
                setInstance(pm.getProcessInstance(getProcessInstId()));
                
                pdr = pm.getProcessDefinitionRemoteWithInstanceId(getProcessInstId());
                pd = pm.getProcessInstance(getProcessInstId()).getProcessDefinition().getBelongingDefinitionId();
            } else {
                if (!UEngineUtil.isNotEmpty(getProcessVersionId())) {
                    return null;
                }
                pdr = pm.getProcessDefinitionRemote(getProcessVersionId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new UEngineException("ProcessFlow Error", e);
        } finally {
            
        }
        
        return pdr;
    }
    
    public ProcessInstanceDAO getEventHandler() throws Exception {
        ProcessInstanceDAO eventHandlerSPs = null;
        SimpleTransactionContext stc = new SimpleTransactionContext();
        
        try {
            
            eventHandlerSPs = (ProcessInstanceDAO)GenericDAO.createDAOImpl(
                    stc,
                    "select * from bpm_procinst where maininstid=?maininstid and isDeleted=0",
                    ProcessInstanceDAO.class
            );
            
            eventHandlerSPs.setMainInstId(new Long(getProcessInstId()));
            eventHandlerSPs.select();
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	throw new UEngineException("EventHandler Error", e);
        } finally {
        	stc.releaseResources();
        }
        
        return eventHandlerSPs;
    }
    
    public WorkListDAO getWorkList() throws Exception {
        WorkListDAO wl = null;
        SimpleTransactionContext stc = new SimpleTransactionContext();

        try {
            wl = (WorkListDAO) GenericDAO.createDAOImpl(
                    stc,
                    "select * from bpm_worklist where rootinstid=?rootinstid",
                    WorkListDAO.class
            );
            
            wl.setRootInstId(new Long(getProcessInstId()));
            wl.select();
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	throw new UEngineException("WorkList Error", e);
        } finally {
        	stc.releaseResources();
        }
        
        return wl;
    }
}
