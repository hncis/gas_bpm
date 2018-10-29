package com.defaultcompany.web.gantt.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.uengine.persistence.dao.DAOFactory;
import org.uengine.persistence.dao.GanttChartDAO;
import org.uengine.processmanager.SimpleTransactionContext;
import org.uengine.util.UEngineUtil;
import org.uengine.util.dao.ConnectionFactory;
import org.uengine.util.dao.DefaultConnectionFactory;
import org.uengine.util.dao.GenericDAO;

import com.defaultcompany.web.gantt.model.GanttChartModel;
import com.defaultcompany.web.strategy.StrategyService;

public class GanttChartWebDAO  {

	//protected JdbcTemplate jdbcTemplate;
	protected ConnectionFactory dcf;
	
	public GanttChartWebDAO(ConnectionFactory dcf) {
		this.dcf = dcf;
	}
	
	public void init(int viewYear,String endpoint, String pd, String globalCom, String orderby, boolean loggedUserIsMaster, String strategyId){
		Calendar nowTemp;
		int firstDayOfMonthTemp =0; 
		int lastDateOfMonthTemp=0; 
		Calendar firstDateTemp; 
		Calendar lastDateTemp; 	
		int monthOfNowTemp;
		
		
		
		
		nowTemp = Calendar.getInstance();
		nowTemp.add(Calendar.YEAR, viewYear+1);
		setNowDate(nowTemp);

		firstDateTemp = (Calendar)nowTemp.clone();	
		firstDateTemp.set(Calendar.DATE, 1);
		setFirstDate(firstDateTemp);
		
		firstDayOfMonthTemp = firstDateTemp.get(Calendar.DAY_OF_WEEK) - 2;
		setFirstDayOfMonth(firstDayOfMonthTemp);
		

		lastDateTemp = (Calendar)nowTemp.clone();
		lastDateTemp.set(Calendar.DATE, 27);
		
		
		monthOfNowTemp = nowTemp.get(Calendar.MONTH);
		setMonthOfNow(monthOfNowTemp);
		
		while(lastDateTemp.get(Calendar.MONTH)==monthOfNowTemp){
			lastDateOfMonthTemp = lastDateTemp.get(Calendar.DATE);
			lastDateTemp.setTimeInMillis(lastDateTemp.getTimeInMillis() + 86400000L);
		}
		setLastDayOfMonth(lastDateOfMonthTemp);
		lastDateTemp.setTimeInMillis(lastDateTemp.getTimeInMillis() - 86400000L);
		
		setLastDate(lastDateTemp);
		
		setEndpoint(endpoint);
		setPd(pd);
		setOrderby(orderby);
		setLoggedUserIsMaster(loggedUserIsMaster);
		setGlobalCom(globalCom);
		
		List<Integer> strategyIds = new ArrayList<Integer>();
		if(UEngineUtil.isNotEmpty(strategyId)){
			StrategyService strategyService = new StrategyService();
			strategyIds =  strategyService.getStrategyIdListById(Integer.parseInt(strategyId));
			strategyIds.add(Integer.parseInt(strategyId)); //add itself again
		}
		
		//setStrategyIds(strategyIds);
		
		
	}
	
	
	List strategyIds;
		public void setStrategyIds(List strategyIds){
			this.strategyIds = strategyIds;
		}
		
		public List getStrategyIds(){
			return strategyIds;
		}
	
	Calendar firstDate;
		public void setFirstDate(Calendar firstDate){
			this.firstDate = firstDate;
		}
		
		public Calendar getFirstDate(){
			return firstDate;
		}
			
	Calendar lastDate;
		public void setLastDate(Calendar lastDate){
			this.lastDate = lastDate;
		}
		
		public Calendar getLastDate(){
			return lastDate;
		}
		
	Calendar now;
		public void setNowDate(Calendar nowDate){
			this.now = nowDate;
		}
		
		public Calendar getNowDate(){
			return now;
		}
		
		
	int firstDayOfMonth; 
		public void setFirstDayOfMonth(int month){
			this.firstDayOfMonth = month;
		}
		
