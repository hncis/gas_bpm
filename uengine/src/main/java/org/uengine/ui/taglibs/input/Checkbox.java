package org.uengine.ui.taglibs.input;

import org.apache.log4j.Logger;
import org.uengine.ui.list.util.HttpUtils;
import org.uengine.util.UEngineUtil;


import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.thoughtworks.xstream.XStream;

/**
*
* @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
* @version $Id: Checkbox.java,v 1.6 2011/07/22 07:33:16 curonide Exp $
*/
public class Checkbox extends TagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Checkbox.class);

    private String name; // name of the checkbox group

    private String value; // value of this particular button

    private String dVal; // our single default value

    private String[] dValArray; // our multiple default values

    private Map attributes; // attributes of the <input> element

    private String attributesText; // attributes of the <input> element as text

    private String beanId; // bean id to get default values from
    
	private int viewMode;

    public void release() {
        super.release();
        name = null;
        dVal = null;
        dValArray = null;
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

            // replace null value with "on"
            if (value == null)
                value = "on";

            // get what we need from the page
            ServletRequest req = pageContext.getRequest();
            JspWriter out = pageContext.getOut();
            
            String beanValue = HttpUtils.getParameter(beanId, name, dVal, viewMode, pageContext)[0];
            ArrayList<String> valueList = new ArrayList<String>();
            
            int beginIndex = 0; 
            int endIndex = 0;
            while(true) {
            	endIndex = beanValue.indexOf(";", beginIndex);
            	
            	if (endIndex == -1) {
            		if (beginIndex == 0) {
            			valueList.add(beanValue);
            		}
            		break;
            	}
            	
            	valueList.add(beanValue.substring(beginIndex, endIndex));
            	beginIndex = endIndex + 1;
            }
            
            
            int index = 0;
            
            if (req.getAttribute(Util.quote(name)) != null) {
           		index = ((Integer)req.getAttribute(Util.quote(name))).intValue();
           		index ++;
            }

            if (valueList.size() > 0) {
	            if (index == valueList.size() - 1) {
	            	req.removeAttribute(Util.quote(name));
	            } else {
	            	req.setAttribute(Util.quote(name), index);
	            }
            }
            
            // start building up the tag
            out.print("<input type=\"checkbox\" ");
            out.print("name=\"" + Util.quote(name) + "\" ");
            out.print("value=\"" + Util.quote(value) + "\" ");
            
            // include any attributes we've got here
            Util.printAttributes(out, attributes);
            if (attributesText != null) {
                out.print(attributesText + " ");
            }
            
//            if (valueList.size() > 0 && UEngineUtil.isNotEmpty(value) && value.equals(valueList.get(index))) {
            if (valueList.size() > 0 && UEngineUtil.isNotEmpty(valueList.get(index))) {
            	out.print(" checked ");
            }
            
            if (viewMode != InputConstants.WRITE) {
            	out.print(" disabled ");
            	out.print(" />");
            	if (index == 0) {
	        		out.print("<input type=\"hidden\" ");
	                out.print("name=\"" + Util.quote(name) + "\" ");
	                out.print("value=\"" + Util.quote(beanValue) + "\" ");
	                out.print(" />");
            	}
            } else {
            	out.print(" />");
            }
            
            /*
            // READONLY ���� ��d. 
			boolean isReadOnly = false;
			
            // construct a vector of default values
            Vector dVals = new Vector();
            if (dVal != null)
                dVals.add(dVal);
            if (dValArray != null) {
                for (int i = 0; i < dValArray.length; i++) {
					if (dValArray[i] != null) {
						if (logger.isInfoEnabled()) {
							logger
									.info("doStartTag() - dValArray[i] : " + dValArray[i]); //$NON-NLS-1$
						}
						dVals.add(dValArray[i]);
					}
                }
			}
            // start building up the tag
            out.print("<input type=\"checkbox\" ");
            out.print("name=\"" + Util.quote(name) + "\" ");
            out.print("value=\"" + Util.quote(value) + "\" ");

            // include any attributes we've got here
            Util.printAttributes(out, attributes);
            if (attributesText != null) {
                out.print(attributesText + " ");
            }

            //
             //* Check this box (no pun intended) against potentially multiple
             //* selections. (No need for a hash table as in <select> because
             //* we're doing this exactly once per tag. We COULD cache stuff
             //* between tags, but I'm not sure that kind of extra performance
             //* would ever be called for.) We first check if there is a bean
             //* value associated with the checkbox, if there is we use it. Then
             //* note that we only use the "defaults" if the request is ENTIRELY
             //* empty; this is different from what we do with the other input
             //* types, checking "defaults" when there's no value for the specific
             //* field. This difference is the result of the underlying
             //* inconsistency between checkboxes and everything else. To achieve
             //* the default concept nicely with a bean, put the default as the
             //* initial value of the property in the bean when it is constructed
             //* rather than using the "default" attribute of the checkbox tag.
             //

            // First check beanValue, if available - then we don't worry about
            // the defaults or request values etc
            String[] beanValues = null;
            Object beanValue = null;
			if ( pageContext.getAttribute(name, PageContext.REQUEST_SCOPE) != null) {
				beanValue = pageContext.getAttribute(name, PageContext.REQUEST_SCOPE);
			}
			
			// �μ���.
			if ( getViewMode() == InputConstants.PRINT ) {
				beanValue = pageContext.getRequest().getParameter(name);
			}
			
			if ( beanValue != null && beanValue instanceof String ) {
				if ( ((String)beanValue).startsWith("<![CDATA[") ) {
					XStream xstream = new XStream();
					beanValue = xstream.fromXML((String)beanValue);
				}
			}

			if ( beanValue != null ) {
				if ( beanValue instanceof String) {
					
					String strBeanValue = (String)beanValue;

					
					if(beanValues == null){
						beanValues = new String[1];
					}
					//beanValues[0] = (String)beanValue;
					beanValues[0] = strBeanValue;
				} else if ( beanValue instanceof String[]) {
					beanValues = (String[])beanValue;
				}
			}
			
			if ( beanValue == null ) {
//				beanValues = (beanId != null ? Util.beanPropertyValues(pageContext.findAttribute(beanId), name) : null);
				beanValues = HttpUtils.getParameter(beanId, name, dVal, viewMode, pageContext);
			}
            
            boolean isChecked = false; // üũ����.
            if (UEngineUtil.isNotEmpty(beanValues)) {
                for (int i = 0; i < beanValues.length; i++) {
					if (logger.isInfoEnabled()) {
						//$NON-NLS-1$ //$NON-NLS-2$
						logger.info("doStartTag() - beanValues[i] : " + beanValues[i] + ", " + value); 
					}
					
					String[] listStrValue = beanValues[i].split(";");
					for (int j = 0; j < listStrValue.length; j++) {
	                    if (beanValues[i] != null && listStrValue[j].equals(value)) {
	                        out.print("checked=\"checked\" ");
	                        isChecked = true;
	                        break;
	                    }
					}
					
                }
            }
            // No bean value, so check if the request is empty - and use
            // defaults if it is
            //else if (!req.getParameterNames().hasMoreElements()) {
			else if (req.getParameterValues(name)!= null) {
				// Use values from the request
				String[] checked = req.getParameterValues(name);
				if (checked != null) {
					// use the request if it says anything
					for (int i = 0; i < checked.length; i++) {
						if (logger.isInfoEnabled()) {
							logger
									.info("doStartTag() - checked[i] : " + checked[i] + ", " + value); //$NON-NLS-1$ //$NON-NLS-2$
						}
						if (checked[i].equals(value)) {
							out.print("checked=\"checked\" ");
							isChecked = true;
							break; // why go on?
						}
					}
				}
            } else {
				
				// use "default" array if we got nothing from the request
				//if (dVals != null) {
            	if ( dVals != null && getViewMode() == InputConstants.WRITE ) { // WRITE MODE �ÿ��� DEFAULT ���?.
					for (int i = 0; i < dVals.size(); i++) {
						if (dVals.get(i) == null
								|| !(dVals.get(i) instanceof String))
							throw new JspTagException(
									"'default' array must only contain non-null "
											+ "Strings");
                        

						if (logger.isInfoEnabled()) {
							logger
									.info("doStartTag() - dVals.get(i) : " + dVals.get(i) + ", " + value); //$NON-NLS-1$ //$NON-NLS-2$
						}
                        
						if ((dVals.get(i)).equals(value)) {
							out.print("checked=\"checked\" ");
							isChecked = true;
							break; // why go on?
						}
					}
				}

            }
            
			if ( getViewMode() == InputConstants.DISABLED || getViewMode() == InputConstants.VIEW || getViewMode() == InputConstants.PRINT || isReadOnly) out.print("disabled ");
			
//			if( isReadOnly ){	// READONLY �� ���?. 
//            	out.print("style='display:none;' ");
//            }
			
            // end the tag
            out.print("/>");
            
            if( isReadOnly && isChecked ){	// READONLY �� ���?. 
	    		out.print("<input type=\"hidden\" ");
	            out.print("name=\"" + Util.quote(name) + "\" ");
	            out.print("value=\"" + Util.quote(value) + "\" ");
	            out.print("/>");
            }
*/
            
            
        } catch (Exception ex) {
            throw new JspTagException(ex.getMessage());
        }
        return SKIP_BODY;
    }

    /**
     * Getter for property name.
     * 
     * @return Value of property name.
     */
    public String getName() {
        return name;
    }

    public void setName(String x) {
        name = x;
    }

    /**
     * Getter for property value.
     * 
     * @return Value of property value.
     */
    public String getValue() {
        return value;
    }

    public void setValue(String x) {
        value = x;
    }

    /**
     * Getter for property defaults.
     * 
     * @return Value of property defaults.
     */
    public String[] getDefaults() {
        return dValArray;
    }

    public void setDefaults(String[] x) {
        dValArray = x;
    }

    /**
     * Getter for property default.
     * 
     * @return Value of property default.
     */
    public String getDefault() {
        return dVal;
    }

    public void setDefault(String x) {
        dVal = x;
    }

    /**
     * Getter for property bean.
     * 
     * @return Value of property bean.
     */
    public String getBean() {
        return beanId;
    }

    public void setBean(String x) {
        beanId = x;
    }

    /**
     * Getter for property attributesText.
     * 
     * @return Value of property attributesText.
     */
    public String getAttributesText() {
        return attributesText;
    }

    public void setAttributesText(String x) {
        attributesText = x;
    }

    /**
     * Getter for property attributes.
     * 
     * @return Value of property attributes.
     */
    public Map getAttributes() {
        return attributes;
    }

    public void setAttributes(Map x) {
        attributes = x;
    }



	public int getViewMode() {
		return viewMode;
	}

	public void setViewMode(int i) {
		viewMode = i;
	}

}