<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>

<script>
	var orgTreeData;
	//jqgrid global settings
	$.jgrid.defaults.width = 240;
	$.jgrid.defaults.responsive = true;
	$(document).ready(function() {
		fn_setOrgTree()
		fn_setUserGrid();
	});
	
	fn_setOrgTree = function(){
		// jstree orgtree ajax
		var orgDialogParent = $("#orgDialog").parent();
		$("#orgDialog").dialog({
			appendTo: orgDialogParent,
			position: [orgDialogParent.position().top, orgDialogParent.position().left],
			height: 270,
			width: 365,
			modal: false,
			autoOpen: true,
			draggable: false,
			resizable: false,
			open: function(event, ui){
				$(".ui-dialog-titlebar-close", ui.dialog).hide();		
				$(".ui-dialog", ui.dialog).css("position", "relative");
				$(".ui-dialog", ui.dialog).removeClass().addClass("ui-jqgrid ui-widget ui-widget-content ui-corner-all");
				$(".ui-dialog-titlebar", ui.dialog).removeClass().addClass("ui-jqgrid-titlebar ui-jqgrid-caption ui-widget-header ui-corner-top ui-helper-clearfix");		
				//ui-jqgrid-titlebar ui-jqgrid-caption ui-widget-header ui-corner-top ui-helper-clearfix
			}
		}).css("font-size","11px");
		
		
		$('#orgTree').on('select_node.jstree', function(node, selected, event) {
							$("#partCode")
									.val(selected.node.id);
							fn_getUserList();
		}).on('loaded.jstree', function(node, selected, event) {
			$('#orgTree').jstree("open_all");
		}).jstree({
			'core' : {
				'multiple' : false,
				'data' : {
					"url" : "<c:url value='/organization/ajax/selectOrganizationTreeByComCode.do' />",
					"dataType" : "json" // needed only if you do not supply JSON headers
				}
			}
		});
	}
	
	
	fn_setUserGrid = function() {
		//jqgrid usertable
		$("#userGridTable").jqGrid({
			colNames : ["사번","이름","부서명","직위"],
			colModel : [ {
				name : "empCode",
				index : "empCode",
				width : 80,
				align : "center"
			}, {
				name : "empName",
				index : "empName",
				width : 80,
				align : "center"
			}, {
				name : "partName",
				index : "partName",
				width : 85,
				align : "center"
			}, {
				name : "jikName",
				index : "jikName",
				sorttype : "string",
				width : 85,
				align : "center",
				//sortable: false
			} ],
			jsonReader : {
				repeatitems : false
			},
			loadonce: true,
			sortname : "empName",
			viewrecords : true,
			sortorder : "asc",
			caption : "사용자 목록",
			width : "100%",
			height : 220,
			multiselect: true,
			//rownumbers: true
		});
		
		//titlebar button hidden
		$(".ui-jqgrid-titlebar-close").hide();
	}
	
	fn_getUserList = function() {
		$.ajax({
			type : "POST",
			url : "<c:url value='/organization/ajax/selectUserListByPartCode.do' />",
			cache : false,
			data : $("#approvalLineForm").serialize(),
			dataType : "JSON",
			success : function(data) {
				if ( window.console )	
					console.log(data);
				$("#userGridTable").jqGrid('clearGridData'
						).jqGrid('setGridParam',
					{
						datatype: "local",
						data:data
					}
				).trigger("reloadGrid",[{current:true}]);
				
				if ( window.console )	
					console.log($("#userGridTable").jqGrid('getGridParam', 'data'));
				
			},
			error : function(data) {
				alert("서버와의 통신에 실패했습니다.");
			}
		});
	}
</script>

<title>결재선 편집기</title>
</head>
<body>
	<form name="approvalLineForm" id="approvalLineForm">
		<input type="hidden" id="comCode" name="comCode"
			value="${sessionScope.loggedUser.comCode}" /> <input type="hidden"
			id="partCode" name="partCode" value="" />
	</form>
	<!-- Page Content -->
	<table width="100%">
		<tr>
			<td style="width: 380px;">
				<table>
					<tr>
						<td style="padding: 5px; width: 380;"><select
							class="form-control" id="approvalLineSearchOption"
							style="margin-bottom: 0px;">
								<option>이름</option>
								<option>사번</option>
								<option>부서명</option>
						</select></td>
						<td style="padding: 5px;"><input type="text"
							class="form-control" id="approvalLineSearchKeyword"
							name="approvalLineSearchKeyword" /></td>
						<td style="padding: 5px;">
							<button type="button" class="btn btn-info">
								<span class="glyphicon glyphicon-search"></span> Search
							</button>
						</td>
					</tr>
					<tr>
						<td colspan="3" style="padding: 5px;">
							<div id="orgDialog" title="조직도" style="overflow: auto;">
								<div class="panel-body" id="orgTree"></div>
							</div>
						</td>
					</tr>
					<tr>
					<td colspan="3" style="padding: 5px;">
						<table id="userGridTable"></table>
						<table id="userGridPager"></table>
					</td>
					</tr>
				</table>
			</td>
			<td style="padding: 5px; width: 40px">
				<p>
					<span class="glyphicon glyphicon-plus-sign"></span>
				</p>
				<p>
					<span class="glyphicon glyphicon-minus-sign"></span>
				</p>
			</td>
			<td>
				<table width=100%>
					<tr>
						<td colspan="3" style="padding: 5px;"><div	class="panel panel-warning"
								style="height: 270px; overflow: auto; margin-bottom: 0px;">
								<div class="panel-heading">결재선</div>
								<div class="panel-body">
									<table id="approvalLineGridTable"></table>
									<table class="table" width="100%">
										<thead>
											<tr>
												<th>번호</th>
												<th>이름</th>
												<th>직위</th>
												<th>결재형식</th>
												<th>전결</th>
											</tr>
										</thead>
										<tbody>
											<tr class="info">
												<td>번호</td>
												<td>이름</td>
												<td>직위</td>
												<td>결재형식</td>
												<td>전결</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>