		public int getFirstDayOfMonth(){
			return firstDayOfMonth;
		}
		
	int lastDateOfMonth;
		public void setLastDayOfMonth(int month){
			this.lastDateOfMonth = month;
		}
		
		public int getLastDayOfMonth(){
			return lastDateOfMonth;
		}
		
	
	int monthOfNow;
		public void setMonthOfNow(int month){
			this.monthOfNow = month;
		}
		
		public int getMonthOfNow(){
			return monthOfNow;
		}
		
	String endpoint;
		public void setEndpoint(String endpoint){
			this.endpoint = endpoint;
		}
		
		public String getEndpoint(){
			return endpoint;
		}
	
	String pd;
		public void setPd(String pd){
			this.pd = pd;
		}
		
		public String getPd(){
			return pd;
		}
    
	//******************************************************
    // - add 2011.03.14 tag
	String status;
    	public String getStatus() {
            return status;
        }
    
        public void setStatus(String status) {
            this.status = status;
        }

    String partCode;
    	public String getPartCode() {
            return partCode;
        }
    
        public void setPartCode(String partCode) {
            this.partCode = partCode;
        }

    String roleCode;
    	public String getRoleCode() {
            return roleCode;
        }
    
        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

    String tag;
    	public String getTag() {
            return tag;
        }
    
        public void setTag(String tag) {
            this.tag = tag;
        }
    // - add end
    //******************************************************

    String orderby;
		public void setOrderby(String orderby){
			this.orderby = orderby;
		}
		
		public String getOrderby(){
			return orderby;
		}
	
	boolean loggedUserIsMaster;
		public void setLoggedUserIsMaster(boolean loggedUserIsMaster){
			this.loggedUserIsMaster = loggedUserIsMaster;
		}
		
		public boolean getLoggedUserIsMaster(){
			return loggedUserIsMaster;
		}
		
	String globalCom;
		public void setGlobalCom(String globalCom){
			this.globalCom = globalCom;
		}
		
		public String getGlobalCom(){
			return globalCom;
		}
	
	
	private String getDateFunction() throws Exception {
		boolean useOracle = DAOFactory.getInstance(null).getDBMSProductName().startsWith("Oracle");
		boolean useMySQL = DAOFactory.getInstance(null).getDBMSProductName().startsWith("MySQL");
		boolean useMsSQL = DAOFactory.getInstance(null).getDBMSProductName().startsWith("MSsql");
		boolean useHSQL = DAOFactory.getInstance(null).getDBMSProductName().startsWith("HSQL");
		boolean usePostgresSQL = DAOFactory.getInstance(null).getDBMSProductName().startsWith("Postgres");
		
		String dateFunction = null;
		
		if (useOracle) dateFunction = "SYSDATE";
		else if (useMsSQL) dateFunction = "getdate()";
		else if (usePostgresSQL) dateFunction = "CURRENT_TIMESTAMP";
		else {
			dateFunction = "CAST(curdate() AS "+(useMySQL?"DATE":"TIMESTAMP")+")";
		}
		
		return dateFunction;
	}
	
	/**
	 * <pre>
	 * Converts the input value to a date 
	 * </pre>
	 *
	 * @param date
	 * @return
	 * @throws Exception
	 * 2011. 3. 8.  Sung-yeol Jung
	 */
	private String getDateFunction(String date) throws Exception {
        boolean useOracle = DAOFactory.getInstance(null).getDBMSProductName().startsWith("Oracle");
        boolean useMySQL = DAOFactory.getInstance(null).getDBMSProductName().startsWith("MySQL");
        boolean useMsSQL = DAOFactory.getInstance(null).getDBMSProductName().startsWith("MSsql");
        boolean useHSQL = DAOFactory.getInstance(null).getDBMSProductName().startsWith("HSQL");
        boolean usePostgresSQL = DAOFactory.getInstance(null).getDBMSProductName().startsWith("Postgres");
        
        String dateFunction = null;
        
        if (useOracle) {
            dateFunction = "TO_DATE('"+date+"', 'YYYY-MM-DD')";
        } else if (useMsSQL) {
            dateFunction = "CAST('"+date+"' AS DATETIME)";
        } else if (usePostgresSQL) {
        	dateFunction = "CAST('"+date+"' AS TIMESTAMP)";
        } else {
            dateFunction = "CAST('"+date+"' AS "+(useMySQL?"DATE":"TIMESTAMP")+")";
        }
        
        return dateFunction;
    }
		
