package org.uengine.formmanager.trans;

import org.uengine.formmanager.html.*;
import org.uengine.kernel.GlobalContext;
import org.uengine.util.UEngineUtil;
import org.uengine.util.StreamUtils;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;
import org.apache.log4j.Logger;
import org.uengine.processmanager.ProcessTransactionContext;

/**
 * HTML to Write JSP ��ȯ ����
 *
 * @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
 * @version $Id: Html2Write.java,v 1.11 2011/07/22 07:33:19 curonide Exp $
 */
public class Html2Write {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Html2Write.class);
	
	private OutputDocument outputDocument;
//	private FormAttrDAOFacade attrDF;
	private BigDecimal formVersionId;
	
	private int uniqueId = 0;
	
	/**
	 * input type="text"
	 * @param formField
	 * @return
	 */
	public String transTextField(FormField formField) throws Exception {
//		Number attrid
//			= attrDF.insertAttribute(formField.getName(), formField.getName(), "", formVersionId, "string", false);		
		
		FormFieldStringBuffer sb = new FormFieldStringBuffer();
        String value = "";
        String strFormat = "";
        boolean isViewMode=false;
        Iterator attrIter = formField.getAttributeList().iterator();
        String objName = UEngineUtil.removeScriptletTag(formField.getName() + "_" + uniqueId++);
        sb.append(
        		"\r\n<%\r\n" +
        		"java.util.Hashtable " + objName + " = new java.util.Hashtable();" +
        		"\r\n"
        );
        
        boolean isExistingAttrId = false;
        while(attrIter.hasNext()) {
            Attribute attr = (Attribute)attrIter.next();
            
            if (	!attr.getName().equalsIgnoreCase("type") && !attr.getName().equalsIgnoreCase("name")) {
                if (attr.getName().equalsIgnoreCase("value")) {
                    value = attr.getValue();
                	
                } else if (attr.getName().equalsIgnoreCase("viewmode")) {
                	isViewMode = "0".equals(attr.getValue())? false: true ;
                	
                } else if (!attr.getName().equalsIgnoreCase("attrid")) {
                    sb.append(objName + ".put(\"" + attr.getName() + "\", \"" + attr.getValue() + "\");\r\n");
                }
            }
        }
        
        sb.append(objName + ".put(\"" + "attrid" + "\", \"" + "TEMP_ATTR_ID" + "\");");
        sb.append("\r\n%>\r\n<input:" + formField.getFormControlTypeId());
        sb.append("name", formField.getName());
        sb.append("viewMode", isViewMode ? "1" : "0");
        sb.append("attributes", "<%=" + objName + "%>");
        sb.append("default", value);
        sb.append("/>");
        return sb.toString();
	}

	/**
	 * input type="textarea"
	 * html2input
	 * @param formField
	 * @return
	 */
	public String transTextAreaField(FormField formField) throws Exception {
//		Number attrid
//			= attrDF.insertAttribute(formField.getName(), formField.getName(), "", formVersionId, "string", false);		

		FormFieldStringBuffer sb = new FormFieldStringBuffer();
		
		String value = "";
        boolean isViewMode=false;
		Iterator attrIter = formField.getAttributeList().iterator();
		String objName= UEngineUtil.removeScriptletTag( formField.getName()+"_"+uniqueId++ );

		sb.append("\r\n");
		sb.append("<%");
		sb.append("\r\n");
		sb.append("java.util.Hashtable " + objName + " = new java.util.Hashtable();");
		sb.append("\r\n"); 
		Attribute attr;
		while ( attrIter.hasNext() ) {
			attr = (Attribute) attrIter.next();
			if ( attr.getName().equalsIgnoreCase("type") ) {
				;	
			} else if ( attr.getName().equalsIgnoreCase("name") ) {
				;
			}else if(attr.getName().equalsIgnoreCase("viewmode")){
	            isViewMode = "0".equals(attr.getValue())? false: true ;
			} else if ( attr.getName().equalsIgnoreCase("value") ) {
				value = attr.getValue();
			} else if ( attr.getName().equalsIgnoreCase("attrid") ) {
				;
			} else {
				sb.append(objName + ".put(\"" + attr.getName() + "\", \"" + attr.getValue() + "\");");
				sb.append("\r\n");
			}
		}
		sb.append(objName + ".put(\"" + "attrid" + "\", \"" + "TEMP_ATTR_ID" + "\");");
		sb.append("\r\n");
		sb.append("%>");		
		
		sb.append("\r\n");
		sb.append("<input:" + formField.getFormControlTypeId());
		sb.append("name", formField.getName());
        sb.append("viewMode", isViewMode? "1":"0");   
		sb.append("attributes", "<%="+objName+"%>");
		
		String defaultValue = formField.getContent();
		if (defaultValue == null ) defaultValue = "";
		
		sb.append("default", defaultValue);
		sb.append("/>");
		return sb.toString();
	}



	/**
	 * 
<%
java.util.Hashtable o = new java.util.Hashtable();
o.put("one", "1");
o.put("two", "2");
o.put("three", "3"); 
%>
<input:select name="choice" default="2" attributes="<%= a %>" options="<%= o %>" multiple="false"/>
	 * @param formField
	 * @return
	 */
	public String transSelectField(FormField formField, boolean isMultiple) throws Exception {
//		Number attrid = null;
//		if ( !isMultiple ) {
//			attrid = attrDF.insertAttribute(formField.getName(), formField.getName(), "", formVersionId, "string", false);		
//		} else {
//			attrid = attrDF.insertAttribute(formField.getName(), formField.getName(), "", formVersionId, "hanwha.bpm.form.attr.ArraySerializer", false);
//		}

		FormFieldStringBuffer sb = new FormFieldStringBuffer();
		
		String attributesObjName= UEngineUtil.removeScriptletTag( formField.getName()+"_"+uniqueId++ );
		
		List selectedValueList = new ArrayList();
		
		sb.append("\r\n");
		sb.append("<%");
		sb.append("\r\n");
		sb.append("java.util.Hashtable " + attributesObjName + " = new java.util.Hashtable();");
		sb.append("\r\n");
		
		Segment segment = formField.getSegment();
        boolean isViewMode=false;
        
		List attrList = formField.getAttributeList();
		if(attrList!=null)
		for(Iterator attrI = attrList.iterator(); attrI.hasNext(); ) {
			Attribute attr = (Attribute)attrI.next();
			if ( attr.getName().equals("taglib") ) {
				if ( attr.getValue() != null && attr.getValue().equals("true") ) {
					return formField.toString();
				} else if ( !attr.getName().equalsIgnoreCase("name") ) {	// select name �Ӽ�; f���� ��Ÿ �Ӽ� �߰�.
					sb.append(attributesObjName + ".put(\"" + attr.getName() + "\", \"" + attr.getValue() + "\");");
				}
			} else if ( attr.getName().equalsIgnoreCase("attrid") ) {
				;
			} else if(attr.getName().equalsIgnoreCase("viewmode")){
	            isViewMode = "0".equals(attr.getValue())? false: true ;
			}else if ( attr.getName().equalsIgnoreCase("name") ) {
				;
			}else {
				sb.append(attributesObjName + ".put(\"" + attr.getName() + "\", \"" + attr.getValue() + "\");");
				sb.append("\r\n");
			}
		}
		
		List optionList = segment.findAllElements("option");
		String optionLabelsName = null; 
		String optionValuesName = null;
		if( optionList != null ){
			Iterator optionIter = optionList.iterator();
//			String optionObjName = formField.getName()+"_"+uniqueId++; 
			optionLabelsName = UEngineUtil.removeScriptletTag( formField.getName()+"_"+uniqueId++ ); 
			optionValuesName = UEngineUtil.removeScriptletTag( formField.getName()+"_"+uniqueId++ ); 			
//			sb.append("java.util.Hashtable " + optionObjName + " = new java.util.Hashtable();");
//			sb.append("\r\n"); 			
			sb.append("java.util.List " + optionLabelsName + " = new java.util.Vector();");
			sb.append("\r\n"); 
			sb.append("java.util.List " + optionValuesName + " = new java.util.Vector();");
			sb.append("\r\n"); 
			while ( optionIter.hasNext() ) {
				Element optionEl = (Element) optionIter.next();
				Attributes attribute = optionEl.getStartTag().getAttributes();
				String key = optionEl.getContentText();
				String valueText = attribute.get("value").getValue();
				if ( attribute.get("selected") != null ) selectedValueList.add(valueText);
//				System.out.println
				//String valueText = attribute.get("value").getValue();
				
//				sb.append(optionObjName + ".put(\"" + key + "\", \"" + valueText + "\");");
				sb.append(optionLabelsName + ".add(\"" + key + "\");");
				sb.append(optionValuesName + ".add(\"" + valueText + "\");");
				
				sb.append("\r\n");
			}
		}
		sb.append(attributesObjName + ".put(\"" + "attrid" + "\", \"" + "TEMP_ATTR_ID" + "\");");
		sb.append("\r\n");		
		sb.append("%>");
		
		String defaultValuesObjName = UEngineUtil.removeScriptletTag( formField.getName()+"_"+uniqueId++ ); 
		if ( selectedValueList.size() > 0 ) {
			String[] defaultSelectedValues = new String[selectedValueList.size()];
			selectedValueList.toArray(defaultSelectedValues);
			sb.append("\r\n");
			sb.append("<%");
			sb.append("\r\n");
			sb.append("String[] "+defaultValuesObjName+" = new String["+selectedValueList.size()+"];");
			for(int i=0; i<defaultSelectedValues.length; i++) {
				sb.append("\r\n");
				sb.append(defaultValuesObjName+"["+i+"] = \""+defaultSelectedValues[i]+"\";");
			}
			sb.append("\r\n");		
			sb.append("%>");
		}
		
		sb.append("\r\n");
		sb.append("<input:select");
		sb.append("name", formField.getName());
		if ( selectedValueList.size() > 0 ) {
			sb.append("defaults", "<%=" + defaultValuesObjName + "%>");
		}
//		sb.append("options", "<%=" + optionObjName + "%>");
		if( optionLabelsName != null ){
			sb.append("optionLabels", "<%=" + optionLabelsName + "%>");
		}
		if( optionValuesName != null ){
			sb.append("optionValues", "<%=" + optionValuesName + "%>");
		}
        sb.append("viewMode", isViewMode? "1":"0");  
		sb.append("attributes", "<%="+attributesObjName+"%>");
		sb.append("multiple", String.valueOf(isMultiple));
		sb.append(" />");
		
		return sb.toString();
	}


	/**
	 * input type="radio"
	 * @param formField
	 * @return
	 */
	public String transRadioField(FormField formField) throws Exception {
//		Number attrid
//			= attrDF.insertAttribute(formField.getName(), formField.getName(), "", formVersionId, "string", false);		
		
		FormFieldStringBuffer sb = new FormFieldStringBuffer();

		String attributesObjName= UEngineUtil.removeScriptletTag( formField.getName()+"_"+uniqueId++ );
		sb.append("\r\n");
		sb.append("<%");
		sb.append("\r\n");
		sb.append("java.util.Hashtable " + attributesObjName + " = new java.util.Hashtable();");
		sb.append("\r\n");
				
		String value = "";
		boolean checked = false;
		boolean isViewMode=false;
		Iterator attrIter = formField.getAttributeList().iterator();		
		Attribute attr;
		while ( attrIter.hasNext() ) {
			attr = (Attribute) attrIter.next();
			if ( attr.getName().equalsIgnoreCase("type") ) {
				;	
			} else if ( attr.getName().equalsIgnoreCase("name") ) {
				;
			} else if(attr.getName().equalsIgnoreCase("viewmode")){
	            isViewMode = "0".equals(attr.getValue())? false: true ;
			} else if ( attr.getName().equalsIgnoreCase("value") ) {
				value = attr.getValue();
			} else if ( attr.getName().equalsIgnoreCase("checked") ) {
				checked = true;
			} else if ( attr.getName().equalsIgnoreCase("attrid") ) {
				;
			} else {
				sb.append(attributesObjName + ".put(\"" + attr.getName() + "\", \"" + attr.getValue() + "\");");
			}
		}

		sb.append(attributesObjName + ".put(\"" + "attrid" + "\", \"" + "TEMP_ATTR_ID" + "\");");
		sb.append("\r\n");		
		sb.append("%>");		

		sb.append("<input:" + formField.getFormControlTypeId());
		sb.append("name", formField.getName());
        sb.append("viewMode", isViewMode? "1":"0"); 
		sb.append("value", value);
		
		sb.append("attributes", "<%="+attributesObjName+"%>");
		
		if ( checked )
			sb.append("default", value);
		else 
			sb.append("default", "");

		sb.append("/>");
		return sb.toString();
	}


	/**
	 * @param formField
	 * @return
	 */
	public String transCheckboxField(FormField formField) throws Exception {
//		Number attrid
//			= attrDF.insertAttribute(formField.getName(), formField.getName(), "", formVersionId, "hanwha.bpm.form.attr.ArraySerializer", false);
		
		FormFieldStringBuffer sb = new FormFieldStringBuffer();
		
		String attributesObjName= UEngineUtil.removeScriptletTag( formField.getName()+"_"+uniqueId++ );
		
		sb.append("\r\n");
		sb.append("<%");
		sb.append("java.util.Hashtable " + attributesObjName + " = new java.util.Hashtable();");
		sb.append("\r\n");			
		String objName= UEngineUtil.removeScriptletTag( formField.getName()+"_"+uniqueId++ );
		List valueList = null;
		if ( checkBoxMap.containsKey(formField.getName()) ) {
			//valueList = (List)checkBoxMap.get(objName);
			valueList = (List)checkBoxMap.get(formField.getName());
			if( valueList != null && valueList.size() > 0 ){
				sb.append("\r\n");
				sb.append("String[] " + objName + " = new String[" + valueList.size() + "];");
				sb.append("\r\n");
				Iterator iter=valueList.iterator();
				for (int selCount = 0; iter.hasNext(); selCount++) {
					sb.append(objName + "[" + selCount + "] = \"" + iter.next().toString() + "\";");
					sb.append("\r\n");
				}
			}
			//checkBoxMap.remove(formField.getName());
		}
		
		String value = "";
		boolean checked = false;
        boolean isViewMode=false;
		Iterator attrIter = formField.getAttributeList().iterator();		
		Attribute attr;
		while ( attrIter.hasNext() ) {
			attr = (Attribute) attrIter.next();
			if ( attr.getName().equalsIgnoreCase("type") ) {
				;	
			} else if ( attr.getName().equalsIgnoreCase("name") ) {
				;
			} else if ( attr.getName().equalsIgnoreCase("value") ) {
				value = attr.getValue();
			} else if ( attr.getName().equalsIgnoreCase("checked") ) {
				checked = true;
			} else if ( attr.getName().equalsIgnoreCase("attrid") ) {
				;
			} else if(attr.getName().equalsIgnoreCase("viewmode")){
	            isViewMode = "0".equals(attr.getValue())? false: true ;
			} else {
				sb.append(attributesObjName + ".put(\"" + attr.getName() + "\", \"" + attr.getValue() + "\");");
			}
		}
		
		sb.append(attributesObjName + ".put(\"" + "attrid" + "\", \"" + "TEMP_ATTR_ID" + "\");");
		sb.append("\r\n");			
		sb.append("%>");
				
		
		sb.append("<input:checkbox");
		sb.append("name", formField.getName());
        sb.append("viewMode", isViewMode? "1":"0");
		sb.append("value", value);
		sb.append("attributes", "<%="+attributesObjName+"%>");
		if ( checkBoxMap.containsKey(formField.getName()) 
				&& valueList != null && valueList.size() > 0 ) {
			sb.append("defaults", "<%=" + objName + "%>");
		}
		
		sb.append("/>");
		return sb.toString();
	}
	
	private Map checkBoxMap;
	/**
	 * @param formField
	 * @return
	 */
	public void preTransCheckBox(FormField formField) {
		String key = formField.getName();
		
		String value = "";
		boolean checked = false;
		Iterator attrIter = formField.getAttributeList().iterator();		
		Attribute attr;
		while ( attrIter.hasNext() ) {
			attr = (Attribute) attrIter.next();
			if ( attr.getName().equalsIgnoreCase("type") ) {
				;	
			} else if ( attr.getName().equalsIgnoreCase("name") ) {
				;
			} else if ( attr.getName().equalsIgnoreCase("value") ) {
				value = attr.getValue();
			} else if ( attr.getName().equalsIgnoreCase("checked") ) {
				checked = true;
			} else {
			}
		}
		if (checked) {
			if ( checkBoxMap.containsKey(key) ) {
				List valueList = (List)checkBoxMap.get(key);
				valueList.add(value);
			} else {
				List valueList = new ArrayList();
				valueList.add(value);
				checkBoxMap.put(key, valueList);
			}
		}
	}
	
