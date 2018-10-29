package org.uengine.ui.taglibs.input;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.uengine.ui.list.util.HttpUtils;
import org.uengine.util.UEngineUtil;

/**
*
* @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
* @version $Id: Password.java,v 1.4 2008/09/30 05:11:41 curonide Exp $
*/
public class Password extends TagSupport {

    private String name; // name of the text field

    private String dVal; // default value if none is found

    private Map attributes; // attributes of the <input> element

    private String attributesText; // attributes of the <input> element as text

    private String beanId; // bean id to get default values from

    private String size;
    
	private int viewMode;

    public void release() {
        super.release();
        name = null;
        dVal = null;
        attributes = null;
        attributesText = null;
        beanId = null;
    }

    public int doStartTag() throws JspException {
        try {
            // sanity check
            if (name == null || name.equals(""))
                throw new JspTagException("invalid null or empty 'name'");

            // Store beanId in a local variable as we change it
            String beanId = this.beanId;

            // Get default beanId
            if (beanId == null) {
                beanId = Util.defaultFormBeanId(this);
            } else if (beanId.length() == 0) {
                // An empty beanId means, do not use any bean - not even default
                beanId = null;
            }

            // get what we need from the page
            ServletRequest req = pageContext.getRequest();
            JspWriter out = pageContext.getOut();
            
            // READONLY
			boolean isReadOnly = false;

			// start building up the tag
            out.print("<input type=\"password\" ");
            out.print("name=\"" + Util.quote(name) + "\" ");
            
            if( isReadOnly ){	// READONLY
            	out.print("style='border:0;' readonly ");
            }

            // include any attributes we've got here
            Util.printAttributes(out, attributes);
            if (attributesText != null) {
                out.print(attributesText + " ");
            }

            if (size != null) {
                out.print("size=\"" + Util.quote(size) + "\" ");
            }

            String beanValue = HttpUtils.getParameter(beanId, name, dVal, viewMode, pageContext)[0];	
			
			out.print("value=\"" + beanValue + "\" ");
			
            // end the tag
            out.print("/>");

        } catch (Exception ex) {
            throw new JspTagException(ex.getMessage());
        }
        return SKIP_BODY;
    }

    public void setName(String x) {
        name = x;
    }

    public void setAttributes(Map x) {
        attributes = x;
    }

    public void setAttributesText(String x) {
        attributesText = x;
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
     * Getter for property attributesText.
     * 
     * @return Value of property attributesText.
     */
    public String getAttributesText() {
        return attributesText;
    }

    /**
     * Getter for property attributes.
     * 
     * @return Value of property attributes.
     */
    public Map getAttributes() {
        return attributes;
    }

    public void setSize(String size) {
        this.size = size;
    }
	public int getViewMode() {
		return viewMode;
	}

	public void setViewMode(int i) {
		viewMode = i;
	}

}