	private String makeGanttChartCountWebQuery() throws Exception {
		String FIRST_DATE = Integer.toString( firstDate.get(Calendar.YEAR) );
		FIRST_DATE += "-";
		FIRST_DATE += "01";
		FIRST_DATE += "-";
		FIRST_DATE += "01";

		String LAST_DATE = Integer.toString( lastDate.get(Calendar.YEAR) );
		LAST_DATE += "-";
		LAST_DATE += "12";
		LAST_DATE += "-";
		LAST_DATE += "31";
		
		
		StringBuilder filtering = new StringBuilder();
		StringBuilder loop = new StringBuilder();
		
		if (!"".equals(endpoint)) {
			String[] endpoints = endpoint.split(";");
			filtering.append(" and bpm_worklist.endpoint in (");
			
			loop = new StringBuilder();
			for (int i=0; i<endpoints.length; i++) {
				if (loop.length() > 0) loop.append(", ");
				loop.append("?endpoint").append(String.valueOf(i));
			}
			
			filtering.append(loop).append(")");
		}
		
		if (!UEngineUtil.isDigit(pd)) pd = "";
		
		if (!"".equals(pd)) {
			String[] pds = pd.split(";");
			filtering.append(" and bpm_procinst.defid in (");
			
			loop = new StringBuilder();
			for (int i=0; i<pds.length; i++) {
				if (loop.length() > 0) loop.append(", ");
				loop.append("?pd").append(String.valueOf(i));
			}
			
			filtering.append(loop).append(")");
		}
		
		//***********************************************************
		// add 2011.03.14 filter
		if (!"".equals(status)) {
            filtering.append(" and bpm_procinst.status = ?status");
        }
        
        if (!"".equals(partCode)) {
            filtering.append(" and bpm_procinst.instId in (select distinct m.instid from bpm_rolemapping as m where m.groupid = ?partcode)");
        }
        
        if (!"".equals(roleCode)) {
            String[] roleCodes = roleCode.split(";");
            filtering.append(" and bpm_procinst.instId in (select distinct m.instid from bpm_rolemapping as m, roleusertable r where m.endpoint = r.empcode and rolecode in (");
            
            loop = new StringBuilder();
            for (int i=0; i<roleCodes.length; i++) {
                if (loop.length() > 0) loop.append(", ");
                loop.append("?rolecode").append(String.valueOf(i));
            }
            
            filtering.append(loop).append(") )");
        }
        
        if (!"".equals(tag)) {
            String[] tags = tag.split(";");
            filtering.append(" and bpm_procinst.instId in (select distinct m.instId from bpm_tag as t, bpm_tagmapping as m where t.tagid = m.tagid and t.name in (");
            
            loop = new StringBuilder();
            for (int i=0; i<tags.length; i++) {
                if (loop.length() > 0) loop.append(", ");
                loop.append("?name").append(String.valueOf(i));
            }
            
            filtering.append(loop).append(") )");
        }
        // end
		//***********************************************************
        
//		if (strategyIds.size() > 0) {
//			filtering.append(" and bpm_procinst.strategyId in (");
//			
//			StringBuilder ss = new StringBuilder();
//			for (int i=0; i<strategyIds.size(); i++) {
//				if (ss.length() > 0) ss.append(", ");
//				ss.append(String.valueOf(strategyIds.get(i)));
//			}
//			
//			filtering.append(ss).append(")");
//		}

		
		StringBuilder sql = new StringBuilder(); 
		
		sql.append(" SELECT count(1) TotalCount                             ").append("\n")
		   .append(" from bpm_worklist                                      ").append("\n")
		   .append("      ,bpm_procinst                                     ").append("\n")
		   .append(" where bpm_procinst.isdeleted=0                         ").append("\n")
		   .append("       and bpm_worklist.instId = bpm_procinst.instId    ").append("\n")
		   .append("       and bpm_worklist.status<>'CANCELLED'             ").append("\n")
		   .append("       and CASE WHEN bpm_worklist.startdate > "+getDateFunction(FIRST_DATE)).append("\n")
		   .append("               THEN bpm_worklist.startdate              ").append("\n")
		   .append("               ELSE " + getDateFunction(FIRST_DATE) + " ").append("\n")
		   .append("           END <= " + getDateFunction(LAST_DATE)         ).append("\n")
		   .append("       and CASE                                         ").append("\n")
		   .append("              WHEN                                      ").append("\n")
		   .append("                  (case when bpm_worklist.enddate is null   ").append("\n")
		   .append("                       then " + getDateFunction() + "       ").append("\n")
		   .append("                       else bpm_worklist.enddate            ").append("\n")
		   .append("                  end ) < " + getDateFunction(LAST_DATE)     ).append("\n")
		   .append("              THEN                                          ").append("\n")
		   .append("                  (case when bpm_worklist.enddate is null   ").append("\n")
		   .append("                       then " + getDateFunction()            ).append("\n")
		   .append("                       else bpm_worklist.enddate            ").append("\n")
		   .append("                  end )                                     ").append("\n")
		   .append("              ELSE                                          ").append("\n")
		   .append("                  " + getDateFunction(LAST_DATE)             ).append("\n")
		   .append("          END >= " + getDateFunction(FIRST_DATE)             ).append("\n")
		   .append(filtering);
		
			

		if(!loggedUserIsMaster) {		
			sql.append(" AND bpm_procinst.defid in (SELECT distinct bpm_procdef.defid FROM bpm_procdef WHERE comcode = ?globalcom)");
		}

		return sql.toString();
	}
		
