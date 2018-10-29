/*
 * Created on 2004. 10. 12.
 */
package org.uengine.kernel;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.designer.*;
import org.uengine.processdesigner.*;
//import org.openadaptor.adaptor.editor.AFEditor;
//import org.openadaptor.adaptor.editor.AdaptorListener;

/**
 * @author Jinyoung Jang
 */
public class OpenAdaptorActivity extends DefaultActivity{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	public OpenAdaptorActivity(){
		super();
		setName("Open adaptor");
	}
	
	public ActivityDesigner createDesigner() {
		ActivityDesigner designer = new OpenAdaptorActivityDesigner();
		designer.setActivity(this);
		
		return designer;
	}
	
	public void executeActivity(ProcessInstance instance) throws Exception {
		super.executeActivity(instance);
	}

}

class OpenAdaptorActivityDesigner extends DefaultActivityDesigner{
	public OpenAdaptorActivityDesigner(){
		super();
	}
	
	public void openDialog() {
//		AFEditor xadaptor = new AFEditor();
//		String[] args = new String[0];
//		xadaptor.main(args);
	}

}