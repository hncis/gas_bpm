
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-monitoring.jspf"%>
<style>
.ui-jqgrid-sortable {font-size: 12px;text-align: left}
.ui-jqgrid-labels .ui-th-column{border-right-width: 0px;  }
.ui-jqgrid tr.ui-row-ltr td {border-right-width: 0px;}
.ui-widget-content {background:#FFFFFF}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {background:#FFFFFF}
/*
*/
</style>

<script>
	
</script>
<title>API Test</title>
</head>
<body>
	<!-- Page Content -->
	<div class="container-fluid">
	
	<table class="table table-reflow">
	  <tbody>
	    <tr>
	      <td  width="100%"></td>
	      <td>
		<div id="procroleDiv" style="float:left">
		     </div>
	      </td>
	    </tr>
	    </tbody>
    </table>
		<table width=100%>
			
			<tr>
				<td style="vertical-align: top;">
					
							<div >
								
									
									<table class="table table-reflow">
									  <tbody>
										<tr>
											<td style="width:900px;  height: 260px; border: 0px; overflow-y: hidden; padding-bottom: 1%;">
										  		<iframe id="contentsFrame" src="<c:url value='/monitoring/newworkstatusdisplay.do' />" name="contentsFrame" style="width: 100%; height: 100%; border: 0px; overflow-y: hidden;"></iframe>
										 	</td>
										 	<td style="width:900px; height: 260px; border: 0px; overflow-y: hidden; padding-bottom: 1%;">
										  		<iframe id="contentsFrame" src="<c:url value='/monitoring/processingdelaystatusbytaskdisplay.do' />" name="contentsFrame" style="width: 100%; height: 100%; border: 0px; overflow-y: hidden;"></iframe>
										 	</td>
										 <tr>
										<tr>
											<td style="width:900px; height: 450px; border: 0px; overflow-y: hidden;">
										  		<iframe id="contentsFrame" src="<c:url value='/monitoring/progressstatusbytaskdisplay.do' />" name="contentsFrame" style="width: 100%; height: 100%; border: 0px; overflow-y: hidden;"></iframe>
										 	</td>
										 	<td style="width:900px; height: 250px; border: 0px; overflow-y: hidden;">
										  		<iframe id="contentsFrame" src="<c:url value='/monitoring/taskcompletedaveragetimedisplay.do' />" name="contentsFrame" style="width: 100%; height: 100%; border: 0px; overflow-y: hidden;"></iframe>
										 	</td>
										 <tr>
									  </tbody>
									</table>
							</div>	
		
		</table>
	</div>
	<!-- /.container -->
</body>

</html>