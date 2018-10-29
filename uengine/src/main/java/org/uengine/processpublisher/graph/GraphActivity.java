package org.uengine.processpublisher.graph;

import org.uengine.kernel.Activity;
import org.uengine.kernel.AllActivity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.EndActivity;
import org.uengine.kernel.LoopActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.SequenceActivity;
import org.uengine.kernel.SwitchActivity;

import org.uengine.kernel.viewer.*;
import java.io.*;
import java.util.*;

public class GraphActivity{

	Vector<GraphActivity>
		incoming = new Vector<GraphActivity>(),
		outgoing = new Vector<GraphActivity>(),
		links = new Vector<GraphActivity>()
	;
	
	String name;
	Activity referenceActivity;
	SwimLanePoint SLPoint;
	boolean isVisited;
	boolean isStartGraphActivity = false;
	boolean isEndGraphActivity = false;
	
	public void setStartGraphActivity() {
		this.isStartGraphActivity = true;;
	}
	
	public void setEndGraphActivity() {
		this.isEndGraphActivity = true;
	}
	
	static Hashtable visited = new Hashtable();
	static Hashtable starters = new Hashtable();
	
	public GraphActivity(String name){
		this.name = name;
	}
	
	public GraphActivity(Activity activity){
		this.name = activity.getName().getText();
		setReferenceActivity(activity);
	}
	
	public SwimLanePoint getSLPoint() {
		return SLPoint;
	}

	public void setSLPoint(SwimLanePoint point) {
		SLPoint = point;
		isVisited=true;
	}
	
	public boolean isVisited(){
		return isVisited;
	}
	
	public String getName(){
		return name;
	}
	
	public Vector<GraphActivity> getNext(){
		return outgoing;
	}
	
	public Vector<GraphActivity> getPrevious(){
		return incoming;
	}

	public boolean isEnd(){
		return outgoing.size() == 0;
	}

	public boolean isStartGraphActivity(){
		return isStartGraphActivity;
	}
	
	public boolean isEndGraphActivity(){
		return isEndGraphActivity;
	}
	
	public boolean isXOR(){
		return this instanceof XORActivity;
	}
	
	public boolean isSplit(){
		return (outgoing.size()>1);
	}
	
	public boolean isJoin(){
		return (incoming.size()>1);
	}
	public synchronized boolean isOnceVisited(){
		return visited.containsKey(this);
	}
	
	public synchronized void flagVisited(){
		visited.put(this, this);
		//System.out.println("act "+name+". flagVisited()" + visited.containsKey(this));
	}
	
	public static synchronized GraphActivity getStarterOf(Activity block){
		return (GraphActivity)starters.get(block);
	}
	
	public synchronized void setAsStarterOf(Activity block){
		starters.put(block, this);
	}
	
	public boolean isPrecedenceOf(GraphActivity act){
		//a matrix can be used rather than this polling
		
		if(act == this) return true;
		
		for(int i=0; i<act.incoming.size(); i++){
			GraphActivity testing = (GraphActivity)act.incoming.elementAt(i);
			if(isPrecedenceOf(testing)) return true;
		}
		
		return false;
	}
	public boolean isCompletePrecedenceOf(GraphActivity act){

		if(act == this) return true;

		if(act.incoming.size()==0) return false;
		for(int i=0; i<act.incoming.size(); i++){
			GraphActivity testing = (GraphActivity)act.incoming.elementAt(i);

			if(act.isXOR()){
				if(isCompletePrecedenceOf(testing)) return true;
			}else{
				if(!isCompletePrecedenceOf(testing)) return false;
			}				
		}
		
		if(act.isXOR())
			return false;
		else 		
			return true;
	}
	
	
	public GraphActivity getJoin(GraphActivity split){ //if this returns null, this is not a split which can form a block
		if(outgoing.size()==0) return null;
		
		for(int i=0; i<outgoing.size(); i++){
			GraphActivity testing = (GraphActivity)outgoing.elementAt(i);
			
			if (testing.incoming.size()>1){
				if(split.isBlockWith(testing)) return testing;
			}
			
			return testing.getJoin(split);
		}
				
		return null;
	}
	public GraphActivity getJoin(){
		return getJoin(this);
	}
	
