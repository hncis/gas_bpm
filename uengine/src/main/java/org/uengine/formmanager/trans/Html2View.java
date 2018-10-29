package org.uengine.formmanager.trans;

import org.uengine.formmanager.html.*;
import org.uengine.kernel.GlobalContext;
import org.uengine.util.StreamUtils;
import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;
import org.uengine.util.UEngineUtil;


/**
 * Not use.
 * HTML to View JSP ��ȯ ����
 *
 * @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
 * @version $Id: Html2View.java,v 1.4 2011/07/22 07:33:19 curonide Exp $
 */
public class Html2View {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Html2View.class);
	
	private OutputDocument outputDocument;
	private Source source;
	private FormFields formFields;
	
	private int count = 0;

	/**
	 * input type="text"
	 * @param formField
	 * @return
	 */
	public String transTextField(FormField formField) {
		FormFieldStringBuffer sb = new FormFieldStringBuffer();
		String value = "";
		sb.append("<%=formInstance.getParameter(\""+formField.getName()+"\")%>");
		return sb.toString();
	}

	/**
	 * input type="textarea"
	 * html2input
	 * @param formField
	 * @return
	 */
	public String transTextAreaField(FormField formField) {
		return transTextField(formField);
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
			FormFieldStringBuffer sb = new FormFieldStringBuffer();
			String value = "";
			sb.append("<%=formInstance.getParameterValues(\""+formField.getName()+"\").toString()%>");
			return sb.toString();			
		}
	}


	/**
	 * input type="radio"
	 * @param formField
	 * @return
	 */
	public String transRadioField(FormField formField) {
		FormFieldStringBuffer sb = new FormFieldStringBuffer();
		
		sb.append("<%");
		sb.append("String " + formField.getName() + "= formInstance.getParameter(\""+formField.getName()+"\");");
		sb.append("%>");
		
		sb.append("<input:" + formField.getFormControlTypeId());
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
		if ( checked )
			sb.append("default", value);
		else 
			sb.append("default", "");

		sb.append("/>");
		return sb.toString();
	}


	/**
	 * html2struts
	 * @param formField
	 * @return
	 */
	public String transCheckboxField(FormField formField) {
		FormFieldStringBuffer sb = new FormFieldStringBuffer();
		
		String objName= formField.getName();
		sb.append("<%");
		sb.append("\r\n");
		sb.append("String[] " + objName + " = formInstance.getParameterArrayValues(\""+formField.getName()+"\");");
		sb.append("\r\n");
		sb.append("%>");

		
		sb.append("<input:checkbox");
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
		
		sb.append("defaults", "<%=" + objName + "%>");
		
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
	

	public void transformation(File file) throws Exception {
		checkBoxMap = new HashMap();
		File parentDir = file.getParentFile();
		
		FileInputStream inputStream = new FileInputStream(file);
		
		String htmlText = StreamUtils.getString(inputStream);
		
		outputDocument = new OutputDocument(htmlText);
		source = new Source(htmlText);
		formFields = source.findFormFields();
		

		if (logger.isInfoEnabled()) {
			logger
					.info("transformation(File) - formFields.size() : " + formFields.size()); //$NON-NLS-1$
		}
		
//		for (Iterator i = formFields.iterator(); i.hasNext();) {
//			FormField formField = (FormField) i.next();
//			FormControlType type = formField.getFormControlType();
//			String transStr = null;
//			if ( type.equals(FormControlType.CHECKBOX) ) {
//				preTransCheckBox(formField);
//			}		
//		}

		for (Iterator i = formFields.iterator(); i.hasNext();) {
			FormField formField = (FormField) i.next();
//			System.out.println("-----------------------------");
//			System.out.println("getName : " + formField.getName());
//			System.out.println("getPredefinedValues : " + formField.getPredefinedValues());
//			System.out.println("getUserValueCount : " + formField.getUserValueCount());
//			System.out.println("getSize : " + formField.getSize());
//			System.out.println("getRows : " + formField.getRows());
//			System.out.println("getCols : " + formField.getCols());
//			System.out.println("getContent : " + formField.getContent());
//			System.out.println("getFormControlTypeId : " + formField.getFormControlTypeId());
			FormControlType type = formField.getFormControlType();
//			if ( type != null ) {
//				System.out.println("allowsMultipleValues : " + type.allowsMultipleValues());
//				System.out.println("isPredefinedValue : " + type.isPredefinedValue());
//				System.out.println("isSubmit : " + type.isSubmit());
//				System.out.println("getFormControlTypeId : " + type.getFormControlTypeId());
//			}

			String transStr = null;
			
			if ( type.equals(FormControlType.BUTTON) ) {
				;
			} else if ( type.equals(FormControlType.CHECKBOX) ) {
				transStr = transCheckboxField(formField);
			} else if ( type.equals(FormControlType.FILE) ) {
				;
			} else if ( type.equals(FormControlType.HIDDEN) ) {
				transStr = transTextField(formField);
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
			
//			System.out.println("-----------------------------");
//			System.out.println("----------------------------- + Segment :" + formField.getSegment().getSourceText());
			
//			if ( formField.getName().equals("Goals")) {
//				System.out.println("***************************** : " + formField.getSegment());
//				outputDocument.add(new StringOutputSegment(formField.getSegment(),
//						"<link rel=\"stylesheet\" type=\"text/css\" href=\"mainddddd.css\" />"));
//			}
		} // end of for()
//		System.out.println(outputDocument.toString());

		writeFile(file);
	}
	
	public void writeFile(File file) throws IOException {
		String fileName = file.getName();
		fileName = fileName.substring(0, fileName.lastIndexOf('.'));
		File transFile = new File(file.getParentFile(), fileName+"_view.jsp");
		OutputStreamWriter writer = null;
		FileOutputStream outputStream = new FileOutputStream(transFile); 
		try {
			writer = new OutputStreamWriter(outputStream, GlobalContext.ENCODING);
			writer.write(Header.getHeader()+outputDocument.toString());
		} catch (IOException e) {
			logger.error("writeFile(File)", e); //$NON-NLS-1$
		} finally {
			if ( writer != null ) {
				try {
					if (writer != null) writer.close();
					if (outputStream != null) outputStream.close();
				} catch (IOException e) {
					logger.error("viewFile(File)", e); //$NON-NLS-1$
				}
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


	public static void main(String[] args) throws Exception {
		Html2View html2bpm = new Html2View();
		File file = new File("/home4/temp/form.htm");
		html2bpm.transformation(file);
		html2bpm.getFormFields();
	}
	
	
}