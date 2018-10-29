package org.uengine.kernel;

import java.io.*;

import org.uengine.contexts.FileContext;
import org.uengine.util.UEngineUtil;

/**
 * @author Jinyoung Jang
 */

public class FileGenerateActivity extends DefaultActivity{
	
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	public FileGenerateActivity(){
		super();
		setName("generate file");
	}
	
	public void executeActivity(ProcessInstance instance)
		throws Exception {
			
		String _filePath 	= evaluateContent(instance, getFilePath()).toString();
		String _content 	= evaluateContent(instance, getContent()).toString();
		
		FileWriter fw = new FileWriter(_filePath, isAppend());
		fw.write(_content);
		fw.close();
		
		fireComplete(instance);		
	}

	String content;
		public String getContent() {
			return content;
		}
		public void setContent(String string) {
			content = string;
		}

	String filePath;
		public String getFilePath() {
			return filePath;
		}
		public void setFilePath(String string) {
			filePath = string;
		}

	boolean append = false;
		public boolean isAppend() {
			return append;
		}
		public void setAppend(boolean b) {
			append = b;
		}

}