//	/** �б���� �Ӽ� ID ����Ʈ */
//	private ArrayList readOnlyList;
//	
//	/**
//	 * �б���� �Ӽ� ����Ʈ ��d.
//	 * @param tmpAttrDAO �Ӽ� d�� ����Ʈ.
//	 */
//	public void presetReadOnlyList(FormAttrDAO tmpAttrDAO) throws Exception {
//		readOnlyList = new ArrayList();;
//		if( tmpAttrDAO != null ){
//			while(tmpAttrDAO.next()){
//				if( tmpAttrDAO.getIsReadOnly().booleanValue() ){
//					readOnlyList.add(tmpAttrDAO.getAttrId());
//				}
//			}
//			tmpAttrDAO.beforeFirst();
//		}
//	}
	
	public void transformation(File file, BigDecimal formVersionId) throws Exception {
		
//		presetReadOnlyList(tmpAttrDAO);	// �б���� �Ӽ� ����Ʈ ��d.
		
//		attrDF = FormAttrDAOFacade.getInstance(tc);

		checkBoxMap = new HashMap();
//		File parentDir = file.getParentFile();
		
		FileInputStream inputStream = new FileInputStream(file);
		
		String htmlText = StreamUtils.getString(inputStream);
		
		outputDocument = new OutputDocument(htmlText);
		Source formFieldsSource = new Source(htmlText);
				
		FormFields formFields = formFieldsSource.findFormFields();
		

		if (logger.isInfoEnabled()) {
			logger
					.info("transformation(File, BigDecimal, TransactionContext, FormAttrDAO) - formFields.size() : " + formFields.size()); //$NON-NLS-1$
		}
		
		for (Iterator i = formFields.iterator(); i.hasNext();) {
			FormField formField = (FormField) i.next();
			FormControlType type = formField.getFormControlType();
			String transStr = null;
			if ( type.equals(FormControlType.CHECKBOX) ) {
				preTransCheckBox(formField);
			}		
		}
		
		this.formVersionId = formVersionId;
		
		// ����� ���۳�Ʈ  ó��.
		if (logger.isInfoEnabled()) {
			logger
					.info("transformation(File, BigDecimal, TransactionContext, FormAttrDAO) - #######Html2Write : formVersionId=[" + formVersionId + "]"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		transCustComp(formFieldsSource);
		
//		for (Iterator i = formFields.iterator(); i.hasNext();) {
//			FormField formField = (FormField) i.next();
//			FormControlType type = formField.getFormControlType();
//
//			String transStr = null;
//			
//			if ( type.equals(FormControlType.BUTTON) ) {
//				;
//			} else if ( type.equals(FormControlType.CHECKBOX) ) {
//				attrDAO.insertAttribute(formField.getName(), formField.getName(), "", formid, "hanwha.bpm.form.attr.ArraySerializer", false);
//			} else if ( type.equals(FormControlType.FILE) ) {
//				;
//			} else if ( type.equals(FormControlType.HIDDEN) ) {
//				attrDAO.insertAttribute(formField.getName(), formField.getName(), "", formid, "string", false);
//			} else if ( type.equals(FormControlType.PASSWORD) ) {
//				attrDAO.insertAttribute(formField.getName(), formField.getName(), "", formid, "string", false);
//			} else if ( type.equals(FormControlType.RADIO) ) {
//				attrDAO.insertAttribute(formField.getName(), formField.getName(), "", formid, "string", false);
//			} else if ( type.equals(FormControlType.SELECT_MULTIPLE) ) {
//				attrDAO.insertAttribute(formField.getName(), formField.getName(), "", formid, "hanwha.bpm.form.attr.ArraySerializer", false);
//			} else if ( type.equals(FormControlType.SELECT_SINGLE) ) {
//				attrDAO.insertAttribute(formField.getName(), formField.getName(), "", formid, "string", false);
//			} else if ( type.equals(FormControlType.SUBMIT) ) {
//				;
//			} else if ( type.equals(FormControlType.TEXT) ) {
//				attrDAO.insertAttribute(formField.getName(), formField.getName(), "", formid, "string", false);
//			} else if ( type.equals(FormControlType.TEXTAREA) ) {
//				attrDAO.insertAttribute(formField.getName(), formField.getName(), "", formid, "string", false);
//			}
//		}


		for (Iterator i = formFields.iterator(); i.hasNext();) {
			FormField formField = (FormField) i.next();
			FormControlType type = formField.getFormControlType();
			String transStr = null;
			
			// Field Name �7��� ���(�Ӽ��? "<" �Ǵ� ">" ����) Skip.
			String fldName = formField.getName();
			if( fldName != null && ( fldName.indexOf("<") > -1 || fldName.indexOf(">") > -1 )){
				continue;
			}
			
			if ( type.equals(FormControlType.BUTTON) ) {
				;
			} else if ( type.equals(FormControlType.CHECKBOX) ) {
				transStr = transCheckboxField(formField);
			} else if ( type.equals(FormControlType.FILE) ) {
				;
			} else if ( type.equals(FormControlType.HIDDEN) ) {
				transStr = transTextField(formField);
			} else if ( type.equals(FormControlType.PASSWORD) ) {
				transStr = transTextField(formField);
			} else if ( type.equals(FormControlType.RADIO) ) {
				transStr = transRadioField(formField);
			} else if ( type.equals(FormControlType.SELECT_MULTIPLE) ) {
				transStr = transSelectField(formField, true);
			} else if ( type.equals(FormControlType.SELECT_SINGLE) ) {
				transStr = transSelectField(formField, false);
			} else if ( type.equals(FormControlType.SUBMIT) ) {
//				transStr = transSubmitField(formField);
			} else if ( type.equals(FormControlType.TEXT) ) {
				transStr = transTextField(formField);
			} else if ( type.equals(FormControlType.TEXTAREA) ) {
				transStr = transTextAreaField(formField);
			}
			
			if ( transStr != null ) {
				outputDocument.add(new StringOutputSegment(formField.getSegment(),
						transStr));
			}
		} // end of for()
//		System.out.println(outputDocument.toString());

//		Source tableTrSource = new Source(htmlText);
//		List tableTrList = tableTrSource.findAllStartTags("tr");
//		System.out.println("tableTrList.size() : " + tableTrList.size());
//		for (Iterator i = formFields.iterator(); i.hasNext();) {
//			StartTag trStartTag = (StartTag)i.next();
//			Attributes attributes = trStartTag.getAttributes();
////			attributes.get()
////			attributes.getList();
//		}
		
		//Form Attr Update. Valid Script d�� �ݿ�. �������� d�� �ݿ�.
//		while(tmpAttrDAO.next()) {
//			attrDF.updateFormAttrValidByName(tmpAttrDAO.getAttrName(), formVersionId, tmpAttrDAO.getValidationContents(), tmpAttrDAO.getValidationYN().booleanValue(), tmpAttrDAO.getIsBinding().booleanValue(), tmpAttrDAO.getIsReadOnly().booleanValue(), tmpAttrDAO.getSerializer());
//		}
		
		writeFile(file);
	}
	
	public void writeFile(File file) throws FileNotFoundException {
		String fileName = file.getName();
		fileName = fileName.substring(0, fileName.lastIndexOf('.'));
		File transFile = new File(file.getParentFile(), fileName+"_write.jsp");
		OutputStreamWriter writer = null;
		FileOutputStream outputStream = new FileOutputStream(transFile); 
		try {
			writer = new OutputStreamWriter(outputStream, GlobalContext.ENCODING);
			writer.write(Header.getHeader()+outputDocument.toString()+ getValidScript());
		} catch (IOException e) {
			logger.error("writeFile(File)", e); //$NON-NLS-1$
		} finally {
			try {
				if (writer != null) writer.close();
				if (outputStream != null) outputStream.close();
			} catch (IOException e) {
			}
		}	
	}
	
	public String getValidScript() {
		StringBuffer sbVSCall = new StringBuffer();
		StringBuffer sbVSOrg = new StringBuffer();
		StringBuffer sbVS = new StringBuffer();
		sbVS.append("\n").append("<script language=\"javascript\"> ")
			.append("\n").append("<!-- ")
			.append("\n").append("function valid_check(){ ");
		sbVS.append("\n").append(sbVSCall);
		sbVS.append("\n").append("	return true; ")
			.append("\n").append("} ");
		sbVS.append("\n").append(sbVSOrg);
		sbVS.append("\n").append("//--> ")
			.append("\n").append("</script> ");
		return sbVS.toString();
	}
/*	
	public static void main(String[] args) throws Exception {
		Html2Write html2bpm = new Html2Write();
		File file = new File("/home4/temp/form.htm");
//		html2bpm.transformation(file);
	}
*/	
	/**
	 * ��ü ����� ���۳�Ʈ ��ȯ.
	 * @param srcHTML HTML �ҽ�.
	 * @throws Exception If error occurs.
	 */
	public void transCustComp(Source srcHTML) throws Exception {
		transCustComp(srcHTML, "bpm:calendar");		// �޷�.
		transCustComp(srcHTML, "bpm:formUserList");	// ����� ã��.
		transCustComp(srcHTML, "bpm:searchManager");// ����ã��.
		transCustComp(srcHTML, "bpm:fileUpload");	// ����÷��.
		transCustComp(srcHTML, "bpm:freeContents");	// ��/���.
		//transCustComp(srcHTML, "bpm:dtPopulator");	// �������̺�.
		
		// ���� ÷������ ��ũ �ʵ� ��d �߰�.
//		attrDF.insertAttribute(BPMConstants.APPROVAL_ATTACHFILE_KEY
//								, BPMConstants.APPROVAL_ATTACHFILE_KEY
//								, ""
//								, formVersionId
//								, "hanwha.bpm.form.attr.AttachFileSerializer"
//								, false);
	}
	
	/**
	 * ����� ���۳�Ʈ ��ȯ.
	 * @param srcHTML HTML �ҽ�.
	 * @param strTagName ����� �±� ��.
	 * @throws Exception If error occurs.
	 */
	public void transCustComp(Source srcHTML, String strTagName) throws Exception {
		if( srcHTML == null || strTagName == null ){
			return;
		}
		
		List tags = srcHTML.findAllStartTags(strTagName);
		if( tags == null ){
			return;
		}
		for (Iterator i = tags.iterator(); i.hasNext();) {
			StartTag startTag = (StartTag)i.next();
			Attributes attributes = startTag.getAttributes();
			Attribute attrName = attributes.get("name");
			if ( attrName == null ){
				continue;
			}
			Attribute attrViewMode = attributes.get("viewMode");
			
			// Attribute �߰�.
			String strSerializer = "string";
			if( "bpm:formUserList".equals(strTagName) ){
				strSerializer = "hanwha.bpm.form.attr.FormUserSerializer";
			}else if( "bpm:fileUpload".equals(strTagName) ){
				strSerializer = "hanwha.bpm.form.attr.AttachFileSerializer";
			}else if( "bpm:freeContents".equals(strTagName) ){
				strSerializer = "hanwha.bpm.form.attr.FreeContentsSerializer";
//			}else if( "bpm:dtPopulator".equals(strTagName) ){
//				strSerializer = "hanwha.bpm.form.attr.DynamicTableSerializer";
			}
			
			// VIEW -> WRITE ����.
			String strTrans = null;
			
			// Field Name �7��� ���(�Ӽ��? "<" �Ǵ� ">" ����) Skip.
			String fldName = attrName.getValue();
			if( fldName != null && ( fldName.indexOf("<") > -1 || fldName.indexOf(">") > -1 )){
				// �ʵ弳d ��Ͽ� �߰� ���� ��=.
				// Readonly ��d �Ұ�.
				// VIEW -> WRITE ����.
				strTrans = ("viewMode=\"<%=InputConstants.WRITE%>\" ");
			}else{
//				Number attrid = attrDF.insertAttribute(attrName.getValue(), attrName.getValue(), "", formVersionId, strSerializer, false);
				
				// VIEW -> WRITE ���� & attrId �߰�.
				strTrans = ("viewMode=\"<%=InputConstants.WRITE%>\" attrid=\""+"TEMP_ATTR_ID"+"\" ");
			}
			
			if ( strTrans != null ) {
				outputDocument.add(new StringOutputSegment(attrViewMode, strTrans));
			}
		}
	}
}
