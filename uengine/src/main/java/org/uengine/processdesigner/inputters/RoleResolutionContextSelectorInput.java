package org.uengine.processdesigner.inputters;

import org.metaworks.inputter.Picker;
import org.metaworks.inputter.PickerInput;
import org.uengine.processdesigner.RolePicker;

/**
 * @author Jinyoung Jang
 */

public class RoleResolutionContextSelectorInput extends PickerInput{

	public RoleResolutionContextSelectorInput(){
		super();
	}

	public Picker getNewPicker() {
		RolePicker rolePicker = RolePicker.create(null);
		
		rolePicker.setModal(true);
		
		return rolePicker;
	}


}