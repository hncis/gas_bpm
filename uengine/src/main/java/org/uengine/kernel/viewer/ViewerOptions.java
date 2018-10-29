package org.uengine.kernel.viewer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.uengine.kernel.Activity;

public class ViewerOptions extends Hashtable<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String SWIMLANE 	= "swimlane";
	public static final String VERTICAL 	= "vertical";
	public static final String HORIZONTAL 	= "horizontal";
	public static final String GANTTCHART 	= "ganttchart";
	
	// DEFAULT KEYSET
	public static final String PREFFERED_ACTIVITY_ICON_WIDTH 				= "prefferedActivityIconWidth";
	public static final String PREFFERED_ACTIVITY_ICON_HEIGHT 				= "prefferedActivityIconHeight";
	public static final String ALL_ACTIVITY_ABSOLUTE_TRACING_TAGS 			= "allActivityAbsoluteTracingTags";
	public static final String INNER_ACTIVITY_ABSOLUTE_TRACING_TAGS 		= "innerActivityAbsoluteTracingTags";
	public static final String HIDDEN_ACTIVITY_TYPES 						= "hiddenActivityTypes";
	public static final String VIEWOPTION_SHOW_HIDDEN_ACTIVITY 			= "show hidden activity";
	public static final String ACTIVITY_PADDING_SIZE 						= "activityPaddingSize";
	public static final String ALIGN 					= "align";
	public static final String VALIGN 					= "valign";
	public static final String IMAGE_PATH_ROOT 		= "imagePathRoot";
	public static final String LOCALE 					= "locale";
	public static final String HIGHLIGHT 				= "highlight";
	public static final String DECORATED 				= "decorated";
	public static final String NOWRAP 					= "nowrap";
	public static final String MAX_LABEL_LENGTH 		= "intMaxLabelLength";
	public static final String MOUSE_OVER_POPUP 		= "mouseOverPopup";
	public static final String VIEW_SUBPROCESS 		= "viewSubProcess";
	public static final String VIEW_FLOWCONTROL = "flowControl";
	
	public ViewerOptions() {}
	
	public void setViewType(String viewType, String viewOption) {
		if (VERTICAL.equals(viewType))
			put(VERTICAL, true);
		else if (HORIZONTAL.equals(viewType))
			put(HORIZONTAL, true);
		else if (GANTTCHART.equals(viewType))
			put(GANTTCHART, true);
		else
			put(SWIMLANE, viewOption);
	}
	
	public void setMouseOverPopUP(String contentType) {
		if ("description".equals(contentType)) {
			put(MOUSE_OVER_POPUP, contentType);
		}
	}
	
	
	public void setMaxLabelLength(int i) {
		put(MAX_LABEL_LENGTH, i);
	}
	
	public int getMaxLabelLength() {
		return (Integer) (containsKey(MAX_LABEL_LENGTH) ? get(MAX_LABEL_LENGTH) : -1);
	}
	
	public void setHiddenActivityTypes(List<Class> list) {
		put(HIDDEN_ACTIVITY_TYPES, list);
	}
	
	public List<Class> getHiddenActivityTypes() {
		return (List<Class>) (containsKey(HIDDEN_ACTIVITY_TYPES) ? get(HIDDEN_ACTIVITY_TYPES) : new ArrayList<Class>());
	}
	
	public void addHiddenActivityType(Class<Activity> cls) {
		List<Class> list = (List<Class>) (containsKey(HIDDEN_ACTIVITY_TYPES) ? get(HIDDEN_ACTIVITY_TYPES) : new ArrayList<Class<Activity>>());
		list.add(cls);
		
		setHiddenActivityTypes(list);
	}
	
	public String getImagePathRoot() {
		return (String) (containsKey(IMAGE_PATH_ROOT) ? get(IMAGE_PATH_ROOT) : "");
	}
	
	
}
