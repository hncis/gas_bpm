/*
 * Created on 2004. 12. 27.
 */
package org.uengine.test;

import junit.framework.*;

import org.uengine.processmanager.*;
import org.uengine.kernel.Activity;
import org.uengine.kernel.AllActivity;
import org.uengine.kernel.ComplexActivity;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.SequenceActivity;



import javax.naming.InitialContext;

import java.util.*;

/**
 * @author Jinyoung Jang
 */
public class ActivityTracingTest extends TestCase{
	
	ProcessManagerRemote pm;
	ProcessDefinition definition;
	InitialContext jndiContext;
	
	Activity currActivity;
	
	protected void setUp() throws Exception {
		definition = createProcessDefinition();
	}
	
	public void testMain() throws Exception{
		List<Activity> prevActs = currActivity.getPreviousActivities();
		
		assertTrue(prevActs.size() == 2);
	}
	
	public ProcessDefinition createProcessDefinition(){
		ProcessDefinition def = new ProcessDefinition();{
			ComplexActivity all1 = new AllActivity();{
				SequenceActivity seq11 = new SequenceActivity();{
					DefaultActivity act111 = new DefaultActivity("111");
					seq11.setChildActivities(new Activity[]{act111});
				}		

				AllActivity all12 = new AllActivity();{
					SequenceActivity seq12 = new SequenceActivity();{
						DefaultActivity act111 = new DefaultActivity("1211");
						seq12.setChildActivities(new Activity[]{act111});
					}
					SequenceActivity seq122 = new SequenceActivity();/*{
						DefaultActivity act111 = new DefaultActivity("1212");
						DefaultActivity act112 = new DefaultActivity("1213");
						seq122.setChildActivities(new Activity[]{act111, act112});
					}*/
					all12.setChildActivities(new Activity[]{seq12, seq122});
				}				
				
				all1.setChildActivities(new Activity[]{seq11, all12});
			}
			
			DefaultActivity act2 = new DefaultActivity("2");
			
			def.setChildActivities(new Activity[]{all1, act2});
			
			currActivity = act2;
		}
		
		return def;
	}
	
}

