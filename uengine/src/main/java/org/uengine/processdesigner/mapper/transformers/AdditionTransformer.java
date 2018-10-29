package org.uengine.processdesigner.mapper.transformers;

import java.util.Map;

import org.metaworks.Type;
import org.uengine.kernel.ProcessInstance;
import org.uengine.processdesigner.mapper.Transformer;

public class AdditionTransformer extends Transformer {
	public static void metaworksCallback_changeMetadata(Type type) {
	}

	public AdditionTransformer() {
		setName("Addition");
	}

	public String[] getInputArguments() {
		return new String[] { "int1", "int2", "int3", "int4", "int5" };
	}

	@Override
	public Object transform(ProcessInstance instance, Map parameterMap, Map options) {
		int strTmp = 0;
		for (int i = 1; i <= parameterMap.size(); i++) {
			if (parameterMap.get("int" + i) != null)
				strTmp += Integer.parseInt(parameterMap.get("int" + i).toString());
		}
		
		return String.valueOf(strTmp);
	}
}
