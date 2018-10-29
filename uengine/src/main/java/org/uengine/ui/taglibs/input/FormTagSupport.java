package org.uengine.ui.taglibs.input;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.uengine.ui.list.util.HttpUtils;

/**
*
* @author Jinyoung Jang
*/
public class FormTagSupport extends TagSupport {

    private String name; // name of the radio-button group

    private String value; // value of this particular button

    private String dVal; // default value if none is found

    private Map attributes; // attributes of the <input> element

    private String beanId; // bean id to get default values from
    
	private int viewMode;
	
   public void release() {
        super.release();
        name = null;
        dVal = null;
        attributes = null;
        beanId = null;
    }

    public void setName(String x) {
        name = x;
    }

    public void setValue(String x) {
        value = x;
    }

    public void setAttributes(Map x) {
        attributes = x;
    }



    public void setBean(String x) {
        beanId = x;
    }

    public void setDefault(String x) {
        dVal = x;
    }

    /**
     * Getter for property name.
     * 
     * @return Value of property name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for property default.
     * 
     * @return Value of property default.
     */
    public String getDefault() {
        return dVal;
    }

    /**
     * Getter for property bean.
     * 
     * @return Value of property bean.
     */
    public String getBean() {
        return beanId;
    }


    /**
     * Getter for property attributes.
     * 
     * @return Value of property attributes.
     */
    public Map getAttributes() {
        return attributes;
    }
	public int getViewmode() {
		return viewMode;
	}

	public void setViewmode(int i) {
		viewMode = i;
	}
	
	
    public Object[] getParameterAsObject() throws JspException{
    	return HttpUtils.getParameterAsObject(beanId, name, dVal, viewMode, pageContext);
    }
    
    public String[] getParameter() throws JspException{
    	return HttpUtils.getParameter(beanId, name, dVal, viewMode, pageContext);
    }
    

}