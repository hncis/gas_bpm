package org.uengine.kernel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.metaworks.Type;
import org.uengine.contexts.FileContext;
import org.uengine.contexts.HtmlFormContext;
import org.uengine.contexts.MappingContext;
import org.uengine.formmanager.FormUtil;
import org.uengine.processdesigner.SimulatorProcessInstance;
import org.uengine.processdesigner.mapper.Transformer;
import org.uengine.processdesigner.mapper.TransformerMapping;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.util.UEngineUtil;


/**
 * 
 * @author <a href="mailto:bigmahler@users.sourceforge.net">Jong-Uk Jeong</a>
 * @version $Id: FormActivity.java,v 1.78 2011/07/22 07:33:14 curonide Exp $
 */
public class FormActivity extends HumanActivity {
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;

	protected final static String SUBPROCESS_INST_ID = "instanceIdOfSubProcess";

	protected final static String SUBPROCESS_INST_ID_COMPLETED = "completedInstanceIdOfSPs";
	public final static String FILE_SYSTEM_DIR = GlobalContext.getPropertyString("filesystem.path", ProcessDefinitionFactory.DEFINITION_ROOT);
	
	public static void metaworksCallback_changeMetadata(Type type){
//		FieldDescriptor fd;
		
		type.setName("Form Activity");
		type.removeFieldDescriptor("Input");
		type.removeFieldDescriptor("Instruction");
		type.removeFieldDescriptor("Message");
		type.removeFieldDescriptor("MessageDefinition");
		type.removeFieldDescriptor("Parameters");
		type.removeFieldDescriptor("FromRole");

	}


	public FormActivity() {
		super();
		setName("form");//test
	}

	MappingContext mappingContext;
		public MappingContext getMappingContext	() {
			return mappingContext;
		}
		public void setMappingContext(MappingContext mappingContext) {
			this.mappingContext = mappingContext;
		}
		
	ProcessVariable variableForHtmlFormContext;
		public ProcessVariable getVariableForHtmlFormContext() {
			return variableForHtmlFormContext;
		}
		public void setVariableForHtmlFormContext(
				ProcessVariable variableForHtmlFormContext) {
			this.variableForHtmlFormContext = variableForHtmlFormContext;
		}
		
	boolean mappingWhenSave;
		public boolean isMappingWhenSave() {
			return mappingWhenSave;
		}
		public void setMappingWhenSave(boolean mappingWhenSave) {
			this.mappingWhenSave = mappingWhenSave;
		}
		
	private static Method getMethod(Class src, String name) {
		Method meths[] = src.getMethods();
		for (int i = 0; i < meths.length; i++) {
			if (meths[i].getName().equals(name))
				return meths[i];
		}
		return null;
	}

/*	public String getDefinitionVersionId(ProcessInstance instance)
			throws Exception {
		ProcessManagerRemote pm = new ProcessManagerBean();

		String versionId = null;
		String definitionId = null;

		String[] defIdAndVersionId = SubProcessActivity
				.splitDefinitionAndVersionId(getDefinitionId());
		definitionId = defIdAndVersionId[0];
		versionId = defIdAndVersionId[1];

		try {
			versionId = pm.getProcessDefinitionProductionVersion(definitionId);
		} catch (Exception e) {
			e.printStackTrace();

			try {
				versionId = pm.getFirstProductionVersionId(definitionId);
			} catch (Exception ex) {
				ex.printStackTrace();
				versionId = pm
						.getProcessDefinitionProductionVersion(definitionId);
			}
		}

		return versionId;
	}

	private String readForm(ProcessInstance instance) throws Exception {
		// read in the source
		String formDefId = getDefinitionVersionId(instance);
		System.out.println(formDefId);

		Reader source = new InputStreamReader(ProcessDefinitionFactory
				.getInstance(instance.getProcessTransactionContext())
				.getResourceStream(formDefId));
		System.out.println(source.toString());

		
		return null;
	}*/

	public static List getParameterList(ProcessManagerRemote pm,
			String formDefId) throws Exception {
		// load up the formbase
		String def = pm.getResource(formDefId);
		Reader source = new StringReader(def);
		
//		builder.addPackageFromDrl(source);
		
		
		HashMap classes = new HashMap();
		
		ArrayList parameterList = new ArrayList();

		for (Iterator iter = classes.keySet().iterator(); iter.hasNext();) {
			Class theClass = (Class) iter.next();

			Method methods[] = theClass.getMethods();
			String clsName = theClass.getName();

			for (int k = 0; k < methods.length; k++) {
				if (methods[k].getName().startsWith("set")) {
					parameterList.add(clsName + ":"
							+ methods[k].getName().substring(3));
				}
			}
		}

		return parameterList;

	}

