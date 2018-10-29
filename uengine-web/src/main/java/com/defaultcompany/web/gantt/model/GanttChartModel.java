package com.defaultcompany.web.gantt.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.viewer.gantt.GanttChartUtil;
import org.uengine.persistence.dao.GanttChartDAO;

public class GanttChartModel {

	public final static String VIEW_TYPE_MONTH="month";
	public final static String VIEW_TYPE_DAY="day";
	public final static String VIEW_TYPE_WEEK="week";
	
	private final String[] VIEW_MONTH = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "Nobvember", "December"};
	private final String[] VIEW_MONTH_ACR = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nob", "Dec"};
	
	private GanttChartDAO ganttDao;
		public void setGanntChartDao(GanttChartDAO ganttDao){
			this.ganttDao = ganttDao;
		}
		
		public GanttChartDAO getGanttChartDao(){
			return ganttDao;
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
		
		
	public String ganttChartTitleToHTML(int currentPage,int intPageCnt) throws Exception{
		
		currentPage=currentPage==0?1:currentPage;
		intPageCnt=intPageCnt==0?10:intPageCnt;
		orderby=orderby.equals("")?"instance":orderby;
		
		
		StringBuilder sb = new StringBuilder();	
		int totalCount = ganttDao.size();

		Calendar today = Calendar.getInstance();
		int todayDate = today.get(Calendar.DATE);
		String oldProcessInstance = "";
		String oldResName = "";

		int rowCount = 0;
		
		int firstRowCount = (currentPage == 1) ? 0 : (currentPage-1) * intPageCnt + 1;
		int lastRowCount = (currentPage * intPageCnt);
		
		String startdate=""; 
		String enddate="";
		String colTitle = "";
		
		if (orderby.equals("resource")) {
			colTitle = GlobalContext.getLocalizedMessageForWeb("By_Instance", "ko", "Instance");
		} else if (orderby.equals("instance")) {
	    	colTitle = GlobalContext.getLocalizedMessageForWeb("Resource", "ko", "Resource");
		}
		
		
		sb.append("<table  style='table-layout:fixed;z-index:999999999999999999999;' bgcolor='#FFFFFF'  border='0' cellspacing='0' cellpadding='0' width='450px' align='left' >");
		sb.append("	<tr  style='border:1px solid #C7D3E4; background:#f2f5f7;height:40px;color:#29384a;font-weight:bold;font-size:11px;'>");
		sb.append("		<th width=60%  style='border:1px solid #C7D3E4;'>"+GlobalContext.getLocalizedMessageForWeb("Task", "ko", "Task") +"</th>");
		sb.append("		<th width=40%  style='border:1px solid #C7D3E4;'>"+colTitle+"</th>");
		
		sb.append("	</tr>");
		
		if (totalCount <= 0) {
		    sb.append(" <tr class='gantable'>");
		    sb.append("     <td style='padding-left:5px;height:30px;' bgcolor='#FFFFFF' colspan=2 align='center'>No Data</td>");
		    sb.append(" </tr>");
		} else {
    		while(ganttDao.next()){
    			rowCount++; 
    			
    			if (rowCount >= firstRowCount && rowCount <= lastRowCount) {
    			    int expCount = rowCount - (intPageCnt * (currentPage - 1));
    				int	start=1, end=-1, dueDate=-1;
    				String status = ganttDao.getStatus();
    				
    				try{
    					if(ganttDao.getStartDate()!=null){
    						Calendar startCalendar = Calendar.getInstance();
    						startCalendar.setTime(ganttDao.getStartDate());
    						startdate=(ganttDao.getStartDate()).toString(); 
    						//System.out.println(startdate);
    		
    						if(startCalendar.before(firstDate))
    							start = 1;
    						else
    							start = ganttDao.getStartDate().getDate();
    					}else
    						start = todayDate;
    		
    					if(ganttDao.getEndDate()!=null){
    						Calendar endCalendar = Calendar.getInstance();
    						endCalendar.setTime(ganttDao.getEndDate());
    						
    						if(endCalendar.before(firstDate))
    							end = 1;
    						else
    							end = ganttDao.getEndDate().getDate();
    					}else
    						end = todayDate;
    		
    					if(ganttDao.getDueDate()!=null){
    						Calendar dueDateCalendar = Calendar.getInstance();
    						dueDateCalendar.setTime(ganttDao.getDueDate());
    						enddate=(ganttDao.getDueDate()).toString();
    						//System.out.println(enddate);
    						
    						if(dueDateCalendar.before(firstDate)){
    							dueDate = 0;
    						}else if(dueDateCalendar.after(lastDate)){
    							dueDate = lastDateOfMonth;//implicitly make it out of bound
    						}else{
    							dueDate = ganttDao.getDueDate().getDate();
    						}
    					}else{
    						dueDate = end;
    					}
    					
    					
    					if(!ganttDao.getStatus().equals("COMPLETED") && dueDate < end){
    						status="DELAY";
    					}
    		
    				}catch(Exception e){
    					//e.printStackTrace();
    				}
    
    				if(status.equals("Skipped") || status.equals("CANCELLED") || status.equals("Stopped") || status.equals("Timeout"))
    					end = dueDate;
    		
    				if(end==-1) end = todayDate;
    				else if(end<start) end = lastDateOfMonth;
    		
    				int drawingEnd = (dueDate > end ? dueDate:end);
    		
    				String processInstanceLabel;
    				
    				if(orderby.equals("instance")){
    					
    		
    		    		if(oldProcessInstance.equals(""+ganttDao.getInstId())){
    		    			processInstanceLabel = "";
    		    		}else{
    		    			
    		    			if(expCount>1){
    		    				sb.append("<tr ><td height='15px' colspan='2' bgcolor='#FFFFFF' style='border:none;'></td></tr>");
    		    			}	
    		    			
    		    			
    		    			processInstanceLabel = ganttDao.get("name") + " ("+ganttDao.getInstId()+")";
    		    			oldProcessInstance = ""+ganttDao.getInstId();
    	
    		    			String instStatus=(String)ganttDao.get("inststatus");
    		    			String statusClass="runsectiontitle";
    		    			if(instStatus!=null && instStatus.equals("Completed"))
    		    				statusClass="comsectiontitle";
    		    			else if(instStatus!=null && instStatus.equals("Running"))
    		    				statusClass="runsectiontitle";
    		    			else if(instStatus!=null &&instStatus.equals("DELAY"))
    		    				statusClass="delaysectiontitle";
    		    			
    		    				
    		    			sb.append("<tr class='gantable'>");
    		    			sb.append("	<td colspan='2' bgcolor='#FFFFFF' style='padding-left:5px;  height:30px;' >");
    		    			sb.append("	<span class='"+statusClass+"' style='position:relative;  z-index:1'><nobr><a href=\"javascript:openProcess('"+ganttDao.getInstId()+"')\"><b style='color:#2E4074'>"+processInstanceLabel+"</b></a></nobr></span>");
    		    			sb.append("	</td>");
    		    			sb.append("</tr>");
    
    		    		}
    		
    		    		String userName = ganttDao.getResName();
    
    	
    		    		sb.append("<tr class='gantable'>");
    	
    				   	sb.append("<td bgcolor=#F4F4F4 style='padding-left:5px;text-overflow:ellipsis; overflow:hidden;' height='17'><nobr>"+ganttDao.getTitle()+"</nobr></td>");
    				   	sb.append("<td bgcolor=#F4F4F4 align=center height='17' style='text-overflow:ellipsis; overflow:hidden;'><nobr>"+userName+"</nobr></td>");
    					
    				   	sb.append("</tr>");
    	
    		        }else{
    		        	
    		        	String userName = ganttDao.getResName();
    
    		            if(oldResName.equals(""+userName)){
    		    			userName = "";
    		    		}else{
    		    			oldResName = ""+userName;
    		        	
    		    			if(expCount>1){
    		    				sb.append("<tr><td height='15px' colspan='2' bgcolor='#FFFFFF' style='border:none;'></td></tr>");
    		    			}	
    		    			
    		    			sb.append("<tr class='gantable'>");
    		    			sb.append("	<td colspan='2' bgcolor='#FFFFFF' style='padding-left:5px; height:30px;'>");
    		    			sb.append("    <span class='sectiontitle' >");
    		    			//sb.append("	<a href=\"javascript:openProcess('"+ganttDao.getInstId()+"')\"><b style='color:#2E4074'>"+userName+"</b></a></span>");
    		    			sb.append("        <b style='color:#2E4074'>"+userName+"</b></span>");
    		    			sb.append("    </span>");
    		    			sb.append("	</td>");
    		    			sb.append("</tr>");
    		    		}
    		
    		            processInstanceLabel = ganttDao.get("name") + " ("+ganttDao.getInstId()+")";
    
    	
    		    		sb.append("<tr class='gantable'>");
    	
    				   	sb.append("<td bgcolor=#F4F4F4 style='padding-left:5px;' height='17'>"+ganttDao.getTitle()+"</td>");
    				   	sb.append("<td bgcolor=#F4F4F4 align=left><a href=\"javascript:openProcess('"+ganttDao.getInstId()+"')\"><b style='color:#2E4074'>"+processInstanceLabel+"</b></a></td>");
    				   	
    				   	sb.append("</tr>");
    		        	
    		        	
    		        }
    			}// end if
    		} //end while
		}

		sb.append("</table>");
		
		ganttDao.releaseResource();
			
		return sb.toString();
	}
	
	
	
	
public String ganttChartListToHTML(int currentPage,int intPageCnt,String viewMode) throws Exception{
		
		currentPage=currentPage==0?1:currentPage;
		intPageCnt=intPageCnt==0?10:intPageCnt;
		orderby=orderby.equals("")?"instance":orderby;
		
		
		StringBuilder sb = new StringBuilder();	
		int totalCount = ganttDao.size();

		Calendar today = Calendar.getInstance();
		int todayDate = today.get(Calendar.DATE);
		String oldProcessInstance = "";
		String oldResName = "";
		
		int rowCount = 0;
		
		int firstRowCount = (currentPage == 1) ? 0 : (currentPage-1) * intPageCnt + 1;
		int lastRowCount = (currentPage * intPageCnt);
		
		
		Calendar firstDate=Calendar.getInstance();
		firstDate.set(Calendar.YEAR, now.get(Calendar.YEAR));
		firstDate.set(Calendar.MONTH, 0);
		firstDate.set(Calendar.DATE, 1);
		
		Calendar lastDate=Calendar.getInstance();
		lastDate.set(Calendar.YEAR, now.get(Calendar.YEAR));
		lastDate.set(Calendar.MONTH, 11);
		lastDate.set(Calendar.DATE, 31);
		
		
		if(viewMode.equals(VIEW_TYPE_DAY)){
			int nDaysList[] = {31 , 28 , 31 , 30 ,31 ,30, 31, 31 , 30 ,31 ,30 ,31}; 
			rowCount = 0;
			
			firstRowCount = (currentPage == 1) ? 0 : (currentPage-1) * intPageCnt + 1;
			lastRowCount = (currentPage * intPageCnt);
			
			String startdate=""; 
			String enddate="";
			String duedate="";
			String colTitle = "";
			int curdays=0;
			
			
			int todayMonth=today.getTime().getMonth();
			for(int i=0; i<todayMonth; i++){
				curdays=curdays+nDaysList[i];
			}
			
			curdays = curdays+today.getTime().getDate();
			
			if (orderby.equals("resource")) {
				colTitle = GlobalContext.getLocalizedMessageForWeb("By_Instance", "ko", "Instance");
			} else if (orderby.equals("instance")) {
		    	colTitle = GlobalContext.getLocalizedMessageForWeb("Resource", "ko", "Resource");
			}
			
			int leftSize = 0;
			int tempCount=0;
			for(int i=0; i<nDaysList.length; i++){
    			for(int day=1; day <= nDaysList[i]; day++){
    			    tempCount++;
    			    if(tempCount==curdays) {
    			        leftSize = (tempCount * -15) + 300;
    			        break;
    			    }
    			}
			}
			
			if (leftSize > 0) {
			    leftSize = 0;
			}
			
			sb.append("<table id='ganttable' style='table-layout:fixed;left:"+leftSize+"px' bgcolor='#C7D3E4' border='0' cellspacing='0' cellpadding='0' width='100%'>");
			sb.append("	<tr style='border:1px solid #C7D3E4; background:#f2f5f7;height:20px;color:#29384a;font-size:11px;'>");
			
			tempCount=0;
			for(int i=0; i<nDaysList.length; i++){
				for(int j=1; j<=nDaysList[i]; j++){
					tempCount++;
					sb.append("<td width=15  style='border:1px solid #C7D3E4;'>");			
					if(tempCount==curdays) {
						sb.append("	<div style='position:relative; width:100%'><div id='curBarDiv' style='position:absolute;width:100%;height:10;z-index:2;filter:alpha(opacity=50);background-color:#F2E3BD;opacity:0.5;'></div></div>");
					}
					
					sb.append("	<div style='position:relative; width:100%' align='center'>");
					if((j + firstDayOfMonth)%7==0) {
						sb.append("<font size='-2' color=red>"+j+"</font>");
					} else {
					    sb.append("<font size='-2'>"+j+"</font>");
					}
					    
					sb.append("</div>");
					
					
					sb.append("</td>");
				}
				
			}
			
			sb.append("	</tr>");
			
			// month start
            sb.append("  <tr style='border:1px solid #C7D3E4; background:#f2f5f7;height:20px;color:#29384a;font-weight:bold;font-size:11px;'>");
            
            for(int i=0; i<nDaysList.length; i++){
                sb.append("<th colspan='"+nDaysList[i]+"'  style='border:1px solid #C7D3E4;'>"+(i+1)+" ("+VIEW_MONTH[i]+")"+"</th>");
            }
            sb.append(" </tr>");
            // month end
            
            if (ganttDao.size() <= 0) {
                sb.append("  <tr class='gantable'>");
                for(int i=0; i<nDaysList.length; i++){
                    for(int j=1; j<=nDaysList[i]; j++){
                        sb.append("<td bgcolor='white' style='height:30px;'>&nbsp;</td>");          
                    }
                }
                sb.append("  </tr>");
            } else {
    			while(ganttDao.next()){
    				
    				rowCount++; 
    				
    				if (rowCount >= firstRowCount && rowCount <= lastRowCount) {
    				    int expCount = rowCount - (intPageCnt * (currentPage - 1));
    					int	start=-1, end=-1, dueDate=-1;
    					String status = ganttDao.getStatus();
    					
    					
    					try{
    						if(ganttDao.getStartDate()!=null){
    							Calendar startCalendar = Calendar.getInstance();
    							startCalendar.setTime(ganttDao.getStartDate());
    							startdate=(ganttDao.getStartDate()).toString(); 
    							//System.out.println(startdate);
    							
    							
    							//System.out.println("firstdate:"+firstDate.getTime());
    			
    							if(startCalendar.before(firstDate)){
    								start = 1;
    							}else{
    								int startMonth=ganttDao.getStartDate().getMonth();
    								for(int i=0; i<startMonth; i++){
    									start=start+nDaysList[i];
    								}
    								start = start+ganttDao.getStartDate().getDate();
    							}
    						}else{
    							int startMonth=now.get(Calendar.MONTH);
    							for(int i=0; i<startMonth; i++){
    								start=start+nDaysList[i];
    							}
    							start = start+todayDate;
    							
    						}
    			
    						if(ganttDao.getEndDate()!=null){
    							Calendar endCalendar = Calendar.getInstance();
    							endCalendar.setTime(ganttDao.getEndDate());
    							
    							if(endCalendar.before(firstDate)){
    								end = 1;
    							}else{
    								
    								int endMonth=ganttDao.getEndDate().getMonth();
    								for(int i=0; i<endMonth; i++){
    									end=end+nDaysList[i];
    								}
    								end = end+ganttDao.getEndDate().getDate();
    							}
    						}else{
    							int endMonth=now.get(Calendar.MONTH)-1;
    							for(int i=0; i<endMonth; i++){
    								end=end+nDaysList[i];
    							}
    							end = todayDate;
    						}
    			
    						if(ganttDao.getDueDate()!=null){
    							Calendar dueDateCalendar = Calendar.getInstance();
    							dueDateCalendar.setTime(ganttDao.getDueDate());
    							
    							
    							if(dueDateCalendar.before(firstDate)){
    								dueDate = 0;
    							}else if(dueDateCalendar.after(lastDate)){
    								dueDate = 365;//implicitly make it out of bound
    							}else{
    								int endMonth=dueDateCalendar.get(Calendar.MONTH);
    								for(int i=0; i<endMonth; i++){
    									dueDate=dueDate+nDaysList[i];
    								}
    								dueDate = dueDate+ganttDao.getDueDate().getDate();
    							}
    						}else{
    							dueDate = end;
    						}
    						
    						
    						if(!ganttDao.getStatus().equals("COMPLETED") && dueDate < end){
    							status="DELAY";
    						}
    			
    					}catch(Exception e){
    						//e.printStackTrace();
    					}
    	
    					if(status.equals("Skipped") || status.equals("CANCELLED") || status.equals("Stopped") || status.equals("Timeout"))
    						end = dueDate;
    			
    					if(end==-1) end = todayDate;
    					else if(end<start) end = lastDateOfMonth;
    			
    					int drawingEnd = (dueDate > end ? dueDate:end);
    			
    					String processInstanceLabel;
    					
    					if(orderby.equals("instance")){
    			
    			
    			    		if(oldProcessInstance.equals(""+ganttDao.getInstId())){
    			    			processInstanceLabel = "";
    			    		}else{
    			    			processInstanceLabel = ganttDao.get("name") + " ("+ganttDao.getInstId()+")";
    			    			oldProcessInstance = ""+ganttDao.getInstId();
    		
    			    			if(expCount>1){
    			    				sb.append("<tr><td height='15px' colspan='365' bgcolor='#FFFFFF' style='border:none;'></td></tr>");
    			    			}	
    			    			
    			    			sb.append("<tr class='gantable'>");
    			    			sb.append("	<td colspan='365' bgcolor='#FFFFFF' style='padding-left:5px; height:30px;'><span class='sectiontitle' >");
    			    			sb.append("	&nbsp;");
    			    			sb.append("	</td>");
    			    			sb.append("</tr>");
    	
    			    		}
    			
    			    		String userName = ganttDao.getResName();
    	
    		
    			    		sb.append("<tr class='gantable'>");
    		
    			    		for(int d=1; d<=start; d++){
    			    			sb.append("<td bgcolor='white' height='17px'></td>");
    			    		}
    			    		
    			    		GanttChartUtil ganttChartUtil = new GanttChartUtil();
    			   			String chartBar = ganttChartUtil.getGanttChartBarHtml(start+1, drawingEnd+1, end+1, dueDate+1, status);
    			   			sb.append(chartBar);
    			   			
    			   			if(drawingEnd<365) {
    			   				for(int d=drawingEnd+1; d<=365; d++){
    			    				sb.append("<td bgcolor='white' height='17px'></td>");
    			   				}
    			   			}
    			   			
    					   	sb.append("</tr>");
    			        }else{
    			            String userName = ganttDao.getResName();

                            if(oldResName.equals(""+userName)){
                                userName = "";
                            }else{
                                oldResName = ""+userName;
                                sb.append("<tr class='gantable'>");
                                sb.append(" <td colspan='365' bgcolor='#FFFFFF' style='padding-left:5px; height:30px;'><span class='sectiontitle' >");
                                sb.append(" &nbsp;");
                                sb.append(" </td>");
                                sb.append("</tr>");
                                

                            }
            
                            sb.append("<tr class='gantable'>");
            
                            for(int d=1; d<=start; d++){
                                sb.append("<td bgcolor=white></td>");
                            }
                            
                            GanttChartUtil ganttChartUtil = new GanttChartUtil();
                            String chartBar = ganttChartUtil.getGanttChartBarHtml(start+1,drawingEnd+1, end+1, dueDate+1, status);
                            sb.append(chartBar);
                            
                            if(drawingEnd<365) {
                                for(int d=drawingEnd+1; d<=365; d++){
                                    sb.append("<td bgcolor=white></td>");
                                }
                            }
                            
                            sb.append("</tr>");
    			        }
    				}
    			} //end while
            }
			
			sb.append("</table>");
		}else if(viewMode.equals(VIEW_TYPE_MONTH)){
			rowCount = 0;			
			firstRowCount = (currentPage == 1) ? 0 : (currentPage-1) * intPageCnt + 1;
			lastRowCount = (currentPage * intPageCnt);
			int curMonth=today.getTime().getMonth()+1;
			
			sb.append("<table id='ganttable' style='table-layout:fixed;' bgcolor='#C7D3E4'  border='0' cellspacing='0' cellpadding='0' width='100%'>");
						
			sb.append("	<tr style='border:1px solid #C7D3E4; background:#f2f5f7;height:40px;color:#29384a;font-weight:bold;font-size:11px;'>");
			
			int tempCount=0;
			for(int i=1; i<=12; i++){
				tempCount++;
				sb.append("<td  style='border:1px solid #C7D3E4;'>");
				if(tempCount==curMonth) {
					sb.append("	<div style='position:relative; width:100%'><div id='curBarDiv' style='position:absolute;width:100%;height:10;z-index:2;filter:alpha(opacity=50);background-color:#F2E3BD;opacity:0.5;'></div></div>");						
				}
				sb.append("<div style='position:relative; width:100%' align='center'><font size='-2'>"+i+ " (" +VIEW_MONTH_ACR[i-1] + ")</font><div>");
				sb.append("</td>");
			}
			
			sb.append("</tr>");
			
			if (ganttDao.size() <= 0) {
                sb.append("  <tr class='gantable'>");
                for(int i=1; i<=12; i++){
                    sb.append("<td bgcolor='white' style='height:30px;'>&nbsp;</td>");          
                }
                sb.append("  </tr>");
            } else {
    			while(ganttDao.next()){
    				rowCount++; 
    				
    				if (rowCount >= firstRowCount && rowCount <= lastRowCount) {
    				    int expCount = rowCount - (intPageCnt * (currentPage - 1));
    					String startdate=(ganttDao.getStartDate()).toString().substring(0,10);
    					String enddate=(ganttDao.getEndDate()).toString().substring(0,10);
    					String duedate=(ganttDao.getDueDate()).toString().substring(0,10);
    					
    					
    					
    					int startMonth=1;
    					int startday=1;
    					
    					int endMonth=1;
    					int endday=1;
    					
    					int dueMonth=1;
    					int dueday=1;
    					
    					if(ganttDao.getStartDate()!=null){
    						Calendar startCalendar = Calendar.getInstance();
    						startCalendar.setTime(ganttDao.getStartDate());
    						startdate=(ganttDao.getStartDate()).toString(); 
    		
    						if(startCalendar.before(firstDate)){
    							startMonth = 1;
    							startday=(100-(100/31))/10;
    						}else{
    							startMonth=Integer.parseInt(startdate.substring(5,7));
    							startday=(100-(Integer.parseInt(startdate.substring(8,10))*(100/31)))/10;
    						}
    					}else{
    						startMonth=now.get(Calendar.MONTH)+1;
    						
    						startday =now.get(Calendar.DAY_OF_MONTH);						
    					}
    					
    					
    					if(ganttDao.getEndDate()!=null){
    						Calendar endCalendar = Calendar.getInstance();
    						endCalendar.setTime(ganttDao.getEndDate());
    						
    						if(endCalendar.before(firstDate)){
    							endMonth = 1;
    							endday = (100-(100/31))/10;
    						}else{
    							endMonth=Integer.parseInt(enddate.substring(5,7));
    							endday=(Integer.parseInt(enddate.substring(8,10))*(100/31))/10;
    						}
    					}else{
    						startMonth=now.get(Calendar.MONTH)+1;						
    						startday =now.get(Calendar.DAY_OF_MONTH);
    					}
    		
    					if(ganttDao.getDueDate()!=null){
    						Calendar dueDateCalendar = Calendar.getInstance();
    						dueDateCalendar.setTime(ganttDao.getDueDate());
    						
    						
    						if(dueDateCalendar.before(firstDate)){
    							dueMonth = 0;
    							dueday = 0;
    						}else if(dueDateCalendar.after(lastDate)){
    							dueMonth = 12;//implicitly make it out of bound
    							dueday = 31;
    						}else{
    							dueMonth=Integer.parseInt(duedate.substring(5,7));
    							dueday=(Integer.parseInt(duedate.substring(8,10))*(100/31))/10;
    						}
    					}else{
    						dueMonth=endMonth;
    						dueday=endday;
    					}
    					
    					int drawEndMonth=dueMonth>endMonth?dueMonth:endMonth;
    							
    					//System.out.println(startMonth+"	"+startday+"	"+endMonth+"	"+endday);
    					
    					float start=(float)(startMonth*10+startday)/10;		
    					float end=(float)(endMonth*10+endday)/10;;
    					float dueDate=(float)(dueMonth*10+dueday)/10;
    					float drawingEnd=(float)dueDate>end?dueDate:end;
    					//System.out.println(start+"	"+end);
    					
    					String status=ganttDao.getStatus();
    					
    					if(!ganttDao.getStatus().equals("COMPLETED") && dueDate < end){
    						status="DELAY";
    					}
    					
    					String processInstanceLabel;
    					
    					if(orderby.equals("instance")){
    			
    			
    			    		if(oldProcessInstance.equals(""+ganttDao.getInstId())){
    			    			processInstanceLabel = "";
    			    		}else{
    			    			processInstanceLabel = ganttDao.get("name") + " ("+ganttDao.getInstId()+")";
    			    			oldProcessInstance = ""+ganttDao.getInstId();
    			    			
    			    			if(expCount>1){
    			    				sb.append("<tr><td height='15px' colspan='12' bgcolor='#FFFFFF' style='border:none;'></td></tr>");
    			    			}
    		
    			    			sb.append("<tr class='gantable'>");
    			    			sb.append("	<td colspan='12' bgcolor='#FFFFFF' style='padding-left:5px; height:30px;'><span class='sectiontitle' >");
    			    			sb.append("	&nbsp;");
    			    			sb.append("	</td>");
    			    			sb.append("</tr>");
    	
    			    		}
    			
    			    		String userName = ganttDao.getResName();
    	
    		
    			    		sb.append("<tr class='gantable'>");
    			    		
    			    		
    						for(int j=1; j<startMonth; j++){
    							sb.append("<td bgcolor='white' height='17px'></td>");
    						}
    						
    						GanttChartUtil ganttChartUtil = new GanttChartUtil(now,"white","month");
    		    			String chartBar = ganttChartUtil.getGanttChartBarHtml(start,drawingEnd, end, dueDate, status);
    		    			sb.append(chartBar);
    		    			
    		    			for(int j=drawEndMonth+1; j<=12; j++){
    		    				sb.append("<td bgcolor='white' height='17px'></td>");		    				
    		    			}
    		    			sb.append("</tr>");
    					}else{
    						String userName = ganttDao.getResName();
    
    			            if(oldResName.equals(""+userName)){
    			    			userName = "";
    			    		}else{
    			    			oldResName = ""+userName;
    			    			
    			    			if(expCount>1){
    			    				sb.append("<tr><td height='15px' colspan='365' bgcolor='#FFFFFF' style='border:none;'></td></tr>");
    			    			}
    		
    			    			sb.append("<tr class='gantable'>");
    			    			sb.append("	<td colspan='"+12+"' bgcolor='#FFFFFF' style='padding-left:5px; height:30px;'><span class='sectiontitle' >");
    			    			sb.append("	&nbsp;");
    			    			sb.append("	</td>");
    			    			sb.append("</tr>");
    			    		}
    	
    			    		sb.append("<tr class='gantable'>");
    			    		
    						for(int j=1; j<startMonth; j++){
    							sb.append("<td bgcolor='white' height='17px'></td>");
    						}
    						
    						GanttChartUtil ganttChartUtil = new GanttChartUtil(now,"white","month");
    		    			String chartBar = ganttChartUtil.getGanttChartBarHtml(start,drawingEnd, end, dueDate, status);
    		    			sb.append(chartBar);
    		    			
    		    			for(int j=drawEndMonth+1; j<=12; j++){
    		    				sb.append("<td bgcolor='white' height='17px'></td>");
    		    			}
    		    			sb.append("</tr>");
    						
    						
    						
    						
    					}
    				}
    				
    			}
            }
			sb.append("</table>");
		}else{			
			rowCount = 0;			
			firstRowCount = (currentPage == 1) ? 0 : (currentPage-1) * intPageCnt + 1;
			lastRowCount = (currentPage * intPageCnt);
			
			Vector weekInfo=new Vector();
			
			String FIRST_DATE = Integer.toString( firstDate.get(Calendar.YEAR) );
			FIRST_DATE += "-";
			FIRST_DATE += "01";
			FIRST_DATE += "-";
			FIRST_DATE += "01";
			
		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		    Date date = df.parse(FIRST_DATE);
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(date);
		    
		    int startDateWeekDay=7-(calendar.get(Calendar.DAY_OF_WEEK)-2);
		    int endDateWeekDay=(365-startDateWeekDay)%7;		    
		    int totalWeek=(365-startDateWeekDay)/7+2;
		    
		    int curWeek=today.get(Calendar.WEEK_OF_YEAR);
		    
		    if(curWeek==1){
				if((today.getTime().getMonth()+1)!=1){
					curWeek=totalWeek;
				}
			}
		    
		    int leftSize = 0;
		    
		    for(int week=0; week<totalWeek; week++){
		        if(week==curWeek) {
		            leftSize = (week * -50) + 300;
                    break;
		        }
		    }
		    
		    if (leftSize > 0) {
		        leftSize = 0;
		    }
			
			sb.append("<table id='ganttable' style='table-layout:fixed;position:relative;left:"+leftSize+"px' bgcolor='#C7D3E4'  border='0' cellspacing='0' cellpadding='0' width='100%'>");
			sb.append("	<tr style='background:#f2f5f7;height:20px;ndex:99;color:#29384a;font-size:11px;'>");
			
			int startWidthPixel=0;
			int endWidthPixel=0;
			
			int tempCount=0;
			for(int i=0; i<totalWeek; i++){				
				int tempWidth=50;
				if(i==0){
					tempWidth=Float.valueOf((float)50/100*((float)100/7*startDateWeekDay)).intValue();
					startWidthPixel=tempWidth;
				}else if(i==totalWeek-1){
					tempWidth=Float.valueOf((float)50/100*(100/7*endDateWeekDay)).intValue();
					endWidthPixel=tempWidth;
				}
				tempCount++;
				sb.append("<td style='border:1px solid #C7D3E4;' width='"+tempWidth+"px'>");				
				if(tempCount==curWeek) {				
					sb.append("	<div style='position:relative; width:100%'><div id='curBarDiv' style='position:absolute;width:"+tempWidth+"px;height:10;z-index:2;filter:alpha(opacity=50);background-color:#F2E3BD;opacity:0.5;'></div><div>");									
				}
				sb.append("	<div style='position:relative; width:100%' align='center'><font size='-3' align='center'>"+(i+1)+"W</font><div>");				
				sb.append("</td>");		
			}
			
			sb.append("	</tr>");
			sb.append("	<tr  style='height:20px;'>");
			sb.append("		<td colspan='"+totalWeek+"' style='height:20px;'>");
			sb.append("			<table border='0'  cellspacing='0' cellpadding='0' width='100%' style='height:20px;'>");
			sb.append("				<tr style='background:#f2f5f7;color:#29384a;font-weight:bold;font-size:11px;'>");
			int totalWidthPixel=startWidthPixel+endWidthPixel+(50*(totalWeek-2));
			int oneDayWidthPixel=totalWidthPixel/365;
			int nDaysList[] = {31 , 28 , 31 , 30 ,31 ,30, 31, 31 , 30 ,31 ,30 ,31};
			
			for(int i=0; i<nDaysList.length; i++){
						
				int tempWidth=oneDayWidthPixel*nDaysList[i];
				String tempWidthStr=tempWidth+"px";
				if(i==nDaysList.length-1)tempWidthStr="*";
				
				sb.append("					<td  align='center' style=' border:1px solid #C7D3E4; border-left-style:none; border-top-style:none; border-bottom-style:none' width='"+tempWidthStr+"'>");
				sb.append("						<font size='-2'>");
				
				sb.append(i+1+" ("+VIEW_MONTH[i]+")");
				
				sb.append(						"</font>");
				sb.append("					</td>");
			}
			
			sb.append("				</tr>");
			sb.append("			</table>");
			sb.append("		</td>");
			sb.append("	</tr>");
			
			if (ganttDao.size() <= 0) {
                sb.append("  <tr class='gantable'>");
                for(int i=0; i<totalWeek; i++){
                    sb.append("<td bgcolor='white' style='height:30px;'>&nbsp;</td>");          
                }
                sb.append("  </tr>");
            } else {
    			while(ganttDao.next()){
    				rowCount++; 
    				
    				if (rowCount >= firstRowCount && rowCount <= lastRowCount) {
    				    int expCount = rowCount - (intPageCnt * (currentPage - 1));
    					String startdate=(ganttDao.getStartDate()).toString().substring(0,10);
    					String enddate=(ganttDao.getEndDate()).toString().substring(0,10);
    					String duedate=(ganttDao.getDueDate()).toString().substring(0,10);
    					
    					Calendar startdateCal=Calendar.getInstance();  
    					startdateCal.setTime(ganttDao.getStartDate());
    					
    				    Calendar enddateCal=Calendar.getInstance();  
    				    enddateCal.setTime(ganttDao.getEndDate());
    				    
    				    Calendar duedateCal=Calendar.getInstance();  
    				    duedateCal.setTime(ganttDao.getDueDate());
    				    
    				    
    				    
    				    int startWeek=1;
    					int startWeekDay=1;					
    					
    					int endWeek=1;
    					int endWeekDay=1;
    										
    					int dueWeek=1;
    					int dueWeekday=1;	
    					
    					
    					if(ganttDao.getStartDate()!=null){
    						
    						if(startdateCal.before(firstDate)){
    							startWeek = 1;
    							startWeekDay=7*(100/7)/10;
    						}else{
    							startWeek=startdateCal.get(Calendar.WEEK_OF_YEAR);
    							startWeekDay=(7-(startdateCal.get(Calendar.DAY_OF_WEEK)-1))*(100/7)/10;
    							
    							if(startWeek==1){
    								if(!startdate.substring(5,7).equals("01")){
    									startWeek=totalWeek;
    								}
    							}
    						}
    					}else{
    						startWeek=now.get(Calendar.WEEK_OF_YEAR);
    						startWeekDay =(7-(now.get(Calendar.DAY_OF_WEEK)-1))*(100/7)/10; 						
    					}
    					
    					if(ganttDao.getEndDate()!=null){
    						if(enddateCal.before(firstDate)){
    							endWeek = 1;
    							endWeekDay = 7*(100/7)/10;
    						}else{
    							endWeek=enddateCal.get(Calendar.WEEK_OF_YEAR);
    							endWeekDay=(100-(7-(enddateCal.get(Calendar.DAY_OF_WEEK)-1))*(100/7))/10;
    							
    							if(endWeek==1){
    								if(!enddate.substring(5,7).equals("01")){
    									endWeek=totalWeek;
    								}
    							}
    						}
    					}else{
    						endWeek=now.get(Calendar.WEEK_OF_YEAR);
    						endWeekDay =(7-(now.get(Calendar.DAY_OF_WEEK)-1))*(100/7)/10; 
    					}
    		
    					if(ganttDao.getDueDate()!=null){
    					
    						if(duedateCal.before(firstDate)){
    							dueWeek = 0;
    							dueWeekday = 0;
    						}else if(duedateCal.after(lastDate)){
    							dueWeek = 12;//implicitly make it out of bound
    							dueWeekday = 7*(100/7)/10;
    						}else{
    							dueWeek=duedateCal.get(Calendar.WEEK_OF_YEAR);
    							dueWeekday=(100-(7-(duedateCal.get(Calendar.DAY_OF_WEEK)-1))*(100/7))/10;
    							
    							if(dueWeek==1){
    								if(!duedate.substring(5,7).equals("01")){
    									dueWeek=totalWeek;
    								}
    							}
    						    
    						}
    					}else{
    						dueWeek=now.get(Calendar.WEEK_OF_YEAR);
    						dueWeekday =(7-(now.get(Calendar.DAY_OF_WEEK)-1))*(100/7)/10; 
    					}
    					
    					int drawEndWeek=dueWeek>endWeek?dueWeek:endWeek;
    							
    					//System.out.println(startWeek+"	"+startWeekDay+"	"+endWeek+"	"+endWeekDay);
    					
    					float start=(float)(startWeek*10+startWeekDay)/10;		
    					float end=(float)(endWeek*10+endWeekDay)/10;;
    					float dueDate=(float)(dueWeek*10+dueWeekday)/10;
    					float drawingEnd=(float)dueDate>end?dueDate:end;
    					//System.out.println(start+"	"+end);
    					
    					String status=ganttDao.getStatus();
    					
    					if(!ganttDao.getStatus().equals("COMPLETED") && dueDate < end){
    						status = "DELAY";
    					}
    					
    					String processInstanceLabel;
    					
    					if(orderby.equals("instance")){
    			    		if(oldProcessInstance.equals(""+ganttDao.getInstId())){
    			    			processInstanceLabel = "";
    			    		}else{
    			    			processInstanceLabel = ganttDao.get("name") + " ("+ganttDao.getInstId()+")";
    			    			oldProcessInstance = ""+ganttDao.getInstId();
    			    			
    			    			if(expCount>1){
    			    				sb.append("<tr><td height='15px' colspan='"+totalWeek+"' bgcolor='#FFFFFF' style='border:none;'></td></tr>");
    			    			}
    		
    			    			sb.append("<tr class='gantable'>");
    			    			sb.append("	<td colspan='"+totalWeek+"' bgcolor='#FFFFFF' style='padding-left:5px; height:30px;'><span class='sectiontitle' >");
    			    			sb.append("	&nbsp;");
    			    			sb.append("	</td>");
    			    			sb.append("</tr>");
    			    		}
    			
    			    		String userName = ganttDao.getResName();
    	
    		
    			    		sb.append("<tr class='gantable'>");
    			    		
    			    		
    						for(int j=1; j<startWeek; j++){
    							sb.append("<td bgcolor='white' height='17px'></td>");
    						}
    						
    						GanttChartUtil ganttChartUtil = new GanttChartUtil(now,"white","week");
    		    			String chartBar = ganttChartUtil.getGanttChartBarHtml(start,drawingEnd, end, dueDate, status);
    		    			sb.append(chartBar);
    		    			
    		    			for(int j=drawEndWeek+1; j<=totalWeek; j++){
    		    				sb.append("<td bgcolor='white' height='17px'></td>");
    		    			}
    		    			sb.append("</tr>");
    					}else{
    						String userName = ganttDao.getResName();
    
    			            if(oldResName.equals(""+userName)){
    			    			userName = "";
    			    		}else{
    			    			oldResName = ""+userName;
    			    			
    			    			if(expCount>1){
    			    				sb.append("<tr><td height='15px' colspan='"+totalWeek+"' bgcolor='#FFFFFF' style='border:none;'></td></tr>");
    			    			}
    		
    			    			sb.append("<tr class='gantable'>");
    			    			sb.append("	<td colspan='"+totalWeek+"' bgcolor='#FFFFFF' style='padding-left:5px; height:30px;'><span class='sectiontitle' >");
    			    			sb.append("	&nbsp;");
    			    			sb.append("	</td>");
    			    			sb.append("</tr>");
    			    		}
    		
    			    		sb.append("<tr class='gantable'>");
    			    		
    						for(int j=1; j<startWeek; j++){
    							sb.append("<td bgcolor='white' height='17px'></td>");
    						}
    						
    						GanttChartUtil ganttChartUtil = new GanttChartUtil(now,"white","week");
    		    			String chartBar = ganttChartUtil.getGanttChartBarHtml(start,drawingEnd, end, dueDate, status);
    		    			sb.append(chartBar);
    		    			
    		    			for(int j=drawEndWeek+1; j<=totalWeek; j++){
    		    				sb.append("<td bgcolor='white' height='17px'></td>");
    		    			}
    		    			sb.append("</tr>");
    					}
    				}
    				
    			}
            }
			sb.append("</table>");
		}
		
		ganttDao.releaseResource();
			
		return sb.toString();
	}

	public String toXML() {
		return "";
	}

	public String toJSON() {
		return "";
	}
	
}
