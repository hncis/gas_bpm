package org.uengine.kernel.viewer;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ProcessInstance;

/**
 * @author Jinyoung Jang
 */

public interface ActivityViewer{
	StringBuilder render(Activity activity, ProcessInstance instance, java.util.Map options);
}