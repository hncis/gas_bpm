/**
 * 
 */
/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package org.uengine.web.util; 

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * <pre>
 * org.uengine.util.util 
 *    |_ CommonUtil.java
 * 
 * </pre>
 * @date : 2016. 6. 20. 오후 3:14:10
 * @version : 
 * @author : next3
 */
/**
 * <pre>
 * org.uengine.util.util 
 * CommonUtil.java
 * 
 * </pre>
 * @date : 2016. 6. 20. 오후 3:14:10
 * @version : 
 * @author : next3
 */
public class CommonUtil {

	private Logger log = Logger.getLogger(this.getClass());
	
	public static List<Map> getTreeObjectByListForBootstrap(List levelTree, String levelFieldName, String idFieldName, String textFieldName, int startLevel) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		List<Map> treeList = new ArrayList();
		
		for ( int i = 0; i < levelTree.size(); i++ ) {
			Object thisObject = levelTree.get(i);
			int thisLevel = Integer.parseInt((String) getFieldValue(thisObject, levelFieldName));
			
			if ( startLevel != thisLevel )
				continue;
			
			Map<String, Object> thisMap = new HashMap<String, Object>();
			thisMap.put("id", getFieldValue(thisObject, idFieldName));
			thisMap.put("text", getFieldValue(thisObject, textFieldName));
			
			if ( i < levelTree.size()-1 ) {
				Object nextObject = levelTree.get(i+1);
				Field nextLevelField = nextObject.getClass().getDeclaredField(levelFieldName);
				nextLevelField.setAccessible(true);
				int nextLevel = Integer.parseInt((String) nextLevelField.get(nextObject));
				if ( nextLevel == (thisLevel+1) ) {
					List childList = new ArrayList();
					for ( int j = i+1; j < levelTree.size(); j++ ) {
						Object childObject = levelTree.get(j);
						int childLevel = Integer.parseInt((String) getFieldValue(childObject, levelFieldName));
						if ( childLevel == thisLevel)	break;
						childList.add(levelTree.get(j));
					}
					thisMap.put("nodes", getTreeObjectByListForBootstrap(childList, levelFieldName, idFieldName, textFieldName, nextLevel));
				}
			}
			
			treeList.add(thisMap);
			
		}
		
		return treeList;
		
	}

	public static List<Map> getTreeObjectByListForJSTree(List levelTree, String levelFieldName, String idFieldName, String textFieldName, int startLevel, String parentId, String typeFieldName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		List<Map> treeList = new ArrayList();
		
		for ( int i = 0; i < levelTree.size(); i++ ) {
			Object thisObject = levelTree.get(i);
			int thisLevel = Integer.parseInt((String) getFieldValue(thisObject, levelFieldName));
			String thisId = (String) getFieldValue(thisObject, idFieldName);
			String thisText = (String) getFieldValue(thisObject, textFieldName);
			String thisType = (String) getFieldValue(thisObject, typeFieldName);
			
			if ( startLevel != thisLevel )
				continue;
			
			Map<String, Object> thisMap = new HashMap<String, Object>();
			thisMap.put("id", thisId);
			thisMap.put("text", thisText);
			thisMap.put("type", thisType);
			if ( !parentId.equals("#") )
				thisMap.put("parent", parentId);
			
			if ( i < levelTree.size()-1 ) {
				Object nextObject = levelTree.get(i+1);
				int nextLevel = Integer.parseInt((String) getFieldValue(nextObject, levelFieldName));
				if ( nextLevel == (thisLevel+1) ) {
					List childList = new ArrayList();
					for ( int j = i+1; j < levelTree.size(); j++ ) {
						Object childObject = levelTree.get(j);
						int childLevel = Integer.parseInt((String) getFieldValue(childObject, levelFieldName));
						if ( childLevel == thisLevel)	break;
						childList.add(levelTree.get(j));
					}
					thisMap.put("children", getTreeObjectByListForJSTree(childList, levelFieldName, idFieldName, textFieldName, nextLevel, thisId, typeFieldName));
				}
			}
			
			treeList.add(thisMap);
			
		}
		
		return treeList;
		
	}
	
	public static Object getFieldValue(Object o, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		Field field = o.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(o);
	}

}
