package org.uengine.formmanager.trans;

import org.uengine.formmanager.html.*;
import org.uengine.kernel.GlobalContext;
import org.uengine.util.UEngineUtil;
import org.uengine.util.StreamUtils;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import org.apache.log4j.Logger;

public class Html2Print extends Html2FormView {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Html2Print.class);
	
	private OutputDocument outputDocument;
//	private AttrDAO attrDAO;
//	private BigDecimal formid;
	
	private int uniqueId = 0;
	
	/**
	 * input type="text"
	 * @param formField
	 * @return
	 */
	public String transTextField(FormField formField) throws Exception {
//		BigDecimal attrid
//			= attrDAO.insertAttribute(formField.getName(), formField.getName(), "", formid, "string", false);		
		
		FormFieldStringBuffer sb = new FormFieldStringBuffer();

		String value = "";
		
		Iterator attrIter = formField.getAttributeList().iterator();
		String objName= UEngineUtil.removeScriptletTag( formField.getName()+"_"+uniqueId++ );

		sb.append("\r\n");
		sb.append("<%");
		sb.append("\r\n");
		sb.append("java.util.Hashtable " + objName + " = new java.util.Hashtable();");
		sb.append("\r\n"); 
		Attribute attr;
		boolean isExistingAttrId = false;
		while ( attrIter.hasNext() ) {
			attr = (Attribute) attrIter.next();
			if ( attr.getName().equalsIgnoreCase("type") ) {
				;	
			} else if ( attr.getName().equalsIgnoreCase("name") ) {
				;
			} else if ( attr.getName().equalsIgnoreCase("value") ) {
				value = attr.getValue();
			} else if ( attr.getName().equalsIgnoreCase("attrid") ) {
				;
			} else {
				sb.append(objName + ".put(\"" + attr.getName() + "\", \"" + attr.getValue() + "\");");
				sb.append("\r\n");
			}
		}
//		sb.append(objName + ".put(\"" + "attrid" + "\", \"" + attrid.toString() + "\");");
		sb.append("\r\n");
		
		sb.append("%>");

		sb.append("\r\n");
		sb.append("<input:" + formField.getFormControlTypeId());
		sb.append("name", formField.getName());
		sb.append("attributes", "<%="+objName+"%>");
		sb.append("default", value);
		sb.append("viewMode", "<%=InputConstants.PRINT%>");
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
//		BigDecimal attrid
//			= attrDAO.insertAttribute(formField.getName(), formField.getName(), "", formid, "string", false);		

		FormFieldStringBuffer sb = new FormFieldStringBuffer();
		
		String value = "";
		
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
			} else if ( attr.getName().equalsIgnoreCase("value") ) {
				value = attr.getValue();
			} else if ( attr.getName().equalsIgnoreCase("attrid") ) {
				;
			} else {
				sb.append(objName + ".put(\"" + attr.getName() + "\", \"" + attr.getValue() + "\");");
				sb.append("\r\n");
			}
		}
