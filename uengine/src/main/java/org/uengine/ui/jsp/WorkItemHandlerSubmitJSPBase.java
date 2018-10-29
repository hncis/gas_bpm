package org.uengine.ui.jsp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.uengine.kernel.Activity;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.KeyedParameter;
import org.uengine.kernel.ResultPayload;
import org.uengine.kernel.Role;
import org.uengine.processmanager.ProcessDefinitionRemote;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.ui.list.util.StringUtils;

/**
 * <pre>
 * 파 일 명 : WorkItemHandlerSubmitJSPBase.java
 * 기    능 : Workitem Handler Submit SuperClass
 * 최초 작성일 : 2007/03/28
 * Comments :
 * 수정내역 :
 * </pre>
 * 
 * @version : 1.0
 * @author : 신동민
 */
public abstract class WorkItemHandlerSubmitJSPBase extends WorkItemHandlerJSPBase {

    /**
     * Submit 페이지에서 변수 설정시 사용할 객체.
     */
    public ResultPayload resultPayload;

    /**
     * WorkItemHandlerSubmitJSPBase Constructor
     */
    public WorkItemHandlerSubmitJSPBase() {
        super();
    }

    /**
     * 프로세스인스턴스 초기화
     * 
     * @param request
     * @throws Exception
     */
    public void setInitializeProc(HttpServletRequest request) throws Exception {
        ProcessDefinitionRemote pdr = null;

        // useBean
        ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();

        pm = processManagerFactory.getProcessManager();

        instanceId = decode(request.getParameter("instanceId"));

        processDefinition = decode(request.getParameter("processDefinition"));
        tracingTag = request.getParameter("tracingTag");

        initiate = "yes".equals(request.getParameter("initiate")); // Submit에서는 isEventHandler 무시.

        isViewMode = "yes".equals(request.getParameter("isViewMode"));
        isMine = initiate || "yes".equals(request.getParameter("isMine"));
        taskId = request.getParameter("taskId");

        dispatchingOption = request.getParameter(KeyedParameter.DISPATCHINGOPTION);

        isRacing = ("" + Role.DISPATCHINGOPTION_RACING).equals(dispatchingOption);

        if (piRemote != null)
            pdRemote = piRemote.getProcessDefinition();

        // --------------------------------------------------------------------
        isEventHandler = "yes".equals(request.getParameter("isEventHandler"));

        // added by bhhan 2007-05-31
        // 상신취소 처리위해 추가함.
        // WorkItemHandlerJugmSubmitBase before()로 옮김.
        // isFlowControl = "yes".equals(request.getParameter("isFlowControl"));

        // 성립-가성립건 철회처리,철회취소,반송취소 시 사용하기 위해 추가함.
        // WorkItemHandlerJugmSubmitBase before()로 옮김.
        // isChangeStaus = "yes".equals(request.getParameter("isChangeStaus"));

        resultPayload = new ResultPayload();
        resultPayload.setExtendedValues(new KeyedParameter[] { new KeyedParameter("TASKID", taskId) });

        if (initiate) {// The case that this workitem handler should initiate the process
            if (instanceId != null && (instanceId.trim().equals("null") || instanceId.trim().length() == 0))
                instanceId = null;
            instanceId = pm.initializeProcess(processDefinition, instanceId);
        }

        piRemote = pm.getProcessInstance(instanceId);

        Map genericContext = new HashMap();
        genericContext.put(HumanActivity.GENERICCONTEXT_CURR_LOGGED_ROLEMAPPING, loggedRoleMapping);
        genericContext.put("request", request);
        pm.setGenericContext(genericContext);
    }

    /**
     * Scriptlet 을 처리한 후 처리할 작업 설정.
     * 
     * @param request
     * @throws Exception
     */
    public void doAfter(HttpServletRequest request) throws Exception {

        super.doAfter(request);

        // 상신취소 처리위해 추가함.
        System.out.println("setActivityInstanceProperty().........................");
        if (isFlowControl) {
            // // 철회 취소 인 경우(06)
            // if("06".equals(request.getParameter("cr_jugm_reslt"))){
            // // 만약 tracingTag가 맞지 않은 경우 조회하여 구한다.
            // int crnt_jugm_cnt = 0; // 심사차수
            // if(request.getParameter("crnt_jugm_cnt") != null || !"".equals(request.getParameter("crnt_jugm_cnt")))
            // crnt_jugm_cnt= Integer.parseInt(request.getParameter("crnt_jugm_cnt"));
            //
            // if(crnt_jugm_cnt == 1){
            // tracingTag = "106";
            // }else if(crnt_jugm_cnt == 2){
            // tracingTag = "107";
            // }else{
            // tracingTag = "108";
            // }
            // System.out.println("* 철회취소  tracingTag ID= " + tracingTag);
            //
            // }else{
            // System.out.println("* 변경전  tracingTag ID= " + tracingTag);
            // int crnt_jugm_cnt = 0; // 심사차수
            // if(request.getParameter("crnt_jugm_cnt") != null || !"".equals(request.getParameter("crnt_jugm_cnt")))
            // crnt_jugm_cnt= Integer.parseInt(request.getParameter("crnt_jugm_cnt"));
            //
            // if(crnt_jugm_cnt == 2){
            // tracingTag = "106";
            // }else{
            // tracingTag = "107";
            // }
            // System.out.println("* 현재 tracingTag ID= " + tracingTag);
            // }
            pm.flowControl("compensateTo", instanceId, tracingTag);
        } else if (isChangeStaus) {
            // pm.setActivityInstanceProperty(String instanceId, String tracingTag, String propertyName, Serializable value,
            // Class valueType)
            System.out.println("setActivityInstanceProperty().........................");
            // 기존 상태삭제
            pm.setActivityInstanceProperty(instanceId, beforeTracingTag, "Status", Activity.STATUS_CANCELLED, String.class);
            pm.setActivityInstanceProperty(instanceId, tracingTag, "Status", Activity.STATUS_COMPLETED, String.class);
        } else if (isEventHandler) {
            // String mainInstanceId = getParameter(parameterMap, "mainInstanceId");
            System.out.println("executeEventByWorkitem().........................");
            String eventName = request.getParameter("eventName");
            pm.executeEventByWorkitem(instanceId, eventName, resultPayload);
        } else if (initiate) {
            pm.executeProcessByWorkitem(instanceId, resultPayload);
        } else {
            System.out.println("completeWorkitem().........................");
            pm.completeWorkitem(instanceId, tracingTag, taskId, resultPayload);
        }
    }

    /**
     * 프로세스인스턴스 객체의 변수를 설정한다. Submit 시에는 ResultPayload 객체를 이용하여 설정한다.
     *
     * @param String key
     * @param Serializable value
     * @throws Exception
     */
    public void setProcVar(String key, Serializable value) throws Exception {
        /*
         * if( resultPayload != null && !StringUtils.isEmpty(key) && value != null){ resultPayload.setProcessVariableChange(new
         * KeyedParameter(key, value)); }
         */

        super.setProcVar(key, value);
    }
}
