package org.uengine.processdesigner.inputters;

import org.uengine.processdesigner.ProcessDesigner;
import java.util.*;

/**
 * @author Jinyoung Jang
 */

public class org_uengine_kernel_ProcessVariableInput extends ProcessVariableInput {
	Properties options = new Properties(){
		public String getProperty(String k){
			return "yes";
		}
	};

}