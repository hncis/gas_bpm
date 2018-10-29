package org.uengine.kernel;

import java.util.Date;
import java.util.Map;

import org.uengine.processmanager.SimulatorTransactionContext;



/**
 * @author Kinam Jung, Jinyoung Jang
 */

public class Evaluate extends Condition{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	String key;
	ProcessVariable pv;
	Object val;
		
	String condition;	// default
	
	// For Reflection	
	public Evaluate(){
	}

	public Evaluate(String key, Object val){
		this( key, "==", val);
	}
	
	public Evaluate(String key, String condition, Object val){
		setKey(key);
		setValue(val);
		setCondition(condition);
	}
	
	public Evaluate(String key, String condition, Object val, String description){
		setKey(key);
		setValue(val);
		setCondition(condition);
		getDescription().setText(description);
	}
	
	public Evaluate(ProcessVariable pv, String condition, Object val){
		this.key = pv.getName();
		this.val = val;
		this.pv = pv;
		this.condition = condition;
	}
	
	public Evaluate(ProcessVariable pv, String condition, Object val, String description){
		this.key = pv.getName();
		this.val = val;
		this.pv = pv;
		this.condition = condition;
		getDescription().setText(description);
	}

	public boolean isMet(ProcessInstance instance, String scope) throws Exception{
		Object returnVal = instance.getProcessDefinition().getProcessVariable(key).get(instance, "");
		Object compareVal = val;
		
		String condition = this.condition.trim();
	
		if(val instanceof ProcessVariable){
			ProcessVariable variable = (ProcessVariable)val;
			compareVal = instance.getProcessDefinition().getProcessVariable(variable.getName()).get(instance, "");
		}
		
		if(compareVal==null){
//			System.out.println("Evaluate::isMet : the evaluated value = " + returnVal);
			
			if(condition.equals("!=")){
				return (returnVal!=null);
			}else if(condition.equals("contains") || condition.equals("not contains")){
				return checkupContainsCompareVal(returnVal, compareVal);
			}else{		
				return (returnVal==null || "".equals(returnVal));
			}
		}
		
		if(returnVal==null){
//			System.out.println("Evaluate::isMet : the evaluated value = " + returnVal);
			
			if(condition.equals("!=")){
				return (compareVal!=null);
			}else{
				return (compareVal==null);
			}
		}

		if ( compareVal instanceof Number &&  returnVal.getClass() == String.class) {
			String strReturnVal = (String)returnVal;
			if(strReturnVal.startsWith("$")){
				strReturnVal = strReturnVal.substring(1, strReturnVal.length());
			}
			
			strReturnVal = strReturnVal.replaceAll("\054","");
			
			if(strReturnVal.indexOf(".") == -1)
				returnVal = new Long(strReturnVal);
			else
				returnVal = new Double(strReturnVal);
		}

//		if ( compareVal instanceof Double &&  returnVal.getClass() == Long.class) {
//			returnVal = ((Long)returnVal).doubleValue();
//		}else if ( compareVal instanceof Double &&  returnVal.getClass() == Integer.class) {
//			returnVal = ((Integer)returnVal).doubleValue();
//		} 
			
//		if ( compareVal.getClass() == Long.class &&  returnVal.getClass() == String.class) {
//			returnVal = new Long((String)returnVal);
//		}


		//review: should support all types comparison		
/*		if( returnVal instanceof Number 
					&& compareVal instanceof Number ){
			
			
			
			
			
			if(compareVal.getClass() == Double.class){
				
				double returnDouble = ((Number)returnVal).doubleValue();
				double valDouble = ((Number)compareVal).doubleValue();
				
				if( condition.equals( "<")){
					if( returnDouble < valDouble)
						return true;
				}else if( condition.equals( "<=") || condition.equals( "=<")){
					if( returnDouble <= valDouble)
						return true;
				}else if( condition.equals( ">")){
					if( returnDouble > valDouble)
						return true;
				}else if( condition.equals( ">=") || condition.equals( "=>")){
					if( returnDouble >= valDouble)
						return true;
				}else if(condition.equals( "==")){
					if( returnDouble == valDouble)
						return true;
				}else if("!=".equals(condition)){
					if( returnDouble != valDouble)
						return true;
				}
				
			}else{

				long returnInt = ((Number)returnVal).longValue();
				long valInt = ((Number)compareVal).longValue();

				if( condition.equals( "<")){
					if( returnInt < valInt)
						return true;
				}else if( condition.equals( "<=") || condition.equals( "=<")){
					if( returnInt <= valInt)
						return true;
				}else if( condition.equals( ">")){
					if( returnInt > valInt)
						return true;
				}else if( condition.equals( ">=") || condition.equals( "=>")){
					if( returnInt >= valInt)
						return true;
				}else if(condition.equals( "==")){
					if( returnInt == valInt)
						return true;
				}
			}

			return false;
		}*/
		
//		//review: should support all types comparison		
//		if( returnVal.getClass() == Long.class 
//					&& compareVal.getClass() == Long.class){
//			
//			long returnInt = ((Long)returnVal).longValue();
//			long valInt = ((Long)compareVal).longValue();
//			  
//			if( condition.equals( "<")){
//				if( returnInt < valInt)
//					return true;
//			}else if( condition.equals( "<=") || condition.equals( "=<")){
//				if( returnInt <= valInt)
//					return true;
//			}else if( condition.equals( ">")){
//				if( returnInt > valInt)
//					return true;
//			}else if( condition.equals( ">=") || condition.equals( "=>")){
//				if( returnInt >= valInt)
//					return true;
//			}else if(condition.equals( "==")){
//				if( returnInt == valInt)
//					return true;
//			}
//			
//			return false;
//		}
		
/*		if( returnVal.getClass() == Integer.class || returnVal.getClass() == Long.class){
			if( condition.equals( "!="))
				return !(returnVal.toString().equals( compareVal.toString()) );
			
			return returnVal.toString().equals( compareVal.toString());
		}*/
		
		if( condition.equals( "!="))
			return !( instance.get(scope, key).equals(compareVal) );

		//use default comparator
		if( condition.equals( "=="))
			return returnVal.equals(compareVal);
		
		if( condition.equals( "!="))
			return !returnVal.equals(compareVal);

		if(!(returnVal instanceof Comparable) && (condition.startsWith(">") || condition.startsWith("<")))
			throw new UEngineException("The value type ["+ returnVal.getClass() +"] cannot be compared.");

		if(returnVal instanceof Comparable){
			
			int compareResult = 0;
			try {
				/*
				 * example : 숫자형을 compareTo 했을때 데이터형이 달라서 String 으로 바꿔서
				 * 비교를 한다면 예로 9 와 10을 비교하면 음수가 나오는것이 아니라 양수가 나오게 된다.
				 * 따라서 숫자형일때는 그형에 맞춰서 비교를 하고 나머지일 경우는 객체 자체를 비교
				 * 그래도 에러가 난다면 문자열로 형변환한 다음에 비교를 하도록 수정하였다.
				 */
				if (compareVal instanceof Integer) {
					compareResult = ((Integer)Integer.parseInt(String.valueOf(returnVal))).compareTo((Integer)compareVal);
				} else if (compareVal instanceof Long) {
					compareResult = ((Long)Long.parseLong(String.valueOf(returnVal))).compareTo((Long)compareVal);
				} else if (compareVal instanceof Float) {
					compareResult = ((Float)Float.parseFloat(String.valueOf(returnVal))).compareTo((Float)compareVal);
				} else if (compareVal instanceof Double) {
					compareResult = ((Double)Double.parseDouble(String.valueOf(returnVal))).compareTo((Double)compareVal);
				} else {
					compareResult = ((Comparable)returnVal).compareTo(compareVal);	
				}
			} catch(Exception e) {
				compareResult = String.valueOf(returnVal).compareTo(String.valueOf(compareVal));
			}

			if( condition.equals(">")){
				return (compareResult>0);
			}
				
			if( condition.equals("<")){
				return (compareResult<0);
			}
			
			if( condition.equals(">=")){
				return (compareResult>0 || compareResult==0);
			}
				
			if( condition.equals("<=")){
				return (compareResult<0 || compareResult==0);
			}
			
		}		
		
		//
		return instance.get(scope, key).equals(compareVal);
	}
	