	public GraphActivity getMinimumBlockSplit(){
		if(incoming.size()==0) return null;
		
		for(int i=0; i<incoming.size(); i++){  //problem: how can I know this block is minimum?
			GraphActivity testing = (GraphActivity)incoming.elementAt(i);
			
			if (testing.isSplit()){
				if(testing.getJoin() != null) return testing;
			}
			
			return testing.getMinimumBlockSplit();
		}

		return null;
	}
		
	
	public GraphActivity getSplit(){ //if this returns null, this is not a join which can form a block
		return null;
	}
	
	public boolean isCompleteSuccessorOf(GraphActivity act){
//System.out.println("in isCompleteSuccessorOf.testing act is "+ act.name);
		if(act == this) return true;

		if(act.outgoing.size()==0) return false;
		for(int i=0; i<act.outgoing.size(); i++){
			GraphActivity testing = (GraphActivity)act.outgoing.elementAt(i);

//			if(act.isXOR()){
//				if(isCompleteSuccessorOf(testing)) return true;
//			}else{
				if(!isCompleteSuccessorOf(testing)) return false;
//			}				
		}
		
//		if(act.isXOR())
//			return false;
//		else
			return true;
	}
	
	public boolean isBlockWith(GraphActivity act){
		return act.isCompleteSuccessorOf(this) && isCompletePrecedenceOf(act);
	}
	
	public boolean linkDrivenNormalize(GraphActivity act, GraphActivity starter, GraphActivity join){

		if(isXOR()){
//			System.out.println("The source block for trying link-driven normailizing should be All-block");
			
			return false; // means failed
		}
		//System.out.println("linkDrivenNormalize");
		
		if(outgoing.size()==0) return false;

		if(!isCompletePrecedenceOf(act)){
			//System.out.println("    ---normalize at top");
			for(int i=0; i<outgoing.size(); i++){
				GraphActivity testing = (GraphActivity)outgoing.elementAt(i);
	
				if(isPrecedenceOf(act)){							
					removeTransition(testing); //cut the transition forming a block
					
					links.add(testing); //replace the transition with a link
					
					if(testing.incoming.size()==0)
						starter.addNext(testing);
					
					return true;
				}
			}
		}
		if(!act.isCompleteSuccessorOf(this)){
			//System.out.println("    ---normalize at bottom");
			for(int i=1; i<act.incoming.size(); i++){
				GraphActivity testing = (GraphActivity)act.incoming.elementAt(i);

				testing.removeTransition(act); //cut the transition forming a block
				testing.links.add(act); //replace the transition with a link
					
				if(testing.outgoing.size()==0)
					testing.addNext(join);		
			}
		}			
				
		return true;
	}
	
	public boolean isNextGraphActivity(GraphActivity next) {
		boolean isNextGraphActivity = true;
		Activity thisActivity = this.getReferenceActivity();

		if (
				thisActivity instanceof EndActivity
				|| (
						thisActivity != null
						&& this.getNext().size() > 0
						&& next.isEndGraphActivity
						&& !(thisActivity.getParentActivity() instanceof LoopActivity)
				)
		) {
			isNextGraphActivity = false;
		}
		
		return isNextGraphActivity;
	}
	
	public GraphActivity addNext(GraphActivity next) {
		if (isNextGraphActivity(next)) {
//			System.out.println("    > addNext from [" + name + "] to [" + next.name+"]");
//			System.out.println("    > next [" + next + "] this [" + this+ "]");
			
			if (!this.outgoing.contains(next))
				this.outgoing.add(next);
			if (!next.incoming.contains(this))
				next.incoming.add(this);
		}
		
		return next;
	}
	
	
	public GraphActivity removeTransition(GraphActivity next){
		//System.out.println("*****linkDrivenNormalize : removed link is from "+name + " to " + next.name);
		this.outgoing.remove(next);	
		next.incoming.remove(this);
		
		return next;
	}
	
