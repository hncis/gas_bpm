package org.uengine.kernel.descriptor;

import org.metaworks.EnablingDependancy;
import org.metaworks.FieldDescriptor;
import org.uengine.kernel.Activity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.LocalEMailActivity;
import org.uengine.kernel.descriptor.EMailActivityDescriptor;
import org.uengine.processdesigner.ProcessDesigner;


/**
 * @author Jinyoung Jang
 */
			 
public class LocalEMailActivityDescriptor extends EMailActivityDescriptor {

	/**
	 * @throws Exception
	 */
	public LocalEMailActivityDescriptor() throws Exception {
		super();
	}
	public void initialize(ProcessDesigner pd, Activity activity) {
		super.initialize(pd, activity);
		
		String contents = GlobalContext.getLocalizedMessage("activitytypes.org.uengine.kernel.localemailactivity.fields.contents.displayname", "Contents");		
		setAttributeIgnoresError("Contents", 	"group", contents);
		setAttributeIgnoresError("TemplateFilePath", 	"group", "���ϳ���");
		
/*		FieldDescriptor fromFd = getFieldDescriptor("From");
		
		fromFd.setAttribute("dependancy", new EnablingDependancy("FromRole"){

			public boolean enableIf(Object fromRoleValue) {
				return (fromRoleValue == null);
			}
			
		});
*/		
		setFieldDisplayNames(LocalEMailActivity.class);
		
	}

	
}