	private String makeGanttChartWebQuery() throws Exception {
		String FIRST_DATE = Integer.toString( firstDate.get(Calendar.YEAR) );
		FIRST_DATE += "-";
		FIRST_DATE += "01";
		FIRST_DATE += "-";
		FIRST_DATE += "01";

		String LAST_DATE = Integer.toString( lastDate.get(Calendar.YEAR) );
		LAST_DATE += "-";
		LAST_DATE += "12";
		LAST_DATE += "-";
		LAST_DATE += "31";
		
		
		StringBuilder filtering = new StringBuilder();
		StringBuilder loop = new StringBuilder();
		if (!"".equals(endpoint)) {
			String[] endpoints = endpoint.split(";");
			filtering.append(" and bpm_worklist.endpoint in (");
			
			loop = new StringBuilder();
			for (int i=0; i<endpoints.length; i++) {
				if (loop.length() > 0) loop.append(", ");
				loop.append("?endpoint").append(String.valueOf(i));
			}
			
			filtering.append(loop).append(")");
		}
		
		if (!UEngineUtil.isDigit(pd)) pd = "";
		
		if (!"".equals(pd)) {
			String[] pds = pd.split(";");
			filtering.append(" and bpm_procinst.defid in (");
			
			loop = new StringBuilder();
			for (int i=0; i<pds.length; i++) {
				if (loop.length() > 0) loop.append(", ");
				loop.append("?pd").append(String.valueOf(i));
			}
			
			filtering.append(loop).append(")");
		}
		
		//***********************************************************
        // add 2011.03.14 filter
        if (!"".equals(status)) {
            filtering.append(" and bpm_procinst.status = ?status");
        }
        
        if (!"".equals(partCode)) {
            filtering.append(" and bpm_procinst.instId in (select distinct m.instid from bpm_rolemapping as m where m.groupid = ?partcode)");
        }
        
        if (!"".equals(roleCode)) {
            String[] roleCodes = roleCode.split(";");
            filtering.append(" and bpm_procinst.instId in (select distinct m.instid from bpm_rolemapping as m, roleusertable r where m.endpoint = r.empcode and rolecode in (");
            
            loop = new StringBuilder();
            for (int i=0; i<roleCodes.length; i++) {
                if (loop.length() > 0) loop.append(", ");
                loop.append("?rolecode").append(String.valueOf(i));
            }
            
            filtering.append(loop).append(") )");
        }
        
        if (!"".equals(tag)) {
            String[] tags = tag.split(";");
            filtering.append(" and bpm_procinst.instId in (select distinct m.instId from bpm_tag as t, bpm_tagmapping as m where t.tagid = m.tagid and t.name in (");
            
            loop = new StringBuilder();
            for (int i=0; i<tags.length; i++) {
                if (loop.length() > 0) loop.append(", ");
                loop.append("?name").append(String.valueOf(i));
            }
            
            filtering.append(loop).append(") )");
        }
        // end
        //***********************************************************
		
		StringBuilder sql = new StringBuilder(); 
		
		sql.append(" select   TASKID    ").append("\n")
        .append("        ,TITLE                      ").append("\n")
        .append("        ,DESCRIPTION                ").append("\n")
        .append("        ,ENDPOINT                   ").append("\n")
        .append("        ,bpm_worklist.STATUS        ").append("\n")
        .append("        ,CASE                       ").append("\n")
        .append("          WHEN  bpm_procinst.status <> 'COMPLETED' AND (case when bpm_procinst.duedate is null   ").append("\n")
        .append("                     then "+getDateFunction()+"                ").append("\n")
        .append("                     else bpm_procinst.duedate                 ").append("\n")
        .append("                end ) < "+getDateFunction()+"                  ").append("\n")
        .append("            THEN                                               ").append("\n")
        .append("                'delay'                                        ").append("\n")
        .append("            ELSE                                               ").append("\n")
        .append("               bpm_procinst.status                             ").append("\n")
        .append("         END inststatus                                        ").append("\n")
        .append("        ,PRIORITY                                              ").append("\n")
        .append("        ,startdate                                             ").append("\n")
        .append("        ,CASE                                                  ").append("\n")
        .append("              WHEN                                             ").append("\n")
        .append("                  (case when bpm_worklist.enddate is null      ").append("\n")
        .append("                       then "+getDateFunction()+"              ").append("\n")
        .append("                       else bpm_worklist.enddate               ").append("\n")
        .append("                  end ) < " + getDateFunction(LAST_DATE) + "   ").append("\n")
        .append("              THEN                                             ").append("\n")
        .append("                  (case when bpm_worklist.enddate is null      ").append("\n")
        .append("                       then "+getDateFunction()+"              ").append("\n")
        .append("                       else bpm_worklist.enddate               ").append("\n")
        .append("                  end )                                        ").append("\n")
        .append("              ELSE                                             ").append("\n")
        .append("                    " + getDateFunction(LAST_DATE) + "         ").append("\n")
        .append("          END enddate               ").append("\n")
        .append("        ,bpm_worklist.DUEDATE       ").append("\n")
        .append("        ,bpm_worklist.INSTID        ").append("\n")
        .append("        ,bpm_worklist.DEFID         ").append("\n")
        .append("        ,bpm_worklist.DEFNAME       ").append("\n")
        .append("        ,TRCTAG                     ").append("\n")
        .append("        ,TOOL                       ").append("\n")
        .append("        ,PARAMETER                  ").append("\n")
        .append("        ,GROUPID                    ").append("\n")
        .append("        ,GROUPNAME                  ").append("\n")
        .append("        ,bpm_worklist.EXT1          ").append("\n")
        .append("        ,bpm_worklist.EXT2          ").append("\n")
        .append("        ,bpm_worklist.EXT3          ").append("\n")
        .append("        ,ISURGENT                   ").append("\n")
        .append("        ,HASATTACHFILE              ").append("\n")
        .append("        ,HASCOMMENT                 ").append("\n")
        .append("        ,DOCUMENTCATEGORY           ").append("\n")
        .append("        ,bpm_worklist.ISDELETED     ").append("\n")
        .append("        ,bpm_worklist.ROOTINSTID    ").append("\n")
        .append("        ,DISPATCHOPTION             ").append("\n")
        .append("        ,DISPATCHPARAM1             ").append("\n")
        .append("        ,ROLENAME                   ").append("\n")
        .append("        ,RESNAME                    ").append("\n")
        .append("        ,REFROLENAME                ").append("\n")
        .append("        ,EXECSCOPE                  ").append("\n")
        .append("        ,SAVEDATE                   ").append("\n")
        .append("        ,DEFVERID                   ").append("\n")
        .append("        ,DEFPATH                    ").append("\n")
        .append("        ,DEFMODDATE                 ").append("\n")
        .append("        ,STARTEDDATE                ").append("\n")
        .append("        ,FINISHEDDATE               ").append("\n")
        .append("        ,INFO                       ").append("\n")
        .append("        ,NAME                       ").append("\n")
        .append("        ,ISADHOC                    ").append("\n")
        .append("        ,ISARCHIVE                  ").append("\n")
        .append("        ,ISSUBPROCESS               ").append("\n")
        .append("        ,ISEVENTHANDLER             ").append("\n")
        .append("        ,MAININSTID                 ").append("\n")
        .append("        ,MAINDEFVERID               ").append("\n")
        .append("        ,MAINACTTRCTAG              ").append("\n")
        .append("        ,MAINEXECSCOPE              ").append("\n")
        .append("        ,ABSTRCPATH                 ").append("\n")
        .append("        ,DONTRETURN                 ").append("\n")
        .append("        ,INITEP                     ").append("\n")
        .append("        ,INITRSNM                   ").append("\n")
        .append("        ,CURREP                     ").append("\n")
        .append("        ,CURRRSNM                   ").append("\n")
        .append(" from bpm_worklist                  ").append("\n")
        .append("      ,bpm_procinst                 ").append("\n")
        .append(" where bpm_procinst.isdeleted = 0   ").append("\n")
        .append("       and bpm_worklist.instId = bpm_procinst.instId                               ").append("\n")
        .append("       and bpm_worklist.status<>'CANCELLED'                                        ").append("\n")
        .append("       and CASE WHEN bpm_worklist.startdate > " + getDateFunction(FIRST_DATE) + "  ").append("\n")
        .append("               THEN bpm_worklist.startdate                                         ").append("\n")
        .append("               ELSE " + getDateFunction(FIRST_DATE) + "                            ").append("\n")
        .append("           END <= " + getDateFunction(LAST_DATE) + "                               ").append("\n")
        .append("       and CASE                                                                    ").append("\n")
        .append("              WHEN                                                                 ").append("\n")
        .append("                  (case when bpm_worklist.enddate is null                          ").append("\n")
        .append("                       then "+getDateFunction()+"                                  ").append("\n")
        .append("                       else bpm_worklist.enddate                                   ").append("\n")
        .append("                  end ) < " + getDateFunction(LAST_DATE) + "                       ").append("\n")
        .append("              THEN                                                                 ").append("\n")
        .append("                  (case when bpm_worklist.enddate is null                          ").append("\n")
        .append("                       then "+getDateFunction()+"                                  ").append("\n")
        .append("                       else bpm_worklist.enddate                                   ").append("\n")
        .append("                  end )                                                            ").append("\n")
        .append("              ELSE                                                                 ").append("\n")
        .append("                  " + getDateFunction(LAST_DATE) + "                               ").append("\n")
        .append("          END >= " + getDateFunction(FIRST_DATE) + "                               ").append("\n")
        .append(filtering);

		if(!loggedUserIsMaster) {		
			sql.append(" AND bpm_procinst.defid in (SELECT distinct bpm_procdef.defid FROM bpm_procdef WHERE comcode = ?globalcom)");
		}
		
		sql.append(" order by  bpm_worklist.instId desc, bpm_worklist.taskid,bpm_worklist.resname ");
		
		return sql.toString();
	}
	
