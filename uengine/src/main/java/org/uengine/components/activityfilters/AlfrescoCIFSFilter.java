package org.uengine.components.activityfilters;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.uengine.contexts.HtmlFormContext;
import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityFilter;
import org.uengine.kernel.FormActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessDefinitionFactory;
import org.uengine.kernel.ProcessInstance;
import org.uengine.util.UEngineUtil;

public class AlfrescoCIFSFilter implements ActivityFilter, Serializable{

	private String locate;
		public String getLocate() {
			return locate;
		}
		public void setLocate(String locate) {
			this.locate = locate;
		}

	public void afterComplete(Activity activity, ProcessInstance instance)
			throws Exception {
		if(activity instanceof FormActivity){
			FormActivity formActivity = (FormActivity)activity;
			
			String fileSystemPath = ProcessDefinitionFactory.DEFINITION_ROOT;
			Serializable value =formActivity.getVariableForHtmlFormContext().get(instance, "");
			
			BufferedInputStream bufferIs = null;
			FileOutputStream os = null;
			try{
				if(value instanceof HtmlFormContext){
					
					setLocate(getLocate()+File.separatorChar+instance.getName());
					File f = new File(getLocate());
					if(f.exists()==false)
						f.mkdir();
					
					HtmlFormContext formContext = (HtmlFormContext)value;
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS", Locale.KOREA);
					StringBuffer buff = new StringBuffer();
					buff.append(getLocate());
					buff.append("/");
					buff.append(activity.getName().getText());
					buff.append("_");
					buff.append(sdf.format(new Date()));
					buff.append(".html");
					
					UEngineUtil.copyToFile(fileSystemPath+formContext.getHtmlPath(), buff.toString());
				}
								
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(bufferIs != null){
					try{bufferIs.close();}catch(Exception e){}}
				
				if(os!=null){
					try{os.close();}catch(Exception e){}}
			}
		}
	}

	public void afterExecute(Activity activity, ProcessInstance instance)
			throws Exception {

		if(activity instanceof FormActivity){
			FormActivity formActivity = (FormActivity)activity;

			String defaultDrive = "x://";
			String defaultFolder = "uengine_inst";
			
			File f = null;
			
			//make directory
			String locate = defaultDrive+defaultFolder;
			locate+=File.separatorChar+instance.getProcessDefinition().getName().getText();
			f = new File(locate);
			if(f.exists()==false)
				f.mkdir();
			System.out.println(locate);
			setLocate(locate);
		}
		
	}

	public void beforeExecute(Activity activity, ProcessInstance instance)
			throws Exception {
	}

	public void onDeploy(ProcessDefinition definition) throws Exception {
	}

	public void onPropertyChange(Activity activity, ProcessInstance instance,
			String propertyName, Object changedValue) throws Exception {
	}

}
