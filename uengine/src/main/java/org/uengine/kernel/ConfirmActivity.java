package org.uengine.kernel;

import java.util.Map;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.metaworks.inputter.RadioInput;

public class ConfirmActivity extends FormActivity {
	
	public static void metaworksCallback_changeMetadata(Type type){
		
		FieldDescriptor fd = type.getFieldDescriptor("AutoComplete");
		fd.setInputter(new RadioInput(
				new String[]{
						"Yes",
						"No"
				}, new Object[]{
						true, 
						false						
				}
			)
		);
		FieldDescriptor fd2 = type.getFieldDescriptor("ViewMode");
		fd2.setInputter(new RadioInput(
				new String[]{
						"Yes",
						"No"
				}, new Object[]{
						true, 
						false					
				}
			)
		);
		
	}
	
	public ConfirmActivity() {
		super();
		setName("Confirm");
		setAutoComplete(false);
		setViewMode(false);
	}

	public String getTool() {
		return "confirmHandler";
	}
	
	boolean isAutoComplete;
		public boolean isAutoComplete() {
			return isAutoComplete;
		}
	
		public void setAutoComplete(boolean isAutoComplete) {
			this.isAutoComplete = isAutoComplete;
		}
	
	boolean isViewMode;
		public boolean isViewMode() {
			return isViewMode;
		}
	
		public void setViewMode(boolean isViewMode) {
			this.isViewMode = isViewMode;
		}

		@Override
	protected void onSave(ProcessInstance instance, Map parameterMap_)
			throws Exception {
		if(!isViewMode()){
			super.onSave(instance, parameterMap_);
		}		
	}
}
