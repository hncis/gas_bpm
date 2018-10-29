package org.uengine.ui.list.util;

import java.sql.*;

import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import org.uengine.ui.list.datamodel.Constants;
import org.uengine.ui.list.datamodel.DataList;
import org.uengine.ui.list.datamodel.DataMap;
import org.uengine.ui.list.datamodel.QueryCondition;
import org.uengine.ui.list.exception.ExceptionCode;
import org.uengine.ui.list.exception.UEngineException;
import org.uengine.ui.list.util.DateUtil;
//import hanwha.commons.dao.GenericDAO;
//import hanwha.commons.dao.IDAO;


public class DAOListOracleUtil {

    private static Logger logger = Logger.getLogger(DAOListOracleUtil.class.getName());

    private static final int DATA_INITIALCAPACITY = 5;
    
    public static String SUCCESS = "DAOUtil.SUCCESS";
    
    private static final String ROWNUM_KEY = "BPM_RN";
    private static final String TOTCNT_KEY = "PM_TOTALCOUNT";
    private static final String LIST_ALIAS_KEY = "BPM_LIST";

    public static int getPageCount(long rowCount, int onePageCount) {
        int pageCount = (int) (rowCount / onePageCount);
        int rest = (int) (rowCount % onePageCount);

        if(rest > 0)
            pageCount = pageCount + 1;

        return pageCount;
    }
    
    public static long getStartPosition(int onePageCount, int viewPageNo){
		return getStartPosition(onePageCount, viewPageNo, 0);
	}
    
    public static long getStartPosition(int onePageCount, int viewPageNo, int offset){
		long startPosition = onePageCount * (viewPageNo - 1) + 1 + offset;
		return startPosition;
	}

    public static void printSQLError(Logger logger, SQLException ex) {
        logger.error("##### SQL Exception #####");
        logger.error("Error message : " + ex.getMessage());
        logger.error("SQL state : " + ex.getSQLState());
        logger.error("Error code : " + ex.getErrorCode());
        logger.error(ex, ex);
    }

    public static void close(Connection conn) {
        try {
            if(conn != null) conn.close();
            conn = null;
        } catch(Exception ex) {
			logger.error("close(Connection)", ex); //$NON-NLS-1$
        }
    }
    
    public static String addListSql(String sql, int viewPage, int onePageCount ) {
    	long startPosition = getStartPosition( onePageCount, viewPage );
    	long endPosition = startPosition + onePageCount;
    	
    	return addListSqlByPosition(sql, startPosition, endPosition);
    }
    public static String addListSqlTemp(String sql, int viewPage, int onePageCount ) {
    	long startPosition = getStartPosition( onePageCount, viewPage );
    	long endPosition = startPosition + onePageCount;
    	
    	return addListSqlByPositionTemp(sql, startPosition, endPosition);
    }    
    
    public static String addListSqlByPosition(String sql, long startPosition, long endPosition ) {
    	if( sql == null || "".equals(sql) )
    		return sql;
    	StringBuffer sb = new StringBuffer();
    	sb.append("select * ")
    		.append("from( ")
    		.append("	select rownum as " + ROWNUM_KEY + ", count(1) over() as " + TOTCNT_KEY + ", " + LIST_ALIAS_KEY + ".* ")
    		.append("	from( ") 
    		.append(sql)
    		.append("	) " +LIST_ALIAS_KEY)
    	    .append(") ")
    	    .append("where " + ROWNUM_KEY + " >= " + startPosition) 
    	    .append("	and " + ROWNUM_KEY + " < " + endPosition );
    	
    	if ( !Constants.PRODUCTION_MODE ) {
    		System.out.println("----------------------");
    		System.out.println(sb.toString());
    		System.out.println("----------------------");
    	}
    	return sb.toString();
    }
    public static String addListSqlByPositionTemp(String sql, long startPosition, long endPosition ) {
    	if( sql == null || "".equals(sql) )
    		return sql;
//    	StringBuffer sb = new StringBuffer();
//    	sb.append("select * ")
//    		.append("from( ")
//    		.append("	select rownum as " + ROWNUM_KEY + ", " + LIST_ALIAS_KEY + ".* ")
//    		.append("	from( ") 
//    		.append(sql)
//    		.append("	) " +LIST_ALIAS_KEY)
//    	    .append(") ")
//    	    .append("where " + ROWNUM_KEY + " >= " + startPosition) 
//    	    .append("	and " + ROWNUM_KEY + " < " + endPosition );
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("select rownum as HWBPM_RN, HWBPM_LIST.* from ( ")
    		.append(sql)
    		.append("	) " +LIST_ALIAS_KEY)
    	    .append(" where rownum >= " + startPosition) 
    	    .append("	and rownum < " + endPosition );   
    	
//    	System.out.println()
    	return sb.toString();
    }    
    
