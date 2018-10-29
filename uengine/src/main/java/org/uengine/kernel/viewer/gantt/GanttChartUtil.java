package org.uengine.kernel.viewer.gantt;

import java.util.Calendar;
import java.util.Vector;

import org.uengine.kernel.GlobalContext;
import org.uengine.util.resources.Properties;


public class GanttChartUtil{
	
	Calendar pointDate;
	String pointColor;
	String viewMode;
	
	public GanttChartUtil(){
		Calendar nowTemp;				
		nowTemp = Calendar.getInstance();
		pointDate=nowTemp;
		pointColor="#F2E3BD";
	}
	
	public GanttChartUtil(Calendar pointDate, String pointBgColor,String viewMode){
		this.pointDate=pointDate;
		this.pointColor=pointBgColor;
		this.viewMode=viewMode;
		
	}
	
	Properties statusImagePaths = new Properties();{
		String root = GlobalContext.getPropertyString("web.context.root","/html/uengine-web");
		
		statusImagePaths.put("NEW",root+"/processmanager/images/ganttbar_green");
		statusImagePaths.put("CONFIRMED", root+"/processmanager/images/ganttbar_green");
		statusImagePaths.put("COMPLETED", root+"/processmanager/images/ganttbar_gray");
		statusImagePaths.put("CANCELLED", root+"/processmanager/images/ganttbar_yellow");
		statusImagePaths.put("RESERVED", root+"/processmanager/images/ganttbar_blue");
		
		statusImagePaths.put("REMAIN", root+"/processmanager/images/ganttbar_blue");
		statusImagePaths.put("DELAY", root+"/processmanager/images/ganttbar_orange");
	}
	
	
	//Single line ganttCharUnitBarHtml
	public String getGanttChartUnitBarHtml(int drawStart, int drawEnd, int unitStart,int unitEnd, String status){
		
		String htmlSource="";
		String statusImagePath =""; 
		String root = GlobalContext.getPropertyString("web.context.root","/html/uengine-web");
		statusImagePath = statusImagePaths.getProperty(status, root+"/processmanager/images/ganttbar_red");

		int barSize = 0;
		if(drawStart != 0){
			barSize = drawEnd - drawStart + 1;
		}else{
			barSize = drawEnd - drawStart;
		}
		
		if(barSize == 1){
			htmlSource = "<td bgcolor=white><img src=" + statusImagePath + "Ball.gif></td>";
		}else{
//			for(int i=unitStart;i<=unitEnd;i++){
//				if(i == unitStart){
//					htmlSource += "<td bgcolor=white><img src=" + statusImagePath + "Start.gif></td>";
//				}else if(i == unitEnd){
//					htmlSource += "<td bgcolor=white><img src=" + statusImagePath + "End.gif></td>";					
//				}else{
//					htmlSource += "<td bgcolor=white><img src=" + statusImagePath + "Med.gif></td>";
//				}
//			}
			
			for(int i = unitStart; i <= unitEnd; i++){
				if(i == drawStart){
					htmlSource += "<td bgcolor=white><table width='100%' height='100%'cellspacing='0' cellpadding='0' border='0'><tr><td><img src =" + statusImagePath + "Start.gif></td></tr><tr><td><img src=" + statusImagePath + "Start.gif></td></tr></table></td>";
				}else if(i == drawEnd){
					htmlSource += "<td bgcolor=white><img src=" + statusImagePath + "End.gif></td>";
				}else{
					htmlSource += "<td bgcolor=white><img src=" + statusImagePath + "Med.gif></td>";
				}
			}
		}
		
		return htmlSource;
	}
	
	
	
