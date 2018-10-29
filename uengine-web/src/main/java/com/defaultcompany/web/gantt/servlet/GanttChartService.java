package com.defaultcompany.web.gantt.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.uengine.kernel.GlobalContext;
import org.uengine.ui.list.exception.UEngineException;
import org.uengine.util.IntegrationDTO;
import org.uengine.util.dao.DefaultConnectionFactory;

import com.defaultcompany.web.gantt.dao.GanttChartWebDAO;
import com.defaultcompany.web.gantt.model.GanttChartModel;

public class GanttChartService extends HttpServlet {

    private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
    
    @Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(GlobalContext.ENCODING);
		IntegrationDTO paramDto= new IntegrationDTO();
		paramDto.parseRequest(request);
		
		String type= paramDto.getValue("type");
		
		String endpoint = paramDto.getValue("endpoint");
		int viewYear   = paramDto.getInt("viewYear"); 
		String pd       = paramDto.getValue("processDefinition");
		
		String orderby  = paramDto.getValue("orderby"); 
		String viewMode = paramDto.getValue("viewMode");
 
		int currentPage = paramDto.getInt("currentPage");
		int intPageCnt  = paramDto.getInt("intPageCnt");
		
		String strategyId = null; //paramDto.getValue("strategyId");
		
		String status = paramDto.getValue("status");
		String partCode = paramDto.getValue("partCode");
		String roleCode = paramDto.getValue("roleCode");
		String tag = paramDto.getValue("tag");
		
		HttpSession session=request.getSession(true);
		String  loggedUserGlobalCom		= (String) session.getAttribute("loggedUserGlobalCom"); 
		boolean loggedUserIsAdmin  		= "1".equals((String) session.getAttribute("loggedUserIsAdmin"));
		boolean loggedUserIsMaster 		= loggedUserIsAdmin && !org.uengine.util.UEngineUtil.isNotEmpty(loggedUserGlobalCom);
		String globalCom = 	 (String) session.getAttribute("loggedUserGlobalCom");
		
		
		GanttChartWebDAO ganttChartWebDao=null;
		GanttChartModel ganttChartModel=null;
		try{
			ganttChartWebDao = new GanttChartWebDAO(DefaultConnectionFactory.create());
			ganttChartWebDao.setStatus(status);
			ganttChartWebDao.setPartCode(partCode);
			ganttChartWebDao.setRoleCode(roleCode);
			ganttChartWebDao.setTag(tag);
			ganttChartWebDao.init(viewYear, endpoint, pd, globalCom, orderby, loggedUserIsMaster,strategyId);
			ganttChartModel = ganttChartWebDao.makeGanttChart();
		
		
			response.setContentType("text/html; charset=UTF-8"); 
			response.setHeader("Cache-Control", "no-cache");
			
			PrintWriter out = response.getWriter();
			out.write(type.equals("title")?ganttChartModel.ganttChartTitleToHTML(currentPage, intPageCnt):ganttChartModel.ganttChartListToHTML(currentPage, intPageCnt,viewMode));
			
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
			new UEngineException(e, "Ganttchart list make exception");
		}
		
	}
	
}