	protected void afterComplete(ProcessInstance instance) throws Exception {
		onSave(instance, null);
		mappingOut(instance);
		super.afterComplete(instance);
	}
	
	protected void mappingOut(ProcessInstance instance) throws Exception{
		// load up the HtmlFormContext
		HtmlFormContext formContext = (HtmlFormContext) getVariableForHtmlFormContext().get(instance, "");
		
		ParameterContext[] params = getMappingContext().getMappingElements();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				ParameterContext param = params[i];
				
				Object value = null;
				String targetFieldName = param.getArgument().getText();
				
				if (param.getTransformerMapping()!= null) {
					
					Map options = new HashMap();
					options.put(org.uengine.processdesigner.mapper.Transformer.OPTION_KEY_OUTPUT_ARGUMENT, param.getTransformerMapping().getLinkedArgumentName() );
					options.put(org.uengine.processdesigner.mapper.Transformer.OPTION_KEY_FORM_FIELD_NAME, targetFieldName);
					
					TransformerMapping tm = param.getTransformerMapping();
					Transformer transformer = tm.getTransformer();
					
					value = param.getTransformerMapping().getTransformer().letTransform(instance, options);
					System.out.println(value);
					instance.setBeanProperty(targetFieldName, (Serializable)value);

				} else {
					String srcVariableName = param.getVariable().getName();
					
					value = instance.getBeanProperty(srcVariableName);
					
					ProcessVariable pv = getProcessDefinition().getProcessVariable(srcVariableName);
					if(pv == getVariableForHtmlFormContext()){ //maps only the child fields of the form activity's target html form
						
						instance.setBeanProperty(targetFieldName, (Serializable)value);
						
					}
				}
			}
		}
	}
	
	public void saveWorkItem(ProcessInstance instance, ResultPayload payload) throws Exception {
		onSave(instance, null);
		if(isMappingWhenSave()){
			mappingOut(instance);
		}
		
		super.saveWorkItem(instance, payload);
	}
	
	public String getParameter(Map parameterMap, String key){
		String[] paramPair = (String[])parameterMap.get(key);
		if(paramPair!=null && paramPair.length > 0)
			return paramPair[0];
		else
			return null;
	}

	public Map getMappedResult(ProcessInstance instance) throws Exception{
		
		if(getParentActivity() instanceof AtomicHumanActivity){
			((AtomicHumanActivity)getParentActivity()).executePreActivities(instance);
		}
		
		if("true".equals(GlobalContext.getPropertyString("org.uengine.kernel.formactivity.run_select_activities_before_formactivity"))){
		}
		
		boolean isMapping = true;
		Map mappedResult = new HashMap();
		HtmlFormContext formContext = instance == null ? (HtmlFormContext)(getVariableForHtmlFormContext().getDefaultValue()) : (HtmlFormContext)(getVariableForHtmlFormContext().get(instance, ""));
		
		if(formContext == null) {
			return mappedResult;
		}
		
		if (formContext.getFilePath() != null) {
			isMapping = false;
			if(formContext.getValueMap() == null){
				formContext.loadValueMap();
			}
			mappedResult.putAll(formContext.getValueMap());
		}
		
		String status = instance.getStatus(getTracingTag());
		
		if (Activity.STATUS_READY.equals(status) ||
		    Activity.STATUS_RUNNING.equals(status) ||
		    Activity.STATUS_TIMEOUT.equals(status)) {
			
			MappingContext mappingContext = getMappingContext();
			ParameterContext[] params = mappingContext.getMappingElements();//getVariableBindings();
			
			if(params != null && instance != null){
				//String script = "";
				String objName = null;
				Serializable objValue = null;
				for (int i = 0; i < params.length; i++) {
					ParameterContext param = params[i];
	
					String targetFormField = param.getArgument().getText();
						
					targetFormField = targetFormField.replace('.','@');
					String [] targetFormFieldName = targetFormField.split("@");
					
					if(getVariableForHtmlFormContext().getName().equals(targetFormFieldName[0])){
						objName = targetFormFieldName[1];
						
						if(param.getTransformerMapping()!=null){
												
							Map options = new HashMap();
							options.put(org.uengine.processdesigner.mapper.Transformer.OPTION_KEY_OUTPUT_ARGUMENT, param.getTransformerMapping().getLinkedArgumentName() );
							options.put(org.uengine.processdesigner.mapper.Transformer.OPTION_KEY_FORM_FIELD_NAME, objName);
							
							objValue = (Serializable) param.getTransformerMapping().getTransformer().letTransform(instance, options);
							
						}else{
							
							String sourceProcessVariable = param.getVariable().getName();
	
							if(sourceProcessVariable.startsWith("["))
								objValue = (Serializable) instance.getBeanProperty(sourceProcessVariable);
							else{
								ProcessVariableValue pvv = instance.getMultiple("", sourceProcessVariable);
								pvv.beforeFirst();
								if(pvv.size()>1){
									Object values[] = new String[pvv.size()];
									int j=0;
									
									do {
										Object objTmp = pvv.getValue();
										StringBuffer strTmpValue = new StringBuffer();
										
										if (objTmp != null) {
											if (objTmp.getClass().isArray()) {
												for (String strTmp : (String[]) objTmp) {
													strTmpValue.append(strTmp).append(";");
												}
											} else {
												strTmpValue.append(pvv.getValue().toString());
											}
										}
										
										values[j++] = strTmpValue.toString();
	//									values[j++] = pvv.getValue();
									} while (pvv.next());
									
									objValue = values;
								}else{
									
									objValue = pvv.getValue();
								}
							}
						}
						if (!mappedResult.containsKey(objName.toLowerCase()) || isMapping) {
							mappedResult.put(objName.toLowerCase(), objValue);
						} 
					}
				}
			}
		}
		
		return mappedResult;
	}

	
	static public Map createParameterMapFromRequest(HttpServletRequest request ) throws Exception{
		return createParameterMapFromRequest(false,request);
	}
	
	static public Map createParameterMapFromRequest(boolean isSimulate ,HttpServletRequest request ) throws Exception{
		Map parameterMap = new HashMap(request.getParameterMap()); 
		
		
		
		return parameterMap;
	}
	
	protected void onSave(ProcessInstance instance, Map parameterMap_) throws Exception {
		boolean isSimulation = instance instanceof SimulatorProcessInstance;
		Map parameterMap = null;
		
		try{
			parameterMap = (Map) instance.getProcessTransactionContext().getProcessManager().getGenericContext().get("parameterMap");
		}catch(Exception e){
		}
		
		if(parameterMap == null){
			if(parameterMap_==null){
				parameterMap = createParameterMapFromRequest(isSimulation,(HttpServletRequest) instance.getProcessTransactionContext().getServletRequest());
			}else{
				parameterMap = parameterMap_;
			}
		}
				
		HashMap valueMap = new HashMap();
		if (parameterMap != null && parameterMap.size() > 0) {
			
			String fileSystemDir = FormActivity.FILE_SYSTEM_DIR;
			String lastChar = fileSystemDir.substring(fileSystemDir.length() - 1, fileSystemDir.length());
			
			if (!"/".equals(lastChar) && !"\\".equals(lastChar)) {
				fileSystemDir += "/";
			}
			
			String tempDir = "temp" + "/";
			String outPath = null;
			FileContext fc = null;
			Iterator interator = parameterMap.keySet().iterator();
			for(int i = 0; i < parameterMap.size(); i++) {
				String key = (String)interator.next();
				String[] value = (String[])parameterMap.get(key); 
				//File Move
				if (value != null) { 
					for (int j=0; j<value.length; j++) {
						if (value[j].contains("<org.uengine.contexts.FileContext>") 
								&& value[j].contains("<path>" + tempDir)) {
							fc = (FileContext) GlobalContext.deserialize(value[j], FileContext.class);
							outPath = fc.getPath().replace(tempDir, "");
							new File(fileSystemDir + fc.getPath()).renameTo(new File(fileSystemDir + outPath));
							fc.setPath(outPath);
							value[j] = GlobalContext.serialize(fc, FileContext.class);
						}
					}
					parameterMap.put(key, value);
				}
				
				
				if(value.length > 1){
					valueMap.put(key.toLowerCase(), value);
				}else if(value.length > 0) {
					valueMap.put(key.toLowerCase(), value[0]);
				}
			}
		}
		
		if(isSimulation) {
			HtmlFormContext newFormCtx = new HtmlFormContext();
			newFormCtx.setValueMap(valueMap);
			
			getVariableForHtmlFormContext().set(instance, "", newFormCtx);
			return;
		}
		
		String filePath = FILE_SYSTEM_DIR + UEngineUtil.getCalendarDir();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS", Locale.KOREA);
		String fileName = filePath + "/" + instance.getInstanceId() + "_" + sdf.format(new Date());
		boolean isHtmlSave = "true".equals(GlobalContext.getPropertyString("formactivity.save.html", "false"));
		
		//Form data save to xml 
		saveFormVariableXML(instance, valueMap, fileName + ".xml");
		//snapshot save to html
		if (isHtmlSave) {
			saveFormHTML(instance, valueMap, fileName + ".html");
		}
		
	}
	
	public void saveFormVariableXML(ProcessInstance instance, Map valueMap, String filePath) throws Exception {
		
		File newFile = new File(filePath);
		File dir = newFile.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		FileOutputStream fos = new FileOutputStream(newFile);
		GlobalContext.serialize(valueMap, fos, HashMap.class);
		fos.close();
		
		HtmlFormContext formDefInfo = (HtmlFormContext)getVariableForHtmlFormContext().getDefaultValue();
		String[] formDefID = formDefInfo.getFormDefId().split("@");
		
		HtmlFormContext newFormCtx = new HtmlFormContext();
		newFormCtx.setFilePath(filePath.substring(FILE_SYSTEM_DIR.length()));
		newFormCtx.setFormDefId(formDefID[0] + "@" + formDefID[1]);
		newFormCtx.setValueMap(valueMap);
		
		if(GlobalContext.logLevelIsDebug && instance!=null){
			instance.addDebugInfo("Form administration url", GlobalContext.WEB_CONTEXT_ROOT + "/processmanager/viewFormDefinition.jsp?objectDefinitionId=" + formDefID[0] + "&processDefinitionVersionID=" + formDefID[1]);
			instance.addDebugInfo("Form data XML path", new File(FILE_SYSTEM_DIR + newFormCtx.getFilePath()).getAbsolutePath());
			instance.addDebugInfo("");
		}
			
		getVariableForHtmlFormContext().set(instance, "", newFormCtx);
	}
	
	private void saveFormHTML(ProcessInstance instance, Map valueMap, String filePath) throws Exception {
		HtmlFormContext formCtx = (HtmlFormContext) getVariableForHtmlFormContext().get(instance, "");
		String[] formDefID = formCtx.getFormDefId().split("@");
		HttpServlet servlet = (HttpServlet)instance.getProcessTransactionContext().getProcessManager().getGenericContext().get("servlet");
		HttpServletResponse response = (HttpServletResponse)instance.getProcessTransactionContext().getServletResponse();
		ServletRequest request = instance.getProcessTransactionContext().getServletRequest();
		
		if(request != null){
			request.setAttribute("mappingResult", valueMap);
			request.setAttribute("instance", instance);
			request.setAttribute("formActivity", this);
			request.setAttribute("loggedRoleMapping", instance.getProcessTransactionContext().getProcessManager().getGenericContext().get(HumanActivity.GENERICCONTEXT_CURR_LOGGED_ROLEMAPPING));
			request.setAttribute("pm", (new ProcessManagerFactoryBean()).getProcessManagerForReadOnly());
		    
			final StringWriter sw = new StringWriter();
			ServletContext servletContext = servlet.getServletContext();

			boolean isJBoss = "JBOSS".equals(GlobalContext.getPropertyString("was.type", "TOMCAT"));
			String webRoot = isJBoss ? GlobalContext.WEB_CONTEXT_ROOT : "";
			//standalone_formDefinition.jsp?formdefinition_url=

			RequestDispatcher dis = servletContext.getRequestDispatcher( webRoot + "/wih/wihDefaultTemplate/header.jsp");
			dis.include(request, new HttpServletResponseWrapper(response){
				public PrintWriter getWriter() throws IOException {	
					return new PrintWriter(sw);
				}
			});
			
			
			String formFileName = formDefID[1];
			String cachedFormRoot = "/wih/formHandler/cachedForms/";
			File contextDir = new File(request.getRealPath(cachedFormRoot));
			FormUtil.copyToContext(contextDir, formFileName);
			dis = servletContext.getRequestDispatcher( webRoot + "/wih/formHandler/cachedForms/" + formFileName + "_formview.jsp");
			dis.include(request, new HttpServletResponseWrapper(response){
				public PrintWriter getWriter() throws IOException {	
					return new PrintWriter(sw);
				}
			});
			
			/**************************
			 * Append Tag Div
			 **************************/
			String Tags = request.getParameter("tags");
			if(UEngineUtil.isNotEmpty(Tags)){
				StringBuffer buff = sw.getBuffer();
				
				buff.append("<div id='tags'>");
				buff.append(Tags.replaceAll(";", ",").substring(0,Tags.length()-1));
				buff.append("</div>");
			}
			
			sw.flush();		
			sw.close();		

			File newFile = new File(filePath);
			File dir = newFile.getParentFile();
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			final OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(newFile), GlobalContext.ENCODING);
			osw.write(sw.toString());
			osw.close();
			
		}
	}
	
	public String getFormDefinitionVersionId(ProcessInstance instance, ProcessManagerRemote pm) throws Exception{
		
		HtmlFormContext formContext = instance==null ? (HtmlFormContext)(getVariableForHtmlFormContext().getDefaultValue()) : (HtmlFormContext)(getVariableForHtmlFormContext().get(instance, ""));
		String formDefId = formContext.getFormDefId();

		return ProcessDefinition.getDefinitionVersionId(pm, formDefId, ProcessDefinition.VERSIONSELECTOPTION_CURRENT_PROD_VER, getProcessDefinition());
	}
	
	public String getFormDefinitionPath(ProcessInstance instance, ProcessManagerRemote pm) throws Exception{
		String formDefinitionVersionId = getFormDefinitionVersionId(instance, pm);
		
		//return ProcessDefinitionFactory.getInstance(instance.getProcessTransactionContext()).getResourcePath(formDefinitionVersionId);
		return ProcessDefinitionFactory.DEFINITION_ROOT + formDefinitionVersionId + ".form";
	}
	
