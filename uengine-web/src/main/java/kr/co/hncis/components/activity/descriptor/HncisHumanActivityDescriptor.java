package kr.co.hncis.components.activity.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.descriptor.HumanActivityDescriptor;
import org.uengine.processdesigner.ProcessDesigner;

import kr.co.hncis.components.activity.HncisHumanActivity;

public class HncisHumanActivityDescriptor extends HumanActivityDescriptor{

	private static final String GROUP_ATTR_NAME = "group";
	
	public HncisHumanActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
		
		setAttributeIgnoresError("ExtValue9", 	"hidden", 	true);
		setAttributeIgnoresError("ExtValue10", 	"hidden", 	true);
		
		String authorityControlValue = GlobalContext.getLocalizedMessage("propertygroupname.authoritycontrol", "Authority Control");
		setAttributeIgnoresError("Approve", 	GROUP_ATTR_NAME, 	authorityControlValue);
		setAttributeIgnoresError("Save", 	GROUP_ATTR_NAME, 	authorityControlValue);
		setAttributeIgnoresError("Delegate", 	GROUP_ATTR_NAME, 	authorityControlValue);
		setAttributeIgnoresError("Collect", 	GROUP_ATTR_NAME, 	authorityControlValue);
		setAttributeIgnoresError("Reject", 	GROUP_ATTR_NAME, 	authorityControlValue);
		setAttributeIgnoresError("Complete", 	GROUP_ATTR_NAME, 	authorityControlValue);
		setAttributeIgnoresError("Delete", 	GROUP_ATTR_NAME, 	authorityControlValue);
		setAttributeIgnoresError("Delete", 	"collapseGroup", 	true);

		setFieldDisplayNames(HncisHumanActivity.class);
	}
	
	

}