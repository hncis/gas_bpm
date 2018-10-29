package org.uengine.kernel;

import java.util.Map;
import java.util.Vector;

import org.uengine.contexts.*;

/**
 * @author Jinyoung Jang
 */

public class DocumentActivity extends HumanActivity{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	FileContext documentFile;
		public FileContext getDocumentFile() {
			return documentFile;
		}
		public void setDocumentFile(FileContext value) {
			documentFile = value;
		}

	boolean useWebDav;
		public boolean isUseWebDav() {
			return useWebDav;
		}
		public void setUseWebDav(boolean b) {
			useWebDav = b;
		}
	
	//

	//Settings for document server.
	public String uploadFTPAddress = GlobalContext.getProperties().getProperty("excelsheetactivity.uploadftpaddress", "localhost"); //you should provide the actual IP addr.	
		public String getUploadFTPAddress() {
			return uploadFTPAddress;
		}
		public void setUploadFTPAddress(String uploadFTPaddress) {
			this.uploadFTPAddress = uploadFTPaddress;
		}
		
	public String uploadFTPDirectory = GlobalContext.getProperties().getProperty("excelsheetactivity.uploadftpdirectory", "uengine_upload");
		public String getUploadFTPDirectory() {
			return uploadFTPDirectory;
		}
		public void setUploadFTPDirectory(String uploadFTPDirectory) {
			this.uploadFTPDirectory = uploadFTPDirectory;
		}
		
	public String FTPid = GlobalContext.getProperties().getProperty("excelsheetactivity.ftpid", "uengine");
		public String getFTPid() {
			return FTPid;
		}
		public void setFTPid(String fTPid) {
			FTPid = fTPid;
		}

	public String FTPpw = GlobalContext.getProperties().getProperty("excelsheetactivity.ftppw", "1234");
		public String getFTPpw() {
			return FTPpw;
		}
		public void setFTPpw(String fTPpw) {
			FTPpw = fTPpw;
		}

		
	//fix the tool setting		
	public String getTool(){
		return "documentHandler";
	}		
	public void setTool(String value){
	}
	//
	
	public DocumentActivity(){
		super();
		setName("document activity");
	}

/*	protected String getWIHAddress(ActivityInstance instance) throws Exception{
		String address = 
			"http://" + getWIHSysAddress() + "/html/uengine-web/wih/"		 
			 + getTool() 
			 + "?message=onHumanActivityResult" + getTracingTag() + "&instanceId=<%=Instance.InstanceId%>&documentFile=";
			 
		if(getDocumentFile()!=null)
			 address = address + getDocumentFile().getPath();
			 
		return address;			 			
	}*/

	public ValidationContext validate(Map options) {
		ValidationContext vc = super.validate(options);
		
		FileContext docFile = getDocumentFile();
/*		if(docFile!=null){
			try{
				ActivityInstance.USE_EJB = false;
				ComplexActivity.USE_JMS = false;
				ComplexActivity.USE_THREAD = false;
						
				ActivityInstance instance 
					= ActivityInstance.create(getProcessDefinition(), "test instance");
				
				EMailActivity.evaluateContent(docFile.getPath(), this, instance, vc);
			}catch(Exception e){}
		}*/
		
		if(docFile==null)
			vc.add(getActivityLabel() + " Document file is not specified");
		
		return vc;
	}

	public Map createParameter(ProcessInstance instance) throws Exception{
		Map parameters = super.createParameter(instance);
		
		if(getDocumentFile()!=null)
			parameters.put("documentFile", getDocumentFile().getPath());
			
		if(isUseWebDav())
			parameters.put(KeyedParameter.TOOL, "webDavDocumentHandler");

		parameters.put("FTPid", getFTPid());
		parameters.put("FTPpw", getFTPpw());
		parameters.put("uploadFTPAddress", getUploadFTPAddress());
		parameters.put("uploadFTPDirectory", getUploadFTPDirectory());
			
		return parameters;
	}

}