	//Two line ganttCharUnitBarHtml
	public String getGanttChartUnitBarHtml(int drawStart, int drawEnd, int planUnitStart,int planUnitEnd, String planStatus , int actUnitStart,int actUnitEnd,String actStatus){
		
		String htmlSource="";
		String planStatusImagePath =""; 
		String actStatusImagePath ="";
		String root = GlobalContext.getPropertyString("web.context.root","/html/uengine-web");
		planStatusImagePath = statusImagePaths.getProperty(planStatus, root+"/processmanager/images/ganttbar_red");
		actStatusImagePath = statusImagePaths.getProperty(actStatus, root+"/processmanager/images/ganttbar_red");
		
		int curday=pointDate.get(Calendar.DAY_OF_YEAR);
		
		
		int planMatrix[]=new int[drawEnd+1];
		int actMatrix[]=new int[drawEnd+1];
		
		for(int i = planUnitStart;  i <= planUnitEnd; i++){
			planMatrix[i]=1;
		}
		
		for(int i = actUnitStart;  i <= actUnitEnd; i++){
			actMatrix[i]=1;
		}
		

		int barSize = 0;
		if(drawStart != 0){
			barSize = drawEnd - drawStart + 1;
		}else{
			barSize = drawEnd - drawStart;
		}
		
		
		
		if(barSize == 1){
		    htmlSource  = "<td bgcolor='"+(curday==drawStart?pointColor:"white")+"' >";
            htmlSource +=       "<table width='100%' cellspacing='0' cellpadding='0' border='0'>" ;
            htmlSource +=           "<tr>" ;
            htmlSource +=               "<td style='border:none;'>" ;
            htmlSource +=                   "<table width='100%' cellspacing='0' cellpadding='0' border='0'><tr><td style='height:15px; background:#DEEFFA;border:none;' align='right'>";
            htmlSource +=                   "<div id='var-outer'>";
            htmlSource +=                       "<div id='var-inner'>";
            htmlSource +=                           "<img src=" + actStatusImagePath + "Ball.gif width='100%' height='8'>";
            htmlSource +=                       "</div>";
            htmlSource +=                   "</div>";
            htmlSource +=                   "</td></tr></table>";
            htmlSource +=               "</td>" ;
            htmlSource +=           "</tr>" ;
            htmlSource +=       "</table>" ;
            htmlSource +=   "</td>";
            
//			htmlSource = "<td bgcolor="+(curday==drawStart?pointColor:"white")+"><table width='100%' height='100%'cellspacing='0' cellpadding='0' border='0'  style='border:none;'><tr><td height='8' bgcolor="+(curday==drawStart?pointColor:"white")+" style='border:none;'> <img src =" + planStatusImagePath + "Ball.gif width='100%' height='8'></td></tr><tr><td height='8' bgcolor="+(curday==drawStart?pointColor:"white")+" style='border:none;'> <img src=" + actStatusImagePath + "Ball.gif width='100%' height='8'></td></tr></table></td>";
			
			
			
		}else{
			for(int i = drawStart; i <= drawEnd; i++){
				String bgColor=(i==curday?pointColor:"white");
				if(i == drawStart){
				    htmlSource += "<td bgcolor=white style='border:1px solid #C7D3E4; border-left:none; border-top:none;'>" ;
                    htmlSource +=       "<table width='100%' cellspacing='0' cellpadding='0' border='0'>" ;
                    htmlSource +=           "<tr>" ;
                    htmlSource +=               "<td style='border:none;'>" ;
                    
                    if (planMatrix[i]==0) {
                        htmlSource +=                   "<table width='100%' cellspacing='0' cellpadding='0' border='0'><tr><td style='height:15px; border:none;' align='right' width='100%'>";
                    } else {
                        htmlSource +=                   "<table width='100%' cellspacing='0' cellpadding='0' border='0'><tr><td style='height:15px; background:#DEEFFA;border:none;' align='right'>";
                    }
                    
                    if (actMatrix[i] == 0) {
                        htmlSource +=                   "&nbsp;</td></tr></table>";
                    } else {
                        htmlSource +=                   "<div id='var-outer'>";
                        htmlSource +=                       "<div id='var-inner'>";
                        htmlSource +=                           "<img src=" + actStatusImagePath + (actUnitEnd==actUnitStart?"Ball.gif":"Start.gif")+" width='13' height='13'>";
                        htmlSource +=                       "</div>";
                        htmlSource +=                   "</div>";
                        htmlSource +=                   "</td></tr></table>";
                    }
                    
                    htmlSource +=               "</td>" ;
                    htmlSource +=           "</tr>" ;
                    htmlSource +=       "</table>" ;
                    htmlSource +=   "</td>";
					
//					htmlSource += "<td bgcolor=white>" ;
//					htmlSource +=		"<table width='100%' height='100%'cellspacing='0' cellpadding='0' border='0' style='border:none;'>" ;
//					htmlSource +=			"<tr>" ;
//					htmlSource +=				"<td bgcolor="+bgColor+" height='8' style='border:none;'>" ;
//					htmlSource +=					planMatrix[i]==0?"":("<img src =" + planStatusImagePath + (planUnitEnd==planUnitStart?"Ball.gif":"Start.gif")+" width='100%' height='8' title='Plan Status Bar'>" );
//					htmlSource +=				"</td>" ;
//					htmlSource +=			"</tr>" ;
//					htmlSource +=			"<tr>" ;
//					htmlSource +=				"<td bgcolor="+bgColor+" height='8' style='border:none;'>" ;
//					htmlSource +=					actMatrix[i]==0?"":("<img src=" + actStatusImagePath + (actUnitEnd==actUnitStart?"Ball.gif":"Start.gif")+" width='100%' height='8' title='Action Status Bar'>" );
//					htmlSource +=				"</td>" ;
//					htmlSource +=			"</tr>" ;
//					htmlSource +=		"</table>" ;
//					htmlSource +=	"</td>";
					 
					
				}else if(i == drawEnd){
				    htmlSource += "<td bgcolor="+bgColor+" style='border:1px solid #C7D3E4; border-left:none; border-top:none;'>" ;
                    htmlSource +=       "<table width='100%' cellspacing='0' cellpadding='0' border='0'  style='border:none;'>" ;
                    htmlSource +=           "<tr>" ;
                    htmlSource +=               "<td style='border:none;'>";
                    
                    if (planMatrix[i] == 0) {
                        htmlSource +=                   "<table width='100%' cellspacing='0' cellpadding='0' border='0'><tr><td style='height:15px; border:none;' width='100%'>";
                    } else {
                        htmlSource +=                   "<table width='100%' cellspacing='0' cellpadding='0' border='0'><tr><td style='height:15px; background:#DEEFFA;border:none;'>";
                    }
                    
                    if (actMatrix[i] == 0) {
                        htmlSource +=                   "&nbsp;</td></tr></table>";
                    } else {
                        htmlSource +=                   "<div id='var-outer'>";
                        htmlSource +=                       "<div id='var-inner'>";
                        htmlSource +=                           "<img src=" + actStatusImagePath + (actUnitEnd==actUnitStart?"Ball.gif":"End.gif") +" width='13' height='13'>";
                        htmlSource +=                       "</div>";
                        htmlSource +=                   "</div>";
                        htmlSource +=                   "</td></tr></table>";
                    }
                    
                    htmlSource +=               "</td>" ;
                    htmlSource +=           "</tr>" ;
                    htmlSource +=       "</table>" ;
                    htmlSource +=   "</td>";
                    
//					htmlSource += "<td bgcolor="+bgColor+">" ;
//					htmlSource +=		"<table width='100%' height='100%'cellspacing='0' cellpadding='0' border='0'  style='border:none;'>" ;
//					htmlSource +=			"<tr>" ;
//					htmlSource +=				"<td bgcolor="+bgColor+" height='8' style='border:none;'>" ;
//					htmlSource +=					planMatrix[i]==0?"":("<img src =" + planStatusImagePath + (planUnitEnd==planUnitStart?"Ball.gif":"End.gif")+" width='100%' height='8' title='Plan Status Bar'>") ;
//					htmlSource +=				"</td>" ;
//					htmlSource +=			"</tr>" ;
//					htmlSource +=			"<tr>" ;
//					htmlSource +=				"<td bgcolor="+bgColor+" height='8' style='border:none;'>" ;
//					htmlSource +=					actMatrix[i]==0?"":("<img src=" + actStatusImagePath + (actUnitEnd==actUnitStart?"Ball.gif":"End.gif") +" width='100%' height='8' title='Action Status Bar'>");
//					htmlSource +=				"</td>" ;
//					htmlSource +=			"</tr>" ;
//					htmlSource +=		"</table>" ;
//					htmlSource +=	"</td>";
				}else{
				    htmlSource += "<td bgcolor="+bgColor+" style='border:1px solid #C7D3E4; border-left:none; border-top:none;'>" ;
                    htmlSource +=       "<table width='100%' cellspacing='0' cellpadding='0' border='0'  style='border:none;'>" ;
                    htmlSource +=           "<tr>" ;
                    htmlSource +=               "<td style='border:none;'>";
                    
                    if (planMatrix[i] == 0) {
                        htmlSource +=                   "<table width='100%' cellspacing='0' cellpadding='0' border='0'><tr><td style='height:15px; border:none;' align='left' width='100%'>";
                    } else {
                        htmlSource +=                   "<table width='100%' cellspacing='0' cellpadding='0' border='0'><tr><td style='height:15px; background:#DEEFFA;border:none;' align='left'>";
                    }
                    
                    if (actMatrix[i] == 0) {
                        htmlSource +=                   "&nbsp;</td></tr></table>";
                    } else {
                        htmlSource +=                   "<div id='var-outer'>";
                        htmlSource +=                       "<div id='var-inner'>";
                        htmlSource +=                           "<img src=" + actStatusImagePath + (actUnitEnd==actUnitStart?"Ball.gif":"Med.gif")+" width='100%' height='13px'>";
                        htmlSource +=                       "</div>";
                        htmlSource +=                   "</div>";
                        htmlSource +=                   "</td></tr></table>";
                    }
                    
                    htmlSource +=               "</td>" ;
                    htmlSource +=           "</tr>" ;
                    htmlSource +=       "</table>" ;
                    htmlSource +=   "</td>";
                    
//					htmlSource += "<td bgcolor="+bgColor+">" ;
//					htmlSource +=		"<table width='100%' height='100%'cellspacing='0' cellpadding='0' border='0'  style='border:none;'>" ;
//					htmlSource +=			"<tr>" ;
//					htmlSource +=				"<td bgcolor="+bgColor+" height='8' style='border:none;'>" ;
//					htmlSource +=					planMatrix[i]==0?"":("<img src =" + planStatusImagePath + (planUnitEnd==planUnitStart?"Ball.gif":"Med.gif")+" width='100%' height='8' title='Plan Status Bar'>") ;
//					htmlSource +=				"</td>" ;
//					htmlSource +=			"</tr>" ;
//					htmlSource +=			"<tr>" ;
//					htmlSource +=				"<td bgcolor="+bgColor+" height='8' style='border:none;'>" ;
//					htmlSource +=					actMatrix[i]==0?"":("<img src=" + actStatusImagePath + (actUnitEnd==actUnitStart?"Ball.gif":"Med.gif")+" width='100%' height='8' title='Action Status Bar'>" );
//					htmlSource +=				"</td>" ;
//					htmlSource +=			"</tr>" ;
//					htmlSource +=		"</table>" ;
//					htmlSource +=	"</td>";
				}
			}
		}
		
		return htmlSource;
	}
	
