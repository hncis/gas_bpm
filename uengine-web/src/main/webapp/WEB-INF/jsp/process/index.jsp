<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-header.jspf"%>

<script>
WEB_CONTEXT_ROOT='${contextRoot}';
</script>
<script>
	var orgTreeData;
	var definitionObjectArray;
	$(document).ready(function() {
		$("#definitionContentsDiv").hide();
		
		$("#processExecuteBtn").click(function(){
			window.open("","workItemHandler","width=800,height=600");
			$("#definitionForm").attr("target","workItemHandler");
			$("#definitionForm").attr("action","<c:url value='/process/executeProcess.do' />");
			$("#definitionForm").submit();
		});
		
		$(".btn-group > .btn").click(function(){
			$(this).addClass("active").siblings().removeClass("active");	
			
			var ids = $(this).attr("id").split("-");
			
			$("#viewType").val(ids[0]);
			$("#viewOption").val(ids[1]);
			fn_drawFlowchart();
		});
		
		$("#contentsFrame").css('height', $(window).height()-105);
		
		$(window).resize(function(){
			$("#contentsFrame").css('height', $(window).height()-105);
		});
		
		fn_setDefinitionTree();
	});
	
	var fn_drawFlowchart = function(){
		$("#divFlowchartArea").hide();
		try{
			formatDrawAreas();
		} catch(e){}
		$.ajax({
	        url: "<c:url value='/chart/flowchart/definition/get/' />",
	        data: $("#definitionForm").serialize(),
	        method: "POST"
	    }).then(function(data) {
	       $("#divFlowchartArea").html(data);
	       if ( $("#viewOption").val() && $("#viewOption").val() == "vertical" )
			   $("#divFlowchartArea").show("blind",1000);
	       else
	    	   $("#divFlowchartArea").show("slide",1000);
			setTimeout("drawAll()", 2000);
		   	
	    });
	}
	fn_setDefinitionTree = function() {

		$('#definitionTree').on('select_node.jstree', function(node, selected, event) {
			if ( selected.node.type == "form" || selected.node.type == "process" ) {
				if ( selected.node.type == "process" ){
					$("#definitionContentsDiv").show();
					
					$("#defId").val(selected.node.id);
					$("#defName").val(selected.node.text);
					$.ajax({
				        url: "<c:url value='/service/get/prodver/' />"+$("#defId").val(),
				        method: "POST",
				        dataType : "json",
				    }).then(function(data) {
				    	$("#defVerId").val(data.defVerId);
				    	$("#processNameDiv").text(data.defName);
				    	$("#processDescriptionTd").text(data.description?data.description:"");
				    	$("#viewType").val("swimlane");
						$("#viewOption").val("horizontal");
						$(".btn").first().addClass("active").siblings().removeClass("active");
						fn_drawFlowchart();
						
				    });
				}
				
			}
		}).on('loaded.jstree', function(node, selected, event) {
			$('#definitionTree').jstree("open_all");
		}).on('open_node.jstree', function(node, selected, event) {
		}).on('close_node.jstree', function(node, selected, event) {
		}).jstree({
			'plugins' : [  "types", "wholerow" ],
			'core' : {
				'multiple' : false,
				'data' : {
					"url" : "<c:url value='/process/processtree/list/' />"+"${sessionScope.loggedUser.comCode}",
					"dataType" : "json", // needed only if you do not supply JSON headers
					"method" : "POST"
				}
			},
			'types' : {
				'#' : {
					'valid_children' : ['root']
				},
				'root' : {
					'icon' : 'glyphicon glyphicon-hdd',
					'valid_children' : ['default']
				},
				'f-open' : {
					'icon' : 'glyphicon glyphicon-folder-open'
				},
				'f-close' : {
					'icon' : 'glyphicon glyphicon-folder-close'
				},
				'process' : {
					'icon' : 'glyphicon glyphicon-forward'
				},
				'form' : {
					'icon' : 'glyphicon glyphicon-edit'
				},
				'default' : {
					'icon' : 'glyphicon glyphicon-folder-close'
				}
			}
		});
	}
	
	
	
</script>

<title>Process Manager</title>
</head>
<body>
	<form:form name="definitionForm" id="definitionForm" target="" action="" method="POST">
		<form:input type="hidden" id="defName"		    path="defName"		   />
		<form:input type="hidden" id="instanceId"		path="instanceId"		   />
		<form:input type="hidden" id="defId"			path="defId"   /> 
		<form:input type="hidden" id="defVerId"  		path="defVerId" />
		<form:input type="hidden" id="parentPdver" 	    path="parentPdver"		   />
		<form:input type="hidden" id="viewType"		    path="viewType"			   />
		<form:input type="hidden" id="viewOption"	    path="viewOption"		   />
		<form:input type="hidden" id="lastInstanceId"   path="lastInstanceId"		   />
		<form:input type="hidden" id="initiate"			path="initiate"			   />
		<form:input type="hidden" id="viewInstanceId"   path="viewInstanceId"		   />
		<form:input type="hidden" id="userId"   path="userId" value="${sessionScope.loggedUser.userId}" 	 />
	</form:form>
	<!-- Page Content -->
	<div class="container-fluid">
		<table width=100%>
			<tr>
				<td width=230px style="vertical-align: top">
					<div id="content-left" class="content-left">
						<div class="list-group">
							<li class="list-group-item disabled"><span class="glyphicon glyphicon-tasks"><spring:message code="menu.worklist.label" /></span></li>
							<li class="list-group-item" style="overflow-x:auto;"id="definitionTree"></li>
						</div>
					</div>
				</td>
				<td width="100%" style="vertical-align: top;">
					<div id="definitionContentsDiv" style="display: none;">
						<div class="panel panel-primary">
							<div class="panel-heading"><span id="gridTitle" class="glyphicon glyphicon-edit"><spring:message code="process.information.label" /><span></div>
							<div class="panel-body">
								<table class="table table-reflow">
								  <tbody>
								    <tr>
								      <td class="active" width="200px"><spring:message code="process.name.label" /></td>
								      <td>
								      	<div id="processNameDiv" style="float:left"></div>
								      	<div id="executeBtnDiv" style="float:left">
								      		<button id="processExecuteBtn" type="button" class="btn btn-info">
												<spring:message code="process.execute.label" />
											</button>
								      	</div>
								      </td>
								    </tr>
								    <tr style="border-bottom: 1px solid #ddd">
								      <td class="active"><spring:message code="process.description.label" /></td>
								      <td id="processDescriptionTd"></td>
								    </tr>
								  </tbody>
								</table>
							</div>
						</div>
						<div class="btn-group" data-toggle="buttons">
							<button type="button" id="swimlane-horizontal" class="btn btn-default active"><spring:message code="flowchart.type.swimlane.horizontal.label" /></button>
							<button type="button" id="swimlane-vertical" class="btn btn-default"><spring:message code="flowchart.type.swimlane.vertical.label" /></button>
							<button type="button" id="horizontal-horizontall" class="btn btn-default"><spring:message code="flowchart.type.horizontal.label" /></button>
							<button type="button" id="vertical-vertical" class="btn btn-default"><spring:message code="flowchart.type.vertical.label" /></button>
						</div>
						<div id="divFlowchartArea" style="display: none;vertical-align: middle; text-align: center;"></div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<!-- /.container -->
</body>
</html>