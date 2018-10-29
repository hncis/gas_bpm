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
* @version $Id: Radio.java,v 1.6 2011/07/22 07:33:16 curonide Exp $
*/
public class Radio extends TagSupport {

    private String name; // name of the radio-button group

    private String value; // value of this particular button

    private String dVal; // default value if none is found

    private Map attributes; // attributes of the <input> element

    private String attributesText; // attributes of the <input> element as text

    private String beanId; // bean id to get default values from
    
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
            // sanity check (but value CAN be empty)
            if (name == null || name.equals("") || value == null)
                throw new JspTagException(
                        "invalid null or empty 'name' or 'value'");

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
//            ServletRequest req = pageContext.getRequest();
            JspWriter out = pageContext.getOut();
            
//          // First check the bean value
//          String beanValue = (beanId != null ? Util.beanPropertyValue(
//                  pageContext.findAttribute(beanId), name) : null);
//          
//			if ( pageContext.getAttribute(name, PageContext.REQUEST_SCOPE) != null) {
//				beanValue = Util.quote((String)pageContext.getAttribute(name, PageContext.REQUEST_SCOPE));				
          
            String beanValue = HttpUtils.getParameter(beanId, name, dVal, viewMode, pageContext)[0];
            
            // READONLY
//			boolean isReadOnly = false;
            
            // start building up the tag
            out.print("<input type=\"radio\" ");
            out.print("name=\"" + Util.quote(name) + "\" ");
            out.print("value=\"" + Util.quote(value) + "\" ");

            // include any attributes we've got here
            Util.printAttributes(out, attributes);
            if (attributesText != null) {
                out.print(attributesText + " ");
            }

            // check this button if it's the right one
            String target = null;
            
            if (beanValue != null) {
                target = beanValue;
            }
//            // If no bean value, check the request - if none, use default
//            else if (req.getParameter(name) == null) {
//                target = dVal;
//            } else {
//                target = req.getParameter(name);
//            }

            String viewModeValue = ""; 
            
            if (target != null && target.equals(value)) {
//                out.print("checked=\"checked\" ");
                out.print(" checked ");
                viewModeValue = "<input type=\"hidden\" " + "name=\"" + Util.quote(name) + "\" " + "value=\"" + Util.quote(value) + "\" />";
            }
			
			if ( getViewMode() == InputConstants.DISABLED || getViewMode() == InputConstants.VIEW || getViewMode() == InputConstants.PRINT /*|| isReadOnly*/ ) {
				out.print("disabled ");
				out.print("/>");
				out.print(viewModeValue);
				
			} else {
				out.print("/>");
			}
			
//			if( isReadOnly ){	// READONLY
//            	out.print("style='display:none;' ");
//            }
			
            // end the tag
            
//            if( isReadOnly ){	// READONLY
//            	if( target != null && target.equals(value) ){
//            		out.print("<input type=\"hidden\" ");
//                    out.print("name=\"" + Util.quote(name) + "\" ");
//                    out.print("value=\"" + Util.quote(value) + "\" ");
//                    out.print("/>");
//            	}
////            	if ( beanValue == null || beanValue.trim().length() == 0 ) beanValue = "&nbsp;";
////				out.print(beanValue);
//            }

        } catch (Exception ex) {
            throw new JspTagException(ex.getMessage());
        }
        return SKIP_BODY;
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
	public int getViewMode() {
		return viewMode;
	}

	public void setViewMode(int i) {
		viewMode = i;
	}

}