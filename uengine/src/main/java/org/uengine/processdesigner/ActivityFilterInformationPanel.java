package org.uengine.processdesigner;

import javax.swing.ImageIcon;

import org.uengine.kernel.ActivityFilter;
import org.uengine.kernel.DefaultActivityFilter;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;

import org.metaworks.*;
import org.metaworks.inputter.*;

/**
 * @author Jinyoung Jang
 */

public class ActivityFilterInformationPanel extends AbstractInformationPanel{

    SelectInput typeInputter;

        
    public ActivityFilterInformationPanel(ProcessDefinition pd){
        super(pd, GlobalContext.getLocalizedMessage("pd.activityfilters.label"), DefaultActivityFilter.class);

        //mr.heo 추가
        java.net.URL imgURL = getClass().getClassLoader().getResource("org/uengine/kernel/images/filter.gif");
        setBorder(new IconTitledBorder(getBorder(), GlobalContext.getLocalizedMessage("pd.activityfilters.label"), new ImageIcon(imgURL)));
        //
        Type table = getApplication().getType();
        FieldDescriptor fd;
        
        fd = table.getFieldDescriptor("BeforeExecuteScript");       
        fd.setInputter(new ContextScriptInput(getProcessDefinition()));
        fd = table.getFieldDescriptor("AfterExecuteScript");
        fd.setInputter(new ContextScriptInput(getProcessDefinition()));
        
    }

    protected Object[] getValues() {
        return getProcessDefinition().getActivityFilters();
    }
    
    protected void applyValues(Object[] objs) {
        ActivityFilter[] filters = new ActivityFilter[objs.length];
        for(int i=0; i<objs.length; i++)
            filters[i] = (ActivityFilter)objs[i];
                    
        getProcessDefinition().setActivityFilters(filters); 
    }
    
    protected String getLabel(Object value){
        if(value == null) return null;
        return ((DefaultActivityFilter)value).getName();
    }
    
    public void onEdit(){
        try{
            //review: TO-DO
            ProcessDefinition procDef = (ProcessDefinition)ProcessDesigner.getInstance().getProcessDefinitionDesigner().getActivity();
            
        }catch(Exception e){
        }
    }

}