    public static String addAllListSql(String sql ) {
    	if( sql == null || "".equals(sql) )
    		return sql;
    	StringBuffer sb = new StringBuffer();
    	sb.append("select rownum as " + ROWNUM_KEY + ", count(1) over() as " + TOTCNT_KEY + ", " + LIST_ALIAS_KEY + ".* ")
    		.append("from( ") 
    		.append(sql)
    		.append(") " +LIST_ALIAS_KEY);
    	return sb.toString();
    }
    
    public static DataList executeDetailInfoListTemp(String sql, QueryCondition condition,
            List keys, Connection con, Logger logger)
    	throws UEngineException {
    	String[] arrKeys = null;
    	if( keys != null ){
    		arrKeys = new String[keys.size()];
    		keys.toArray(arrKeys);
    	}
    	return executeDetailInfoList(sql, condition, arrKeys, con, logger);
    }
    
    public static DataList executeDetailInfoList(String sql, QueryCondition condition,
            String[] keys, Connection con, Logger logger)
    	throws UEngineException {
		DataList dl = new DataList();
		DataMap dm = DAOListOracleUtil.executeDetailInfo(sql, condition, keys, con, logger);
		dl.add(dm);
		return dl;
    }
    
    public static DataMap executeDetailInfo(String sql, QueryCondition condition,
                                 String[] keys, Connection con, Logger logger) throws
        UEngineException {
    	PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
//            int viewPage = condition.getPageNo();
//            int onePageCount = condition.getOnePageCount();
        	// Detail Row Number.
            int detailRowNum = condition.getDetailRowNum();
            long rowCount = 0;
            //int totalPage = 0;
            
           	pstmt = con.prepareStatement( addListSqlByPosition( sql, detailRowNum - 1, detailRowNum + 2 ) );
           
            Map map = condition.getMap();
            
            if( keys != null ){
	            for (int i = 1; i <= keys.length; i++) {
	                Object param = map.get(keys[i - 1]);
	                if(param instanceof String)
	                    pstmt.setString(i, (String)param);
	                else if(param instanceof java.sql.Date)
	                    pstmt.setDate(i, (java.sql.Date)param);
	                else if(param instanceof java.sql.Timestamp)
	                    pstmt.setTimestamp(i, (java.sql.Timestamp)param);
	            }
            }
            
            rs = pstmt.executeQuery();

            //DataList dataList = new DataList();
            DataMap dm = new DataMap();

            boolean isStartRow = true;
            ResultSetMetaData rsMd = rs.getMetaData();
            int colCnt = rsMd.getColumnCount();

            while(rs.next()) {
            	if(isStartRow){
                    rowCount = rs.getLong(TOTCNT_KEY);
            		isStartRow = false;
            	}
                DataMap data = new DataMap(colCnt);
                for(int i = 1; i<=colCnt; i++){
                    int type = rsMd.getColumnType(i);
                    switch (type) {
                        case java.sql.Types.VARCHAR:
                            data.put(rsMd.getColumnName(i), rs.getString(i));
                            break;
                        case java.sql.Types.DATE:
                            data.put(rsMd.getColumnName(i), rs.getTimestamp(i));
                            break;
                        case java.sql.Types.TIME:
                        case java.sql.Types.TIMESTAMP:
                            data.put(rsMd.getColumnName(i), rs.getTimestamp(i));
                            break;
                        default:
                            data.put(rsMd.getColumnName(i), rs.getString(i));
                            break;
                    }
                }
                // RowNum ��d.
                int rownum = rs.getInt(ROWNUM_KEY);
                data.setRownum(rs.getInt(ROWNUM_KEY));
                
                if( rownum == detailRowNum - 1 ){
                	dm.setPrevData(data);
                }else if( rownum == detailRowNum ){
                	dm.putAll(data);
                }else if( rownum == detailRowNum + 1 ){
                	dm.setNextData(data);	
                }
                //dataList.add(data);
            } 
            
            //totalPage = getPageCount(rowCount, onePageCount);
            //dataList.setTotalPageNo(totalPage);
            //dataList.setTotalCount(rowCount);
            
            //return dataList;
            return dm;
        }catch(NullPointerException e){
			logger
					.error(
							"executeDetailInfo(String, QueryCondition, String[], Connection, Logger)", e); //$NON-NLS-1$
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_NULL_POINT_ERR);
        }catch(SQLException e){
			logger
					.error(
							"executeDetailInfo(String, QueryCondition, String[], Connection, Logger)", e); //$NON-NLS-1$
            printSQLError(logger, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_SQL_ERR);
        }catch(Exception e){
			logger
					.error(
							"executeDetailInfo(String, QueryCondition, String[], Connection, Logger)", e); //$NON-NLS-1$
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_LOGIC_ERR);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch(Exception e) {
				logger
						.error(
								"executeDetailInfo(String, QueryCondition, String[], Connection, Logger)", e); //$NON-NLS-1$
                logger.error(e, e);
            }
        }
    }
    
    public static DataList executeListNoPage(String sql, QueryCondition condition,
                                 String[] keys, Connection con, Logger logger) throws
        UEngineException {
    	return executeAllList(sql, condition, keys, con, logger);
    }
    
    public static DataList executeAllListByType(String sql, QueryCondition condition,
                                 Connection con, Logger logger) throws
        UEngineException {
    	return executeAllListByType(sql, condition, null, con, logger);
    }
    
    public static DataList executeAllListByType(String sql, QueryCondition condition,
                                 String[] keys, Connection con, Logger logger) throws
        UEngineException {
    	return executeAllList(sql, condition, keys, con, logger);
    }
    
    public static DataList executeAllList(String sql, QueryCondition condition,
                                 String[] keys, Connection con, Logger logger) throws
        UEngineException {
    	return executeList(sql, condition, keys, con, logger, true);
    }
    
    public static DataList executeListByTypeTemp(String sql, QueryCondition condition,
                                 List keys, Connection con, Logger logger) throws
        UEngineException {
    	
    	String[] arrKeys = null;
    	if( keys != null ){
    		arrKeys = new String[keys.size()];
    		keys.toArray(arrKeys);
    	}
    	return executeListByType(sql, condition, arrKeys, con, logger);
    }
    
    public static DataList executeListByType(String sql, QueryCondition condition,
                                 Connection con, Logger logger) throws
        UEngineException {
    	return executeListByType(sql, condition, null, con, logger);
    }
    
    public static DataList executeListByType(String sql, QueryCondition condition,
                                 String[] keys, Connection con, Logger logger) throws
        UEngineException {
    	return executeList(sql, condition, keys, con, logger);
    }
    
    public static DataList executeListTemp(String sql, QueryCondition condition,
                                 List keys, Connection con, Logger logger) throws
        UEngineException {
    	String[] arrKeys = null;
    	if( keys != null ){
    		arrKeys = new String[keys.size()];
    		keys.toArray(arrKeys);
    	}
    	return executeList(sql, condition, arrKeys, con, logger);
    }
    
    
    public static DataList executeList(String sql, QueryCondition condition, List keyAndData, Connection con, Logger logger) 
    throws UEngineException {
		Map dataMap = condition.getMap();
		int keyCnt = 0;
		List keyMapList = new ArrayList();
		for(Iterator iter=keyAndData.iterator(); iter.hasNext(); ) {
		keyCnt++;
		dataMap.put("DATAKEY"+keyCnt+"X", iter.next());
		keyMapList.add("DATAKEY"+keyCnt+"X");
		}
		String[] _keys = new String[keyMapList.size()];
		keyMapList.toArray(_keys);		
		condition.setMap(dataMap);   	
		
		return executeList(sql, condition, _keys, con, logger);
	}    
    
    public static DataList executeList(String sql, QueryCondition condition,
                                 String[] keys, Connection con, Logger logger) throws
        UEngineException {
   	
    	
    	return executeList(sql, condition, keys, con, logger, false);
    }
    
    
    public static DataList executeListWithoutCount(String sql, QueryCondition condition,
            String[] keys, Connection con, Logger logger) throws
