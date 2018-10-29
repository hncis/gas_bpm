package org.uengine.ui.list.util;

public class ListPageInfo
{
	private static final int DEFAULT_PAGE_SIZE = 10;
	private static final int DEFAULT_PAGESET_SIZE = 10;
	private static final int DEFAULT_ABSOLUTE_PAGE = 1;
	private int mPageCount;
	private int mRecordCount;
	private int mPageSize;
	private int mPageSetSize;
	private int mAbsolutePage;
	private int mStartPage;
	private int mEndPage;
	private int mFirstPage;
	private int mLastPage;

	public ListPageInfo()
		throws Exception
	{
		this(0, DEFAULT_PAGE_SIZE, DEFAULT_PAGESET_SIZE, DEFAULT_ABSOLUTE_PAGE);
	}

	public ListPageInfo(int nRecordCount)
		throws Exception
	{
		this(nRecordCount, DEFAULT_PAGE_SIZE, DEFAULT_PAGESET_SIZE, DEFAULT_ABSOLUTE_PAGE);
	}

	public ListPageInfo(int nRecordCount, int nPageSize)
		throws Exception
	{
		this(nRecordCount, nPageSize, DEFAULT_PAGESET_SIZE, DEFAULT_ABSOLUTE_PAGE);
	}

	public ListPageInfo(int nRecordCount, int nPageSize, int nPagesetSize)
		throws Exception
	{
		this(nRecordCount, nPageSize, nPagesetSize, DEFAULT_ABSOLUTE_PAGE);
	}

	public ListPageInfo(int nRecordCount, int nPageSize, int nPagesetSize, int nAbsolutePage)
		throws Exception
	{
		mRecordCount = nRecordCount;
		mPageSize = nPageSize;
		mPageSetSize = nPagesetSize;
		mAbsolutePage = nAbsolutePage;
		if(nRecordCount == 0)
			initProperties();
		else
			setProperties();
	}

	public void initProperties()
	{
		mPageCount = 0;
		mStartPage = 0;
		mEndPage = 0;
		mFirstPage = 0;
		mLastPage = 0;
	}

	public void setProperties()
		throws Exception
	{
		mPageCount = mRecordCount / mPageSize;
		if(mRecordCount % mPageSize != 0)
			mPageCount++;
		mLastPage = mPageCount;
		mFirstPage = 1;
		mStartPage = ((mAbsolutePage - 1) / mPageSetSize) * mPageSetSize + 1;
		if(mStartPage < mFirstPage)
			mStartPage = mFirstPage;
		mEndPage = (((mAbsolutePage - 1) + mPageSetSize) / mPageSetSize) * mPageSetSize;
		if(mEndPage > mLastPage)
			mEndPage = mLastPage;
	}

	public boolean isPrevPageSet()
	{
		return mStartPage > 1;
	}

	public boolean isNextPageSet()
	{
		return mEndPage < mLastPage;
	}

	public int getPrevPageSet()
	{
		int nRet = 0;
		if(mStartPage > mFirstPage)
			nRet = mStartPage - 1;
		if(nRet < mFirstPage)
			nRet = mFirstPage;
		return nRet;
	}

	public int getNextPageSet()
	{
		int nRet = 0;
		if(mEndPage < mLastPage)
			nRet = mEndPage + 1;
		if(nRet > mLastPage)
			nRet = mLastPage;
		return nRet;
	}

	public void setRecordCount(int nRecordCount)
	{
		mRecordCount = nRecordCount;
	}

	public int getRecordCount()
	{
		return mRecordCount;
	}

	public void setPageCount(int nPageCount)
	{
		mPageCount = nPageCount;
	}

	public int getPageCount()
	{
		return mPageCount;
	}

	public void setPageSize(int pageSize)
	{
		mPageSize = pageSize;
	}

	public int getPageSize()
	{
		return mPageSize;
	}

	public void setPageSetSize(int pageSetSize)
	{
		mPageSetSize = pageSetSize;
	}

	public int getPageSetSize()
	{
		return mPageSetSize;
	}

	public void setAbsolutePage(int absolutePage)
	{
		mAbsolutePage = absolutePage;
	}

	public int getAbsolutePage()
	{
		return mAbsolutePage;
	}

	public int getStartPage()
	{
		return mStartPage;
	}

	public int getEndPage()
	{
		return mEndPage;
	}

	public int getFirstPage()
	{
		return mFirstPage;
	}

	public int getLastPage()
	{
		return mLastPage;
	}
}
