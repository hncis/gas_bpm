package org.uengine.kernel.viewer;

import java.util.Map;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.util.UEngineUtil;

/**
 * @author Jinyoung Jang
 */

public class EmptyActivityViewer extends DefaultActivityViewer{

	public StringBuilder render(
			Activity activity,
			ProcessInstance instance,
			Map options
	) {
		String actName = activity.getName().getText();

		if("Skip".equals(actName) || !UEngineUtil.isNotEmpty(actName))
			return new StringBuilder("<img src=").append(getIconImageURL(activity, instance, options)).append(" border=0>");

		else
			return super.render(activity, instance, options);
	}

}