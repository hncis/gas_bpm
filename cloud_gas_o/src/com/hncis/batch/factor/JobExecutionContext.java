package com.hncis.batch.factor;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;


// TODO: Auto-generated Javadoc
/**
 * The Class JobExecutionContext. - JobExecutionContext 클래스
 */
@Component("jobExecutionContext")
public class JobExecutionContext {

    /** The parameter map. */
    private Map<String,Object> parameterMap = new HashMap<String,Object>();

    /**
     * Gets the parameter map.- parameter map 을 가져옴 
     *
     * @return the parameter map
     */
    public Map<String, Object> getParameterMap()
    {
        return parameterMap;
    }

    /**
     * Sets the parameter map.- parameter map 설정
     *
     * @param parameterMap the parameter map
     */
    public void setParameterMap(Map<String, Object> parameterMap)
    {
        this.parameterMap = parameterMap;
    }

    /**
     * Reset.- parameter map 초기화
     */
    public void reset()
    {
        parameterMap.clear();
    }

}
