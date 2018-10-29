package org.uengine.processdesigner.inputters;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.uengine.contexts.FileContext;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariable;
import org.uengine.processmanager.ProcessDefinitionRemote;
import org.uengine.util.UEngineUtil;
import org.metaworks.FieldDescriptor;
import org.metaworks.inputter.*;
import org.metaworks.web.HTML;

import javax.swing.*;
/**
 * @author Jinyoung Jang
 */

public class org_uengine_contexts_FileContextInput extends ObjectInput{
	public org_uengine_contexts_FileContextInput(){
		super(FileContext.class);
	}

//	public Object getValue(){
//		JTextField tf = ((JTextField)getValueComponent());
//		String val = tf.getText();
//		
//		if(val!=null){
//			FileContext fctx = new FileContext();
//			fctx.setPath(val);
//				
//			return fctx;
//		}else
//			return null;
//	}
//	
//	public void setValue(Object val){
//		if(val!=null){
//			if(val instanceof FileContext)
//				val = ((FileContext)val).getPath();
//			
//			super.setValue(val);		
//		}else
//			super.setValue(null);	
//	}

	public Object createValueFromHTTPRequest(Map parameterValues, String section, String fieldName, Object oldValue) {
		String filePath = TextInput.createStringFromHTTPRequest(parameterValues, section, fieldName);
		
		FileContext fileContext = (oldValue!=null ? (FileContext)oldValue : new FileContext());
		fileContext.setPath(filePath);
		
		return fileContext;
	}

	public String getInputterHTML(String section, FieldDescriptor fd, Object defaultValue, Map options) {
		if(options==null) options = new HashMap();
		
		if(defaultValue!=null && defaultValue instanceof FileContext){
			FileContext defaultFileContext = (FileContext)defaultValue;
			
			ProcessDefinitionRemote pdr = (ProcessDefinitionRemote)options.get("definitionRemote");
			ProcessVariable[] variables = pdr.getProcessVariableDescriptors();
			for(int i=0; i<variables.length; i++){
				if(variables[i].getName().equals(fd.getName())) 
						defaultFileContext = (FileContext)variables[i].getDefaultValue();
			}
			
			ProcessInstance instance = (ProcessInstance)options.get("instance");
			
			if(defaultFileContext.isFtpFile()){
				StringBuffer inputterHTML = new StringBuffer();

				String defaultPath = defaultFileContext.getPath();
				
				if(!UEngineUtil.isNotEmpty(defaultPath))
					defaultPath = null;
				
				if(defaultPath!=null){
					if(     defaultPath.indexOf("<") > -1 
						|| 	defaultPath.indexOf(">") > -1
						|| 	defaultPath.indexOf("%") > -1
						|| 	defaultPath.indexOf("=") > -1
						) defaultPath = "";
				}else{
					if(instance != null)
						try {
							defaultPath = defaultFileContext.getFullSavingFTPURL(instance);
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
					
				
				inputterHTML
					.append("input type=button value='Open'")
					.append(" onclick=\"ftp_openFile('")
					.append(defaultPath)
					.append("','")
					.append(defaultFileContext.getFtpAddress())
					.append("','")
					.append(defaultFileContext.getFtpDirectory())
					.append("','")
					.append(defaultFileContext.getFtpUserId())
					.append("','")
					.append(defaultFileContext.getFtpPassword())
				;
				
				if(options.containsKey("instanceId"))
					inputterHTML
						.append("','")
						.append(options.get("instanceId"))
					;
				else
					inputterHTML.append("','null");
				
				if(options.containsKey("defVerId"))
					inputterHTML
						.append("','")
						.append(options.get("defVerId"))
					;
				else
					inputterHTML.append("','null");

				inputterHTML
					.append("','")
					.append(fd.getName())
				;

				inputterHTML.append("')\"");
				
//				inputterHTML = new StringBuffer(inputterHTML.toString().replaceAll("<","").replaceAll(">","").replaceAll("%",""));
				
				inputterHTML
					.insert(0, "<")
					.append(">")
				;
				
				try{
					StringBuffer hiddenInptter = new StringBuffer();
					hiddenInptter
						.append("input type=hidden")
						.append(HTML.getAttrHTML("name", createNameAttribute(section, fd.getName())))
						.append(" value='"+ (defaultPath!=null ? defaultPath : defaultFileContext.getFullSavingFTPURL(null)) +"'")
					;
					
//					hiddenInptter = new StringBuffer(hiddenInptter.toString().replaceAll("<","").replaceAll(">","").replaceAll("%","").replaceAll("=",""));
			
					hiddenInptter
						.insert(0, "<")
						.append(">")
					;
					
					inputterHTML.append(hiddenInptter);

				}catch(Exception e){
					new RuntimeException(e);
				}

				return inputterHTML.toString();
			}
		}
		
		StringBuffer inputterHTML = new StringBuffer();
		inputterHTML
			.append("<input type=file")
			.append(HTML.getAttrHTML("name", createNameAttribute(section, fd.getName())))
			.append(" onclick='changeEncType()'>")
		;
		
		return inputterHTML.toString();
	}

	public void addScriptTo(Properties scriptSet, String section, FieldDescriptor fd, Object defaultValue, Map options) {
		scriptSet.put(
			"ftp_openFile", 
			"<script>"+
				"function ftp_openFile(documentFile, ftpAddress, ftpDirectory, ftpUserId, ftpPassword, instanceId, defVerId, variableName){"+
					"location='"+ org.uengine.kernel.GlobalContext.WEB_CONTEXT_ROOT +"/wih/documentHandler/DocumentInvoker.jnlp?documentFile='+documentFile+'"+
					"&uploadFTPAddress='+ftpAddress+'"+
					"&uploadFTPDirectory='+ftpDirectory+'"+
					"&FTPid='+ftpUserId+'"+
					"&FTPpw='+ftpPassword+'"+
					"&instanceId='+instanceId+'"+
					"&defVerId='+defVerId+'"+
					"&variableName='+variableName+'"+
					
					"';"+
				"}"+
				
				"function ftp_openFileReadOnly(documentFile){"+
					"window.open(documentFile,'ftp_doc','width=1024,height=700')"+
				"}"+
			"</script>"
		);
				
		scriptSet.put(
			"changeEncType", 
			"<script>"+
				"function changeEncType(){"+
					"document.forms[0].encoding = 'multipart/form-data'"+
				"}"+
			"</script>"
		);
	}

	public String getViewerHTML(String section, FieldDescriptor fd, Object defaultValue, Map options) {
		if(defaultValue!=null && !"".equals(defaultValue)){
			FileContext fileContext = (FileContext)defaultValue;
			
			if(fileContext.isFtpFile()){
				StringBuffer viewerHTML = new StringBuffer();
				String defaultPath = fileContext.getPath();
				if(defaultPath==null)
					defaultPath = fileContext.getTemplateFilePath();

				viewerHTML
					.append("<input type=button value='View'")
					.append(" onclick=\"ftp_openFileReadOnly('")
					.append(defaultPath)
					.append("')\">")
				;
				
				return viewerHTML.toString();

			}else{
				File file = fileContext.getFile();
				if(file==null) return "No file";
				
				String fNameOnly = file.getName();
				String linkPath = fileContext.getPath();
				
				try {
					linkPath = URLEncoder.encode(linkPath, GlobalContext.ENCODING);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				return "<a href='"+GlobalContext.WEB_CONTEXT_ROOT+"/common/fileDownloader.jsp?filePath="+ linkPath + "'>"+fNameOnly+"</a>";
			}
		}else
			return "<not set>";
	}

	
}