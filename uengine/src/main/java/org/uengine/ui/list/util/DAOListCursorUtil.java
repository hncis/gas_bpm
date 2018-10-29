package org.uengine.ui.list.util;

import java.sql.*;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.List;

import org.uengine.ui.list.datamodel.DataList;
import org.uengine.ui.list.datamodel.DataMap;
import org.uengine.ui.list.datamodel.QueryCondition;
import org.uengine.ui.list.exception.ExceptionCode;
import org.uengine.ui.list.exception.UEngineException;

public class DAOListCursorUtil {

    private static Logger logger = Logger.getLogger(DAOListCursorUtil.class.getName());

    private static final int DATA_INITIALCAPACITY = 5;

    public static String SUCCESS = "DAOListCursorUtil.SUCCESS";

    public static void addSql(StringBuffer sb, int value, String addStr) {
        if(sb != null && addStr != null) {
            addSqlLogic(sb, String.valueOf(value), addStr);
        }
    }

    public static void addSql(StringBuffer sb, long value, String addStr) {
        if(sb != null && addStr != null) {
            addSqlLogic(sb, String.valueOf(value), addStr);
        }
    }

    public static void addSql(StringBuffer sb, float value, String addStr) {
        if(sb != null && addStr != null) {
            addSqlLogic(sb, String.valueOf(value), addStr);
        }
    }

    public static void addSql(StringBuffer sb, double value, String addStr) {
        if(sb != null && addStr != null) {
            addSqlLogic(sb, String.valueOf(value), addStr);
        }
    }

    public static void addSql(StringBuffer sb, String value, String addStr) {
        if(value != null && value.length() > 0 && sb != null && addStr != null) {
            addSqlLogic(sb, value, addStr);
        }
    }

    private static void addSqlLogic(StringBuffer sb, String value,
                                    String addStr) {
        StringBuffer perfectStr = new StringBuffer("");

        while(true) {
            int replaceStartIndex = addStr.indexOf("?");
            if(replaceStartIndex == -1)
                return;
            perfectStr.append(addStr.substring(0, replaceStartIndex));
            perfectStr.append(value);
            if(replaceStartIndex < addStr.length() - 1);
            perfectStr.append(addStr.substring(replaceStartIndex + 1,
                                               addStr.length()));
            sb.append(perfectStr.toString());
        }
    }

    public static long getRowCount(ResultSet result) throws SQLException {
        result.last();
        long count = result.getRow();
        result.beforeFirst();

        return count;
    }

