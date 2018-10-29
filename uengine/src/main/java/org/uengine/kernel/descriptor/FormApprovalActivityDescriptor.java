package org.uengine.kernel.descriptor;

import org.uengine.kernel.Activity;
import org.uengine.kernel.FormApprovalActivity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.HumanActivity;
import org.uengine.processdesigner.ProcessDesigner;

/**
 * 
 * @author <a href="mailto:bigmahler@users.sourceforge.net">Jong-Uk Jeong</a>
 * @version $Id: FormApprovalActivityDescriptor.java,v 1.5 2010/05/14 08:41:05 curonide Exp $
 * @version $Revision: 1.5 $
 */
public class FormApprovalActivityDescriptor extends FormActivityDescriptor{

	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;

	public FormApprovalActivityDescriptor() throws Exception{
		super();
	}
	
	public void initialize(ProcessDesigner pd, Activity activity){
		super.initialize(pd, activity);

		setAttributeIgnoresError("Approver", 	"hidden", true);

		setFieldDisplayNames(FormApprovalActivity.class);
	}

}