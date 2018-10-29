package org.uengine.ui.taglibs.input;

import org.apache.log4j.Logger;
import org.uengine.ui.list.util.HttpUtils;
import org.uengine.util.UEngineUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
 * @version $Id: Text.java,v 1.23 2010/11/01 00:47:38 allbegray Exp $
 */
public class Text extends TagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Text.class);

	private String name; // name of the text field

	private String dVal; // default value if none is found

	private Map attributes; // attributes of the <input> element

	private String attributesText; // attributes of the <input> element as text

	private String beanId; // bean id to get default values from

	private String size;

	private int viewMode;
	
//	private String format;

	private String title;
	
	public void release() {
		super.release();
		name = null;
		dVal = null;
		attributes = null;
		attributesText = null;
		beanId = null;
		size = null;
	}
	
	
	/**
	 * 속성 format 의 설정에 따라 vlaue를 변환하여 반환. [money || number || date]
	 * @param String
	 * @return String
	 * @throws ParseException 
	 */
	private String parseBeanValue(String value) throws ParseException {
		String strValue = value.trim();
		
/*		if (UEngineUtil.isNotEmpty(format) || attributes.containsKey("format")) {
			if (attributes.containsKey("format")) {
				setFormat(attributes.get("format") + "");
			}
			*/
		if (attributes.containsKey("format")) {
			String format = (String) attributes.get("format"); 
			if ("money".equalsIgnoreCase(format) || "number".equalsIgnoreCase(format)) {
				strValue = strValue.replaceAll("[^\\+\\-0-9.]*", "");
				if (UEngineUtil.isNotEmpty(strValue)) {
					String ssValues[] = strValue.split("\\.");
					long number = Long.parseLong(ssValues[0]);
					strValue = String.format("%,d", number);
					
					if (ssValues.length > 1) strValue += "\\." +  ssValues[1].trim();
				} else {
					strValue = "0";
				}
//			} else if ("date".equalsIgnoreCase(strFormat)) {
//				DateFormat df = DateFormat.getDateInstance();
//				Date dateValue = df.parse(value);
//				
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//				strValue = formatter.format(dateValue);
			}
		}
		
		return strValue;
	}
	
	private String getOptionString() {
		String strOption = "";
		
//		if (UEngineUtil.isNotEmpty(format)) {
		if (attributes.containsKey("format")) {
			String format = (String)attributes.get("format");
			if ("money".equalsIgnoreCase(format) || "number".equalsIgnoreCase(format)) {
				strOption = " style=\"text-align:right;\" ";
//				strOption = " onkeydown=\"handlerNum(this);\" onkeyup=\"formatNumber(this);\" ";
			}
			
		}
		return strOption;
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
				beanId = null; 		// An empty beanId means, do not use any bean - not even default
			}

			// get what we need from the page
			// ServletRequest req = pageContext.getRequest();
			JspWriter out = pageContext.getOut();

//			String[] beanValues = HttpUtils.getParameter(beanId, name, dVal, viewMode, pageContext);
			Object[] beanValues = HttpUtils.getParameterAsObject(beanId, name, dVal, viewMode, pageContext);

        	if (attributes.containsKey("title")) {
        		setTitle((String) attributes.get("title"));
        	}
        	
			for (int i = 0; i < beanValues.length; i++) {
//				String beanValue = parseBeanValue(beanValues[i]);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				// 캘린더 형식의 데이터를 처리하기 위한 로직
				String beanValue = null;
				
				if (beanValues[i] instanceof Calendar || beanValues[i] instanceof Date) {
					Object dateBeanValue = beanValues[i];
					if (beanValues[i] instanceof Calendar) {
						dateBeanValue = ((Calendar) dateBeanValue).getTime();
					}
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					beanValue = formatter.format((Date) dateBeanValue);
				} else {
					beanValue = parseBeanValue(HttpUtils.quote("" + beanValues[i]));
				}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if (viewMode == InputConstants.VIEW || viewMode == InputConstants.PRINT) {
					if (!UEngineUtil.isNotEmpty(beanValue)) beanValue = "&nbsp;";
					
					out.print(beanValue+"<input type=\"hidden\" name=\"" + Util.quote(name) + "\" value=\""+beanValue+"\">");
					return EVAL_PAGE;
				}

				// READONLY
				boolean isReadOnly = false;

				// start building up the tag
				StringBuilder sbHtml = new StringBuilder();
				sbHtml.append("<input type=\"text\" name=\"" + Util.quote(name) + "\" ");

				// include any attributes we've got here
				sbHtml.append(Util.getAttributesTag(attributes));
				if (attributesText != null) {
					sbHtml.append(attributesText + " ");
				}

				if (size != null) {
					sbHtml.append("size=\"" + Util.quote(size) + "\" ");
				}

				// System.out.println("=== beanId : " + beanId);
				// System.out.println("=== pageContext.findAttribute(beanId) : "
				// + pageContext.findAttribute(beanId));
				// System.out.println("=== name : " + name + " getAtt : "+
				// pageContext.getAttribute(name));
				// if ( pageContext.getAttribute(name,
				// PageContext.REQUEST_SCOPE) != null) {
				// out.print("value=\"" +
				// Util.quote((String)pageContext.getAttribute(name,
				// PageContext.REQUEST_SCOPE))
				// + "\" ");
				// } else if (beanValue != null) {
				// out.print("value=\"" + Util.quote(beanValue) + "\" ");
				// } else if (req.getParameter(name) != null) {
				// out.print("value=\"" + Util.quote(req.getParameter(name))
				// + "\" ");
				// } else {
				// if (dVal != null)
				// out.print("value=\"" + Util.quote(dVal) + "\" ");
				// else
				// out.print("value=\"\" ");
				// }

				if (viewMode == InputConstants.DISABLED) {
					sbHtml.append(" disabled='disabled' ");
				}
				
				String title = this.title != null ? this.title : "";

				if (isReadOnly) { // READONLY
					sbHtml.append(" readonly='readonly' ");
				} else if (!UEngineUtil.isNotEmpty(beanValue) && UEngineUtil.isNotEmpty(title)) {
		            	beanValue = title;
		            	sbHtml.append(" style='color:#CCC' ");
		        }
				
	            sbHtml.append("value=\"" + beanValue + "\" ");

				// end the tag
	            sbHtml.append("title=\"" + title + "\" onfocus='clearTitle(this)' onblur='setTitle(this)' " + getOptionString() + " />");
				out.print(sbHtml);
				
			}
		} catch (Exception ex) {
			logger.error("doStartTag()", ex); //$NON-NLS-1$
			// throw new JspTagException(ex.getMessage());
		}
		return SKIP_BODY;
	}

	public void setName(String x) {
		name = Util.quote(x);
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
/*
	public String getFormat() {
		return format;
	}


	public void setFormat(String format) {
		this.format = Util.quote(format);
	}*/


	public void setDefault(String x) {
		dVal = Util.quote(x);
	}

	public void setTitle(String title) {
		if (UEngineUtil.isNotEmpty(title)) {
			this.title = title;
		} else {
			this.title = "";
		}
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
		this.size = Util.quote(size);
	}

	public void setViewMode(int i) {
		viewMode = i;
	}

}