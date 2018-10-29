package org.uengine.ui.list.datamodel;

import org.uengine.kernel.GlobalContext;

public class Constants
{

	private static Constants instance;

	public static String HOST_NAME;
	public static String HOST_PORT;
	public static String DB_NAME;
	public static String DB_DRIVER;
	public static String DB_URL;
	public static String DB_USER;
	public static String DB_PASS;
	public static String DS_NAME;
	public static String LOCALE;
	public static String LOG_PROPERTY;
	public static String UPLOADPATH;
	public static int SESSION_TIMEOUT = 3600;
	public static String CHARSET;
	public static String SELECTED_DATA = "SELECTED_DATA";

	public static String USER = "USER";
	public static String SEL_USER = "SEL_USER";
	public static String ORG_TEAM = "ORG_TEAM";
	public static String SEL_ORG_TEAM = "SEL_ORG_TEAM";
	public static String ORG_DEPT = "ORG_DEPT";
	public static String SEL_ORG_DEPT = "SEL_ORG_DEPT";
	public static String ORG_POST = "ORG_POST";
	public static String SEL_ORG_POST = "SEL_ORG_POST";
	public static String USER_ROLE = "USER_ROLE";
	public static String COMMON_USER_ROLE;
	public static char DATE_DELIMITER;

//	public static String DATEFORMAT="dd";
	public static String DATEFORMAT="yyyy-MM-dd HH:mm";
	public static String MONTHFORMAT="MM";
	public static String TIMEFORMAT="HHmmss";
	public static String NUMBERFORMAT;

	public static int DEFAULT_PAGE_UNIT_CNT;
	public static int DEFAULT_ONE_PAGE_CNT;

	public static String JOLT_POOL_NAME;

	public static final String ERROR_KEY = "com.cnm.common.Constants.ERROR_KEY";
	public static final String MESSAGE_KEY =
		"com.cnm.common.Constants.MESSAGE_KEY";

	public static String CODE_BASE;
	public static String CODE;
	public static String SERVLET;
	public static String USES_LIBRARY;
	public static String USES_LIBRARY_CODE_BASE;
	public static String USES_LIBRARY_VERSION;
	
	public static final String CONTEXT	= GlobalContext.WEB_CONTEXT_ROOT;
	public static final String CSS		= CONTEXT + "/lib/css";
	public static final String JS		= CONTEXT + "/lib/js";
	public static final String IMG		= CONTEXT + "/images";
	
	public static final String SORT_COLUMN 	= "sort_column";
	public static final String SORT_COND 	= "sort_cond";
	
	public static final String SEARCH_TARGET_COND 	= "targetCond";
	public static final String SEARCH_KEYWORD_COND 	= "keywordCond";
	
	public static final String DATAMAP 	= "datamap";
	
	public static final boolean USE_FILTER_ENCODING = false;
	
	public static final String DATA_SOURCE_NAME = GlobalContext.DATASOURCE_JNDI_NAME;
	
	public static final boolean PRODUCTION_MODE = false;

	private Constants()
	{
	}

	public static Constants getInstance()
	{
		return ( instance == null ) ? ( instance = new Constants() ) : instance;
	}
}