UEngineException {
return executeListTemp(sql, condition, keys, con, logger, false);
}    
    
    
    
    
    
    

    

    public static long getRowCount(ResultSet result) throws SQLException {
        result.last();
        long count = result.getRow();
        result.beforeFirst();

        return count;
    }
    
    public static boolean setPositionCusor(ResultSet result, int onePageCount,
                                           int viewPageNo) throws SQLException {
    	return setPositionCusor(result, onePageCount, viewPageNo, 0 );
    }    
   
    public static boolean setPositionCusor(ResultSet result, int onePageCount,
            int viewPageNo, int offset) throws SQLException {

		int rowCusor = onePageCount * (viewPageNo - 1) + 1 + offset;

		if(!result.absolute(rowCusor))
		return false;

		return true;
	}
    
    public static DataList executeListNoCount(String sql, QueryCondition condition, List keyAndData, Connection con, Logger logger) 
    throws UEngineException {
		Map dataMap = condition.getMap();
		int keyCnt = 0;
		List keyMapList = new ArrayList();
		for(Iterator iter=keyAndData.iterator(); iter.hasNext(); ) {
		keyCnt++;
		dataMap.put("DATAKEY"+keyCnt+"X", iter.next());
		keyMapList.add("DATAKEY"+keyCnt+"X");
		}
		String[] _keys = new String[keyMapList.size()];
		keyMapList.toArray(_keys);		
		condition.setMap(dataMap);   	
		
		return executeListNoCount(sql, condition, _keys, con, logger);
	}     

    public static DataList executeListNoCount(String sql, QueryCondition condition, String[] keys, Connection con, Logger logger) 
    throws UEngineException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql,
                                         ResultSet.TYPE_SCROLL_INSENSITIVE,
                                         ResultSet.CONCUR_READ_ONLY);

            Map map = condition.getMap();

            for (int i = 1; i <= keys.length; i++) {
                Object param = map.get(keys[i - 1]);
                if(param instanceof String)
                    pstmt.setString(i, (String)param);
                else if(param instanceof java.sql.Date)
                    pstmt.setDate(i, (java.sql.Date)param);
                else if(param instanceof java.sql.Timestamp)
                    pstmt.setTimestamp(i, (java.sql.Timestamp)param);
            }

            rs = pstmt.executeQuery();

            int onePageCount = condition.getOnePageCount();
            int viewPage = condition.getPageNo();

            DataList dataList = new DataList();

            if(!DAOListOracleUtil.setPositionCusor(rs, onePageCount, viewPage)) {
                return dataList;
            }

            int dataCnt = 1;
            ResultSetMetaData rsMd = rs.getMetaData();
            int colCnt = rsMd.getColumnCount();

            if(rs != null) {
                do {
                    DataMap data = new DataMap(colCnt);
                    for(int i = 1; i<=colCnt; i++){
                        data.put(rsMd.getColumnName(i), rs.getString(i));
                    }

                    // RowNum ��d.
                    data.setRownum(rs.getRow());

                    dataList.add(data);

                    dataCnt++;
                    if(dataCnt > onePageCount)
                        break;

                } while(rs.next());
            }

            return dataList;
        }catch(NullPointerException e){
            logger.error(e, e);
            throw new UEngineException(e.getMessage(), ExceptionCode.DAO_NULL_POINT_ERR);
        }catch(SQLException e){
            DAOListOracleUtil.printSQLError(logger, e);
            throw new UEngineException(e.getMessage(), ExceptionCode.DAO_SQL_ERR);
        }catch(Exception e){
            logger.error(e, e);
            throw new UEngineException(e.getMessage(), ExceptionCode.DAO_LOGIC_ERR);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch(Exception e) {
                logger.error(e, e);
            }
        }
    }    
    
    public static DataList executeListByCursor(String sql, QueryCondition condition, List keyAndData, Connection con, Logger logger) 
    throws UEngineException {
		Map dataMap = condition.getMap();
		int keyCnt = 0;
		List keyMapList = new ArrayList();
		for(Iterator iter=keyAndData.iterator(); iter.hasNext(); ) {
		keyCnt++;
		dataMap.put("DATAKEY"+keyCnt+"X", iter.next());
		keyMapList.add("DATAKEY"+keyCnt+"X");
		}
		String[] _keys = new String[keyMapList.size()];
		keyMapList.toArray(_keys);		
		condition.setMap(dataMap);   	
		
		return executeListByCursor(sql, condition, _keys, con, logger);
	}        

    public static DataList executeListByCursor(String sql, QueryCondition condition,
                                 String[] keys, Connection con, Logger logger) throws
        UEngineException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql,
                                         ResultSet.TYPE_SCROLL_INSENSITIVE,
                                         ResultSet.CONCUR_READ_ONLY);

            Map map = condition.getMap();

            for (int i = 1; i <= keys.length; i++) {
                Object param = map.get(keys[i - 1]);
                if(param instanceof String)
                    pstmt.setString(i, (String)param);
                else if(param instanceof java.sql.Date)
                    pstmt.setDate(i, (java.sql.Date)param);
                else if(param instanceof java.sql.Timestamp)
                    pstmt.setTimestamp(i, (java.sql.Timestamp)param);
            }

            rs = pstmt.executeQuery();

            int onePageCount = condition.getOnePageCount();
            int viewPage = condition.getPageNo();
            long rowCount = DAOListOracleUtil.getRowCount(rs);
            int totalPage = DAOListOracleUtil.getPageCount(rowCount, onePageCount);

            DataList dataList = new DataList();

            dataList.setTotalPageNo(totalPage);
            dataList.setTotalCount(rowCount);

            if(!DAOListOracleUtil.setPositionCusor(rs, onePageCount, viewPage)) {
                return dataList;
            }

            int dataCnt = 1;
            ResultSetMetaData rsMd = rs.getMetaData();
            int colCnt = rsMd.getColumnCount();

            if(rs != null) {
                do {
                    DataMap data = new DataMap(colCnt);
                    for(int i = 1; i<=colCnt; i++){
                        data.put(rsMd.getColumnName(i), rs.getString(i));
                    }

                    // RowNum ��d.
                    data.setRownum(rs.getRow());

                    dataList.add(data);

                    dataCnt++;
                    if(dataCnt > onePageCount)
                        break;

                } while(rs.next());
            }

            return dataList;
        }catch(NullPointerException e){
            logger.error(e, e);
            throw new UEngineException(e.getMessage(), ExceptionCode.DAO_NULL_POINT_ERR);
        }catch(SQLException e){
            DAOListOracleUtil.printSQLError(logger, e);
            throw new UEngineException(e.getMessage(), ExceptionCode.DAO_SQL_ERR);
        }catch(Exception e){
            logger.error(e, e);
            throw new UEngineException(e.getMessage(), ExceptionCode.DAO_LOGIC_ERR);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch(Exception e) {
                logger.error(e, e);
            }
        }
    }    

    public static DataList executeList(String sql, QueryCondition condition,
                                 String[] keys, Connection con, Logger logger, boolean isAllList) throws
        UEngineException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            int viewPage = condition.getPageNo();
            int onePageCount = condition.getOnePageCount();
            long rowCount = 0;
            int totalPage = 0;
            
