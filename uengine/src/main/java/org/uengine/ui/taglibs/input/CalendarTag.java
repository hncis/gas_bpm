package org.uengine.ui.taglibs.input;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.uengine.kernel.GlobalContext;
import org.uengine.ui.list.util.HttpUtils;
import org.uengine.util.UEngineUtil;

/**
*
* @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
* @version $Id: CalendarTag.java,v 1.36 2011/07/22 07:33:16 curonide Exp $
*/
public class CalendarTag extends TagSupport implements HTTPValueHandler{

    private String name; // name of the radio-button group

    private String value; // value of this particular button

    private String dVal; // default value if none is found

    private Map attributes; // attributes of the <input> element

    private String beanId; // bean id to get default values from
    
	private int viewMode;
	
	private String limited;
	
	private String objecttype;

    public void release() {
        super.release();
        name = null;
        dVal = null;
        attributes = null;
        beanId = null;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            
            Object[] beanValuesDisplay = HttpUtils.getParameterAsObject(beanId, name, dVal, viewMode, pageContext);
   
//			String[] beanValuesDisplay = HttpUtils.getParameterAs(beanId, name, dVal,
//					viewMode, pageContext);
			
			for (int i = 0; i < beanValuesDisplay.length; i++) {
				
				String beanValueDisplay = null;
				
				if (beanValuesDisplay[i] != null && (beanValuesDisplay[i] instanceof Calendar || beanValuesDisplay[i] instanceof Date)) {
					
					Object dateBeanValue = beanValuesDisplay[i];
					if(beanValuesDisplay[i] instanceof Calendar){
						dateBeanValue = ((Calendar)dateBeanValue).getTime();
					}						
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					beanValueDisplay = formatter.format((Date)dateBeanValue);
				} else if (beanValuesDisplay[i] != null && beanValuesDisplay[i] instanceof String) {
					beanValueDisplay = (String) beanValuesDisplay[i];					
				}
				
				if (!UEngineUtil.isNotEmpty(beanValueDisplay)) beanValueDisplay = "";
								
//				String beanValueDisplay = beanValuesDisplay[i];
				String className = (String)this.getClass().getName();
				
				if (getViewmode() == InputConstants.VIEW || getViewmode() == InputConstants.PRINT) {
					out.print("<input type='hidden' name='" + getName() + "' value='" + beanValueDisplay + "' /> ");
					out.print("<input type='hidden' name='" + getName() + "_value_handler_"+("calendar".equals(getObjecttype())?"class":"string")+"' value='" + className + "' />");
					out.print(beanValueDisplay);
					return EVAL_PAGE;
				}
				/*
				String limitedOption = "";
				if (UEngineUtil.isNotEmpty(limited)) {
					if (limited.equals("today")) {
						limitedOption = " onchange=\"isAfterToday(this)\" ";
					} else {
						limitedOption = " onchange=\"compareDate('" + limited + "', this)\" ";
					}
				}
				*/
//				String strHtml = "<input readonly='readonly' type='text' name='" + getName() + "' value='" + beanValueDisplay + "' " 
//						+ limitedOption + " id='" + getName() + "' />"
//						+ "<input type='hidden' name='" + getName() + "_value_handler_"+("calendar".equals(getObjecttype())?"class":"string")+"' value='" + className + "' />"
//						+ "&nbsp;<img src='" + GlobalContext.WEB_CONTEXT_ROOT + "/images/Icon/btn_calendar.gif'"
//	            		+ " align='middle' name='" + getName() + "' onclick=\"showFullcalendar('" + getObjecttype() + "', this.name)\" />";
				
				String strHtml = "<input readonly='readonly' class='j_calendar' type='text' name='" + getName() 
					+ "' id='" + getName() + "' Style='width: 80px'"
					+ " value='" + beanValueDisplay + "' /> "
					+ "<input type='hidden' name='" + getName() + "_value_handler_"+("calendar".equals(getObjecttype())?"class":"string")+"' value='" + className + "' />";

				out.println(strHtml);
			}
        } catch (Exception ex) {
            throw new JspTagException(ex.getMessage());
        }
        return SKIP_BODY;
    }
   
	public Object parseToString(Map parameterMap, String key) {
		String dateString = (String)parameterMap.get(key);
		if (!UEngineUtil.isNotEmpty(dateString)) {
			return null;
		} else {
			return dateString;
		}
	}
	
	public Object parseValue(Map parameterMap, String key) {
		String dateString = (String)parameterMap.get(key);
		if (!UEngineUtil.isNotEmpty(dateString)) {
			return null;
		} else {
			Calendar cal = new GregorianCalendar();
			cal.set(Calendar.YEAR, Integer.parseInt(dateString.substring(0, 4)));
			cal.set(Calendar.MONTH, Integer.parseInt(dateString.substring(5, 7)) - 1);
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateString.substring(8, 10)));
			if (key.length() == 16) {
				cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateString.substring(11, 13)));
				cal.set(Calendar.MINUTE, Integer.parseInt(dateString.substring(14, 16)));
			} else {
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
			}
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return cal;
		}

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

	public String getLimited() {
		return limited;
	}

	public void setLimited(String limited) {
		this.limited = limited.trim();
	}

	public String getObjecttype() {
		return objecttype;
	}

	public void setObjecttype(String objecttype) {
		this.objecttype = objecttype;
	}
	
	

}