//		sb.append(objName + ".put(\"" + "attrid" + "\", \"" + attrid.toString() + "\");");
		sb.append("\r\n");
		sb.append("%>");		
		
		sb.append("\r\n");
		sb.append("<input:" + formField.getFormControlTypeId());
		sb.append("name", formField.getName());
		sb.append("attributes", "<%="+objName+"%>");
		
		String defaultValue = formField.getContent();
		if (defaultValue == null ) defaultValue = "";
		
		sb.append("default", defaultValue);
		sb.append("viewMode", "<%=InputConstants.PRINT%>");
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
//		BigDecimal attrid = null;
//		if ( !isMultiple ) {
//			attrid = attrDAO.insertAttribute(formField.getName(), formField.getName(), "", formid, "string", false);		
//		} else {
//			attrid = attrDAO.insertAttribute(formField.getName(), formField.getName(), "", formid, "hanwha.bpm.form.attr.ArraySerializer", false);
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
		
		List attrList = formField.getAttributeList();
		if(attrList!=null)
		for(Iterator attrI = attrList.iterator(); attrI.hasNext(); ) {
			Attribute attr = (Attribute)attrI.next();
			if ( attr.getName().equals("taglib") ) {
				if ( attr.getValue() != null && attr.getValue().equals("true") ) {
					return formField.toString();
				} else if ( !attr.getName().equalsIgnoreCase("name") ) {	// select name �Ӽ��� ������ ��Ÿ �Ӽ� �߰�.
					sb.append(attributesObjName + ".put(\"" + attr.getName() + "\", \"" + attr.getValue() + "\");");
				}
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
//		sb.append(attributesObjName + ".put(\"" + "attrid" + "\", \"" + attrid.toString() + "\");");
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
		sb.append("attributes", "<%="+attributesObjName+"%>");
		sb.append("multiple", String.valueOf(isMultiple));
		sb.append("viewMode", "<%=InputConstants.PRINT%>");
		sb.append(" />");
		
		return sb.toString();
	}


	/**
	 * input type="radio"
	 * @param formField
	 * @return
	 */
	public String transRadioField(FormField formField) throws Exception {
//		BigDecimal attrid
//			= attrDAO.insertAttribute(formField.getName(), formField.getName(), "", formid, "string", false);		
		
		FormFieldStringBuffer sb = new FormFieldStringBuffer();

		String attributesObjName= UEngineUtil.removeScriptletTag( formField.getName()+"_"+uniqueId++ );
		sb.append("\r\n");
		sb.append("<%");
		sb.append("\r\n");
		sb.append("java.util.Hashtable " + attributesObjName + " = new java.util.Hashtable();");
		sb.append("\r\n");
				
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
			} else if ( attr.getName().equalsIgnoreCase("attrid") ) {
				;
			} else {
				sb.append(attributesObjName + ".put(\"" + attr.getName() + "\", \"" + attr.getValue() + "\");");
			}
		}

//		sb.append(attributesObjName + ".put(\"" + "attrid" + "\", \"" + attrid.toString() + "\");");
		sb.append("\r\n");		
		sb.append("%>");		

		sb.append("<input:" + formField.getFormControlTypeId());
		sb.append("name", formField.getName());

		sb.append("value", value);
		
		sb.append("attributes", "<%="+attributesObjName+"%>");
		
		if ( checked )
			sb.append("default", value);
		else 
			sb.append("default", "");
		
		sb.append("viewMode", "<%=InputConstants.PRINT%>");
		sb.append("/>");
		return sb.toString();
	}


	/**
	 * @param formField
	 * @return
	 */
	public String transCheckboxField(FormField formField) throws Exception {
//		BigDecimal attrid
//			= attrDAO.insertAttribute(formField.getName(), formField.getName(), "", formid, "hanwha.bpm.form.attr.ArraySerializer", false);
		
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
			} else {
				sb.append(attributesObjName + ".put(\"" + attr.getName() + "\", \"" + attr.getValue() + "\");");
			}
		}
		
