package org.uengine.processpublisher.graph;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.uengine.kernel.Activity;
import org.uengine.kernel.AllActivity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.CompositeRole;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.LoopActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.Role;
import org.uengine.kernel.SwitchActivity;
import org.uengine.kernel.viewer.ViewerOptions;
import org.uengine.kernel.viewer.swimlane.SwimlaneViewer;
import org.uengine.util.ActivityForLoop;

public class SwimLaneCoordinate {
	
	int gPointX=1, gPointIndexX=1, gPointY=1;
	int gMaxPointX=1, gMaxPointIndexX=1, gMaxPointY=1; 
	Vector<SwimLanePoint> pointTemp = new Vector<SwimLanePoint>();
	Hashtable roleList = new Hashtable();
	Vector<GraphActivity> graphList = new Vector<GraphActivity>();
	
	public Hashtable getRoleList() {
		return roleList;
	}
	
	public Vector<GraphActivity> getGraphList() {
		return graphList;
	}
	
	public int CountCoodinate(int pointX,int pointIndexX,int pointY){
		for (int i = 0; i < pointTemp.size(); i++) {
			if (
					((SwimLanePoint) pointTemp.get(i)).pointX == pointX 
					&& ((SwimLanePoint) pointTemp.get(i)).pointY == pointY
			) {
				pointIndexX++;
			}
		}
		return pointIndexX;
	}
	
	public boolean CheckVisited(GraphActivity graph) {
		boolean isVisited = false;
		for (int i=0 ; i < graphList.size() ; i++) {
			GraphActivity graphTemp = (GraphActivity) graphList.get(i);
			
			if (graphTemp == graph) {
				isVisited = true;
				break;
			}
		}
        return isVisited;
	}
	
	private boolean hasSameNextGraph(GraphActivity graph1, GraphActivity graph2)
	{
		boolean hasSame = false;
		Vector<GraphActivity> nextGraph1s = graph1.getNext();
		Vector<GraphActivity> nextGraph2s = graph2.getNext();
		
		for(GraphActivity nextGraph1 : nextGraph1s)
		{
			for(GraphActivity nextGraph2 : nextGraph2s)
			{
				if(nextGraph1 == nextGraph2)
				{
					hasSame = true;
					break;
				}
			}
			
			if(hasSame) break;
		}
		
		return hasSame;
	}
	
	private boolean hasSameLinkGraph(GraphActivity graph1, GraphActivity graph2, boolean isCompareNext)
	{
		boolean hasSame = false;
		Vector<GraphActivity> preGraph1s = graph1.getPrevious();
		Vector<GraphActivity> preGraph2s = graph2.getPrevious();
		
		for(GraphActivity preGraph1 : preGraph1s)
		{
			for(GraphActivity preGraph2 : preGraph2s)
			{
				if(preGraph1 == preGraph2)
				{
					hasSame = true;
					break;
				}
				else if(isCompareNext)
				{
					hasSame = hasSameNextGraph(preGraph1, preGraph2);
				}
			}
			
			if(hasSame) break;
		}
		
		return hasSame;
	}
	
	private boolean needSetPointY(GraphActivity graph, int tempPointY)
	{
		boolean isSet = false;
		
		for (GraphActivity graphTemp : graphList)
		{
			if (!graphTemp.isEndGraphActivity && graphTemp.getSLPoint().pointY == tempPointY)
			{
				if(hasSameLinkGraph(graphTemp, graph, true))
				{
					isSet = false;
					break;
				}
				else
				{
					isSet = true;
				}
			}
		}
		
		return isSet;
	}
	
	private void traverseEndGraphActivity(GraphActivity graph) throws Exception
	{
		Vector<GraphActivity> endPreNode = graph.getPrevious();
		int endPointX = 0, endPointY = 0;
		
		for (GraphActivity graphTemp : endPreNode)
		{
			if (graphTemp.getSLPoint() == null)
			{
				continue;
			}
			
			if (endPointX < graphTemp.getSLPoint().pointX)
			{
				endPointX = graphTemp.getSLPoint().pointX;
				
			}
			
			if (endPointY <= graphTemp.getSLPoint().pointY)
			{
				endPointY = graphTemp.getSLPoint().pointY + 1;
			}
		}
		
		SwimLanePoint point = new SwimLanePoint();
		point.pointX = endPointX;
		point.pointIndexX = 1;
		point.pointY = endPointY;

		graph.setSLPoint(point);
	}
	
