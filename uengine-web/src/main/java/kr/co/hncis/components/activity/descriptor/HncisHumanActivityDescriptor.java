package kr.co.hncis.components.activity.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.descriptor.HumanActivityDescriptor;
import org.uengine.processdesigner.ProcessDesigner;

import kr.co.hncis.components.activity.HncisHumanActivity;

public class HncisHumanActivityDescriptor extends HumanActivityDescriptor{

	public HncisHumanActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);
		
		setAttributeIgnoresError("ExtValue9", 	"hidden", 	true);
		setAttributeIgnoresError("ExtValue10", 	"hidden", 	true);
		
		String authorityControlValue = GlobalContext.getLocalizedMessage("propertygroupname.authoritycontrol", "Authority Control");
		setAttributeIgnoresError("Approve", 	"group", 	authorityControlValue);
		setAttributeIgnoresError("Save", 	"group", 	authorityControlValue);
		setAttributeIgnoresError("Delegate", 	"group", 	authorityControlValue);
		setAttributeIgnoresError("Collect", 	"group", 	authorityControlValue);
		setAttributeIgnoresError("Reject", 	"group", 	authorityControlValue);
		setAttributeIgnoresError("Complete", 	"group", 	authorityControlValue);
		setAttributeIgnoresError("Delete", 	"group", 	authorityControlValue);
		setAttributeIgnoresError("Delete", 	"collapseGroup", 	true);

		setFieldDisplayNames(HncisHumanActivity.class);
	}
	
	

}