	//Two line ganttCharUnitBarHtmlDetail week
	public String getGanttChartUnitBarHtml(float drawStart, float drawEnd, float planUnitStart,float planUnitEnd, String planStatus , float actUnitStart,float actUnitEnd,String actStatus){
		
		String htmlSource="";
		String planStatusImagePath =""; 
		String actStatusImagePath ="";
		String root = GlobalContext.getPropertyString("web.context.root","/html/uengine-web");
		planStatusImagePath = statusImagePaths.getProperty(planStatus, root+"/processmanager/images/ganttbar_red");
		actStatusImagePath = statusImagePaths.getProperty(actStatus, root+"/processmanager/images/ganttbar_red");
		
		String startPer=String.valueOf((int)(drawStart%1*100))+"%";
		String endPer=String.valueOf((int)(drawEnd%1*100))+"%";
		String planUnitStartPer=String.valueOf((int)(planUnitStart%1*100))+"%";
		String planUnitEndPer=String.valueOf((int)(planUnitEnd%1*100))+"%";
		String actStartPer=String.valueOf((int)(actUnitStart%1*100))+"%"; 
		String actUnitPer=String.valueOf((int)(actUnitEnd%1*100))+"%";
		
		
		int planMatrix[]=new int[(int)Math.floor((double)drawEnd)+1];
		int actMatrix[]=new int[(int)Math.floor((double)drawEnd)+1];
		
		for(int i = (int)Math.floor((double)planUnitStart);  i <= (int)Math.floor((double)planUnitEnd); i++){
			planMatrix[i]=1;
		}
		
		for(int i = (int)Math.floor((double)actUnitStart);  i <= (int)Math.floor((double)actUnitEnd); i++){
			actMatrix[i]=1;
		}
		

		int barSize = 0;
		if(drawStart != 0){
			barSize = (int)Math.floor((double)drawEnd) - (int)Math.floor((double)drawStart) + 1;
		}else{
			barSize = (int)Math.floor((double)drawEnd) - (int)Math.floor((double)drawStart) + 1;
		}
		
		int curMonthOrWeek=viewMode.equals("month")?(pointDate.get(Calendar.MONTH)+1):pointDate.get(Calendar.WEEK_OF_YEAR);
		
		if(barSize == 1){
//			htmlSource  = "<td bgcolor="+(curMonthOrWeek==(int)Math.floor((double)drawStart)?pointColor:"white")+" height='17px'>";
//			htmlSource +=     "<table width='100%' height='100%'cellspacing='0' cellpadding='0' border='0' style='border:none;'>";
//			htmlSource +=     "<tr><td height='8' bgcolor="+(curMonthOrWeek==(int)Math.floor((double)drawStart)?pointColor:"white")+" style='border:none;'> ";
//			htmlSource +=     "<img src =" + planStatusImagePath + "Ball.gif height='8' width='100%'>";
//			htmlSource +=     "</td></tr><tr><td height='8' bgcolor="+(curMonthOrWeek==(int)Math.floor((double)drawStart)?pointColor:"white")+" style='border:none;'>";
//			htmlSource +=     "<img src=" + actStatusImagePath + "Ball.gif height='8' width='100%'></td></tr></table></td>";
			
            htmlSource += "<td bgcolor="+ (curMonthOrWeek==(int)Math.floor((double)drawStart)?pointColor:"white") +" style='border:1px solid #C7D3E4; border-left:none; border-top:none;'>" ;
            htmlSource +=       "<table width='100%' cellspacing='0' cellpadding='0' border='0' style='border:none;'>" ;
            htmlSource +=           "<tr>" ;
            htmlSource +=               "<td style='border:none;'  align='right'>" ;
            htmlSource +=                   "<table width="+planUnitStartPer+"  bgcolor='#DEEFFA' cellspacing='0' cellpadding='0' border='0'><tr><td height='15px' align='right' style='border:none;'>";
            htmlSource +=                   "<div id='var-outer'>";
            htmlSource +=                       "<div id='var-inner'>";
            htmlSource +=                           "<img src=" + actStatusImagePath + "Ball.gif height='13px' width='100%'>";
            htmlSource +=                       "</div>";
            htmlSource +=                   "</div>";
            htmlSource +=                   "</td></tr></table>";
            htmlSource +=               "</td>" ;
            htmlSource +=           "</tr>" ;
            htmlSource +=       "</table>" ;
            htmlSource +=   "</td>";
		}else{
			for(int i = (int)Math.floor((double)drawStart); i <= (int)Math.floor((double)drawEnd); i++){
				String bgColor=(i==curMonthOrWeek?pointColor:"white");
				if(i == (int)Math.floor((double)drawStart)){
				    //int startPerPx = (int)Math.round((1 - ((actUnitStart%1*100) / 100)) * 50 );
				    int startPerPx = (int)Math.round(((actUnitStart%1*100) / 100) * 50 );
				    //int startPerPx = 10;
				    
				    htmlSource += "<td bgcolor="+bgColor+" style='border:1px solid #C7D3E4; border-left:none; border-top:none;'>" ;
                    htmlSource +=       "<table width='100%' cellspacing='0' cellpadding='0' border='0' style='border:none;'>" ;
                    htmlSource +=           "<tr>" ;
                    htmlSource +=               "<td style='border:none;'  align='right'>" ;
                    
                    if (planMatrix[i] == 0) {
                        htmlSource +=                   "<table width="+planUnitStartPer+"  cellspacing='0' cellpadding='0' border='0'><tr><td height='15px' align='left' style='border:none;' width='100%'>";
                    } else {
                        htmlSource +=                   "<table width="+planUnitStartPer+"  bgcolor='#DEEFFA' cellspacing='0' cellpadding='0' border='0'><tr><td height='15px' align='right' style='border:none;'>";
                    }
                    
                    if (actMatrix[i] == 0) {
                        htmlSource +=                   "&nbsp;</td></tr></table>";
                    } else {
                        htmlSource +=                   "<div id='var-outer'>";
                        htmlSource +=                       "<div id='"+ (viewMode.equals("week")?"var-inner-week":"var-inner") +"'>";
                        htmlSource +=                           "<img src=" + actStatusImagePath + (actUnitEnd==actUnitStart?"Ball.gif":"Start.gif")+" width='"+startPerPx+"px' height='13px'>";
                        htmlSource +=                       "</div>";
                        htmlSource +=                   "</div>";
                        htmlSource +=                   "</td></tr></table>";
                    }
                    
                    htmlSource +=               "</td>" ;
                    htmlSource +=           "</tr>" ;
                    htmlSource +=       "</table>" ;
                    htmlSource +=   "</td>";
					
//					htmlSource += "<td bgcolor="+bgColor+" height='17px'>" ;
//					htmlSource +=		"<table width='100%' height='100%'cellspacing='0' cellpadding='0' border='0' style='border:none;'>" ;
//					htmlSource +=			"<tr>" ;
//					htmlSource +=				"<td bgcolor="+bgColor+" height='8' style='border:none;' align='right'>" ;
//					htmlSource +=					planMatrix[i]==0?"":("<img src =" + planStatusImagePath + (planUnitEnd==planUnitStart?"Ball.gif":"Start.gif")+" width='"+planUnitStartPer+"' height='8'>" );
//					htmlSource +=				"</td>" ;
//					htmlSource +=			"</tr>" ;
//					htmlSource +=			"<tr>" ;
//					htmlSource +=				"<td bgcolor="+bgColor+" height='8' style='border:none;' align='right'>" ;
//					htmlSource +=					actMatrix[i]==0?"":("<img src=" + actStatusImagePath + (actUnitEnd==actUnitStart?"Ball.gif":"Start.gif")+" width='"+actStartPer+"' height='8'>" );
//					htmlSource +=				"</td>" ;
//					htmlSource +=			"</tr>" ;
//					htmlSource +=		"</table>" ;
//					htmlSource +=	"</td>";
				}else if(i == (int)Math.floor((double)drawEnd)){
				    //int startPerPx = (int)Math.round( (1 - ((actUnitStart%1*100) / 100)) * 50);
				    int startPerPx = (int)Math.round( ((actUnitEnd%1*100) / 100) * 50);
				    
				    htmlSource += "<td bgcolor="+bgColor+" style='border:1px solid #C7D3E4; border-left:none; border-top:none;'>" ;
                    htmlSource +=       "<table width='100%' cellspacing='0' cellpadding='0' border='0'  style='border:none;'>" ;
                    htmlSource +=           "<tr>" ;        
                    htmlSource +=               "<td style='border:none;'  align='left'>" ;
                    
                    if (planMatrix[i] == 0) {
                        htmlSource +=                   "<table width='"+planUnitEndPer+"' cellspacing='0' cellpadding='0' border='0'><tr><td height='15px' align='left' style='border:none;' width='100%'>";
                    } else {
                        htmlSource +=                   "<table width='"+planUnitEndPer+"' bgcolor='#DEEFFA' cellspacing='0' cellpadding='0' border='0'><tr><td height='15px' align='left' style='border:none;'>";
                    }
                    
                    if (actMatrix[i] == 0) {
                        htmlSource +=                   "&nbsp;</td></tr></table>";
                    } else {
                        htmlSource +=                   "<div id='var-outer'>";
                        htmlSource +=                       "<div id='"+ (viewMode.equals("week")?"var-inner-week":"var-inner") +"'>";
                        htmlSource +=                           "<img src=" + actStatusImagePath + (actUnitEnd==actUnitStart?"Ball.gif":"End.gif") +" width='"+startPerPx+"px' height='13px'>";
                        htmlSource +=                       "</div>";
                        htmlSource +=                   "</div>";
                        htmlSource +=                   "</td></tr></table>";
                    }
                    
                    htmlSource +=               "</td>" ;   
                    htmlSource +=           "</tr>" ;
                    htmlSource +=       "</table>" ;
                    htmlSource +=   "</td>";
                    
//					htmlSource += "<td bgcolor="+bgColor+"  height='17px'>" ;
//					htmlSource +=		"<table width='100%' height='100%'cellspacing='0' cellpadding='0' border='0'  style='border:none;'>" ;
//					htmlSource +=			"<tr>" ;
//					htmlSource +=				"<td bgcolor="+bgColor+" height='8' style='border:none;' align='left'>" ;
//					htmlSource +=					planMatrix[i]==0?"":("<img src =" + planStatusImagePath + (planUnitEnd==planUnitStart?"Ball.gif":"End.gif")+" width='"+planUnitEndPer+"' height='8'>") ;
//					htmlSource +=				"</td>" ;
//					htmlSource +=			"</tr>" ;
//					htmlSource +=			"<tr>" ;
//					htmlSource +=				"<td bgcolor="+bgColor+" height='8' style='border:none;' align='left'>" ;
//					htmlSource +=					actMatrix[i]==0?"":("<img src=" + actStatusImagePath + (actUnitEnd==actUnitStart?"Ball.gif":"End.gif") +" width='"+actUnitPer+"' height='8'>");
//					htmlSource +=				"</td>" ;
//					htmlSource +=			"</tr>" ;
//					htmlSource +=		"</table>" ;
//					htmlSource +=	"</td>";
				}else{
				    htmlSource += "<td bgcolor="+bgColor+" style='border:1px solid #C7D3E4; border-left:none; border-top:none;'>" ;
                    htmlSource +=       "<table width='100%' cellspacing='0' cellpadding='0' border='0'  style='border:none;'>" ;
                    htmlSource +=           "<tr>" ;
                    htmlSource +=               "<td style='border:none;'>" ;
                    
                    if (planMatrix[i] == 0) {
                        htmlSource +=                   "<table width='"+planUnitEndPer+"' cellspacing='0' cellpadding='0' border='0'><tr><td height='15px' style='border:none;' width='100%'>";
                    } else {
                        htmlSource +=                   "<table width='"+planUnitEndPer+"'  bgcolor='#DEEFFA' cellspacing='0' cellpadding='0' border='0'><tr><td height='15px' style='border:none;'>";
                    }
                    
                    if (actMatrix[i] == 0) {
                        htmlSource +=                   "&nbsp;</td></tr></table>";
                    } else {
                        htmlSource +=                   "<div id='var-outer'>";
                        htmlSource +=                       "<div id='"+ (viewMode.equals("week")?"var-inner-week":"var-inner") +"'>";
                        htmlSource +=                           "<img src=" + actStatusImagePath + (actUnitEnd==actUnitStart?"Ball.gif":"Med.gif")+" width='50px' height='13px'>";
                        htmlSource +=                       "</div>";
                        htmlSource +=                   "</div>";
                        htmlSource +=                   "</td></tr></table>";
                    }
                    
                    htmlSource +=               "</td>" ;                                   
                    htmlSource +=           "</tr>" ;
                    htmlSource +=       "</table>" ;
                    htmlSource +=   "</td>";
                    
//					htmlSource += "<td bgcolor="+bgColor+"  height='17px'>" ;
//					htmlSource +=		"<table width='100%' height='100%'cellspacing='0' cellpadding='0' border='0'  style='border:none;'>" ;
//					htmlSource +=			"<tr>" ;
//					htmlSource +=				"<td bgcolor="+bgColor+" height='8' style='border:none;'>" ;
//					htmlSource +=					planMatrix[i]==0?"":("<img src =" + planStatusImagePath + (planUnitEnd==planUnitStart?"Ball.gif":"Med.gif")+" width='100%' height='8'>") ;
//					htmlSource +=				"</td>" ;
//					htmlSource +=			"</tr>" ;
//					htmlSource +=			"<tr>" ;
//					htmlSource +=				"<td bgcolor="+bgColor+" height='8' style='border:none;'>" ;
//					htmlSource +=					actMatrix[i]==0?"":("<img src=" + actStatusImagePath + (actUnitEnd==actUnitStart?"Ball.gif":"Med.gif")+" width='100%' height='8'>" );
//					htmlSource +=				"</td>" ;
//					htmlSource +=			"</tr>" ;
//					htmlSource +=		"</table>" ;
//					htmlSource +=	"</td>";
				}
			}
		}
		
		return htmlSource;
	}
	
