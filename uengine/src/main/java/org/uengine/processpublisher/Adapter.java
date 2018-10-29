package org.uengine.processpublisher;

/**
 * @author Jinyoung Jang
 */

public interface Adapter{
	
	public Object convert(Object src, java.util.Hashtable keyedContext) throws Exception;

}