package kr.co.hncis.components.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.uengine.kernel.Activity;
import org.uengine.kernel.FlagActivity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.Role;
import org.uengine.kernel.RoleMapping;
import org.uengine.processmanager.ProcessTransactionContext;

public class HncisHumanActivity extends HumanActivity {

	public HncisHumanActivity() {
	}
	
	static {
		org.uengine.kernel.descriptor.ActivityDescriptor.fieldOrder.insertElementAt("Approve", 0);
		org.uengine.kernel.descriptor.ActivityDescriptor.fieldOrder.insertElementAt("Save", 	0);
		org.uengine.kernel.descriptor.ActivityDescriptor.fieldOrder.insertElementAt("Delegate", 0);
		org.uengine.kernel.descriptor.ActivityDescriptor.fieldOrder.insertElementAt("Collect", 0);
		org.uengine.kernel.descriptor.ActivityDescriptor.fieldOrder.insertElementAt("Reject", 0);
		org.uengine.kernel.descriptor.ActivityDescriptor.fieldOrder.insertElementAt("Complete", 0);
		org.uengine.kernel.descriptor.ActivityDescriptor.fieldOrder.insertElementAt("Delete", 0);
	}
	
	boolean isAutoCompleteByHistory;
		public boolean isAutoCompleteByHistory() {
			return isAutoCompleteByHistory;
		}
		public void setAutoCompleteByHistory(boolean isAutoCompleteByHistory) {
			this.isAutoCompleteByHistory = isAutoCompleteByHistory;
		}
	boolean approve;
		public boolean isApprove() {
			return approve;
		}
		public void setApprove(boolean approve) {
			this.approve = approve;
		}
	boolean save;
		public boolean isSave() {
			return save;
		}
		public void setSave(boolean save) {
			this.save = save;
		}
	boolean delegate;
		public boolean isDelegate() {
			return delegate;
		}
		public void setDelegate(boolean delegate) {
			this.delegate = delegate;
		}
	boolean collect;
		public boolean isCollect() {
			return collect;
		}
		public void setCollect(boolean collect) {
			this.collect = collect;
		}
	boolean reject;
		public boolean isReject() {
			return reject;
		}
		public void setReject(boolean reject) {
			this.reject = reject;
		}
	boolean complete;
		public boolean isComplete() {
			return complete;
		}
		public void setComplete(boolean complete) {
			this.complete = complete;
		}
	boolean delete;
		public boolean isDelete() {
			return delete;
		}
		public void setDelete(boolean delete) {
			this.delete = delete;
		}

	@Override
	public void executeActivity(ProcessInstance instance)
			throws Exception {
		// TODO Auto-generated method stub
		super.executeActivity(instance);
	}

	

}