	private int getDepth(GraphActivity graph)
	{
		int depth = 1;
		
		for(GraphActivity preGraph : graph.incoming)
		{
			if(preGraph.SLPoint != null && preGraph.SLPoint.pointY >= depth)
			{
				depth = preGraph.SLPoint.pointY + 1;
			}
		}
		
		if(needSetPointY(graph, depth))
		{
			for (GraphActivity graphListTemp : graphList)
			{
				if(
						graphListTemp.getSLPoint().pointY > depth && !hasSameLinkGraph(graphListTemp, graph, false)
				)
				{
					graphListTemp.getSLPoint().pointY++;
				}
			}
			depth++;
		}
		
		return depth;
	}
	
	public GraphActivity traverse(GraphActivity graph, Hashtable context, Map options) throws Exception {
		
		Activity act = graph.getReferenceActivity();
		
		boolean isVisited = CheckVisited(graph);
		
		if (!isVisited) {
			int depth = getDepth(graph);
			
			if (act instanceof HumanActivity) {
		    	Role[] roles = getAssignedRoles(act);
		    	
		    	for (int roleCount = 0; roleCount < roles.length; roleCount++) {
		    		int pointX = 0;      //X-sequence
		    		int pointY = 0;      //Y-sequence
		    		int roleIndex = 0;   //rain sequence
		    		int pointIndexX = 0; //X-sequence in rain
		    		
					roleIndex = appendRoleList(roles[roleCount]);
					
					pointX = roleIndex;
					pointY = depth;
					
					pointIndexX = CountCoodinate(pointX, 1, pointY);
					
					SwimLanePoint point = new SwimLanePoint();
					point.pointX = gPointX = pointX;
					point.pointIndexX = gPointIndexX = pointIndexX;
					point.pointY = gPointY = pointY;
					
					graph.setSLPoint(point);
					pointTemp.add(point);
					graphList.add(graph);
		    	}
			}
			else if (act instanceof ComplexActivity)
			{
				ActivityForLoop af = new ActivityForLoop()
				{
					public void logic(Activity activity)
					{
						if (activity instanceof HumanActivity) {
							Role[] roles = getAssignedRoles(activity);
							stop(roles[0]);
						}
					}
				};
				
				af.run(act);
				if ((Role) af.getReturnValue() != null) {
					gPointX = appendRoleList((Role)af.getReturnValue());
				}
				setGraphActivityPoint(graph, depth);
			} else {
				setGraphActivityPoint(graph, depth);
			}
			
			for (GraphActivity graghTemp : graph.getNext()) {
				traverse(graghTemp, context, options);
				
				if (graghTemp.getSLPoint() != null) {
					gPointX = (graghTemp.getSLPoint()).pointX;
				}
			}
		}
		
		setEndGraphActivityPoint();
		
		return graph;
	}
	
	private void setEndGraphActivityPoint() throws Exception
	{
		for (GraphActivity graphListTemp : graphList)
		{
			if(graphListTemp.isEndGraphActivity)
			{
				traverseEndGraphActivity(graphListTemp);
				break;
			}
		}
	}
	
	private void setGraphActivityPoint(GraphActivity graph, int depth)throws Exception{
		gPointY = depth;
		gPointIndexX = CountCoodinate(gPointX, gPointIndexX, gPointY);

		SwimLanePoint point = new SwimLanePoint();
		point.pointX = gPointX;
		point.pointIndexX = gPointIndexX;
		point.pointY = gPointY;
		
		graph.setSLPoint(point);
		pointTemp.add(point);
		graphList.add(graph);
	}
	
	private int appendRoleList(Role role)throws Exception{
		int roleIndex=0;
		if (!roleList.contains(role)) {
			roleIndex = roleList.size() + 1;
			// key = index, value = roleName
			roleList.put(new Integer(roleIndex), role);
		} else {
			int i = 1;
			while (true) {
				if (roleList.get(new Integer(i)).equals(role)) {
					roleIndex = i;
					break;
				}
				i++;
			}
		}
		return roleIndex;
	}
	
