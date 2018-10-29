package org.uengine.ui.taglibs.input;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.RoleMapping;
import org.uengine.ui.list.util.HttpUtils;
import org.uengine.util.UEngineUtil;

/**
*
* @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
* @version $Id: FindUser.java,v 1.27 2011/07/22 07:33:16 curonide Exp $
*/
public class FindUser extends TagSupport {

    private String name; // name of the radio-button group

    private String value; // value of this particular button

    private String dVal; // default value if none is found

    private Map attributes; // attributes of the <input> element

    private String beanId; // bean id to get default values from

    private String defaultusername; // bean id to get default values from
    
    private String defaultuserid; // bean id to get default values from
    
    private boolean defaultusernameFlag = false;
    private boolean defaultuseridFlag = false;
    
    private boolean multiple;
    
    public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	private String extvalue1receiver;
    private String extvalue2receiver;
    private String extvalue3receiver;
    
	private int viewMode;

    public void release() {
        super.release();
        name = null;
        dVal = null;
        attributes = null;
        beanId = null;
    }

    private String getRoleMappingXML(String ids, String names){
    	StringBuffer genXML = new StringBuffer();
    	String[] defaultUserNameArray = names.split(";");
    	String[] defaultUserIdArray = ids.split(";");
    	String rmClsName = RoleMapping.USE_CLASS.getName();
    	
    	int chkCnt=0;
    	for(int i=0; i<defaultUserNameArray.length; i++){
    		if(chkCnt==0){
    			genXML.append("<"+rmClsName+">"); 
    			genXML.append("  <endpoint>"+defaultUserIdArray[i]+"</endpoint>");
    			genXML.append("  <resourceName>"+defaultUserNameArray[i]+"</resourceName>");								
    		}
    		if(chkCnt==1){
    			genXML.append("<multipleMappings>");
    			genXML.append("<"+rmClsName+ "  reference='../..'/>");
    		}
    		if(chkCnt>0){
    			genXML.append("<"+rmClsName+">");
    			genXML.append("  <endpoint>"+defaultUserIdArray[i]+"</endpoint>");
    			genXML.append("  <resourceName>"+defaultUserNameArray[i]+"</resourceName>");
    			genXML.append("</"+rmClsName+">");
    		}
    		chkCnt++;
    	}
    	if(chkCnt>1){
    		genXML.append("</multipleMappings>");
    		genXML.append("<dispatchingOption>-1</dispatchingOption>");
    	}
    	if(chkCnt>0){
    		genXML.append("</"+rmClsName+">");
    	}	
    	
    	return genXML.toString();
	}
    public int doStartTag() throws JspException {
        try {
        	JspWriter out = pageContext.getOut();
        	String genXML = "";
        	String userNames = "";
        	String userId = "";
        	
        	if(defaultusernameFlag == false || defaultuseridFlag == false){
        		defaultusername = "";
        		defaultuserid = "";
        	}
        	
        	Object[] values= HttpUtils.getParameterAsObject( beanId,  name,  dVal,  viewMode,  pageContext);
        	
			String[] beanValuesId = HttpUtils.getParameter(beanId, name, dVal, viewMode, pageContext);

			String[] beanValuesNname = HttpUtils.getParameter(beanId, name+"_display", dVal, viewMode, pageContext);
			
			for (int i = 0; i < values.length; i++) {
				String beanValueId="";
				String beanValueName="";
				if(values[i] instanceof RoleMapping){
					RoleMapping rm = (RoleMapping)values[i];
					 
					do {
						if (UEngineUtil.isNotEmpty(beanValueId)) {
							beanValueId += ";";  
							beanValueName += ";";  
						}
						beanValueId += rm.getEndpoint();
						beanValueName += rm.getResourceName();
					} while(rm.next());
					
				} else {
					beanValueId = beanValuesId[i];
					beanValueName = beanValuesNname[i];
				}
				
//				if(UEngineUtil.isNotEmpty(getDefaultusername())) {
//                    userNames = getDefaultusername();
//                    userId = getDefaultuserid();
//				} else {
//				    userNames = beanValueName;
//				    userId = beanValueId;
//				}
				
				if(beanValueId != null && !"".equals(beanValueId)){
					genXML = getRoleMappingXML(beanValueId, beanValueName );
					userId = beanValueId;
					userNames = beanValueName;
				}else if(!"".equals(getDefaultuserid())){
					genXML = getRoleMappingXML(defaultuserid, defaultusername );
					userId = getDefaultuserid();
					userNames = getDefaultusername();
				}else{
					genXML = "";
					userId = "";
					userNames = "";
				}
				
//				boolean isdefaultuser = UEngineUtil.isNotEmpty(defaultuserid);
//				
//		       	if(!isdefaultuser && !"".equals(userId)) {
//	        		defaultuserid   = userId;
//	        		defaultusername = userNames;
//		       	}
//		       	
//		       	genXML = getRoleMappingXML(defaultuserid,defaultusername );
//		       	
//		       	if(isdefaultuser) {
//		       		genXML = getRoleMappingXML(defaultuserid,defaultusername );
//                }else{
//                	genXML = beanValueId;
//                }
	 
				if (getViewmode() == InputConstants.VIEW || getViewmode() == InputConstants.PRINT) {
					out.print("<input type='hidden' name=\""+getName()+"__which_is_xml_value\" id=\""+getName()+"__which_is_xml_value\" value=\" "+genXML+" \">");
		    		out.print("<input type='hidden' name=\""+getName()+"\" id=\""+getName()+"\" value='"+userId+"'>");
		            out.print("<input type='hidden' name=\""+getName()+"_display\" id=\""+getName()+"_display\" value='"+userNames+"'>");

					out.print(userNames+" / "+beanValueId);
					return EVAL_PAGE;
				}

	    		out.print("<input type=hidden name=\""+getName()+"__which_is_xml_value\" id=\""+getName()+"__which_is_xml_value\" value=\" "+genXML+" \">");
	    		out.print("<input type=hidden name=\""+getName()+"\" id=\""+getName()+"\" value='"+userId+"'>");
	            out.print("<input readonly='readonly' name=\""+getName()+"_display\" id=\""+getName()+"_display\" size=20 value='"+userNames+"'>");
	    		out.print("<img name=\""+getName()+"\" style=\"cursor:pointer\" align=\"middle\" src=\""+GlobalContext.WEB_CONTEXT_ROOT + "/processmanager/images/Toolbar-toblock.gif\" onclick=\"searchPeopleObj(this, " + isMultiple() + ");\" >");

			}
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

	public String getExtvalue1receiver() {
		return extvalue1receiver;
	}

	public void setExtvalue1receiver(String extValue1Receiver) {
		this.extvalue1receiver = extValue1Receiver;
	}

	public String getExtvalue2receiver() {
		return extvalue2receiver;
	}

	public void setExtvalue2receiver(String extValue2Receiver) {
		this.extvalue2receiver = extValue2Receiver;
	}

	public String getExtvalue3receiver() {
		return extvalue3receiver;
	}

	public void setExtvalue3receiver(String extValue3Receiver) {
		this.extvalue3receiver = extValue3Receiver;
	}

	public String getDefaultusername() {
		return defaultusername;
	}

	public void setDefaultusername(String defaultusername) {
		this.defaultusernameFlag = true;
		this.defaultusername = defaultusername;
	}

	public String getDefaultuserid() {
		return defaultuserid;
	}

	public void setDefaultuserid(String defaultuserid) {
		this.defaultuseridFlag = true;
		this.defaultuserid = defaultuserid;
	}


}