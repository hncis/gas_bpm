package com.defaultcompany.external.activities;

import org.metaworks.Type;
import org.uengine.kernel.HumanActivity;

public class ExternalHumanActivity extends HumanActivity {
	
	private static final long serialVersionUID = 1L;
	
	public static void metaworksCallback_changeMetadata(Type type) {
//		type.getFieldDescriptor("ExtValue1").setDisplayName("주 화면 아이디");
//		type.getFieldDescriptor("ExtValue2").setDisplayName("서브 화면 아이디");
	}

	public ExternalHumanActivity() {
		super();
		setName("Human Work");
		setDuration(5);
		setTool("externalHandler");
	}

}
