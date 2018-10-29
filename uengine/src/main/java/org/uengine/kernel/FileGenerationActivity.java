package org.uengine.kernel;

import java.io.FileWriter;
import java.util.Map;

public class FileGenerationActivity extends DefaultActivity{
	
	String fileName;
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		
	String contents;
		public String getContents() {
			return contents;
		}
		public void setContents(String contents) {
			this.contents = contents;
		}
		
	Role fileReceiver;
		public Role getFileReceiver() {
			return fileReceiver;
		}
		public void setFileReceiver(Role fileReceiver) {
			this.fileReceiver = fileReceiver;
		}
	
	
	public FileGenerationActivity(){
		setName("file generation");
	}

	public void executeActivity(ProcessInstance instance) throws Exception {
		FileWriter fw = new FileWriter(getFileName());
		fw.write(getContents());
		fw.close();
	}
	
	public ValidationContext validate(Map options) {
		ValidationContext vc =  super.validate(options);
		
		if(getContents()==null){
			vc.addWarning("efewf");
		}
		
		return vc;
	}
	
	


}