	public GanttChartModel makeGanttChart() throws Exception {
		GanttChartModel ganttChartModel = null;
	
		String queryStr = makeGanttChartWebQuery();
		GanttChartDAO task = (GanttChartDAO)GenericDAO.createDAOImpl(DefaultConnectionFactory.create(), queryStr.toString(), GanttChartDAO.class);
		task.set("pd", pd);
		
		String[] endpoints = endpoint.split(";");
		for (int i=0; i<endpoints.length; i++) {			
			task.set("endpoint"+i, endpoints[i]);
		}
		
		String[] pds = pd.split(";");
		if(UEngineUtil.isNotEmpty(pds)){
			for (int i=0; i<pds.length; i++) {
					task.set("pd"+i, new Long(pds[i]));
			}
		}
		
		//***********************************************************
        // add 2011.03.14 filter
        if (!"".equals(status)) {
            task.set("status", status);
        }
        
        if (!"".equals(partCode)) {
            task.set("partcode", partCode);
        }
        
        if (!"".equals(roleCode)) {
            String[] roleCodes = roleCode.split(";");
            for (int i=0; i<roleCodes.length; i++) {
                task.set("rolecode"+i, roleCodes[i].trim());
            }
        }
        
        if (!"".equals(tag)) {
            String[] tags = tag.split(";");
            for (int i=0; i<tags.length; i++) {
                task.set("name"+i, tags[i].trim());
            }
        }
        // end
        //***********************************************************
		
		//task.set("endpoint", endpoint);
		task.set("globalcom", globalCom);
		task.select();
		
		ganttChartModel = new GanttChartModel();
		ganttChartModel.setEndpoint(getEndpoint());
		ganttChartModel.setFirstDate(getFirstDate());
		ganttChartModel.setFirstDayOfMonth(getFirstDayOfMonth());
		ganttChartModel.setLastDayOfMonth(getLastDayOfMonth());
		ganttChartModel.setGanntChartDao(task);
		ganttChartModel.setLastDate(getLastDate());
		ganttChartModel.setLoggedUserIsMaster(getLoggedUserIsMaster());
		ganttChartModel.setMonthOfNow(getMonthOfNow());
		ganttChartModel.setNowDate(getNowDate());
		ganttChartModel.setOrderby(getOrderby());
		ganttChartModel.setPd(getPd());
		
		
		return ganttChartModel;
	}
	
	
	public int getGanttChartCount() throws Exception {
		int totalSize=0;
		SimpleTransactionContext stc = new SimpleTransactionContext();
		
		try {
			String queryStr = makeGanttChartCountWebQuery();
			GanttChartDAO task = (GanttChartDAO)GenericDAO.createDAOImpl(stc, queryStr.toString(), GanttChartDAO.class);
			task.set("pd", pd);
			task.set("globalcom", globalCom);
			
			String[] endpoints = endpoint.split(";");
			for (int i=0; i<endpoints.length; i++) {
				task.set("endpoint"+i, endpoints[i]);
			}
			
			String[] pds = pd.split(";");
			if(UEngineUtil.isNotEmpty(pds)){
				for (int i=0; i<pds.length; i++) {
					task.set("pd"+i, new Long(pds[i]));
				}
			}
			
			//***********************************************************
	        // add 2011.03.14 filter
	        if (!"".equals(status)) {
	            task.set("status", status);
	        }
	        
	        if (!"".equals(partCode)) {
	            task.set("partcode", partCode);
	        }
	        
	        if (!"".equals(roleCode)) {
	            String[] roleCodes = roleCode.split(";");
	            for (int i=0; i<roleCodes.length; i++) {
	                task.set("rolecode"+i, roleCodes[i].trim());
	            }
	        }
	        
	        if (!"".equals(tag)) {
	            String[] tags = tag.split(";");
	            for (int i=0; i<tags.length; i++) {
	                task.set("name"+i, tags[i].trim());
	            }
	        }
	        // end
	        //***********************************************************
			
			task.select();
			
			while(task.next()){
				totalSize = task.getTotalCount();
			}
		} finally {
			stc.releaseResources();
		}
		return totalSize;	
		
	}
	
}