	public Activity createBlockActivity(){
		return new DefaultActivity(name);
	}
	
	public static boolean isSequenceBlock(ComplexActivity act){
		return act.getClass() == ComplexActivity.class;
	}
	public static boolean isAllBlock(ComplexActivity act){
		return act.getClass() == AllActivity.class;
	}
	public static boolean isInAllBlock(ComplexActivity act){
		try{
			//System.out.println("isInAllBlock: getParentBlockOf(act) is " + getParentBlockOf(act));
			return getParentBlockOf(act).getClass().getName().equals("org.uengine.kernel.AllActivity");
		}catch(Exception e){
			return false;
		}
	}
	public static ComplexActivity getParentBlockOf(ComplexActivity block){		
		if(isSequenceBlock(block)) block = (ComplexActivity)block.getParentActivity(); //seq�ȿ� �ִٰ� all�� ���� ���

		if(block==null) return null;

		block = (ComplexActivity)block.getParentActivity();
		
		if(block==null) return null;

		if(isSequenceBlock(block)) block = (ComplexActivity)block.getParentActivity(); //�����µ� �űⰡ seq�� ���

		return block;
	}
	public static ComplexActivity getMinimumBlockOf(ComplexActivity block){
				
		if(isSequenceBlock(block)) block = (ComplexActivity)block.getParentActivity(); //seq�ȿ� �ִٰ� all�� ���� ���
		
		GraphActivity split = getStarterOf(block);
		
		while(split.getJoin() == null){
			block = getParentBlockOf(block);
			if(block==null) return null;
			
			split = getStarterOf(block);
		}

		return block;
	}
	
