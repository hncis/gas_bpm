package org.uengine.ui.taglibs.input;

import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;
import org.uengine.kernel.GlobalContext;
import org.uengine.ui.taglibs.input.*;
import org.uengine.ui.list.util.HttpUtils;

/**
*
* @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
* @version $Id: RichTextArea.java,v 1.22 2011/07/22 07:33:16 curonide Exp $
*/
public class RichTextArea extends BodyTagSupport {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name; // name of the textarea

    private String dVal; // default value if none is found

    private Map attributes; // attributes of the <textarea> element

    private String attributesText; // attributes of the <input> element as text

    private String beanId; // bean id to get default values from

    private String width, height;
    
	private int viewmode;

    public void release() {
        super.release();
        name 				= null;
        dVal 					= null;
        attributes 			= null;
        attributesText 		= null;
        beanId 				= null;
        width 				= null;
        height 				= null;
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
            ServletRequest req = pageContext.getRequest();
            JspWriter out = pageContext.getOut();

            Object beanValues[] = HttpUtils.getParameterAsObject(beanId,  name,  dVal,  viewmode,  pageContext);
			String beanValue = beanValues[0].toString();
			String strCss = "";
			if (width != null) {
				strCss += "width='" + (width) + "';";
            }
            if (height != null) {
            	strCss += "height='" + (height) + "';";
            }
            
//			String strCss = " style=\\\"";
//			if (width != null) {
//				strCss += "width:" + (width) + ";";
//            }
//            if (height != null) {
//            	strCss += "height:" + (height) + ";";
//            }
//            strCss += "\\\" ";
			
			
			if ( viewmode == InputConstants.VIEW || viewmode == InputConstants.PRINT ) {
				out.print(beanValue+"<input type=\"hidden\" name=\"" + Util.quote(name) + "\" value=\""+StringEscapeUtils.escapeHtml(beanValue)+"\">");
				return EVAL_PAGE;
			}
			
			// READONLY
			boolean isReadOnly = false;
			
            // start building up the tag
//			String strHtml = "<script type=\"text/javascript\">"
//					+ "\n	id=\"" + Util.quote(name) + "\"; "
//					+ "\n	beanValue=\"" + Util.quote(beanValue.replaceAll("\\s", " ")) + "\"; "
//					+ "\n	strCss=\"" + strCss + "\"; "
//					+ "\n"
//					+ "\n	createRichTextEditor( id, beanValue, strCss);"
//					+ "\n</script>";
			StringBuffer strHtml = new StringBuffer(); 
//			strHtml.append("<textarea class=\"ckeditor\" id=\"").append(Util.quote(name)).append("\" ")
			strHtml.append("<textarea id=\"").append(Util.quote(name)).append("\" ")
			.append("name=\"").append(Util.quote(name)).append("\" ")
			.append("style=\"").append(strCss).append("\" >") 
			.append(HtmlUtils.htmlEscape(beanValue))
			.append("</textarea>")
			.append("<script type=\"text/javascript\">")
			.append("CKEDITOR.replace(\"").append(Util.quote(name)).append("\", ")
			.append("{ toolbar : \"General\" });")
			.append("</script>");
            out.print(strHtml);
            
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

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWidth(String width) {
        this.width = width;
    }
    
	public String getWidth() {
		return width;
	}

	public String getHeight() {
		return height;
	}

	public int getViewmode() {
		return viewmode;
	}

	public void setViewmode(int i) {
		viewmode = i;
	}

}