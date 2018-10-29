package org.uengine.processpublisher.graph.exporter;

import java.io.*;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.uengine.kernel.Activity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.HumanActivity;
import org.uengine.kernel.LoopActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.Role;
import org.uengine.kernel.SQLActivity;
import org.uengine.kernel.SequenceActivity;
import org.uengine.kernel.SwitchActivity;
import org.uengine.kernel.viewer.swimlane.SwimlaneViewer;
import org.uengine.processpublisher.Adapter;
import org.uengine.processpublisher.graph.GraphActivity;
import org.uengine.processpublisher.graph.SwimLaneCoordinate;
import org.uengine.processpublisher.graph.SwimLanePoint;


public class ProcessDefinitionAdapter extends ComplexActivityAdapter{
	static Hashtable adapters = new Hashtable();

	
	public Object convert(Object src, Hashtable keyedContext) throws Exception {
		GraphActivity graph = (GraphActivity) super.convert(src, keyedContext);
		ProcessDefinition processDefinition = (ProcessDefinition) src;
		
		GraphActivity start = new GraphActivity(processDefinition);
		start.setStartGraphActivity();
		start.addNext(graph);
		
		GraphActivity end = new GraphActivity(processDefinition);
		end.setEndGraphActivity();
		
		Hashtable Context = new Hashtable();
		Vector<GraphActivity> endNodeList = new  Vector<GraphActivity>();
		start.getEndNodeList(start, Context, endNodeList);
	
		for(int i=0 ; i <endNodeList.size(); i++){
			GraphActivity graphEndNode = endNodeList.get(i);
			graphEndNode.addNext(end);
		}

		return start;
	}

	public static Adapter getAdapter(Class activityType){
		Class orgActivityType = activityType;
		
		if(adapters.containsKey(activityType))
			return (Adapter)adapters.get(activityType);
		
		Adapter adapter = null;
		do{	
			try{
				String activityTypeName = org.uengine.util.UEngineUtil.getClassNameOnly(activityType);
				adapter = (Adapter)Class.forName("org.uengine.processpublisher.graph.exporter." + activityTypeName + "Adapter").newInstance();
				
				adapters.put(orgActivityType, adapter);
			}catch(Exception e){
				activityType = activityType.getSuperclass();
			}
		}while(adapter==null && activityType!=Object.class);

		if(adapter==null)			
			System.out.println("ProcessDefinitionAdapter::getAdapter : can't find adapter for " + activityType);
			
		return adapter;
	}
	
	public static void main(String[] args) throws Exception{
		
		Activity act;
		ComplexActivity cAct1, cAct2;
		
		Role role1 = new Role("Role1");
		Role role2 = new Role("Role2");
		Role role3 = new Role("Role3");
		
		ProcessDefinition def = new ProcessDefinition();{
			act = new HumanActivity();
			act.setName("report trouble");
			((HumanActivity) act).setRole(role1);
			def.addChildActivity(act);

			act = new HumanActivity();
			act.setName("set the right person");
			((HumanActivity) act).setRole(role2);			
			def.addChildActivity(act);
			
			cAct1 = new SwitchActivity();
			cAct1.setName("switch1");
			def.addChildActivity(cAct1);{
				cAct2 = new SequenceActivity();
				cAct1.addChildActivity(cAct2);{
					act = new HumanActivity();
					act.setName("Draft with resolution");
					((HumanActivity) act).setRole(role3);
					cAct2.addChildActivity(act);
					
					act = new SQLActivity();
					act.setName("Database");
					cAct2.addChildActivity(act);
				}
				cAct2 = new LoopActivity();
				cAct1.addChildActivity(cAct2);{
					act = new HumanActivity();
					act.setName("Suggestion");
					((HumanActivity) act).setRole(role3);
					cAct2.addChildActivity(act);
					
					act = new HumanActivity();
					act.setName("examine");
					((HumanActivity) act).setRole(role2);
					cAct2.addChildActivity(act);
				}
			}
		}
		
		ProcessDefinitionAdapter adapter = new ProcessDefinitionAdapter();
		Hashtable keyedContext=new Hashtable();
		keyedContext.put("main","scope");
		GraphActivity graph = (GraphActivity)adapter.convert(def, keyedContext);
		
		SwimLaneCoordinate coodinate = new SwimLaneCoordinate();
//		graph = coodinate.traverse(graph, null);
		
//		SwimlaneViewer viewer = new SwimlaneViewer();
	//`		StringBuffer sb = viewer.render(graph,coodinate,instance,null);
//		
//		PrintWriter out1 =
//	          new PrintWriter(
//	            new BufferedWriter(
//	              new FileWriter("c://a.html")));

//		out1.print(sb);

//		out1.close();
		
	}

}