	public GraphActivity convertToBlock(ComplexActivity resultingBlock, GraphActivity starter){
		//System.out.println("----- convertToBlk : "+ name);
		if (outgoing.size()==1 && incoming.size()<=1) {
			//System.out.println("in seq");
			ComplexActivity sequenceBlock;

			
			if (isSequenceBlock(resultingBlock)) {
				sequenceBlock = resultingBlock;
			} else {
				sequenceBlock = new ComplexActivity();				
				resultingBlock.addChildActivity(sequenceBlock);
			}
			
			sequenceBlock.addChildActivity(createBlockActivity());

			GraphActivity next = (GraphActivity)outgoing.elementAt(0);
			
			//System.out.println("in seq: next:" + next.outgoing.size());

//			if(!next.isEnd())
				next = next.convertToBlock(sequenceBlock, starter);
				
			return next;
			
			
		} else if(outgoing.size() > 1) {
			
			//starter = this;
			
			GraphActivity next = null;
			ComplexActivity innerBlock = null;
			ComplexActivity firstBlock = resultingBlock;	
			
			Vector insertedActivities = new Vector(); //for undo
			
			if (isXOR()) { //Switch block
				innerBlock = new SwitchActivity();

			} else {	//All block
				//System.out.println("in all");
				innerBlock = new AllActivity();
				
				if (!isSequenceBlock(resultingBlock)) {
					ComplexActivity sequenceBlock = new ComplexActivity();
												
					resultingBlock.addChildActivity(sequenceBlock);
					resultingBlock = sequenceBlock; 
				}
				
				Activity thisAct = createBlockActivity();
				resultingBlock.addChildActivity(thisAct);
				
				insertedActivities.add(resultingBlock);
				insertedActivities.add(thisAct);
				
			}	
			
			setAsStarterOf(innerBlock);
			resultingBlock.addChildActivity(innerBlock);
			
			insertedActivities.add(innerBlock);
			
			for (int i=0; i<outgoing.size(); i++) {
				next = (GraphActivity)outgoing.elementAt(i); 
				next = next.convertToBlock(innerBlock, this);
				
				if(next == null) break;
			}
			
			if (next != null) {

				//System.out.println("next is " + next.name);
				
				if(!next.isOnceVisited() && !next.isEnd()){
					next.flagVisited();	//flow only once
					
					GraphActivity findingStarter = starter;
					ComplexActivity findingBlock = resultingBlock;
					
					while (findingStarter!=null && findingStarter.isBlockWith(next)) {
						//System.out.println("in going up the level of block, the starter is " + starter.name);
						starter = findingStarter;
						resultingBlock = findingBlock;
						
						findingBlock = getParentBlockOf(resultingBlock);
						if(findingBlock==null) 
							findingStarter = null;
						else 
							findingStarter = getStarterOf(findingBlock);	
					}
					
					//���� ��'�� seq�� �ƴϸ� seq�� ����� �� ��, �ڽ�; �ű⿡ �߰��ؾ���
					if (!(innerBlock instanceof SwitchActivity))
						resultingBlock.addChildActivity(next.createBlockActivity());
	
					((GraphActivity)next.outgoing.elementAt(0)).convertToBlock(resultingBlock, starter);
				}
				
				return next;

			} else {
				//firstBlock.remove();
				//System.out.println("*********rebuild the block");
				
				//recode: '���� all���; ��� '�� seq�� �� �ٷ� ���� ���� seq�� ����־�� ��	
				for(int i=0; i<insertedActivities.size(); i++)
					firstBlock.removeChildActivity((Activity)insertedActivities.elementAt(i));
									
				if(!isInAllBlock(innerBlock)){
					//System.out.println("	-- is not in all-block. the starter is " + starter.name);
					return convertToBlock(firstBlock, starter); //trace again if the first try has failed. the first try would make a little change good for retracing
				}else {
					//System.out.println("	-- is in all-block");
					return null;
				}
			}
			
						
		}else if(incoming.size()>1){	//Join node
//System.out.println("in join: ");
			
			//if(resultingBlock)// testing this join is corresponding to this block
//			System.out.println("in join:act.getParentActivity()" + resultingBlock.getParentActivity());
//			System.out.println("in join:act name = " + name + " and the resultingBlock is " + resultingBlock +" isInAllBlock is " + isInAllBlock(resultingBlock));
//			System.out.println("in join:the starter is " + starter.name);
			
			if (isInAllBlock(resultingBlock) && isXOR()) {
//				System.out.println(" case 1");
				GraphActivity next = ((GraphActivity)outgoing.elementAt(0)).convertToBlock(resultingBlock, starter); //skip this XOR act and continue tracing
				return next;
			}else if(!isInAllBlock(resultingBlock) && isXOR() && !starter.isBlockWith(this)){
//				System.out.println(" case 2: XOR split "+ starter.name );
				GraphActivity next = ((GraphActivity)outgoing.elementAt(0)).convertToBlock(resultingBlock, starter); //skip this XOR act and continue tracing
				return next;
			}else

/*			if(!isInAllBlock(resultingBlock) && !isXOR()){
System.out.println(" case 3");
				resultingBlock.addChildActivity(createBlockActivity()); //don't skip and continue tracing
				GraphActivity next = ((GraphActivity)outgoing.elementAt(0)).convertToBlock(resultingBlock, starter); //skip this XOR act and continue tracing
				
				return next;
			}else */
			{
			
			
//case 4: ���� ������� ���; ��� link�� ����� ��'�� all-block�� ���Խ�Ŵ

				ComplexActivity superBlock = getMinimumBlockOf(resultingBlock);
//				System.out.println(" case 4: resultingBlock is " + resultingBlock);
//				System.out.println(" 	   : isBlockWith " + starter.name + " = " + starter.isBlockWith(this));
//				System.out.println(" 	   : superBlock1 is " + superBlock);

				if(!isXOR() && !starter.isBlockWith(this) && /*isInAllBlock(resultingBlock) && */superBlock!=null && isAllBlock(superBlock)){
//					superBlock = getParentBlockOf(superBlock);
//System.out.println("	: superBlock2 is " + superBlock);
					
					if(superBlock!=null)
					{
						GraphActivity superStarter = getStarterOf(superBlock);
						GraphActivity superJoin = superStarter.getJoin();
//						System.out.println("	: starter is " + superStarter.name);
//						System.out.println("	: starter is " + superStarter.name + " and the corr. join is " + superJoin.name);
						//inner Block ���� �� ����8�� �׸�
						if(this == superJoin) return this;
						
						starter.linkDrivenNormalize(this, superStarter, superJoin);
						//starter.convertToBlock(superBlock, superStarter);
						
						//removeBlock(resultingBlock);
						
						return null;
					}
				}
				
/*				if(isInAllBlock(resultingBlock) && !isXOR()){
	System.out.println(" case 5");
					resultingBlock.addChildActivity(createBlockActivity()); //don't skip and continue tracing
					GraphActivity next = ((GraphActivity)outgoing.elementAt(0)).convertToBlock(resultingBlock, starter); //skip this XOR act and continue tracing
					
					return next;
		
				}*/
			}
			
										
			return this;	// stop recursive call, '�� all�̳� switch����� �� transition; ������ �� �ٰ���
		}
		
//		System.out.println("------------ no case is met -------------");

		return this;

	}
	
