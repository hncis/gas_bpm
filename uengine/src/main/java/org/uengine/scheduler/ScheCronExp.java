package org.uengine.scheduler;

import java.io.Serializable;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.metaworks.inputter.RadioInput;

public class ScheCronExp implements Serializable {
	
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	public ScheCronExp() {
		this.setType(Boolean.TRUE);
	}
	
	public static void metaworksCallback_changeMetadata(Type type){
		type.setName("Scheduler CronExpression");
		type.setFieldOrder(new String[] { 
				"Type", "ManualScheCronExp", "AutoScheCronExp"
		});
		
		FieldDescriptor fd;
		fd = type.getFieldDescriptor("Type");
		fd.setInputter(
			new RadioInput(
				new String[] { "Manual", "Auto" },
				new Object[] { Boolean.TRUE, Boolean.FALSE }
			)
		);
	}

	boolean type;
		public boolean isType() {
			return type;
		}
		public void setType(boolean type) {
			this.type = type;
		}

	ManualScheCronExp manualScheCronExp;
		public ManualScheCronExp getManualScheCronExp() {
			return manualScheCronExp;
		}
		public void setManualScheCronExp(ManualScheCronExp manualScheCronExp) {
			this.manualScheCronExp = manualScheCronExp;
		}

	AutoScheCronExp autoScheCronExp;
		public AutoScheCronExp getAutoScheCronExp() {
			return autoScheCronExp;
		}
		public void setAutoScheCronExp(AutoScheCronExp autoScheCronExp) {
			this.autoScheCronExp = autoScheCronExp;
		}

}
