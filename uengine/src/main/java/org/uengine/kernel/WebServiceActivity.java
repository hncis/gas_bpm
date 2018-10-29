package org.uengine.kernel;


import org.apache.wsif.WSIFMessage;
import org.apache.wsif.WSIFOperation;
import org.apache.wsif.WSIFPort;
import org.apache.wsif.WSIFService;
import org.apache.wsif.WSIFServiceFactory;

import org.uengine.processmanager.SimulatorTransactionContext;
import org.uengine.webservice.*;

import com.ibm.wsdl.factory.WSDLFactoryImpl;

//review:
import java.io.FileInputStream;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;
import javax.wsdl.*;
import javax.wsdl.xml.WSDLReader;

/**
 * @author Jinyoung Jang
 */

public class WebServiceActivity extends DefaultActivity{

	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	ServiceDefinition service;
		public ServiceDefinition getService() {
			if (service == null && getRole() != null)
				return getRole().getServiceType();

			return service;
		}
		public void setService(ServiceDefinition service) {
			this.service = service;
		}

	String portType;
		public String getPortType() {
			return portType;
		}
		public void setPortType(String value) {
			portType = value;
		}

	String operationName;
		public String getOperationName() {
			return operationName;
		}
		public void setOperationName(String value) {
			operationName = value;
			System.out.println(
				"WebServiceActivity::setOperationName.parameterin = " + value);
		}

	Object[] parameters;
		public Object[] getParameters() {
			return parameters;
		}
		public void setParameters(Object[] value) {
			parameters = value;
		}

	Role role;
		public Role getRole() {
			return role;
		}
		public void setRole(Role value) {
			System.out.println(
				"WebServiceActivity::setRole.parameterin = " + value);
			role = value;
		}

	ProcessVariable output;
		public ProcessVariable getOutput() {
			return output;
		}
		public void setOutput(ProcessVariable value) {
			System.out.println(
				"WebServiceActivity::setOut.parameterin = " + value);
			output = value;
		}
	
	boolean stublessInvocation;
		public boolean getStublessInvocation() {
			return stublessInvocation;
		}
		public void setStublessInvocation(boolean stublessInvocation) {
			this.stublessInvocation = stublessInvocation;
		}
		
	public WebServiceActivity(){
		super("Invoke");
		setStublessInvocation(true);
	}

/*	public WebServiceActivity(String portType, String operationName, Object[] parameters){
		this();
		this.portType = portType;
		this.operationName = operationName;
		this.parameters = parameters;
	}*/
	
	public WebServiceActivity(Role role, String portType, String operationName, Object[] parameters, ProcessVariable out,boolean stublessInvocation){
		this.role = role;
		this.portType = portType;
		this.operationName = operationName;
		this.parameters = parameters;
		this.stublessInvocation = stublessInvocation;
		setOutput(out);
	}
	
	public void executeActivity(ProcessInstance instance) throws Exception{
		
		try{
			Thread.currentThread().setContextClassLoader(GlobalContext.getComponentClassLoader());
						
			if (getStublessInvocation()) {
				
				ServiceDefinition sd = getService();
				String wsdlUrl = sd.getWsdlLocation();
				WSDLReader reader = (new WSDLFactoryImpl()).newWSDLReader();
				reader.readWSDL(wsdlUrl);

				Object[] actualParameters = getActualParameters(instance);
				
				String endpoint="";
				try{
					endpoint = role.getMapping(instance, getTracingTag()).getEndpoint();
				}catch(Exception e){
					throw new UEngineException("Couldn't get the endpoint to invoke WS",e);
				}

				WSIFServiceFactory factory = WSIFServiceFactory.newInstance();

				WSIFService service = factory.getService(wsdlUrl, null, null,endpoint, null);

				WSIFPort port = service.getPort();

				WSIFOperation operation = port.createOperation(getOperationName());

				WSIFMessage input = operation.createInputMessage();
				WSIFMessage output = operation.createOutputMessage();
				WSIFMessage fault = operation.createFaultMessage();

				Message me = input.getMessageDefinition();
				Map partList = me.getParts();
				Set set = partList.keySet();

				int i = 0;
				for (Iterator iter = set.iterator(); iter.hasNext(); i++) {
					String partName = (String) iter.next();
					input.setObjectPart(partName, actualParameters[i]);
				}

				if (operation.executeRequestResponseOperation(input, output,fault)) {

					String partName = (String) output.getPartNames().next();
					Object result = output.getObjectPart(partName);

					ProcessVariable outputVar = getOutput();
					outputVar.set(instance, "", (Serializable) result);

					System.out.println("result=" + result);

				} else {
					throw new UEngineException(fault.toString());
				}
			} else {
				
				ServiceProvider sp = GlobalContext.getServiceProvider(getService(), getPortType());
				System.out.println("role is null? "+ role +" rolemapping is null?" + role.getMapping(instance, getTracingTag()));
				String endpoint = role.getMapping(instance, getTracingTag()).getEndpoint();
				System.out.println("invoking endpoint? "+ endpoint);
				Object[] actualParameters = getActualParameters(instance);
				
				Object res = sp.invokeService(endpoint, operationName,actualParameters);
				
				if (res != null && getOutput() != null)
					instance.set(""/* getTracingTag() */, getOutput().getName(),
							(java.io.Serializable) res);
			}
			
			fireComplete(instance);
			
		}catch(Exception e){
			throw new UEngineException("WebServiceActivity:: fail to replace the classloader", e);
		}			
	}
	