	public static GraphActivity[] createTestGraphProcess(){
		GraphActivity[] activities = new GraphActivity[21];

		Hashtable XORActivities = new Hashtable();
		XORActivities.put("3", "");
		XORActivities.put("6", "");
		XORActivities.put("14", "");
		XORActivities.put("18", "");
		
		for(int i=0; i<activities.length; i++){
			if(XORActivities.containsKey(""+i)) 
				activities[i] = new XORActivity("XOR act: "+i);
			else 
				activities[i] = new GraphActivity("act: "+i);
		}
			

		activities[0]
			.addNext(activities[2])
			.addNext(activities[3])
			.addNext(activities[5])
			.addNext(activities[7])
			.addNext(activities[10])
			.addNext(activities[15])
			.addNext(activities[17])
			.addNext(activities[18])
			.addNext(activities[19])
			.addNext(activities[20]);
		
		activities[10]
			.addNext(activities[14])
			.addNext(activities[16])
			.addNext(activities[17]);

		activities[3]
			.addNext(activities[4])
			.addNext(activities[6])
			.addNext(activities[9])
			.addNext(activities[14]);

		activities[6]
			.addNext(activities[8])
			.addNext(activities[12])
			.addNext(activities[18]);
			

System.out.println(activities[6].isBlockWith(activities[18])); //true
System.out.println(activities[18].isCompleteSuccessorOf(activities[3])); //true
System.out.println(activities[3].isCompletePrecedenceOf(activities[18])); //true
System.out.println(activities[10].isCompletePrecedenceOf(activities[17])); //true
System.out.println(activities[17].isCompleteSuccessorOf(activities[10])); //true
System.out.println(activities[3].isBlockWith(activities[18])); //true

		return activities;
	}		
	
	public static GraphActivity[] createTestGraphProcess2(){
		GraphActivity[] activities = new GraphActivity[37];

		Hashtable XORActivities = new Hashtable();
		XORActivities.put("23", "");
		XORActivities.put("26", "");
		XORActivities.put("29", "");
		XORActivities.put("31", "");
		XORActivities.put("34", "");
		
		for(int i=0; i<activities.length; i++){
			if(XORActivities.containsKey(""+i)) 
				activities[i] = new XORActivity("XOR act: "+i);
			else 
				activities[i] = new GraphActivity("act: "+i);
		}

		activities[0]
			.addNext(activities[23])
			.addNext(activities[24])
			.addNext(activities[25])
			.addNext(activities[26])
			.addNext(activities[27])
			.addNext(activities[33])
			.addNext(activities[34])
			.addNext(activities[9]);
		
		activities[24]
			.addNext(activities[31])
			.addNext(activities[32])
			.addNext(activities[33]);

		activities[23]
			.addNext(activities[28])
			.addNext(activities[29])
			.addNext(activities[30])
			.addNext(activities[26]);

		activities[30]
			.addNext(activities[31]);
			
		activities[29]
			.addNext(activities[35])
			.addNext(activities[36])
			.addNext(activities[34]);
			

System.out.println(activities[6].isBlockWith(activities[18])); //true
System.out.println(activities[18].isCompleteSuccessorOf(activities[3])); //true
System.out.println(activities[3].isCompletePrecedenceOf(activities[18])); //true
System.out.println(activities[10].isCompletePrecedenceOf(activities[17])); //true
System.out.println(activities[17].isCompleteSuccessorOf(activities[10])); //true
System.out.println(activities[3].isBlockWith(activities[18])); //true

		return activities;
	}	
		
