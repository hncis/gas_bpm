package org.uengine.ui.taglibs.input;

import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.uengine.ui.list.util.HttpUtils;
import org.uengine.util.UEngineUtil;

/**
*
* @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
* @version $Id: TextArea.java,v 1.16 2011/07/22 07:33:16 curonide Exp $
*/
public class TextArea extends BodyTagSupport {

    private String name; // name of the textarea

    private String dVal; // default value if none is found

    private Map attributes; // attributes of the <textarea> element

    private String attributesText; // attributes of the <input> element as text

    private String beanId; // bean id to get default values from

    private String cols, rows;
    
	private int viewMode;
	
	private String title;

    public void release() {
        super.release();
        name = null;
        dVal = null;
        attributes = null;
        attributesText = null;
        beanId = null;
        cols = null;
        rows = null;
    }

    public int doEndTag() throws JspException {
        try {
            // sanity check
            if (name == null || name.equals(""))
                throw new JspTagException("invalid null or empty 'name'");

            // Store beanId in a local variable because we change it
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

            String beanValues[] = HttpUtils.getParameter(beanId, name, dVal, viewMode, pageContext);	
			String beanValue = beanValues[0];
			
			if ( getViewMode() == InputConstants.VIEW || getViewMode() == InputConstants.PRINT ) {
				
            	String value = "";
				if (UEngineUtil.isNotEmpty(beanValue)) {
					value = HttpUtils.toHTML(beanValue);
            	}
            	out.print(value+"<input type=\"hidden\" name=\"" + Util.quote(name) + "\" value=\""+beanValue+"\">");

				return EVAL_PAGE;
			}
			
			// READONLY
//			boolean isReadOnly = false;
			
        	StringBuffer sbHtml = new StringBuffer();
            // start building up the tag
        	sbHtml.append("<textarea name=\"").append(Util.quote(name)).append("\" ");

            // include any attributes we've got here
        	sbHtml.append(Util.getAttributesTag(attributes));
        	if (attributes.containsKey("title")) {
        		setTitle((String) attributes.get("title"));
        	}
        	
            if (attributesText != null) {
            	sbHtml.append(attributesText).append(" ");
            }

            if (cols != null) {
            	sbHtml.append("cols='").append(Util.quote(cols)).append("' ");
            }
            if (rows != null) {
            	sbHtml.append("rows='").append( Util.quote(rows)).append("' ");
            }
            
//            if( isReadOnly ){	// READONLY
//            	sbHtml.append(" readonly='readonly' ");
//            }

//            String defaultValue = dVal;
//            if (getBodyContent() != null && getBodyContent().getString() != null) {
//                defaultValue = getBodyContent().getString();
//            }

            title = (String)attributes.get("title");
            
            if (!UEngineUtil.isNotEmpty(beanValue)) {
            	beanValue = title;
            	sbHtml.append(" style='color:#CCC' ");
            }
            
            if (!UEngineUtil.isNotEmpty(title)) {
            	title = "";
            }
            
            // end the starting tag
            sbHtml.append("title=\"").append(title).append("\" onfocus='clearTitle(this)' onblur='setTitle(this)'>");
            
            sbHtml.append(UEngineUtil.isNotEmpty(beanValue) ? beanValue : "");

//            /*
//             * print out the value from the bean if it's there, or from the
//             * request if it's there, or use the default value if it's not
//             */
//            String beanValue = (beanId != null ? Util.beanPropertyValue(
//                    pageContext.findAttribute(beanId), name) : null);
//			if ( pageContext.getAttribute(name, PageContext.REQUEST_SCOPE) != null) {
//				out.print(Util.quote((String)pageContext.getAttribute(name, PageContext.REQUEST_SCOPE)));				
//			} else if (beanValue != null) {
//                out.print(Util.quote(beanValue));
//            } else if (req.getParameter(name) != null) {
//                out.print(Util.quote(req.getParameter(name)));
//            } else {
//                if (defaultValue != null)
//                    out.print(Util.quote(defaultValue));
//            }

            // end the textarea
            sbHtml.append("</textarea>");
            
            out.print(sbHtml.toString());

        } catch (Exception ex) {
            throw new JspTagException(ex.getMessage());
        }
        return EVAL_PAGE;
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

    public void setCols(String cols) {
        this.cols = cols;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }
	public int getViewMode() {
		return viewMode;
	}

	public void setViewMode(int i) {
		viewMode = i;
	}
	
	public void setTitle(String title) {
		if (UEngineUtil.isNotEmpty(title)) {
			this.title = title;
		} else {
			this.title = "";
		}
	}

}