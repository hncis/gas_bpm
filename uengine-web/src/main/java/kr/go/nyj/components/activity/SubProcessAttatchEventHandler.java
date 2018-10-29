package kr.go.nyj.components.activity;

import org.uengine.kernel.AllActivity;
import org.uengine.kernel.SequenceActivity;
import org.uengine.kernel.SubProcessActivity;

public class SubProcessAttatchEventHandler extends SequenceActivity {
	
	public SubProcessAttatchEventHandler() {
		AllActivity eventAllActivity = new AllActivity();
		SubProcessActivity eventSubProcessActivity = new EventSubProcessActivity();
		setEventAllActivity(eventAllActivity);
		eventAllActivity.addChildActivity(eventSubProcessActivity);
		EventHumanActivity eventHumanActivity = new EventHumanActivity();
		eventHumanActivity.setEventSubProcessActivity(eventSubProcessActivity);
		addChildActivity(eventHumanActivity);
		addChildActivity(eventAllActivity);
	}
	
	AllActivity eventAllActivity;
	
	public AllActivity getEventAllActivity() {
		return eventAllActivity;
	}
	
	public void setEventAllActivity(AllActivity eventAllActivity) {
		this.eventAllActivity = eventAllActivity;
	}
	
	EventHumanActivity eventHumanActivity;
	
	public EventHumanActivity getEventHumanActivity() {
		return eventHumanActivity;
	}

	public void setEventHumanActivity(EventHumanActivity eventHumanActivity) {
		this.eventHumanActivity = eventHumanActivity;
	}
}
