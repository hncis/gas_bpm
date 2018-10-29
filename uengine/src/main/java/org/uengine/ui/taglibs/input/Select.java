package org.uengine.ui.taglibs.input;

import org.apache.log4j.Logger;
import org.uengine.ui.list.util.HttpUtils;
import org.uengine.util.UEngineUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
*
* @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
* @version $Id: Select.java,v 1.10 2009/05/10 05:50:58 erim79 Exp $
*/
public class Select extends TagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Select.class);

    private String name; // name of the select element

    private String dVal; // default value if none is found

    private String[] dValArray; // our multiple default values

    private Map attributes; // attributes of the <select> element

    private Map options; // what are our options? :)

    private String attributesText; // attributes of the <input> element as text

    private String beanId; // bean id to get default values from

    private boolean multiple; // select multiple

    private String size; // select size

    private List optionLabels; // a list of option labels

    private List optionValues; // a list of option values

    private HashMap chosen; // chosen options (created in doStartTag)
    
	private int viewMode;
	
	private StringBuffer sbSelectedLabels;
	
	private boolean isReadOnly;
	
    public void release() {
        super.release();
        name = null;
        dVal = null;
        dValArray = null;
        attributes = null;
        options = null;
        attributesText = null;
        beanId = null;
        multiple = false;
        size = null;
        optionLabels = null;
        optionValues = null;
        chosen = null;
    }

    public int doStartTag() throws JspException {
        try {
        	String strHtml = "";
        	
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
            
            // READONLY ���� ��d. 
            isReadOnly = false;
			
            /*
             * Print out our options, selecting one or more if appropriate. If
             * there are multiple selections but the page doesn't call for a
             * <select> that accepts them, ignore the selections. This is
             * preferable to throwing a JspException because the (end) user can
             * control input, and we don't want the user causing exceptions in
             * our application.
             */

            String[] selected = null;

            // Use selected from the bean, if available
            String[] beanValues = (beanId != null ? Util.beanPropertyValues(
                    pageContext.findAttribute(beanId), name) : null);
            
            Object objs = pageContext.getAttribute(name, PageContext.REQUEST_SCOPE);
            if ( objs == null || getViewMode() == InputConstants.PRINT) {
            	//objs = GWUtil.toEncode(pageContext.getRequest().getParameterValues(name));
            	objs = (pageContext.getRequest().getParameterValues(new String(name.getBytes("UTF-8"), "8859_1")));
            }
            
    		if (objs != null) {
				if ( objs instanceof String[]) {
					beanValues = (String[]) objs;	
				} 
			}
    		
    		//2008.01.07
    		if (!UEngineUtil.isNotEmpty(beanValues))
    			beanValues = HttpUtils.getParameter(beanId, name, dVal, viewMode, pageContext);
    		
    		if (UEngineUtil.isNotEmpty(beanValues)) {
                selected = beanValues;
            } else {
                // Use defaults
                if (dValArray != null && dVal != null) {
                    selected = new String[dValArray.length + 1];
                    selected[0] = dVal;
                    System.arraycopy(dValArray, 0, selected, 1, dValArray.length);
                } else if (dValArray != null) {
                    selected = dValArray;
                } else if (dVal != null) {
                    selected = new String[] { dVal };
                }
            }

            if (
            		selected != null && 
            		selected.length > 1 && 
            		((attributes == null || !attributes.containsKey("multiple")) && !multiple)
            ) selected = null;

            // load up the selected values into a hash table for faster access
            // and for option tags to access
            // NB: the chosen Map may be modified by Option tags in this select
            // tag
            // so that only the first option is selected if the select is not a
            // multiple select
            chosen = new HashMap();
            if (selected != null) {
                for (int i = 0; i < selected.length; i++) {
                    if (selected[i] != null) {
                        chosen.put(selected[i], null);
                    }
                }
            }
/*
            if( isReadOnly ){	// READONLY �� ���. 
            	if (selected != null) {
                    for (int i = 0; i < selected.length; i++) {
                        if (selected[i] != null) {
                            strHtml += selected[i] + "&nbsp;";
                        }
                    }
                }else{
                	strHtml += "&nbsp;";
                }
            }
*/            
            // start building up the tag
			if ( getViewMode() != InputConstants.VIEW && getViewMode() != InputConstants.PRINT ) {
				
	            String strBeanValues = "";
				if (UEngineUtil.isNotEmpty(beanValues)) {
					for (String tmp : beanValues) {
						strBeanValues += tmp + ";";
					}
				}
				
				strHtml += "<select name=\"" + Util.quote(name) + "\" defaultValue=\"" + strBeanValues + "\"";

				// include any attributes we've got here
				strHtml += Util.getAttributesTag(attributes);
				if (attributesText != null) {
					strHtml += attributesText + " ";
				}

				if (multiple) {
					strHtml += "multiple=\"multiple\" ";
				}
				if (size != null) {
					strHtml += "size=\"" + Util.quote(size) + "\" ";
				}
			
				if ( getViewMode() == InputConstants.DISABLED ) strHtml += "disabled ";
				
				if( isReadOnly ){	// READONLY �� ���. 
					strHtml += "style='display:none;' ";
	            }
				
				// end the starting tag
				strHtml += ">";
			}
            
            // actually print the <option> tags
            if (optionLabels != null) {
                // Print out using the optionLabels
                int n = optionLabels.size();
                for (int i = 0; i < n; i++) {
                	// v�� ���ã�� �˾�� ����ڸ� �Է��Ͽ�; ���
                	// optionLabels, optionValues � ���� ������ �ʰ�
                	// XML ����� d���� �Ѿ��
                	// �ӽ÷� beanValues[i]�� �Ѿ�� XML�κ��� oLabel, oVal; ���ϰ�
                	// ��; ��=8�ν� VIEW ��忡�� ���ڰ� �ȳ��4� ���; �ذ�
                	String userId = null;
                	String userName = null;
                	String deptName = null;
                	try {
                		if ((beanValues[i]!=null)&&(beanValues[i].startsWith("<hanwha.bpm.form.attr.OrgVO"))) {
                			// XML���� XPATH�� ��� ��� f��� �� �����
                			userId = beanValues[i].substring(beanValues[i].indexOf("<userId>")+"<userId>".length(), beanValues[i].indexOf("</userId>"));
                			userName = beanValues[i].substring(beanValues[i].indexOf("<userName>")+"<userName>".length(), beanValues[i].indexOf("</userName>"));
                			deptName = beanValues[i].substring(beanValues[i].indexOf("<deptName>")+"<deptName>".length(), beanValues[i].indexOf("</deptName>"));
                		}
                	} catch (Exception ex) {
                		// Exception�� �߻��Ѵٸ� d������ SELECT �±׷�
                		// beanValues[i]�� XML�� �Ѿ���� ��: ����̹Ƿ� Exception�� �߻���
                	}
                	Object oLabel = null;
                	Object oVal = null;
                	if (userId==null) {
	                	oLabel = optionLabels.get(i);
	                    oVal = (options != null ? options.get(oLabel)
	                            : (optionValues != null ? optionValues.get(i)
	                                    : oLabel));
                	} else {
                		oLabel = userName + "(" + deptName + ")";
                		oVal = beanValues[i];
                	}

                	strHtml += outputOption(oLabel, oVal);
                }
            } else if (options != null) {
                Iterator i = options.keySet().iterator();
                while (i.hasNext()) {
                    Object oLabel = i.next();
                    Object oVal = options.get(oLabel);

                    strHtml += outputOption(oLabel, oVal);
                }
            }

            // doEndTag()�� ����; �Űܳ�=
            if ( getViewMode() != InputConstants.VIEW && getViewMode() != InputConstants.PRINT) {
            	strHtml += "</select>";

	            // Ajax���� readonly�� �Ǵ��ϱ� '�� �߰��� ����
	            if (isReadOnly) {
	            	strHtml += "<input type=\"hidden\" name=\"" + Util.quote(name) + "_readonly\" value=\"true\">";
	            }

	            if (isReadOnly && sbSelectedLabels != null ){
	            	strHtml += sbSelectedLabels.toString();
	            }
			}
			if ( getViewMode() == InputConstants.VIEW || getViewMode() == InputConstants.PRINT) {
				// VIEW��忡�� Ajax�� ����� ��� ��; �Ѹ��� ����
				try {
					String showValue = "";
					String showText = "";
					String ajaxValue = HttpUtils.getParameter(beanId, name+"_valuesForAjax", dVal, viewMode, pageContext)[0];
					String selValue = HttpUtils.getParameter(beanId, name, dVal, viewMode, pageContext)[0];
					if (UEngineUtil.isNotEmpty(ajaxValue)) {
						String[] ajaxValues = ajaxValue.split(";");
						for(int i=0; i<ajaxValues.length; i=i+2) {
							if (selValue.equals(ajaxValues[i])) {
								showValue = ajaxValues[i];
								showText = ajaxValues[i+1];
								break;
							}
						}
						strHtml += showText;
						out.println(strHtml);
						return EVAL_BODY_INCLUDE;
					}
				} catch (Exception e) {
					logger.error("doStartTag()", e); //$NON-NLS-1$
				}

				// SELECT Tag�� VIEW��带 '�� ó��
				if (optionLabels==null && beanValues != null ) {
					for(int i=0; i<beanValues.length; i++) {
						strHtml += beanValues[i].toString()+"&nbsp;";
					}
				}
			}
			out.println(strHtml);
			
        } catch (Exception ex) {
            throw new JspTagException(ex.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    private String outputOption(Object oLabel, Object oVal )
            throws java.io.IOException {
        String label = oLabel.toString();
        String strHtml = "";


		if ( getViewMode() != InputConstants.VIEW && getViewMode() != InputConstants.PRINT) {
	        /*
	         * Convert the value to a String if it is not already.
	         */
	        String value = (oVal != null ? oVal.toString() : null);
	
	        /* Output the option tag */
	        strHtml = "<option";
	        
	        
	        /* Output the value if there is one specified separate from the label */
	        if ( value != null ) {
	        	strHtml += " value=\"" + Util.quote(value) + "\"";
	        }
	        
	        /* 
	         * If there is no value specified then use the label as the value,
	         * this is for checking if this option is selected based on value.
	         */
	        if (value == null)
	            value = label; // use label if value is null
	        
	        /*
	         * This may look confusing: we match the VALUE of this option pair with
	         * the KEY of the 'chosen' Map (We want to match <option>s on values,
	         * not keys.)
	         */
	        if (chosen.containsKey(value)) {
//	            if (!multiple) {
//	                chosen.remove(value);
//	            }
	        	strHtml += " selected=\"selected\"";
	        }
	        strHtml += ">" + Util.quote(label) + "</option>";
		} 
		
		if ( getViewMode() == InputConstants.VIEW || getViewMode() == InputConstants.PRINT || isReadOnly ) {
			String value = (oVal != null ? oVal.toString() : null);
//			if ( value != null ) {
//				value = Util.quote(value);
//			}
			if (value == null) value = label;
			if (chosen.containsKey(value)) {
//				if (!multiple) {
//					chosen.remove(value);
//				}
				if (isReadOnly) {
					if ( sbSelectedLabels == null ) {
						sbSelectedLabels = new StringBuffer();
					}
					sbSelectedLabels.append(label);
				} else {
					strHtml += label;
				}
			}
		}

        return strHtml;
    }

    public int doEndTag() throws JspException {
//		try {
//		} catch (Exception ex) {
//			throw new JspTagException(ex.getMessage());
//		}
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

    public void setMultiple(boolean x) {
        multiple = x;
    }

    public void setSize(String x) {
        size = x;
    }

    public void setDefault(String x) {
        dVal = x;
    }

    public void setDefaults(String[] x) {
        dValArray = x;
    }

    public void setDefaults(Map x) {
        dValArray = new String[x.size()];
        Iterator it = x.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            dValArray[i++] = it.next().toString();
        }
    }

    public void setDefaults(Collection c) {
        dValArray = new String[c.size()];
        Iterator it = c.iterator();
        int i = 0;
        while (it.hasNext()) {
            dValArray[i++] = it.next().toString();
        }
    }

    public void setOptions(Map x) {
        options = x;
    }

    public void setOptionLabels(List x) {
        optionLabels = x;
    }

    public void setOptionValues(List x) {
        optionValues = x;
    }

    public HashMap getChosen() {
        return chosen;
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

    /**
     * Getter for property defaults.
     * 
     * @return Value of property defaults.
     */
    public String[] getDefaults() {
        return dValArray;
    }

    /**
     * Getter for property multiple.
     * 
     * @return Value of property multiple.
     */
    public boolean isMultiple() {
        return multiple;
    }

    /**
     * Getter for property optionLabels.
     * 
     * @return Value of property optionLabels.
     */
    public List getOptionLabels() {
        return optionLabels;
    }

    /**
     * Getter for property optionValues.
     * 
     * @return Value of property optionValues.
     */
    public List getOptionValues() {
        return optionValues;
    }

    /**
     * Getter for property options.
     * 
     * @return Value of property options.
     */
    public Map getOptions() {
        return options;
    }

    /**
     * Getter for property size.
     * 
     * @return Value of property size.
     */
    public String getSize() {
        return size;
    }

	public int getViewMode() {
		return viewMode;
	}

	public void setViewMode(int i) {
		viewMode = i;
	}

}