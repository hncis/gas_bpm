package org.uengine.ui.worklist.filter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.*;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessDefinitionFactory;
import org.uengine.kernel.UEngineException;

public class WorkListFilterList {
	public final static String FILE_SYSTEM_DIR = GlobalContext.getPropertyString("filesystem.path", ProcessDefinitionFactory.DEFINITION_ROOT);
	public final static String FILE_PATH = FILE_SYSTEM_DIR + "worklist\\"+"workListFilters.xml";
	
	
	HashMap workItemFilters;
		public WorkListFilter[] getWorkItemFilters() {
			return HashMapToWorkListFilterArray(this.workItemFilters);
		}
		public void setWorkItemFilters(WorkListFilter workListFilter) {
			this.workItemFilters.put(workListFilter.getFilterUId(), workListFilter);
		}
			
	private WorkListFilter[] HashMapToWorkListFilterArray(HashMap wlf){
		WorkListFilter[] workListFilterArray = new WorkListFilter[wlf.size()];
		
		int index=0;
		Set clonedSet = new HashSet();
		clonedSet.addAll(wlf.keySet());
		Iterator keyIter = clonedSet.iterator();
		while(keyIter.hasNext()) {
			String key = (String)keyIter.next();
			workListFilterArray[index++] = (WorkListFilter)wlf.get(key);
		}
		return workListFilterArray;
	}
	
		
	public void deleteFilter(String filterUID) throws UEngineException {
		if(this.workItemFilters.containsKey(filterUID)){
			this.workItemFilters.remove(filterUID);
		}
		
		save(this);
	}
	
	public WorkListFilterList(){
		this.workItemFilters = new HashMap();
	}
	
	public static void save(WorkListFilterList workListFilterList)throws UEngineException{
		try {
			File dirToCreate = new File(FILE_SYSTEM_DIR + "worklist");
			dirToCreate.mkdirs();
		
			File newFile = new File(FILE_PATH);
			FileOutputStream fos = new FileOutputStream(newFile);
			
			String strDef;
		
			ByteArrayOutputStream bao = new ByteArrayOutputStream();			
			GlobalContext.serialize(workListFilterList, bao, String.class);
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
	
	public static WorkListFilterList load()throws UEngineException{
		try {
			File f = new File(FILE_PATH);
			if(f.exists()){
				InputStream fis = new FileInputStream(FILE_PATH);
				
				return (WorkListFilterList)GlobalContext.deserialize(fis, WorkListFilterList.class);
			}

		}catch(Exception e){
			throw new UEngineException("Error when to load filter: " + e.getMessage(), e);
		}	
		
		return new WorkListFilterList();
	}
}
