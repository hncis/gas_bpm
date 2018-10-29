package org.uengine.kernel;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author Jinyoung Jang
 */

public class UEngineException extends Exception{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	public final static int WARNING = 0;
	public final static int ERROR = 1;
	public final static int FATAL = 2;
	public final static int MESSAGE_TO_USER = 3;
	
	public final static int SUCCESS_CODE = 0;
	public final static int ERR_CODE_PROCESS_CODE_IS_NULL = 101;
	public final static int ERR_CODE_BIZ_KEY_IS_NULL = 102;
	public final static int ERR_CODE_STATUS_CODE_IS_NULL = 103;
	public final static int ERR_CODE_ACTION_CODE_IS_NULL = 104;
	public final static int ERR_CODE_USER_ID_IS_NULL = 105;
	public final static int ERR_CODE_DELEGATE_IDS_IS_NULL = 106;
	public final static int ERR_CODE_INSTANCE_ID_IS_NULL = 107;
	public final static int ERR_CODE_WORKITEM_NO_AUTHORTY_OR_COMPLETED = 108;
	public final static int ERR_CODE_DUE_DATE_IS_NULL = 109; //2017-03-06 chonk
	public final static int ERR_CODE_WORKITEM_ALREADY_COMPLETED = 110; //2017-03-23 chonk
	public final static int ERR_CODE_BIZ_KEY_IS_DUPLICATED = 111; //2017-03-30 chonk
	public final static int ERR_CODE_SYSTEM_ERROR = 999;

	int errorCode = 999;
		public int getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(int errorCode) {
			this.errorCode = errorCode;
		}


	int errorLevel = ERROR;
		public int getErrorLevel() {
			return errorLevel;
		}
		public void setErrorLevel(int value) {
			errorLevel = value;
		}

	//Some exception types are difficult to be serialized to XML. So uEngine Exception doesn't serialize the cause exception.
	transient Throwable cause;
	
		public Throwable getCauseException(){
			return cause;
		}
			
		public void setCauseException(Throwable value){
			cause = value;
		}
		
	String details;
		public String getDetails() {
			if (details == null) {
				try {
					ByteArrayOutputStream bao = new ByteArrayOutputStream();
					printStackTrace(new PrintStream(bao));

					setDetails(bao.toString());
				} catch (Exception e) {
					/*System.out.println(
						"[UEngineException::getDetails] An non-fatal error : "
							+ e.getMessage());*/
				}
			}

			return details;
		}
		public void setDetails(String value) {
			/*if(value.startsWith("null")){
				System.out.println("=======================> "+value);
			}*/
//System.out.println("UEngineException.setDetails('" + value + "')");
			//details = value;
		}
		
	String resolution;
		public String getResolution() {
			return resolution;
		}
		public void setResolution(String value) {
			resolution = value;
		}

	public UEngineException(String errorMsg, String resolution, Throwable cause){
		this(errorMsg, resolution, cause, null, null);

	}
	
	public UEngineException(int errCode, String errorMsg) {
		this(errorMsg, null, null);
		this.errorCode = errCode;
	}
		
	public UEngineException(String errorMsg, String resolution, Throwable cause, ProcessInstance instance, Activity activity){
		super(NPEifNull(errorMsg), makeExceptionRicher(cause, instance));
		setCauseException(cause);
		setResolution(resolution);
		setInstance(instance);
		setActivity(activity);
		
		if(cause instanceof UEngineException){
			setErrorLevel(((UEngineException) cause).getErrorLevel());
		}
	}	

	private static String NPEifNull(String errorMsg){
		return (errorMsg != null ? errorMsg : "Null Pointer Exception");
	}
	
	private static Throwable makeExceptionRicher(Throwable e, ProcessInstance instance){
		if(instance==null) return e;
		
		StringBuilder richDebugInfo = (StringBuilder) instance.getProcessTransactionContext().getDebugInfo();
		
		if(GlobalContext.logLevelIsDebug){
			DebuggingContext dc;
			try {
				dc = new DebuggingContext(instance.getRootProcessInstance().getProcessTransactionContext(), instance.getRootProcessInstanceId(), true);
				
				richDebugInfo.append(dc.getExecutionPaths());
			} catch (Exception e1) {
			}
		}
		
		if(richDebugInfo!=null){
			return (new UEngineException(richDebugInfo.toString(), e));
		}
		
		return e;
	}

	public UEngineException(String errorMsg, Throwable cause){
		this(errorMsg, null, cause);
	}	
	
	public UEngineException(String errorMsg, String resolution){
		this(errorMsg, resolution, null);
	}	

	public UEngineException(String errorMsg){
		this(errorMsg, null, null);
	}
	
	public UEngineException(){
		this(null, null, null);
	}
	
	public String toString(){
		return getMessage();
	}
	
	public UEngineExceptionContext createContext(){
		UEngineExceptionContext ctx = new UEngineExceptionContext();
		ctx.setMessage(getMessage());

		ctx.setDetails(getDetails());
		ctx.setResolution(getResolution());
		
		return ctx;
	}
	
	ProcessInstance instance;
		public ProcessInstance getInstance() {
			return instance;
		}
		public void setInstance(ProcessInstance instance) {
			this.instance = instance;
		}
		
	Activity activity;
		public Activity getActivity() {
			return activity;
		}
		public void setActivity(Activity activity) {
			this.activity = activity;
		}
		
	public void printStackTrace(PrintWriter pw) {
		String instanceInfo = getInstanceInfo();
		if(instanceInfo!=null){			
			pw.print(instanceInfo);
			//pw.flush();
		}
	
		super.printStackTrace(pw);
	}
	
	public void printStackTrace(PrintStream arg0) {
		
		String instanceInfo = getInstanceInfo();
		if(instanceInfo!=null){
			PrintWriter pw = new PrintWriter(arg0);
			pw.print(instanceInfo);
//			pw.flush();
			
			printStackTrace(pw);
		}else
			super.printStackTrace(arg0);
		
	}

	public String getInstanceInfo(){
		if(getInstance()!=null){
			StringBuffer errorMsg = new StringBuffer();
			errorMsg
				.append("<BPM Application Exception>\n")
				//.append((instance.getRequestContext()!=null && instance.getRequestContext().getUser()!=null) ? "  : " + instance.getRequestContext().getUser().getLoginName()  + "\n": "" )
				.append(" Instance: " + instance + "\n")
				.append(getActivity()!=null ? " Activity: "+ getActivity() + "\n" : "")
				.append(getActivity()!=null ? " Definition: "+ getActivity().getProcessDefinition()+"("+getActivity().getProcessDefinition().getAlias()+")" + "\n" : "")
				.append(" Detailed Info: ")
			;
			
			return  errorMsg.toString();
		}
		
		return null;
	}

}

