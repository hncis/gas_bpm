/*
 * Created on 2004. 12. 11.
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and 
Comments
 */
package org.uengine.kernel;

import org.uengine.webservices.worklist.*;
import junit.framework.*;
import java.util.*;

/**
 * @author Administrator
 *
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code
 *         and Comments
 */
public class HumanActivityTest extends TestCase {

    HumanTestProcess  process;
    ProcessInstance   instance;
    SimulatorWorkList worklist;

    public void setUp() {
        try {
            ProcessInstance.USE_CLASS = DefaultProcessInstance.class;
            ComplexActivity.USE_JMS = false;
            ComplexActivity.USE_THREAD = false;
            worklist = new SimulatorWorkList();
            WorkListServiceLocator.USE_OBJECT = worklist;

            process = new HumanTestProcess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testMultipleActor() {
        try {
            instance = process.createInstance();
            instance.putRoleMapping("drafter", "uengine@hanwha.co.kr");

            RoleMapping roleMapping = new RoleMapping();
            roleMapping.setName("participant");
            roleMapping.setEndpoint("test1@test.com");

            roleMapping.moveToAdd();
            roleMapping.setEndpoint("test2@test.com");

            roleMapping.moveToAdd();
            roleMapping.setEndpoint("test3@test.com");

            instance.putRoleMapping("participant", roleMapping);
            instance.execute();

            String[] taskIds = process.humanActivity.getTaskIds(instance);

            assertTrue(taskIds.length == 3);
            assertTrue(taskIds[0].equals("0"));
            assertTrue(taskIds[1].equals("1"));
            assertTrue(taskIds[2].equals("2"));

            int i = 0;
            Hashtable filter = new Hashtable();

            filter.put("USERID", "test1@test.com");
            i += worklist.findWorkitem(filter, false).size();
            filter.put("USERID", "test2@test.com");
            i += worklist.findWorkitem(filter, false).size();
            filter.put("USERID", "test3@test.com");
            i += worklist.findWorkitem(filter, false).size();

            assertTrue(i == 3);

            ResultPayload payload = new ResultPayload();

            payload.setExtendedValues(new KeyedParameter[] { new KeyedParameter(HumanActivity.PAYLOADKEY_TASKID, "0") });
            process.fireMessage(process.humanActivity.getMessage(), instance, payload);

            payload.setExtendedValues(new KeyedParameter[] { new KeyedParameter(HumanActivity.PAYLOADKEY_TASKID, "2") });
            process.fireMessage(process.humanActivity.getMessage(), instance, payload);

            assertTrue(!process.humanActivity.getStatus(instance).equals(Activity.STATUS_COMPLETED));

            payload.setExtendedValues(new KeyedParameter[] { new KeyedParameter(HumanActivity.PAYLOADKEY_TASKID, "1") });
            process.fireMessage(process.humanActivity.getMessage(), instance, payload);

            System.out.println("map = " + process.humanActivity.getTaskStatusMap(instance));

            assertTrue(process.humanActivity.getStatus(instance).equals(Activity.STATUS_COMPLETED));
            assertTrue(process.getStatus(instance).equals(Activity.STATUS_COMPLETED));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testSingleActor() {
        try {
            instance = process.createInstance();
            instance.putRoleMapping("drafter", "uengine@hanwha.co.kr");

            RoleMapping roleMapping = new RoleMapping();
            roleMapping.setName("participant");
            roleMapping.setEndpoint("test1@test.com");

            instance.putRoleMapping("participant", roleMapping);
            instance.execute();

            String[] taskIds = process.humanActivity.getTaskIds(instance);

            assertTrue(taskIds.length == 1);
            assertTrue(taskIds[0].equals("3"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        junit.swingui.TestRunner.run(HumanActivityTest.class);
    }

}

class HumanTestProcess extends ProcessDefinition {
    public HumanActivity humanActivity;

    public HumanTestProcess() {
        super();

        // set roles
        setRoles(new Role[] { new Role("referencer"), new Role("drafter"), new Role("approver") });

        // set process variables
        ProcessVariable pv;
        {
            pv = new ProcessVariable();
            pv.setName("var1");
            pv.setType(Integer.class);
            setProcessVariables(new ProcessVariable[] { pv });
        }

        // set activities
        humanActivity = new HumanActivity();
        humanActivity.setRole(Role.forName("participant"));

        setChildActivities(new Activity[] { humanActivity });
    }
}
