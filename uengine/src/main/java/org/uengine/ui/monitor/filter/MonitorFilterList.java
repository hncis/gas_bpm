package org.uengine.ui.monitor.filter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinitionFactory;
import org.uengine.kernel.UEngineException;

public class MonitorFilterList {
    public final static String FILE_SYSTEM_DIR = GlobalContext.getPropertyString("filesystem.path", ProcessDefinitionFactory.DEFINITION_ROOT);
    public final static String FILE_PATH = FILE_SYSTEM_DIR + "monitor\\"+"monitorListFilters_";
    
    
    HashMap<String, MonitorFilter> monitorFilters;
        public MonitorFilter[] getMonitorFilters() {
            return hashMapToMonitorFilterArray(this.monitorFilters);
        }
        public void setMonitorFilters(MonitorFilter monitorFilter) {
            this.monitorFilters.put(monitorFilter.getFilterUId(), monitorFilter);
        }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private MonitorFilter[] hashMapToMonitorFilterArray(HashMap<String, MonitorFilter> wlf){
        MonitorFilter[] monitorFilterArray = new MonitorFilter[wlf.size()];
        
        int index=0;
        Set clonedSet = new HashSet();
        clonedSet.addAll(wlf.keySet());
        Iterator keyIter = clonedSet.iterator();
        
        while(keyIter.hasNext()) {
            String key = (String)keyIter.next();
            monitorFilterArray[index++] = wlf.get(key);
        }
        return monitorFilterArray;
    }
    
        
    public void deleteFilter(String filterUID, String loggedUserId) throws UEngineException {
        if(this.monitorFilters.containsKey(filterUID)){
            this.monitorFilters.remove(filterUID);
        }
        
        save(this, loggedUserId);
    }
    
    public MonitorFilterList(){
        this.monitorFilters = new HashMap();
    }
    
    public static void save(MonitorFilterList monitorFilterList, String loggedUserId)throws UEngineException{
        try {
            File dirToCreate = new File(FILE_SYSTEM_DIR + "monitor");
            dirToCreate.mkdirs();
            
            File newFile = new File(FILE_PATH + loggedUserId + ".xml");
            FileOutputStream fos = new FileOutputStream(newFile);
            
            String strDef;
        
            ByteArrayOutputStream bao = new ByteArrayOutputStream();            
            GlobalContext.serialize(monitorFilterList, bao, String.class);
            strDef = bao.toString("UTF-8");
        
            OutputStreamWriter bw = null;
            try{
                bw = new OutputStreamWriter(fos, "UTF-8");
                bw.write(strDef);
                bw.close();
            }catch(Exception e){
                throw e;
            }finally{
                if(bw!=null)
                    try{bw.close();}catch(Exception e){};
            }
        } catch (Exception e) {
            throw new UEngineException("Error when to save filter: " + e.getMessage(), e);
        }
    }
    
    public static MonitorFilterList load(String loggedUserId)throws UEngineException{
        try {
            File f = new File(FILE_PATH + loggedUserId + ".xml");
            if(f.exists()){
                InputStream fis = new FileInputStream(FILE_PATH + loggedUserId + ".xml");
                
                return (MonitorFilterList)GlobalContext.deserialize(fis, MonitorFilterList.class);
            }

        } catch(Exception e) {
            throw new UEngineException("Error when to load filter: " + e.getMessage(), e);
        }   
        
        return new MonitorFilterList();
    }
}
