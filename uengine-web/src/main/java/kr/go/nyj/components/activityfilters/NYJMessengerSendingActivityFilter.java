package kr.go.nyj.components.activityfilters;

import kr.go.nyj.util.CommonUtil;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ActivityFilter;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;

/**
 * 
 * runtime시에 해당 업무에 대한 유저들의 정보를 가져오고
 * 그 유저들에게 신규 업무 알림 메시지를 메신저로 전송하는 액티비티 필터이다.
 * 
 * @author chonk_Enki
 *
 */
public class NYJMessengerSendingActivityFilter implements ActivityFilter {

	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
	
	@Override
	public void afterExecute(Activity activity, ProcessInstance instance) throws Exception {
		CommonUtil.sendStoryZoneMessageToNYJ(activity, instance);
	}

	@Override
	public void afterComplete(Activity activity, ProcessInstance instance)
			throws Exception {
	}

	@Override
	public void beforeExecute(Activity activity, ProcessInstance instance)
			throws Exception {
	}
	
	@Override
	public void onPropertyChange(Activity activity, ProcessInstance instance,
			String propertyName, Object changedValue) throws Exception {
	}

	@Override
	public void onDeploy(ProcessDefinition definition)
			throws Exception {
	}
}