//		sb.append(attributesObjName + ".put(\"" + "attrid" + "\", \"" + attrid.toString() + "\");");
		sb.append("\r\n");			
		sb.append("%>");
				
		
		sb.append("<input:checkbox");
		sb.append("name", formField.getName());
		
		sb.append("value", value);
		sb.append("attributes", "<%="+attributesObjName+"%>");
		if ( checkBoxMap.containsKey(formField.getName()) 
				&& valueList != null && valueList.size() > 0 ) {
			sb.append("defaults", "<%=" + objName + "%>");
		}
		sb.append("viewMode", "<%=InputConstants.PRINT%>");
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
	

	public void transformation(File file, BigDecimal formid) throws Exception {
		checkBoxMap = new HashMap();
		File parentDir = file.getParentFile();
		
		FileInputStream inputStream = new FileInputStream(file);
		
		String htmlText = StreamUtils.getString(inputStream);
		
//		StringReader strReader = new St
		
		
		outputDocument = new OutputDocument(htmlText);
		Source formFieldsSource = new Source(htmlText);
		
//		formFieldsSource.findAllElements()
		
		FormFields formFields = formFieldsSource.findFormFields();
		

		if (logger.isInfoEnabled()) {
			logger
					.info("transformation(File, BigDecimal) - formFields.size() : " + formFields.size()); //$NON-NLS-1$
		}
		
		for (Iterator i = formFields.iterator(); i.hasNext();) {
			FormField formField = (FormField) i.next();
			FormControlType type = formField.getFormControlType();
			String transStr = null;
			if ( type.equals(FormControlType.CHECKBOX) ) {
				preTransCheckBox(formField);
			}		
		}
		
		//  ����� ���۳�Ʈ  ó��.
		transCustComp(formFieldsSource);
		
//		attrDAO = new AttrDAO();
//		this.formid = formid;
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
			
			// Field Name ������ ���(�Ӽ��? "<" �Ǵ� ">" ����) Skip.
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
		
		writeFile(file);
	}
	
	public void writeFile(File file) throws FileNotFoundException {
		String fileName = file.getName();
		fileName = fileName.substring(0, fileName.lastIndexOf('.'));
		File transFile = new File(file.getParentFile(), fileName+"_print.jsp");
		OutputStreamWriter writer = null;
		FileOutputStream outputStream = new FileOutputStream(transFile); 
		try {
			writer = new OutputStreamWriter(outputStream, GlobalContext.ENCODING);
			writer.write(Header.getHeader()+replaceCustomExpression(outputDocument.toString()));
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
	
	private String replaceCustomExpression(String source) throws IOException {
		StringBuffer contents = new StringBuffer();
		String line="";
		BufferedReader in = new BufferedReader(new StringReader(source));
		while ( (line=in.readLine()) != null  ) {
//			System.out.println(line);
			if ( line.indexOf("formHeaderBean.get") > 0 || line.startsWith("formHeaderBean.get") ) {
				int idx = line.lastIndexOf("formHeaderBean.get");
				StringBuffer checkLine = new StringBuffer(line.substring(0, idx));
//				System.out.println(checkLine + ", " + checkLine.length());
				int startPos = 0;
				for ( int i = checkLine.length()-1 ; i >= 0 ; i--)  {		
//					System.out.println(i+" : " + checkLine.charAt(i));
					if ( checkLine.charAt(i) == '<' ) {
						startPos = i;
						break;
					}
				}
				if ( startPos != 0 ) {
					line = line.substring(0, startPos) + "<% formHeaderBean.setViewMode(1); %>" + line.subSequence(startPos, line.length());
				} else {
					line = "<% formHeaderBean.setViewMode(1); %>" + line;
				}
			}
//			System.out.println(line);
			contents.append(line);
			contents.append("\r\n");
		}		
		return contents.toString();
	}

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
		transCustComp(srcHTML, "bpm:freeContents");	// �������.
		transCustComp(srcHTML, "bpm:dtPopulator");	// �������̺�.
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
			
//			// Attribute �߰�.
//			String strSerializer = "string";
//			if( "bpm:formUserList".equals(strTagName) ){
//				strSerializer = "hanwha.bpm.form.attr.FormUserSerializer";
//			}else if( "bpm:fileUpload".equals(strTagName) ){
//				strSerializer = "hanwha.bpm.form.attr.AttachFileSerializer";
//			}else if( "bpm:freeContents".equals(strTagName) ){
//				strSerializer = "hanwha.bpm.form.attr.FreeContentsSerializer";
//			}
//			attrDF.insertAttribute(attrName.getValue(), attrName.getValue(), "", formVersionId, strSerializer, false);
			
			// VIEW -> PRINT ����.
			String strTrans = ("viewMode=\"<%=InputConstants.PRINT%>\"");
			if ( strTrans != null ) {
				outputDocument.add(new StringOutputSegment(attrViewMode, strTrans));
			}
		}
	}
}