    public static int getPageCount(long rowCount, int onePageCount) {
        int pageCount = (int) (rowCount / onePageCount);
        int rest = (int) (rowCount % onePageCount);

        if(rest > 0)
            pageCount = pageCount + 1;

        return pageCount;
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
    
    public static DataList executeList(String sql, QueryCondition condition,
            List keys, Connection con) throws
        UEngineException {
    	String[] arrKeys = new String[keys.size()];
    	arrKeys = (String[])keys.toArray(arrKeys);
    	return executeList(sql, condition, arrKeys, con);
    }
    
    public static DataList executeList(String sql, QueryCondition condition,
                                 String[] keys, Connection con) throws
        UEngineException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
//        	sql = new String(sql.getBytes(), "KSC5601");
            pstmt = con.prepareStatement(sql,
                                         ResultSet.TYPE_SCROLL_INSENSITIVE,
                                         ResultSet.CONCUR_READ_ONLY);

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
	                if(param instanceof Integer)
	                	pstmt.setInt(i, (Integer)param);
	                else if(param == null)
	                	pstmt.setNull(i,java.sql.Types.VARCHAR);
	            }
            }

            rs = pstmt.executeQuery();

            int onePageCount = condition.getOnePageCount();
            int viewPage = condition.getPageNo();
            long rowCount = DAOListCursorUtil.getRowCount(rs);
            int totalPage = DAOListCursorUtil.getPageCount(rowCount, onePageCount);

            DataList dataList = new DataList();

            dataList.setTotalPageNo(totalPage);
            dataList.setTotalCount(rowCount);

            if(!DAOListCursorUtil.setPositionCusor(rs, onePageCount, viewPage)) {
                return dataList;
            }

            int dataCnt = 1;
            ResultSetMetaData rsMd = rs.getMetaData();
            int colCnt = rsMd.getColumnCount();

            if(rs != null) {
                do {
                    DataMap data = new DataMap(colCnt);
                    for(int i = 1; i<=colCnt; i++){
                        int type = rsMd.getColumnType(i);
                        
                        String colName = rsMd.getColumnName(i).toUpperCase();
                        String colLabel = rsMd.getColumnLabel(i); 
                        if (colLabel != null && !"".equals(colLabel) 
                        		&& !colName.equalsIgnoreCase(colLabel)) {
                        	colName = colLabel.toUpperCase();
                        }
                        
                        switch (type) {
                            case java.sql.Types.VARCHAR:
                                data.put(colName, rs.getString(i));
                                break;
                            case java.sql.Types.DATE:
                            case java.sql.Types.TIME:
                            case java.sql.Types.TIMESTAMP:
                                data.put(colName, rs.getTimestamp(i));
                                break;
                            default:
                                data.put(colName, rs.getString(i));
                                break;
                        }
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
			logger
					.error(
							"executeList(String, QueryCondition, String[], Connection, Logger)", e); //$NON-NLS-1$
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_NULL_POINT_ERR);
        }catch(SQLException e){
			logger
					.error(
							"executeList(String, QueryCondition, String[], Connection, Logger)", e); //$NON-NLS-1$
            DAOListCursorUtil.printSQLError(logger, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_SQL_ERR);
        }catch(Exception e){
			logger
					.error(
							"executeList(String, QueryCondition, String[], Connection, Logger)", e); //$NON-NLS-1$
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_LOGIC_ERR);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            } catch(Exception e) {
				logger
						.error(
								"executeList(String, QueryCondition, String[], Connection, Logger)", e); //$NON-NLS-1$
                logger.error(e, e);
            }
        }
    }
    
    public static DataList executeDetailInfoList(String sql, QueryCondition condition,
            List keys, Connection con)
    	throws UEngineException {
    	String[] arrKeys = new String[keys.size()];
    	arrKeys = (String[])keys.toArray(arrKeys);
		return executeDetailInfoList(sql, condition, arrKeys, con);
    }
    
    public static DataList executeDetailInfoList(String sql, QueryCondition condition,
            String[] keys, Connection con)
    	throws UEngineException {
		DataList dl = new DataList();
		DataMap dm = executeDetailInfo(sql, condition, keys, con);
		dl.add(dm);
		return dl;
    }
    
    public static DataMap executeDetailInfo(String sql, QueryCondition condition,
                                 String[] keys, Connection con) throws
        UEngineException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql,
                                         ResultSet.TYPE_SCROLL_INSENSITIVE,
                                         ResultSet.CONCUR_READ_ONLY);

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
	                else if(param == null)
	                	pstmt.setNull(i,java.sql.Types.VARCHAR);
	            }
            }

            rs = pstmt.executeQuery();

            long rowCount = DAOListCursorUtil.getRowCount(rs);
            // Detail Row Number.
            int detailRowNum = condition.getDetailRowNum();
            DataMap dm = new DataMap();

            int dataCnt = 1;
            ResultSetMetaData rsMd = rs.getMetaData();
            int colCnt = rsMd.getColumnCount();

            if(rs != null && detailRowNum > 1 && rs.absolute( detailRowNum - 1 )) {
			    DataMap data = new DataMap(colCnt);
			    for(int i = 1; i<=colCnt; i++){
			    	String value = rs.getString(i);
			        data.put(rsMd.getColumnName(i), value);
			     }
			    // RowNum ��d.
                data.setRownum(rs.getRow());
			    dm.setPrevData(data);
			}

            if(rs != null && rs.absolute( detailRowNum )) {
//            	DataMap data = new DataMap(colCnt);
                for(int i = 1; i<=colCnt; i++){
                	String value = rs.getString(i);
//                	data.put(rsMd.getColumnName(i), value);
                	dm.put(rsMd.getColumnName(i), value);
                }
                // RowNum ��d.
                dm.setRownum(rs.getRow());
                //dm.putAll(data);
            }

			if(rs != null && detailRowNum < rowCount && rs.absolute( detailRowNum + 1 )) {
                DataMap data = new DataMap(colCnt);
                for(int i = 1; i<=colCnt; i++){
                	String value = rs.getString(i);
                    data.put(rsMd.getColumnName(i), value);
                }
                // RowNum ��d.
                data.setRownum(rs.getRow());
                dm.setNextData(data);
            }
            return dm;
        }catch(NullPointerException e){
            logger.error(e, e);
            throw new UEngineException(e, e.getMessage(), ExceptionCode.DAO_NULL_POINT_ERR);
        }catch(SQLException e){
            DAOListCursorUtil.printSQLError(logger, e);
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
