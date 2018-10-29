package org.uengine.processdesigner.mapper.transformers;

import java.util.HashMap;
import java.util.Map;

import org.uengine.kernel.DefaultProcessInstance;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.RoleMapping;

import junit.framework.TestCase;

public class RegularExpValidatorTransformerTest extends TestCase {

	public void testMain() throws Exception{
		
		
		ProcessInstance.USE_CLASS = DefaultProcessInstance.class;
		ProcessInstance pi = DefaultProcessInstance.create();

		String fieldName = "value";
		String fieldName1 = "value1";
		String fieldName2 = "value2";
		String fieldName3 = "value3";
		String errorSuffix = " should be a number";

		RegularExpValidatorTransformer revt = new RegularExpValidatorTransformer();
		revt.setRegularExpression("[0-9]");
		revt.setValidationMessage(errorSuffix);
		
		revt.getArgumentSourceMap().put(revt.getInputArguments()[0], fieldName);
		revt.getArgumentSourceMap().put(revt.getInputArguments()[1], fieldName1);
		revt.getArgumentSourceMap().put(revt.getInputArguments()[2], fieldName2);
		revt.getArgumentSourceMap().put(revt.getInputArguments()[3], fieldName3);
		
		revt.setAliasesForEachInputArgument(new String[]{"value field1", "value field2"});
		
		Map options = new HashMap();
		options.put(org.uengine.processdesigner.mapper.Transformer.OPTION_KEY_OUTPUT_ARGUMENT, "out2");
		options.put(org.uengine.processdesigner.mapper.Transformer.OPTION_KEY_FORM_FIELD_NAME, fieldName2);

//		pi.set("", fieldName, "a string");
//		pi.set("", fieldName1, "a string2");
//		pi.set("", fieldName2, "a string3");
//		pi.set("", fieldName3, "a string4");
		pi.set("", fieldName, "1");
		pi.set("", fieldName1, "2");
		pi.set("", fieldName2, "3");
		pi.set("", fieldName3, "4");
		
		try{
			Object output = revt.letTransform(pi, options);
			assertTrue("a string2".equals(output));
			
		}catch(Exception e){
			assertTrue(e.getMessage().equals(fieldName + " " + fieldName1 + " " + fieldName2 + " " + fieldName3 + " " + errorSuffix));
			return;
		}
		
		assertTrue(false);
	}
	
}
