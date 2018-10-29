package org.uengine.web.service.vo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DefaultVO implements Serializable {
	
	public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String comCode;
	private String userId;
	private int startRow = 0;
	private int endRow = 0;
	private int pageNo = 1;
	private int pageSize = 15;
	private int pagerSize = 10;
	private int totalCount = 0;
	private int lastPage = 0;
	private int firstPage = 1;
	private int nextMinPage = 0;
	private int prevMaxPage = 0;
	private boolean hasLastPage = true;
	private boolean hasFirstPage = true;
	private boolean hasPrevPage = true;
	private boolean hasNextPage = true;
	private List<Integer> pageGroup;
	private String searchOption;
	private String searchKeyword;
	private String searchFromDate;
	private String searchToDate;
	private String callbackFunc;
	private Date fromDate;
	private Date toDate;
	private String strFromDate;
	private String strToDate;
	private int rnum;
	private String folderName;

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}

	public String getSearchFromDate() {
		return searchFromDate;
	}

	public void setSearchFromDate(String searchFromDate) {
		this.searchFromDate = searchFromDate;
	}

	public String getSearchToDate() {
		return searchToDate;
	}

	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}

	public int getPagerSize() {
		return pagerSize;
	}

	public void setPagerSize(int pagerSize) {
		this.pagerSize = pagerSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getCallbackFunc() {
		return callbackFunc;
	}

	public void setCallbackFunc(String callbackFunc) {
		this.callbackFunc = callbackFunc;
	}

	public List<Integer> getPageGroup() {
		return pageGroup;
	}

	public void setPageGroup(List<Integer> pageGroup) {
		this.pageGroup = pageGroup;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public boolean isHasLastPage() {
		return hasLastPage;
	}

	public void setHasLastPage(boolean hasLastPage) {
		this.hasLastPage = hasLastPage;
	}

	public boolean isHasFirstPage() {
		return hasFirstPage;
	}

	public void setHasFirstPage(boolean hasFirstPage) {
		this.hasFirstPage = hasFirstPage;
	}

	public boolean isHasPrevPage() {
		return hasPrevPage;
	}

	public void setHasPrevPage(boolean hasPrevPage) {
		this.hasPrevPage = hasPrevPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public int getNextMinPage() {
		return nextMinPage;
	}

	public void setNextMinPage(int nextMinPage) {
		this.nextMinPage = nextMinPage;
	}

	public int getPrevMaxPage() {
		return prevMaxPage;
	}

	public void setPrevMaxPage(int prevMaxPage) {
		this.prevMaxPage = prevMaxPage;
	}

	public int getRnum() {
		return rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}

	public Date getFromDate() {
		Date date = null;
		return date;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		Date date = null;
		return date;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getStrFromDate() {
		return strFromDate;
	}

	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}

	public String getStrToDate() {
		return strToDate;
	}

	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

}