/*	public void onSave(ProcessInstance instance, HttpServletRequest request) throws Exception{
		Enumeration enumeration = request.getParameterNames();
		HashMap valueMap = new HashMap();
		
		for(;enumeration.hasMoreElements();){
			String key = (String)enumeration.nextElement();
			String value = request.getParameter(key);
			
			valueMap.put(key, value);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS", Locale.KOREA);
		
		String filePath = GlobalContext.getPropertyString(
				"server.definition.path","./uengine/definition/")
				+ UEngineUtil.getCalendarDir();
		File dirToCreate = new File(filePath);
		dirToCreate.mkdirs();
		
		String fileName = sdf.format(new Date()) + ".xml";
		File newFile = new File(filePath+"/"+fileName);
		FileOutputStream fos = new FileOutputStream(newFile);
		GlobalContext.serialize(valueMap, fos, HashMap.class);
		fos.close();
		
		HtmlFormContext formDefInfo = (HtmlFormContext)getVariableForHtmlFormContext().getDefaultValue();
		formDefInfo.setFilePath(newFile.getAbsolutePath());
		formDefInfo.setFormDefId(formDefInfo.getFormDefId());

		getVariableForHtmlFormContext().set(instance, "", formDefInfo);
	}*/

	public String getTool() {
		return "formHandler";
	}

	
	public void executeActivity(ProcessInstance instance) throws Exception {
		if(instance instanceof SimulatorProcessInstance){
			onReceive(instance, null);
			return;
		}
		
		super.executeActivity(instance);
	}

	public ValidationContext validate(Map options) {
		ValidationContext superVC =  super.validate(options);
		
		if(getVariableForHtmlFormContext()==null)
			superVC.add(getActivityLabel() + "Variable For HTMLContext should not be null");
		
		return superVC;
	}

	//added by yookjy 2011.04.05
	protected void saveSnapshotHTML(ProcessInstance instance) throws Exception {
//		ProcessInstance rootInstance = instance.getRootProcessInstance();
//		int	year = rootInstance.getProcessDefinition().getStartedTime(rootInstance).get(Calendar.YEAR);
//		String fileName = this.getTaskIds(instance)[0] + ".html";
//		String filePath = SNAPSHOT_DIRECTORY + year + "/" + rootInstance.getInstanceId() + "/" + fileName;
//		
//		saveFormHTML(instance, getMappedResult(instance), filePath);
	}
}