	//Single line getGanttChartBarArea
	public Vector getGanttChartBarArea(int drawingStart, int drawingEnd, int end, int dueDate, String status){
		
		Vector drawingArea = new Vector();
		int startPosition = drawingStart;
		int endPosition = drawingStart;
		String tempStatus = status;
		String color = "";
		
		for(int i = drawingStart ; i <= drawingEnd; i++){
			if(i > end){
				if(!tempStatus.equals("REMAIN")){				
					GanttChartPosition ganttChartPosition = new GanttChartPosition();
					ganttChartPosition.setStart(startPosition);
					ganttChartPosition.setEnd(endPosition);
					ganttChartPosition.setStatus(tempStatus);
					drawingArea.add(ganttChartPosition);
					startPosition = i;
					endPosition = i;
					tempStatus = "REMAIN";
				}
			}else if(i > dueDate){
				if(!tempStatus.equals("DELAY")){
					GanttChartPosition ganttChartPosition = new GanttChartPosition();
					ganttChartPosition.setStart(startPosition);
					ganttChartPosition.setEnd(endPosition);
					ganttChartPosition.setStatus(tempStatus);
					drawingArea.add(ganttChartPosition);
					startPosition = i;
					endPosition = i;
					tempStatus = "DELAY";
				}
			}	
			endPosition = i;
		}	
		GanttChartPosition ganttChartPosition = new GanttChartPosition();
		ganttChartPosition.setStart(startPosition);
		ganttChartPosition.setEnd(endPosition);
		ganttChartPosition.setStatus(tempStatus);
		drawingArea.add(ganttChartPosition);			
			
		return drawingArea;
	}
	
	
	//two line getGanttChartBarArea
	public GanttChartPosition[] getGanttChartBarArea(int drawingStart, int drawingEnd, int end, int dueDate,String planStatus, String actStatus){
		
		GanttChartPosition ganttChartPosition[]=new GanttChartPosition[2];
		
			
		
		GanttChartPosition ganttChartPlanPosition = new GanttChartPosition();
		
		
		ganttChartPlanPosition.setStart(drawingStart);
		ganttChartPlanPosition.setEnd(dueDate);
		ganttChartPlanPosition.setStatus(planStatus);
		ganttChartPosition[0]=ganttChartPlanPosition;
		
		
		GanttChartPosition ganttChartActPosition = new GanttChartPosition();
		ganttChartActPosition.setStart(drawingStart);
		ganttChartActPosition.setEnd(end);
		ganttChartActPosition.setStatus(actStatus);
		ganttChartPosition[1]=ganttChartActPosition;
			
			
		return ganttChartPosition;
	}
	
	
	
	
	//two line getGanttChartBarArea Detail
	public GanttChartPositionToDetail[] getGanttChartBarArea(float drawingStart, float drawingEnd, float end, float dueDate,String planStatus, String actStatus){
		
		GanttChartPositionToDetail ganttChartPosition[]=new GanttChartPositionToDetail[2];
		
			
		
		GanttChartPositionToDetail ganttChartPlanPosition = new GanttChartPositionToDetail();
		
		
		ganttChartPlanPosition.setStart(drawingStart);
		ganttChartPlanPosition.setEnd(dueDate);
		ganttChartPlanPosition.setStatus(planStatus);
		ganttChartPosition[0]=ganttChartPlanPosition;
		
		
		GanttChartPositionToDetail ganttChartActPosition = new GanttChartPositionToDetail();
		ganttChartActPosition.setStart(drawingStart);
		ganttChartActPosition.setEnd(end);
		ganttChartActPosition.setStatus(actStatus);
		ganttChartPosition[1]=ganttChartActPosition;
			
			
		return ganttChartPosition;
	}
	
	
	
	
	public String getGanttChartBarHtml(int drawingStart, int drawingEnd, int end, int dueDate, String status){
		
		String ganttChartBarHtml = "";
		
		GanttChartPosition ganttChartPosition[] = getGanttChartBarArea(drawingStart, drawingEnd, end, dueDate,"REMAIN",status);
		GanttChartPosition ganttChartPlanPosition=ganttChartPosition[0];
		GanttChartPosition ganttChartActPosition=ganttChartPosition[1];
		
		ganttChartBarHtml += getGanttChartUnitBarHtml(drawingStart, drawingEnd, ganttChartPlanPosition.getStart(), ganttChartPlanPosition.getEnd(), ganttChartPlanPosition.getStatus(),ganttChartActPosition.getStart(), ganttChartActPosition.getEnd(), ganttChartActPosition.getStatus());
		/*
		for(int i=0; i< barArea.size(); i++){
			GanttChartPosition ganttChartBar = (GanttChartPosition) barArea.get(i);
			ganttChartBarHtml += getGanttChartUnitBarHtml(drawingStart, drawingEnd, ganttChartBar.getStart(), ganttChartBar.getEnd(), ganttChartBar.getStatus());
		}
		*/
		
		
		return ganttChartBarHtml;		
	}
	
	
	
	
	
