package org.uengine.kernel;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.util.UEngineUtil;
import org.uengine.util.ftp.Uploader;

/**
 * @author Jinyoung Jang
 */

public class FTPActivity extends DefaultActivity{
	
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	public FTPActivity(){
		super();
		setName("ftp upload");
	}
	
	public void executeActivity(ProcessInstance instance)
		throws Exception {
			
		String _sourceURL 	= evaluateContent(instance, getSourceURL()).toString();		
		String _address 	= evaluateContent(instance, getAddress()).toString();
		String _directory 	= evaluateContent(instance, getDirectory()).toString();
		String _account 	= evaluateContent(instance, getAccount()).toString();
		String _password 	= evaluateContent(instance, getPassword()).toString();
		String _fileName 	= evaluateContent(instance, getFileName()).toString();
		
		Uploader uploader = new Uploader();
		uploader.connect(_address, _account, _password);
		uploader.uploadFile(_sourceURL, _directory + "/" + _fileName);
		
		fireComplete(instance);		
	}

	String sourceURL;
	String address;
	String directory;
	String account;
	String password;
	String fileName;

	public String getAccount() {
		return account;
	}

	public String getAddress() {
		return address;
	}

	public String getDirectory() {
		return directory;
	}

	public String getFileName() {
		return fileName;
	}

	public String getPassword() {
		return password;
	}

	public String getSourceURL() {
		return sourceURL;
	}

	public void setAccount(String string) {
		account = string;
	}

	public void setAddress(String string) {
		address = string;
	}

	public void setDirectory(String string) {
		directory = string;
	}

	public void setFileName(String string) {
		fileName = string;
	}

	public void setPassword(String string) {
		password = string;
	}

	public void setSourceURL(String string) {
		sourceURL = string;
	}

}