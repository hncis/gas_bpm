package org.uengine.ui.list.datamodel;

import org.uengine.ui.list.datamodel.Constants;

import java.util.Map;

public class QueryCondition implements java.io.Serializable {
    private int onePageCount = 10;

    private int pageNo = 1;

    private Map map;
    
    private int detailRowNum;
    
    public QueryCondition() {
        map = new DataMap();
    }
    
    public QueryCondition(int onePageCount, int pageNo, Map map) {
    	this.onePageCount = onePageCount;
    	this.pageNo = pageNo;
        this.map = map;
    }

    public int getOnePageCount() {
        return onePageCount;
    }

    public void setOnePageCount(int onePageCount) {
        this.onePageCount = onePageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
    
	public int getStartRownum(){
		int startRownum = 1;
		if (pageNo != 1) {
			startRownum = (pageNo-1)*onePageCount +1 ;
		}
		return startRownum;
	}
	
	public int getEndRownum(){
		int endRownum = onePageCount;
		if (pageNo != 1) {
			endRownum = pageNo*onePageCount;
		}
		return endRownum;
	}
	
	public String getOrderField() {
	    String   sortColumn = (String)map.get(Constants.SORT_COLUMN.toUpperCase());
	    if (sortColumn != null && sortColumn.equals("")){
	        sortColumn = null;
	    }
	    return sortColumn;
	}
	
	public boolean isOrderAsc() {
	    String   sortCond = (String)map.get(Constants.SORT_COND.toUpperCase());
	    
	    if ( sortCond == null || sortCond.equals("") || sortCond.equalsIgnoreCase("ASC"))
	        return true;
	    else
	        return false;
	}
	
	public String getSearchField() {
	    String searchFiled = (String)map.get(Constants.SEARCH_TARGET_COND.toUpperCase());
	    if (searchFiled == null){
	    	searchFiled = "";
	    }
	    return searchFiled;
	}
	
	public String getSearchKeyword() {
	    String   searchKeyword = (String)map.get(Constants.SEARCH_KEYWORD_COND.toUpperCase());
	    if (searchKeyword == null){
	    	searchKeyword = "";
	    }
	    return searchKeyword;
	}
	
	public int getDetailRowNum() {
		return detailRowNum;
	}
	
	public void setDetailRowNum(int detailRowNum) {
		this.detailRowNum = detailRowNum;
	}
}