	public String toString(){
		if(getDescription().getText()!=null)
			return getDescription().getText();
		
		Object k = key;
		
		if(pv!=null)
			k = pv;				
		
		String returnVal = k + " " + condition + " " + val;
		
		return returnVal;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue(){
		return val;
	}
	public void setValue( Object value){
		this.val = value;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public ValidationContext validate(Map options){
		ValidationContext validationContext = super.validate(options);
		
		Activity activity = (Activity)options.get("activity");
		
		Evaluate evaluate = this;
		ProcessVariable pv = activity.getProcessDefinition().getProcessVariable(evaluate.getKey()); 
		if(pv==null)
			validationContext.addWarning(activity.getActivityLabel()+" unrecognized process variable '" + evaluate.key + "' used in a condition expression" );
		else{
			try{
				Class valueType;
				
				if(evaluate.getValue() instanceof ProcessVariable){
					valueType = activity.getProcessDefinition().getProcessVariable(((ProcessVariable)evaluate.getValue()).getName()).getType();
				}else
					valueType = evaluate.getValue().getClass();
									
				if(!valueType.isAssignableFrom(pv.getType())){
					if(evaluate.getValue() instanceof java.lang.Number){
						try{
							pv.getType().asSubclass(Number.class);
						}catch (Exception e) {
							validationContext.addWarning(activity.getActivityLabel()+" value '" + evaluate.getValue() + "' is not comparable to process variable '" + evaluate.key + "'" );
						}
					}else{
						validationContext.addWarning(activity.getActivityLabel()+" value '" + evaluate.getValue() + "' is not comparable to process variable '" + evaluate.key + "'" );
					}
					
					
					
					
				}
			}catch(Exception e){
			}
		}
		return validationContext;		
	}
	
	public static void main(String[] args) throws Exception{
		ProcessDefinition proc = new ProcessDefinition();
		proc.setProcessVariables(new ProcessVariable[]{
			ProcessVariable.forName("testvar")
		});
		
		ProcessInstance inst = new DefaultProcessInstance();
		inst.setProcessTransactionContext(new SimulatorTransactionContext());
		inst.setProcessDefinition(proc);
		inst.set("testvar", new Date(9));
		
		Evaluate cond = new Evaluate("testvar", "<", new Date(10));
		cond.isMet(inst, "");
	}
	
	private boolean checkupContainsCompareVal(Object returnVal, Object compareVal){
		
		boolean contain = false;
		
		if(returnVal instanceof ProcessVariableValue){
			ProcessVariableValue pvv = (ProcessVariableValue) returnVal; 
			do{
				Object unitVal = pvv.getValue();
				if(unitVal instanceof String && compareVal instanceof String){
					if(((String) compareVal).equals((String)unitVal))
							contain = true;
				}
				
			}while(pvv.next());
			
		}
		
		return contain;
	}
	
}