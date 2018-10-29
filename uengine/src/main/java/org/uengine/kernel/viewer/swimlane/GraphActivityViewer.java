package org.uengine.kernel.viewer.swimlane;

import org.uengine.kernel.ProcessInstance;
import org.uengine.processpublisher.graph.GraphActivity;
import org.uengine.processpublisher.graph.SwimLaneCoordinate;

public interface GraphActivityViewer{
	StringBuilder render(GraphActivity graph, SwimLaneCoordinate coodinate ,ProcessInstance instance,java.util.Map options);

}