	public String getGanttChartBarHtml(float drawingStart, float drawingEnd, float end, float dueDate, String status){
		
		String ganttChartBarHtml = "";
		
		GanttChartPositionToDetail ganttChartPosition[] = getGanttChartBarArea(drawingStart, drawingEnd, end, dueDate,"REMAIN",status);
		GanttChartPositionToDetail ganttChartPlanPosition=ganttChartPosition[0];
		GanttChartPositionToDetail ganttChartActPosition=ganttChartPosition[1];
		
		ganttChartBarHtml += getGanttChartUnitBarHtml(drawingStart, drawingEnd, ganttChartPlanPosition.getStart(), ganttChartPlanPosition.getEnd(), ganttChartPlanPosition.getStatus(),ganttChartActPosition.getStart(), ganttChartActPosition.getEnd(), ganttChartActPosition.getStatus());
		/*
		for(int i=0; i< barArea.size(); i++){
			GanttChartPosition ganttChartBar = (GanttChartPosition) barArea.get(i);
			ganttChartBarHtml += getGanttChartUnitBarHtml(drawingStart, drawingEnd, ganttChartBar.getStart(), ganttChartBar.getEnd(), ganttChartBar.getStatus());
		}
		*/
		
		
		return ganttChartBarHtml;		
	}
	
	
	
	class GanttChartPosition{
		
		int start;
		int end;
		String status;
		
		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}
		public int getEnd() {
			return end;
		}
		public void setEnd(int end) {
			this.end = end;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	}
	
	class GanttChartPositionToDetail{
		
		float start;
		float end;
		String status;
		
		public float getStart() {
			return start;
		}
		public void setStart(float start) {
			this.start = start;
		}
		public float getEnd() {
			return end;
		}
		public void setEnd(float end) {
			this.end = end;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	}

}