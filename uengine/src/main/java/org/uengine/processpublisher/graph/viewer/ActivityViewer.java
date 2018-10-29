package org.uengine.processpublisher.graph.viewer;

import org.uengine.processpublisher.graph.GraphActivity;
import org.uengine.processpublisher.graph.SwimLaneCoordinate;

public interface ActivityViewer {
	StringBuffer render(GraphActivity graph,SwimLaneCoordinate coodinate ,java.util.Map options);

}