//            String temp = addListSql( sql, viewPage, onePageCount );
//            System.out.println(temp);
            
//            IDAO idao = GenericDAO.createDAOImpl(temp);
//            idao.select();
//            System.out.println(idao.size());
//            pstmt = con.prepareStatement( addAllListSql( sql ) );
            if( isAllList ){
            	pstmt = con.prepareStatement( addAllListSql( sql ) );
            }else{
            	pstmt = con.prepareStatement( addListSql( sql, viewPage, onePageCount ) );
            }
            
            Map map = condition.getMap();
            
            if( keys != null ){
	            for (int i = 1; i <= keys.length; i++) {
	                Object param = map.get(keys[i - 1]);
	                if(param instanceof String)
	                    pstmt.setString(i, (String)param);
	                else if(param instanceof java.sql.Date)
	                    pstmt.setDate(i, (java.sql.Date)param);
	                else if(param instanceof java.sql.Timestamp)
	                    pstmt.setTimestamp(i, (java.sql.Timestamp)param);
	            }
            }
            
            rs = pstmt.executeQuery();

            DataList dataList = new DataList();

            boolean isStartRow = true;
            ResultSetMetaData rsMd = rs.getMetaData();
            int colCnt = rsMd.getColumnCount();

            while(rs.next()) {
            	if(isStartRow){
                    rowCount = rs.getLong(TOTCNT_KEY);
            		isStartRow = false;
            	}
                DataMap data = new DataMap(colCnt);
                for(int i = 1; i<=colCnt; i++){
                    int type = rsMd.getColumnType(i);
                    switch (type) {
                        case java.sql.Types.VARCHAR:
                            data.put(rsMd.getColumnName(i), rs.getString(i));
                            break;
                        case java.sql.Types.DATE:
                            data.put(rsMd.getColumnName(i), rs.getTimestamp(i));
                            break;
                        case java.sql.Types.TIME:
                        case java.sql.Types.TIMESTAMP:
                            data.put(rsMd.getColumnName(i), rs.getTimestamp(i));
                            break;
                        default:
                            data.put(rsMd.getColumnName(i), rs.getString(i));
                            break;
                    }
                }
                // RowNum ��d.
                data.setRownum(rs.getInt(ROWNUM_KEY));
                
                dataList.add(data);
            } 
            
            totalPage = getPageCount(rowCount, onePageCount);
            dataList.setTotalPageNo(totalPage);
            dataList.setTotalCount(rowCount);
            
            return dataList;
        }catch(NullPointerException e){
			logger
					.error(
							"executeList(String, QueryCondition, String[], Connection, Logger, boolean)", e); //$NON-NLS-1$
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_NULL_POINT_ERR);
        }catch(SQLException e){
			logger
					.error(
							"executeList(String, QueryCondition, String[], Connection, Logger, boolean)", e); //$NON-NLS-1$
            printSQLError(logger, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_SQL_ERR);
        }catch(Exception e){
			logger
					.error(
							"executeList(String, QueryCondition, String[], Connection, Logger, boolean)", e); //$NON-NLS-1$
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_LOGIC_ERR);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch(Exception e) {
				logger
						.error(
								"executeList(String, QueryCondition, String[], Connection, Logger, boolean)", e); //$NON-NLS-1$
                logger.error(e, e);
            }
        }
    }
    
    public static DataList executeListTemp(String sql, QueryCondition condition,
                                 String[] keys, Connection con, Logger logger, boolean isAllList) throws
        UEngineException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            int viewPage = condition.getPageNo();
            int onePageCount = condition.getOnePageCount();
            long rowCount = 0;
            int totalPage = 0;
            
