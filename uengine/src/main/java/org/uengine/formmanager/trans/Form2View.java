package org.uengine.formmanager.trans;

import org.metaworks.web.WebUtil;
import org.uengine.formmanager.html.*;
import org.uengine.kernel.GlobalContext;
import org.uengine.util.StreamUtils;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class Form2View {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Form2View.class);
	
	private OutputDocument outputDocument;
	private Source source;
	private FormFields formFields;
	private HttpServletRequest request;
	
	private int count = 0;
	
	private String getParameter(String string) {
		String param = request.getParameter(string);
		if ( param == null || param.length() == 0 ) return "&nbsp;";
		
		if(WebUtil.webStringDecoder!=null)
			return WebUtil.webStringDecoder.decode(param);
		
		return param;
	}


	/**
	 * input type="text"
	 * @param formField
	 * @return
	 */
	public String transTextField(FormField formField) {
		return getParameter(formField.getName());
//		StringBuffer sb = new StringBuffer();
//		sb.append(getParameter(formField.getName()));
//		return sb.toString();
	}

	/**
	 * input type="textarea"
	 * html2input
	 * @param formField
	 * @return
	 */
	public String transTextAreaField(FormField formField) {
		return transTextField(formField).replaceAll("\r\n", "<br>");
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
	public String transSelectField(FormField formField, boolean isMultiple) {
		if ( !isMultiple ) {
			return transTextField(formField);
		} else {
			StringBuffer sb = new StringBuffer();
			String values[] = request.getParameterValues(formField.getName());
			for(int i=0; i<values.length; i++) {
				sb.append(values[i]);
				if (i != (values.length-1)) sb.append(",");
			}
			return sb.toString();
		}
	}


	/**
	 * input type="radio"
	 * @param formField
	 * @return
	 */
	public String transRadioField(FormField formField) {
		String inputValue = getParameter(formField.getName());

//		<input type="radio" name="radio" value="a" checked="checked" />a 
//		<input type="radio" name="radio" value="b" />b
//		<input type="radio" name="radio" value="c" />c
//		<input type="radio" name="radio" value="d" />d
		
		FormFieldStringBuffer sb = new FormFieldStringBuffer();
		
		sb.append("<input");
		sb.append("type", "radio");
		sb.append("name", formField.getName());
				
		String value = "";
//		boolean checked = false;
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
//				checked = true;
			} else {
			}
		}
		sb.append("value", value);
		if ( value != null && value.equals(inputValue) ) {
			sb.append("checked", "checked");
		}
		

		sb.append("/>");
		return sb.toString();
	}


	/**
	 * html2struts
	 * @param formField
	 * @return
	 */
	public String transCheckboxField(FormField formField) {
		String inputValues[] = request.getParameterValues(formField.getName());
		
		FormFieldStringBuffer sb = new FormFieldStringBuffer();

//		<input type="checkbox" name="checkbox" value="2" checked="checked" />2
		
		String objName= formField.getName();
		
		sb.append("<input");
		sb.append("type", "checkbox");
		sb.append("name", formField.getName());

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
		sb.append("value", value);
		
		if ( containsValue(inputValues, value) ) {
			sb.append("checked", "checked");
		}
		
		sb.append("/>");
		return sb.toString();
	}
	
	private boolean containsValue(String inputValues[], String value) {
		if ( inputValues == null || value == null ) return false;
		for(int i=0; i<inputValues.length; i++) {
			if ( inputValues[i].equals(value) ) return true;
		}
		return false;
	}
	

	public void transformation(File file) throws Exception {
		File parentDir = file.getParentFile();
		
		FileInputStream inputStream = new FileInputStream(file);
		
		String htmlText = StreamUtils.getString(inputStream);
		
		transDocument(htmlText);

		writeFile(file);
	}

	private void writeFile(File file) throws IOException {
		String fileName = file.getName();
		fileName = fileName.substring(0, fileName.lastIndexOf('.'));
		File transFile = new File(file.getParentFile(), fileName+"_view.htm");
		OutputStreamWriter writer = null;
		FileOutputStream outputStream = new FileOutputStream(transFile); 
		try {
			writer = new OutputStreamWriter(outputStream, GlobalContext.ENCODING);
			writer.write(outputDocument.toString());
		} catch (IOException e) {
			logger.error("writeFile(File)", e); //$NON-NLS-1$
		} finally {
			try {
				if (writer != null) writer.close();
				if (outputStream != null) outputStream.close();
			} catch (IOException e) {
					logger.error("writeFile(File)", e); //$NON-NLS-1$
			}
		}		
	}
	
	public String transformation(HttpServletRequest request, String htmlText) throws Exception {
		this.request = request;
		transDocument(htmlText);
		return outputDocument.toString();
	}


	private void transDocument(String htmlText) {
		outputDocument = new OutputDocument(htmlText);
		source = new Source(htmlText);
		
		// ���� �κ��� �Ⱥ��̰� ��
		List divStartTags = source.findAllStartTags("div");
		
		if(divStartTags!=null)
		for (Iterator i = divStartTags.iterator(); i.hasNext();) {
			StartTag divStartTag = (StartTag)i.next();
			String divTagStr = divStartTag.getSourceText();
				if (logger.isInfoEnabled()) {
					logger
							.info("transDocument(String) - ::::divTagStr : " + divTagStr); //$NON-NLS-1$
				}
			if ( divTagStr.indexOf("helpMessage") > 0 ) {
				divTagStr = divTagStr.replaceFirst("block", "none");
				outputDocument.add(new StringOutputSegment(divStartTag, divTagStr));				
			}
		}
		
		formFields = source.findFormFields();
		

		if (logger.isInfoEnabled()) {
			logger
					.info("transDocument(String) - formFields.size() : " + formFields.size()); //$NON-NLS-1$
		}
		
		if(formFields!=null)
		for (Iterator i = formFields.iterator(); i.hasNext();) {
			FormField formField = (FormField) i.next();
			FormControlType type = formField.getFormControlType();

			String transStr = null;
			
			if ( type.equals(FormControlType.BUTTON) ) {
				;
			} else if ( type.equals(FormControlType.CHECKBOX) ) {
				transStr = transCheckboxField(formField);
			} else if ( type.equals(FormControlType.FILE) ) {
				;
			} else if ( type.equals(FormControlType.HIDDEN) ) {
//				transStr = transTextField(formField);
				//transStr =  transField(formField);
			} else if ( type.equals(FormControlType.PASSWORD) ) {
				transStr = transTextField(formField);
				//transStr = transField(formField);
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
			
		} 
	}
	


	/**
	 * @return
	 */
	public FormFields getFormFields() {
		return formFields;
	}

	/**
	 * @param fields
	 */
	public void setFormFields(FormFields fields) {
		formFields = fields;
	}

}