	@Override
	public Map getActivityDetails(ProcessInstance instance, String locale) throws Exception{
		Map details = super.getActivityDetails(instance, locale);

		try{
			Object[] actualParameters = getActualParameters(instance);
			
			if(actualParameters!=null)
				for(int i=0; i<actualParameters.length; i++){
					details.put("Parameters["+i+"]", actualParameters[i]);
				}
		
			if(getOutput()!=null)
				details.put("Output", getOutput().get(instance, ""));
		}catch(Exception e){e.printStackTrace();}
		
		return details;
	}
	
	private Object[] getActualParameters(ProcessInstance instance) throws Exception{
		if(getParameters()==null) return null;
				
		Object[] actualParameters = new Object[getParameters().length];		
		if(getParameters()!=null){
			
			for(int i=0; i<getParameters().length; i++){
				//for backward compatibility
				Object actualParameter = getParameters()[i];
				
				if(actualParameter instanceof ParameterContext){
					actualParameter = ((ParameterContext)actualParameter).getVariable(); 
				}
				
				if(actualParameter instanceof ProcessVariable){
					actualParameter = ((ProcessVariable)actualParameter).get(instance, "");
				}
				//
				actualParameters[i] = actualParameter;
			}
		}
		
		return actualParameters;
	}


	public ValidationContext validate(Map options){
		ValidationContext validationContext  = super.validate(options);
		
		if(getPortType()== null)
			validationContext.add(getActivityLabel()+" 'PortType' must be specified.");
			
		if(getOperationName()== null)
			validationContext.add(getActivityLabel()+" 'OperationName' must be specified.");
			
		if(getParameters()!=null){
			Object[] parameters = getParameters();
			for(int i=0; i<parameters.length; i++){
				if(parameters[i] instanceof ParameterContext && ((ParameterContext)parameters[i]).getVariable()==null){
					validationContext.addWarning(getActivityLabel()+" All of the parameters must be bound with variable.");
					break;
				}
			}
		}
		
		if(getRole()== null)
			validationContext.add(getActivityLabel()+" Role must be specified.");

		return validationContext;
	}
	
	public PortType getPortType(Definition wsdlDef){
//		if(wsdlDef==null) wsdlDef = getService().getDefinition();
		
		String ptName = getPortType();
		Map portTypes = wsdlDef.getPortTypes();
		for(Iterator iter = portTypes.values().iterator(); iter.hasNext(); ){
			PortType pt = (PortType)iter.next();
			if(pt.getQName().getLocalPart().equals(ptName)){
				return pt;
			}
		}
		return null;
	}

	public Operation getOperation(Definition wsdlDef){
//		if(wsdlDef==null) wsdlDef = getService().getDefinition();

		PortType pt = getPortType(wsdlDef);
		List operations = pt.getOperations();
		for(Iterator iter = operations.iterator(); iter.hasNext();){
			Operation op = (Operation)iter.next();
			//review: actually testing operation types also needed 
			//		or webservice activity should have 'message' field 
			//      so that the overloaded operations those have same signature name
			//		can be designated			
			if(op.getName().equals(getOperationName())){
				if(op.getInput().getMessage().getParts().size() == getParameters().length){
					return op;		
				}
			}
		}
		
		return null;
	}
	
	 public static void main(String[] args)  throws Exception{
		
		ProcessDefinition process = new ProcessDefinition();	
		
		Role role = new Role();
		role.setName("unpayedFactService");
		role.setDefaultEndpoint("http://localhost:8080/axis/UnpayedUserQueryService.jws");
		process.setRoles(new Role[]{
			role	
		});
		
		ProcessVariable var1 = new ProcessVariable();
		var1.setName("unpayedFactReturn");
		var1.setType(String.class);
		process.setProcessVariables(new ProcessVariable[]{
			var1	
		});
		
		ServiceDefinition sd = new ServiceDefinition();
		sd.setName("unpayedFactService");
		sd.setWsdlLocation("http://localhost:8080/axis/UnpayedUserQueryService.jws?wsdl");
		
		WebServiceActivity wsAct = new WebServiceActivity();
		
		wsAct.setOperationName("getUnpayedFact");
		wsAct.setRole(role);
		wsAct.setService(sd);
		wsAct.setParameters(new Object[]{"7709211907315"});
		wsAct.setOutput(var1);
		wsAct.setPortType("UnpayedUserQueryService");
	
		process.addChildActivity(wsAct);
		
		process.afterDeserialization();
		
		//(ProcessDefinition) GlobalContext.deserialize(new FileInputStream(""), ProcessDefinition.class);
		
		ProcessInstance instance = new DefaultProcessInstance();
		instance.setProcessDefinition(process);
		instance.setProcessTransactionContext(new SimulatorTransactionContext());
		instance.execute();
	}
}
