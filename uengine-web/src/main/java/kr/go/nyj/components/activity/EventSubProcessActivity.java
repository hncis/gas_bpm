package kr.go.nyj.components.activity;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ResultPayload;
import org.uengine.kernel.SubProcessActivity;
import org.uengine.processmanager.ProcessManagerBean;

public class EventSubProcessActivity extends SubProcessActivity {
	
	@Override
	protected void afterComplete(ProcessInstance instance) throws Exception {
		super.afterComplete(instance);
		
		//부모 EventHumanActivity에서 생성된 자식 EventSubProcessActivity가 모두 완료 처리될 경우 부모 EventHumanActivity를 완료 처리, 2017-02-23 chonk
//		Activity parent = getParentActivity();
//		
//		while(!(parent instanceof SubProcessAttatchEventHandler)) {
//			parent = parent.getParentActivity();
//		}
//
//		SubProcessAttatchEventHandler eventHandler = (SubProcessAttatchEventHandler) parent;
//		
//		if (eventHandler.getEventHumanActivity() != null) {
//			ProcessManagerBean processManagerBean = instance.getProcessTransactionContext().getProcessManager();
//			processManagerBean.completeWorkitem(
//					instance.getInstanceId(),
//					eventHandler.getEventHumanActivity().getTracingTag(), eventHandler.getEventHumanActivity().getTaskIds(instance)[0],
//					new ResultPayload());
//		}
	}
}
