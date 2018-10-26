package com.hncis.common.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * 
 * Dao 에서 처리해야 할 공통적인 작업을 처리한다.
 * 입력, 다건입력, 수정, 다건수정
 * 
 * @author 6804401
 *
 */
public class CommonDao extends SqlMapClientDaoSupport {
		
	/**
	 * Obect 에 DL_SEVICE_CODE 를 SET해서 한건 자료를 입력한다.
	 * 
	 * @param statementName 호출되는 SQL 이름
	 * @param obj 입력해야 할 Object
	 * @return 입력된 건수 (무조건 1)
	 */
	public int insert(String statementName, Object obj) {
		Object obj2 = getSqlMapClientTemplate().insert(statementName, obj);
		if(obj2 == null){
			return 1;
		}else{
			return 0;
		}
//		return 1;		
	}
	
	/**
	 * Object 에 DL_SEVICE_CODE 를 SET해서 다건 자료를 입력한다.
	 * @param statementName 호출되는 SQL 이름
	 * @param list 다 건의 입력해야 할 Object 를 List 에 추가하여 받은 파라미터
	 * @return 입력된 건수
	 */
	@SuppressWarnings("unchecked")
	public int insert(final String statementName, final List list) {
		return (Integer)getSqlMapClientTemplate().execute( new SqlMapClientCallback() {
            public Object doInSqlMapClient( SqlMapExecutor excutor ) throws SQLException {
                //excutor.startBatch();
                int rs = 0;
                for ( Object obj : list ) {
                	//excutor.insert(statementName, obj);
                	//rs++;
                	rs += insert(statementName, obj);
                }
                //excutor.executeBatch();
                return new Integer(rs);
            	
            }
        });
	}
	
	/**
	 * Obect 에 DL_SEVICE_CODE 를 SET해서 한건 자료를 수정한다.
	 * @param statementName 호출되는 SQL 이름
	 * @param obj 수정해야 할 Object 
	 * @return 수정된 건수(무조건 1)
	 */
	public int update(String statementName, Object obj) {
		int rs = getSqlMapClientTemplate().update(statementName, obj);
		return rs;		
	}
	
	/**
	 * Object 에 DL_SEVICE_CODE 를 SET해서 다건 자료를 수정한다.
	 * @param statementName 호출되는 SQL 이름
	 * @param list 수정해야 할 Object 를 List 에 추가하여 받은 파라미터
	 * @return 수정된 건수
	 */
	@SuppressWarnings("unchecked")
	public int update(final String statementName, final List list) {
		return (Integer)getSqlMapClientTemplate().execute( new SqlMapClientCallback() {
            public Object doInSqlMapClient( SqlMapExecutor excutor ) throws SQLException {
                //excutor.startBatch();
                int rs = 0;
                for ( Object obj : list ) {
                	//excutor.update(statementName, obj);
                	//rs++;
                	rs += update(statementName, obj);
                }
                //excutor.executeBatch();
                
                return new Integer(rs);
            }
        });
	}	
	
	
	/**
	 * Obect 에 DL_SEVICE_CODE 를 SET해서 삭제한다.
	 * 
	 * @param statementName 호출되는 SQL 이름
	 * @param obj 삭제 해야 할 Object
	 * @return 삭제된 건수 (무조건 1)
	 */
	public int delete(String statementName, Object obj) {
		int rs = getSqlMapClientTemplate().delete(statementName, obj);
		return rs;		
	}
	
	/**
	 * Object 에 DL_SEVICE_CODE 를 SET해서 다건 자료를 삭제한다.
	 * @param statementName 호출되는 SQL 이름
	 * @param list 다 건의 삭제해야 할 Object 를 List 에 추가하여 받은 파라미터
	 * @return 삭제된 건수
	 */
	@SuppressWarnings("unchecked")
	public int delete(final String statementName, final List list) {
		return (Integer)getSqlMapClientTemplate().execute( new SqlMapClientCallback() {
            public Object doInSqlMapClient( SqlMapExecutor excutor ) throws SQLException {
                //excutor.startBatch();
                int rs = 0;
                for ( Object obj : list ) {
                	//excutor.delete(statementName, obj);
                	//rs++;
                	rs += delete(statementName, obj);
                }
                //excutor.executeBatch();
                
                return new Integer(rs);
            }
        });
	}
}
