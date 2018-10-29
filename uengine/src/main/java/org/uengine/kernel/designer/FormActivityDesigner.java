/*
 * Created on 2004. 12. 18.
 */
package org.uengine.kernel.designer;

import javax.swing.*;

import org.metaworks.FieldDescriptor;
import org.metaworks.InputForm;
import org.metaworks.Instance;
import org.metaworks.Type;
import org.uengine.contexts.HtmlFormContext;
import org.uengine.kernel.FormActivity;
import org.uengine.kernel.GlobalContext;
import org.uengine.processdesigner.ProcessDesigner;
import org.uengine.processdesigner.ProgressDialog;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.util.ForLoop;
import org.uengine.util.SystemLauncher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jinyoung Jang
 */
public class FormActivityDesigner extends HumanActivityDesigner{
	
	OpenedForm openedForm;
	
	static HashMap openedForms = new HashMap();
	static WindowFocusListener windowsFocusListener;
	
	public FormActivityDesigner(){
		super();
		
		//final JPopupMenu popup = new JPopupMenu();
		JMenuItem edit = new JMenuItem("Edit form with system editor");
		JMenuItem previewForm = new JMenuItem("Preview Form");
		edit.setName("edit");
		previewForm.setName("preview");
				
		edit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				SystemLauncher launcher = new SystemLauncher(){
					public void completed(){
					}
					
					public void write(String line){
						//Do nothing....
					}
				};
				
				
				
				FormActivity formActivity = (FormActivity) getActivity();
				HtmlFormContext formContext = (HtmlFormContext) formActivity.getVariableForHtmlFormContext().getDefaultValue();
				
				String formDefID[] = formContext.getFormDefId().split("@");


				openedForm = new OpenedForm();
				openedForm.formDefVerId = formDefID[1];
				openedForm.formName = formDefID[0].replaceAll("[\\[||\\]]","");
				openedForm.launcher = launcher; //may result huge memory resident in the static variable "openedForms"
				
				String fileName = openedForm.formName + ".jsp";
				String tempFilePath = System.getProperty("temp.path", "C:\\uengine\\");

				File newFile = new File(tempFilePath);
				if(!newFile.exists()){
					newFile.mkdirs();
				}
				
				tempFilePath = tempFilePath + File.separatorChar + fileName;

				openedForm.localFilePath = tempFilePath;
		
				InputStream is;
				try {
					is = ProcessDesigner.getInstance().getClientProxy().showFormDefinitionWithVersionId(formDefID[1]);
					File outFile = new File(tempFilePath);
					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile));
					/*if(is!=null){
						byte [] buf = new byte[1024];
						int len;
						while ((len = is.read(buf)) > 0) {
							bos.write(buf, 0, len);
						}
						is.close();
			      	}
					bos.close();*/
					
					if(is!=null)
						org.uengine.util.UEngineUtil.copyStream(is, bos);
					
					openedForm.lastModified = outFile.lastModified();
					//openedForm.
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				String tool = System.getProperty("form.editor", "notepad");
				
				//review: this command will work only in Windows
				launcher.run(
					"cmd /c \"" + tool + "\" " + tempFilePath
				);
				
				openedForms.put(openedForm.formName, openedForm);
				
				// keep only one window listener for all the form designers there
				if(windowsFocusListener==null){
					windowsFocusListener = new WindowFocusListener(){

							public void windowGainedFocus(WindowEvent e) {
								
								try {

									final Type deployOrNot = new Type("Select forms to deploy");
									final Instance deployInstance = deployOrNot.createInstance();

									new ForLoop(){

										public void logic(Object target) {
											OpenedForm openedForm = (OpenedForm) target;
											
											File file = new File(openedForm.localFilePath);
											if(openedForm.lastModified != file.lastModified()){
												deployOrNot.addFieldDescriptor(new FieldDescriptor(openedForm.formName, Boolean.class, null, null));
												deployInstance.setFieldValue(openedForm.formName, true);
												openedForm.lastModified = file.lastModified();
											}
										}
										
									}.run(openedForms);
									
									if(deployOrNot.getFieldDescriptors()==null || deployOrNot.getFieldDescriptors().length == 0) return;
									
									InputForm inputForm = new InputForm(deployOrNot){
										public void onSaveOK(final Instance rec, JDialog dialog) {

											dialog.dispose();
											
											SwingUtilities.invokeLater(new Runnable(){

												public void run() {
													new ProgressDialog("Deploy", ProcessDesigner.getInstance()){

														public void run() throws Exception {
															FieldDescriptor[] fds = deployOrNot.getFieldDescriptors();

															for(int i=0; i<deployOrNot.getFieldDescriptors().length; i++){
																
																String fieldName = fds[i].getName();
																Boolean value = (Boolean)rec.getFieldValue(fieldName);
																if(value.booleanValue()){
																	OpenedForm of = (OpenedForm) openedForms.get(fieldName);
																	deployForm(of);
																}
															}
														}
														
													}.show();
												}
												
											});
											
										}

										public void onUpdateOK(Instance rec, JDialog dialog) {
											onUpdateOK(rec, dialog);
										}
									};
									
									inputForm.setInstance(deployInstance);
									
									inputForm.postInputDialog(ProcessDesigner.getInstance(), "Deploy", "Deploy");
									
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}

							public void windowLostFocus(WindowEvent e) {
								
							}

					};
					
					ProcessDesigner.getInstance().addWindowFocusListener(windowsFocusListener);
				}
				
			}
			
		});
		
		previewForm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				FormActivity formActivity = (FormActivity) getActivity();
				HtmlFormContext formContext = (HtmlFormContext) formActivity.getVariableForHtmlFormContext().getDefaultValue();
				String[] formDefID = formContext.getFormDefId().split("@");
				String host = ProcessDesigner.getInstance().getClientProxy().getHttpClient().getHost();
				int port = ProcessDesigner.getInstance().getClientProxy().getHttpClient().getPort();
				ProcessDesigner.getInstance().openNativeBrowser("http://" + host + ":" + port + GlobalContext.WEB_CONTEXT_ROOT + "/processmanager/previewFormDefinition.jsp?defVerId=" + formDefID[1]);
			}			
		});
				
		popup.add(edit);
		popup.add(previewForm);
	}	
	
	public static void deployForm(OpenedForm openedForm) throws Exception{
		
		String filePath = openedForm.localFilePath;
		String definitionDoc = "";
		
		FileInputStream fis = new FileInputStream(filePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte [] b = new byte[1024];
		int len = 0;
		while ( (len=fis.read(b))!= -1 ) {
		    baos.write(b,0,len);
		}
		definitionDoc = baos.toString("UTF-8");
		ProcessDesigner.getInstance().getClientProxy().saveFormDefinition(openedForm.formDefVerId, definitionDoc);
	
		//Deploy후 Preview 화면 실행
		String host = ProcessDesigner.getInstance().getClientProxy().getHttpClient().getHost();
		int port = ProcessDesigner.getInstance().getClientProxy().getHttpClient().getPort();
		ProcessDesigner.getInstance().openNativeBrowser("http://" + host + ":" + port + "/" + GlobalContext.WEB_CONTEXT_ROOT + "/processmanager/previewFormDefinition.jsp?defVerId=" + openedForm.formDefVerId );					 

	}
	
	class OpenedForm{
		String formName;
		String localFilePath;
		String formDefVerId;
		long lastModified;
		
		SystemLauncher launcher;
	}
}