	private Role[] getAssignedRoles(Activity act) {
		Role role=null; 
    	Role[] roles=null;
    	if (act instanceof HumanActivity) {
    		role = ((HumanActivity) act).getRole();
    		roles = new Role[1];
    		roles[0] = new Role();
    		roles[0] = role;
    	} 
    	return roles;
	}
	
	
	private LoopActivity getLoopActivity(Activity activity)
	{
		LoopActivity loop = null;
		
		while(activity != null)
		{
			if(activity instanceof LoopActivity)
			{
				loop = (LoopActivity) activity;
				break;
			}
			else
			{
				activity = activity.getParentActivity();
			}
		}
		
		return loop;
	}

	private boolean isLoop(GraphActivity sGraph, GraphActivity tGraph)
	{
		LoopActivity sLoop = getLoopActivity(sGraph.referenceActivity);
		LoopActivity tLoop = getLoopActivity(tGraph.referenceActivity);
		return (sLoop != null && tLoop != null && sGraph.SLPoint.pointY > tGraph.SLPoint.pointY);
	}
	
	public StringBuilder appendCoordinateFunctionScript(ProcessInstance instance , StringBuilder sb, Map options) throws Exception{
		StringBuffer sbTemp = new StringBuffer();
		SwimLaneCoordinate coodinate=this;
		
		for (int i = 0; i < coodinate.getGraphList().size(); i++) {
			String actStatus = "Ready";
			
			SwimlaneViewer viewer = new SwimlaneViewer();
			
			GraphActivity sga = (GraphActivity) coodinate.getGraphList().get(i);
			Activity activityIncomming = sga.getReferenceActivity();
			String sgaTT = viewer.getActivityWebId(sga, instance, options);
			

			Vector<GraphActivity> outgoing = sga.getNext();
			
			for (int j = 0; j < outgoing.size(); j++) {
				GraphActivity tga = (GraphActivity) outgoing.get(j);
				String tgaTT = viewer.getActivityWebId(tga, instance, options);
				
				Activity activityOutgoing = tga.getReferenceActivity();
				if (tga == null || sga == null)
					continue;
				
				if (tga.isEndGraphActivity()) {
					if (activityIncomming != null) {
						actStatus = activityIncomming.getStatus(instance);
						if(!"Completed".equals(actStatus) && !"Skipped".equals(actStatus))
						{
							actStatus = "Ready";
						}
					}
				} else if(sga.isStartGraphActivity()) {
					actStatus = activityOutgoing.getStatus(instance);
				} else {
					if ("Running".equals(actStatus) || "Timeout".equals(actStatus)) {
						if (sga.isStartGraphActivity()) {
							actStatus = "Completed";
						} else {
							actStatus = "Ready";
						}
					} else if (
							activityIncomming instanceof SwitchActivity
							|| activityIncomming instanceof AllActivity
							|| activityIncomming == null
					) {
						actStatus = activityOutgoing.getStatus(instance);
					} else {
						actStatus = activityIncomming.getStatus(instance);
						if(!"Completed".equals(actStatus) && !"Skipped".equals(actStatus))
						{
							actStatus = "Ready";
						}
					}
				}
				
				ViewerOptions viewerOption = new ViewerOptions();
				
				LoopActivity loopActivity = null;
				if (isLoop(sga, tga)) {
					loopActivity = getLoopActivity(activityIncomming);
					String loopLabel = loopActivity.getName().getText();
					sb.append(
							"drawLoopCountForSwimlane(paper, '" + sgaTT 
							+ "', '" + tgaTT
							+ "', '" + options.get(viewerOption.SWIMLANE) 
							+ "', '" + loopLabel 
							+ "', " + loopActivity.getLoopingCount(instance) 
							+ ");\n"
					);
				}
				
				sbTemp.append(
						"drawSwimlaneLine(paper, '" + sgaTT 
						+ "', '" + tgaTT
						+ "', '" + options.get(viewerOption.SWIMLANE) 
						+ "', '" + actStatus 
						+ "', " + sga.getSLPoint().pointX
						+ ");\n"
				);
			}
		}
		return sb.append(sbTemp);
	}
}