	public static GraphActivity[] createTestGraphProcess3(){
		GraphActivity[] activities = new GraphActivity[37];
		
		for(int i=0; i<activities.length; i++){
			activities[i] = new GraphActivity("act: "+i);
		}

		activities[0]
			.addNext(activities[1])
			.addNext(activities[2])
			.addNext(activities[6])
			.addNext(activities[7])
			.addNext(activities[8])
			.addNext(activities[9])
			.addNext(activities[10]);
		
		activities[6]
			.addNext(activities[15])
			.addNext(activities[8]);
		
		activities[1]
			.addNext(activities[12])
			.addNext(activities[13])
			.addNext(activities[14])
			.addNext(activities[15]);
			
		activities[1]
			.addNext(activities[16])
			.addNext(activities[12]);
			
		activities[12]
			.addNext(activities[18])
			.addNext(activities[19])
			.addNext(activities[9]);
			
		activities[16]
			.addNext(activities[17])
			.addNext(activities[18]);

//		System.out.println("	: " + activities[1].name + " is block with 9 " + activities[1].isBlockWith(activities[9]));

System.out.println(activities[6].isBlockWith(activities[18])); //true
System.out.println(activities[18].isCompleteSuccessorOf(activities[3])); //true
System.out.println(activities[3].isCompletePrecedenceOf(activities[18])); //true
System.out.println(activities[10].isCompletePrecedenceOf(activities[17])); //true
System.out.println(activities[17].isCompleteSuccessorOf(activities[10])); //true
System.out.println(activities[3].isBlockWith(activities[18])); //true

		return activities;
	}	
	
	public static void main(String[] args) throws Exception{
		GraphActivity[] activities = createTestGraphProcess3();
		
		ProcessDefinition proc = new ProcessDefinition();
		activities[0].convertToBlock(proc, activities[0]);
				
		StringBuilder sb = ProcessDefinitionViewer.getInstance().render(proc, null, new Hashtable());
		
		sb.append("<ul>links<br>");
		for(int i=0; i<activities.length; i++){
			for(int j=0; j<activities[i].links.size(); j++){
				sb.append("<li> [" + activities[i].name + "] to [" + ((GraphActivity)activities[i].links.elementAt(j)).name + "]");
			}
		}		
		sb.append("</ul>");
		
		FileWriter f = new FileWriter("result.process.html");
		f.write(sb.toString());
		f.close();
		//f.close();		
	}

	public Activity getReferenceActivity() {
		return referenceActivity;
	}

	public void setReferenceActivity(Activity referenceActivity) {
		this.referenceActivity = referenceActivity;
	}

	GraphActivity graphPreNode = null;
	//Vector endNodeList = new Vector();
	public void getEndNodeList(GraphActivity graph, Map graphList, Vector<GraphActivity> endNodeList){
		
		boolean isVisited = graphList.containsKey(graph);
		
		if (!isVisited) {
			for (int i = 0; i < graph.getNext().size(); i++) {
				GraphActivity graghTemp = (GraphActivity) graph.getNext().get(i);
				graphList.put(graph,graph);
				graphPreNode = graph;
				getEndNodeList(graghTemp, graphList, endNodeList);
			}
			
			if (graph.isEnd())
				endNodeList.add(graph);
		} else {
			GraphActivity graphTemp = graphPreNode;
			Vector<GraphActivity>  graphPre= graphTemp.getNext();
			if (graphPre.size() == 1) {
				endNodeList.add(graphTemp);
			}
		}

		//return endNodeList;
	}


}
