package org.uengine.ui.taglibs.input;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.uengine.contexts.FileContext;
import org.uengine.kernel.GlobalContext;
import org.uengine.ui.list.util.FileUploadUtil;
import org.uengine.ui.list.util.HttpUtils;
import org.uengine.util.UEngineUtil;

/**
*
* @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
* @version $Id: FileUpload.java,v 1.20 2011/07/22 07:33:16 curonide Exp $
*/
public class FileUpload extends TagSupport implements HTTPValueHandler{

    private String name; // name of the radio-button group

    private String value; // value of this particular button

    private String dVal; // default value if none is found

    private Map attributes; // attributes of the <input> element

    private String beanId; // bean id to get default values from
    
	private int viewmode;
	
	private String fileclass;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getdVal() {
		return dVal;
	}

	public void setdVal(String dVal) {
		this.dVal = dVal;
	}

	public Map getAttributes() {
		return attributes;
	}

	public void setAttributes(Map attributes) {
		this.attributes = attributes;
	}

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public int getViewmode() {
		return viewmode;
	}

	public void setViewmode(int viewmode) {
		this.viewmode = viewmode;
	}

	public String getFileclass() {
		return fileclass;
	}

	public void setFileclass(String fileclass) {
		this.fileclass = fileclass;
	}
	
    public void release() {
        super.release();
        name = null;
        dVal = null;
        attributes = null;
        beanId = null;
    }
    
    public int doStartTag() throws JspException {
        try {
        	FileContext fc = null;
            JspWriter out = pageContext.getOut();
            Object[] beanValues = HttpUtils.getParameterAsObject(beanId, name, dVal, viewmode, pageContext); //mapping values
			
            if (beanValues[0] instanceof FileContext) {
            	fc = (FileContext) beanValues[0];
            } else {
            	if (UEngineUtil.isNotEmpty((String)beanValues[0])) {
            		String xmlValue = (String) beanValues[0];
            		xmlValue = xmlValue.replaceAll("&lt;", "<");
            		xmlValue = xmlValue.replaceAll("&gt;", ">");
            		fc = (FileContext)GlobalContext.deserialize(xmlValue);
            	}
            }

            String tag = FileUploadUtil.getFileUploadTag(getName(), fc, getFileclass(), getViewmode());
            out.print("<div name=\"_div_file_" + getName() + "\" id=\"_div_file_" + getName() + "\">");
            out.print(tag);
            out.print("</div>");
            
        } catch (Exception ex) {
            throw new JspTagException(ex.getMessage());
        }
        return SKIP_BODY;
    }

	
	public Object parseValue(Map parameterMap, String key) {
		FileContext fc = null;
		String xmlValue = (String)parameterMap.get(key);
		if (!UEngineUtil.isNotEmpty(xmlValue)) {
			return null;
		} else {
			try {
				fc = (FileContext)GlobalContext.deserialize(xmlValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return fc;
		}
	}
}