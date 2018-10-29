package org.uengine.web.common.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * org.uengine.web.common.dao 
 * AbstractDAO.java
 * 
 * </pre>
 * @date : 2016. 6. 3. 오후 1:37:29
 * @version : 
 * @author : mkbok_Enki
 */
public class AbstractDAO {
	protected Log log = LogFactory.getLog(AbstractDAO.class);
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : printQueryId
	 * @date : 2016. 5. 19.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 19.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param queryId
	 */ 	
	protected void printQueryId(String queryId) {
		if(log.isDebugEnabled()){
			log.debug("\t QueryId \t: " + queryId);
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : insert
	 * @date : 2016. 5. 19.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 19.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param queryId
	 * @return
	 */ 	
	public Object insert(String queryId){
		printQueryId(queryId);
		return sqlSession.insert(queryId);
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : insert
	 * @date : 2016. 5. 19.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 19.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param queryId
	 * @param params
	 * @return
	 */ 	
	public Object insert(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : update
	 * @date : 2016. 5. 19.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 19.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param queryId
	 * @return
	 */ 	
	public Object update(String queryId){
		printQueryId(queryId);
		return sqlSession.update(queryId);
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : update
	 * @date : 2016. 5. 19.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 19.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param queryId
	 * @param params
	 * @return
	 */ 	
	public Object update(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.update(queryId, params);
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : delete
	 * @date : 2016. 5. 19.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 19.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param queryId
	 * @return
	 */ 	
	public Object delete(String queryId){
		printQueryId(queryId);
		return sqlSession.delete(queryId);
	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : delete
	 * @date : 2016. 5. 19.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 19.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param queryId
	 * @param params
	 * @return
	 */ 	
	public Object delete(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.delete(queryId, params);
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : selectOne
	 * @date : 2016. 5. 19.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 19.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param queryId
	 * @return
	 */ 	
	public Object selectOne(String queryId){
		printQueryId(queryId);
		return sqlSession.selectOne(queryId);
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : selectOne
	 * @date : 2016. 5. 19.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 19.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param queryId
	 * @param params
	 * @return
	 */ 	
	public Object selectOne(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : selectList
	 * @date : 2016. 5. 19.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 19.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param queryId
	 * @return
	 */ 	
	public Object selectList(String queryId){
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : selectList
	 * @date : 2016. 5. 19.
	 * @author : mkbok_Enki
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2016. 5. 19.		mkbok_Enki				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param queryId
	 * @param params
	 * @return
	 */ 	
	public Object selectList(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.selectList(queryId, params);
	}
}
