package org.uengine.kernel;

import javax.naming.InitialContext;

/**
 * @author Jinyoung Jang
 */

public class DefaultActivity extends Activity {
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	public DefaultActivity(String name) {
		setName(name);
	}

	public DefaultActivity() {
		this("");
	}

	public void executeActivity(ProcessInstance instance) throws Exception {
		System.out.println("default activity::execute");

		fireComplete(instance);
	}

	public InitialContext getInitialContext() throws javax.naming.NamingException {
		return new InitialContext();
	}
	
}