//            String temp = addListSqlTemp( sql, viewPage, onePageCount );
//            System.out.println(sql);
            
//            IDAO idao = GenericDAO.createDAOImpl(temp);
//            idao.select();
//            System.out.println(idao.size());
//            pstmt = con.prepareStatement( addAllListSql( sql ) );
//            if( isAllList ){
//            	pstmt = con.prepareStatement( addAllListSql( sql ) );
//            }else{
//            	pstmt = con.prepareStatement( addListSqlTemp( sql, viewPage, onePageCount ) );
//            }
            
            pstmt = con.prepareStatement( sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            Map map = condition.getMap();
            
            if( keys != null ){
	            for (int i = 1; i <= keys.length; i++) {
	                Object param = map.get(keys[i - 1]);
	                //System.out.println(param );
	                if(param instanceof String)
	                    pstmt.setString(i, (String)param);
	                else if(param instanceof java.sql.Date)
	                    pstmt.setDate(i, (java.sql.Date)param);
	                else if(param instanceof java.sql.Timestamp)
	                    pstmt.setTimestamp(i, (java.sql.Timestamp)param);
	            }
            }
            
            rs = pstmt.executeQuery();
            
        	long startPosition = getStartPosition( onePageCount, viewPage );
        	long endPosition = startPosition + onePageCount;  
        	
//        	rs.last();
//        	rowCount = rs.getInt(ROWNUM_KEY);
        	
        	if ( startPosition > 1 )
        		rs.absolute((int)startPosition);
            
//            pstmt.execute

            DataList dataList = new DataList();

            boolean isStartRow = true;
            ResultSetMetaData rsMd = rs.getMetaData();
            int colCnt = rsMd.getColumnCount();

            for(int j=(int)startPosition; rs.next(); j++) {
            	if ( j == (int)endPosition ) {
            		break;
            	}
            	if(isStartRow){
                    //rowCount = rs.getLong(TOTCNT_KEY);
            		rowCount = 500;
            		isStartRow = false;
            	}
                DataMap data = new DataMap(colCnt);
                for(int i = 1; i<=colCnt; i++){
                    int type = rsMd.getColumnType(i);
                    switch (type) {
                        case java.sql.Types.VARCHAR:
                            data.put(rsMd.getColumnName(i), rs.getString(i));
                            break;
                        case java.sql.Types.DATE:
                            data.put(rsMd.getColumnName(i), rs.getTimestamp(i));
                            break;
                        case java.sql.Types.TIME:
                        case java.sql.Types.TIMESTAMP:
                            data.put(rsMd.getColumnName(i), rs.getTimestamp(i));
                            break;
                        default:
                            data.put(rsMd.getColumnName(i), rs.getString(i));
                            break;
                    }
                }
                // RowNum ��d.
                data.setRownum(rs.getInt(ROWNUM_KEY));
                
                dataList.add(data);
            } 
            
            totalPage = getPageCount(rowCount, onePageCount);
            dataList.setTotalPageNo(totalPage);
            dataList.setTotalCount(rowCount);
            
            return dataList;
        }catch(NullPointerException e){
			logger
					.error(
							"executeList(String, QueryCondition, String[], Connection, Logger, boolean)", e); //$NON-NLS-1$
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_NULL_POINT_ERR);
        }catch(SQLException e){
			logger
					.error(
							"executeList(String, QueryCondition, String[], Connection, Logger, boolean)", e); //$NON-NLS-1$
            printSQLError(logger, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_SQL_ERR);
        }catch(Exception e){
			logger
					.error(
							"executeList(String, QueryCondition, String[], Connection, Logger, boolean)", e); //$NON-NLS-1$
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_LOGIC_ERR);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch(Exception e) {
				logger
						.error(
								"executeList(String, QueryCondition, String[], Connection, Logger, boolean)", e); //$NON-NLS-1$
                logger.error(e, e);
            }
        }
    }    
    
    
    
    public static DataMap executeQuery(String sql, String[] keys, DataMap condition,
                                    Connection con, Logger logger) throws
        UEngineException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql);

            for(int i=1; i<=keys.length; i++){
                Object param = condition.get(keys[i-1]);
                if(param instanceof String)
                    pstmt.setString(i, (String)param);
                else if(param instanceof java.sql.Date)
                    pstmt.setDate(i, (java.sql.Date)param);
                else if(param instanceof java.sql.Timestamp)
                    pstmt.setTimestamp(i, (java.sql.Timestamp)param);

            }

            rs = pstmt.executeQuery();

            DataMap data = null;

            if (rs != null && rs.next()) {
                ResultSetMetaData rsMd = rs.getMetaData();
                int colCnt = rsMd.getColumnCount();

                data = new DataMap(colCnt + DATA_INITIALCAPACITY);
                for (int i = 1; i <= colCnt; i++) {
                    data.put(rsMd.getColumnName(i), rs.getString(i));
                }
            }else{
                data = new DataMap(1);
                data.setErrCode(ExceptionCode.DATA_NOT_FOUND_ERR);
            }

            return data;
        }catch(NullPointerException e){
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_NULL_POINT_ERR);
        }catch(SQLException e){
            DAOListOracleUtil.printSQLError(logger, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_SQL_ERR);
        }catch(Exception e){
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_LOGIC_ERR);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch(Exception e) {
                logger.error(e, e);
            }
        }
    }
    
    public static DataMap executeQuery(String sql, String condition,
                                    Connection con, Logger logger) throws
        UEngineException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, condition);

            rs = pstmt.executeQuery();

            DataMap data = null;

            if (rs != null && rs.next()) {
                ResultSetMetaData rsMd = rs.getMetaData();
                int colCnt = rsMd.getColumnCount();
                data = new DataMap(colCnt + DATA_INITIALCAPACITY);
                for (int i = 1; i <= colCnt; i++) {
                    data.put(rsMd.getColumnName(i), rs.getString(i));
                }
            }else{
                data = new DataMap(1);
                data.setErrCode(ExceptionCode.DATA_NOT_FOUND_ERR);
            }


            return data;
        }catch(NullPointerException e){
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_NULL_POINT_ERR);
        }catch(SQLException e){
            DAOListOracleUtil.printSQLError(logger, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_SQL_ERR);
        }catch(Exception e){
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_LOGIC_ERR);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch(Exception e) {
                logger.error(e, e);
            }
        }
    }
    
    public static DataMap hasNullexecuteQuery(String sql, String[] keys, DataMap condition,
                                    Connection con, Logger logger) throws
        UEngineException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql);

            for(int i=1; i<=keys.length; i++){
                Object param = condition.get(keys[i-1]);
                if(param instanceof String)
                    pstmt.setString(i, (String)param);
                else if(param instanceof java.sql.Date)
                    pstmt.setDate(i, (java.sql.Date)param);
                else if(param instanceof java.sql.Timestamp)
                    pstmt.setTimestamp(i, (java.sql.Timestamp)param);
                else if(param==null)
                	pstmt.setNull(i,java.sql.Types.VARCHAR);
            }

            rs = pstmt.executeQuery();

            DataMap data = null;

            if (rs != null && rs.next()) {
                ResultSetMetaData rsMd = rs.getMetaData();
                int colCnt = rsMd.getColumnCount();

                data = new DataMap(colCnt + DATA_INITIALCAPACITY);
                for (int i = 1; i <= colCnt; i++) {
                    data.put(rsMd.getColumnName(i), rs.getString(i));
                }
            }else{
                data = new DataMap(1);
                data.setErrCode(ExceptionCode.DATA_NOT_FOUND_ERR);
            }

            return data;
        }catch(NullPointerException e){
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_NULL_POINT_ERR);
        }catch(SQLException e){
            DAOListOracleUtil.printSQLError(logger, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_SQL_ERR);
        }catch(Exception e){
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_LOGIC_ERR);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch(Exception e) {
                logger.error(e, e);
            }
        }
    }
    
    public static String[] getKeysByList(List keys){
    	String[] arrKeys = null;
    	if( keys != null ){
    		arrKeys = new String[keys.size()];
    		keys.toArray(arrKeys);
    	}
    	return arrKeys;
    }
}
