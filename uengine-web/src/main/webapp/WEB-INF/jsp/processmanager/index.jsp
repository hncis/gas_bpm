<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>

<script>
	var selectedNode;
	var isTreeClick = false;
	$(document).ready(function() {
		$("#contentsFrame").css('height', $(window).height()-105);
		$(window).resize(function(){
			$("#contentsFrame").css('height', $(window).height()-105);
		});
		fn_setDefinitionTree();
		
		//tree click event
		$(document).click(function(){
			if (selectedNode.type && isTreeClick){
				if ( selectedNode.type == "form" || selectedNode.type == "process" ) {
					if ( selectedNode.type == "process" ){
						fn_showProcessDefinition();
					}
				}
			}
			isTreeClick = false;
		});
		
		$(document).bind("contextmenu", function(e){
			isTreeClick = false;
		});
		
		//add folder
		$("#addFolderConfirmBtn").click(function(){
			fn_addFolder();
		});
		
		
		//add folder close
		$("#addFolderCloseBtn").click(function(){
			$("#folderName").val("");
		});
		
	});

	//drag&drop event
	
	$(document).on("dnd_stop.vakata", function(e, data){
	});
	
	//add folder
	fn_addFolder = function(){
		if ($("#folderName").val().trim() == ""){
			alert("<spring:message code="message.folder.name.null" />");
			return false;
		}
		var sendData = new Object();
		sendData.folderName=$("#folderName").val();
		sendData.parentFolder=selectedNode.id=="0"?"-1":selectedNode.id;
		sendData.comCode="${sessionScope.loggedUser.comCode}";
		$.ajax({
	        url: "<c:url value='/processmanager/put/folder' />",
	        data: JSON.stringify(sendData),
	        method: "POST",
	        contentType : "application/json;charset=utf-8;",
	        dataType : "json",
	    }).then(function(data) {
	    	if(window.console)
	    		console.log(data);
	    	$('#definitionTree').jstree().create_node(
	    		selectedNode.id, 
	    		{"id":data.id,"text":data.text}, 
	    		"last",
	    		function(){
	    			$('#definitionTree').jstree("deselect_all");
	    			$('#definitionTree').jstree("select_node", "#"+data.id);
	    		}
	    	);
	    }).fail(function(data) {
	    	alert("error");
	    });
		$("#folderName").val("");
	}
	
	//move item
	fn_moveItem = function(){
		$.ajax({
	        url: "<c:url value='/processmanager/put/folder/move' />/"+selectedNode.parent+"/"+selectedNode.id,
	        method: "POST",
	        contentType : "application/json;charset=utf-8;",
	        dataType : "json",
	    }).then(function(data) {
	    	$('#definitionTree').jstree("deselect_all");
			$('#definitionTree').jstree("select_node", "#"+selectedNode.id);
	    }).fail(function(data) {
	    	alert("error");
	    });
	};
	
	//remove item
	fn_removeItem = function(){
		if(window.console){
			console.log(selectedNode);
		}
		if(selectedNode.children && selectedNode.children.length > 0){
			alert("<spring:message code="message.folder.remove.alert" />");
			return true;
		}
		if(confirm("<spring:message code="message.folder.remove.confirm" />")){
			$.ajax({
		        url: "<c:url value='/processmanager/put/folder/remove' />/"+selectedNode.id,
		        method: "POST",
		        contentType : "application/json;charset=utf-8;",
		        dataType : "json",
		    }).then(function(data) {
		    	$('#definitionTree').jstree("delete_node", selectedNode);
		    }).fail(function(data) {
		    	alert("error");
		    });
		}
	}
	
	fn_createProcess = function(){
        $("#folderId").val(selectedNode.id=="0"?"-1":selectedNode.id);
        $("#defId").val("");
		$("#definitionForm").attr("action","<c:url value='/processmanager/processDesigner.jnlp' />");
        $("#definitionForm").submit();
	}
	
	fn_addFolderPopup = function(){
		$("#modalPopupBtn").click();
	}
	
	fn_createForm = function(){
		$("#folderId").val(selectedNode.id=="0"?"-1":selectedNode.id);
		$("#definitionForm").attr("action","<c:url value='/processmanager/createFormDefinition.do' />");
        $("#definitionForm").submit();
	}
	
	
	fn_processManagerMenu = function(node){
		var items = {
			'item1' : {
				'separator_before' : false,
				'label' : '<spring:message code="definition.tree.context.menu.show" />',
				'action' : fn_showProcessDefinition
			},
			'item2' : {
				'separator_before' : true,
				'label' : '<spring:message code="definition.tree.context.menu.new" />',
				'action' : false,
				'submenu' : {
					'new_item1' : {
						'separator_before' : false,
						'label' : '<spring:message code="definition.tree.context.menu.new.folder" />',
						'action' : fn_addFolderPopup
					},
					'new_process' : {
						'separator_before' : true,
						'label' : '<spring:message code="definition.tree.context.menu.new.process" />',
						'action' : fn_createProcess
					},
					'new_form' : {
						'separator_before' : true,
						'label' : '<spring:message code="definition.tree.context.menu.new.form" />',
						'action' : fn_createForm
					}
				}	
			},
			'item3' : {
				'separator_before' : false,
				'label' : '<spring:message code="definition.tree.context.menu.remove" />',
				'action' : fn_removeItem
			},
		}	
		if ( node.type != "process" ) {
			items.item1._disabled = true;
		} 
		if ( node.type != "root" && node.type != "default" ) {
			items.item2._disabled = true;
		} 
		if (node.children.length>0) {
			items.item3._disabled = true;
		} 
		return items;
	};
	
	fn_showProcessDefinition = function(){
		$("#definitionForm").attr("action","<c:url value='/processmanager/viewProcessDefinition.do' />");
		$("#definitionForm").submit();
	}

	fn_setDefinitionTree = function() {
		
		$('#definitionTree').on('select_node.jstree', function(node, selected, event) {
			selectedNode=selected.node;
			$("#defId").val(selected.node.id);
			isTreeClick = true;
		}).on('loaded.jstree', function(node, selected, event) {
			$('#definitionTree').jstree("open_all");
			$('#definitionTree').jstree("deselect_all");
		}).on('open_node.jstree', function(node, selected, event) {
		}).on('close_node.jstree', function(node, selected, event) {
		}).on('move_node.jstree', function(node, selected, event) {
			fn_moveItem();
		}).jstree({
			'plugins' : [  "state", "types", "wholerow", "contextmenu", "dnd" ],
			'contextmenu' : {
				'items' : fn_processManagerMenu
			},
			'core' : {
				'multiple' : false,
				'data' : {
					"url" : "<c:url value='/processmanager/processtree/list/' />"+"${sessionScope.loggedUser.comCode}",
					"dataType" : "json", // needed only if you do not supply JSON headers
					"method" : "POST",
					"cache" : false
				},
				"check_callback" : function(operation, node, node_parent, node_position, more){
					if(operation == "move_node"){
						if (node_parent.type == "default" || node_parent.type=="folder")
							return true;
						else
							return false;
					}
					return true;
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
			},
			'dnd' : {
				check_while_dragging : true
			}
			
		});
	}
	
	
	
</script>

<title>Process Manager</title>
</head>
<body>
	<form:form name="definitionForm" id="definitionForm" action="" method="post" target="contentsFrame">
		<form:input path="comCode" type="hidden" id="comCode" name="comCode" value="${sessionScope.loggedUser.comCode}" /> 
		<form:input path="defId" type="hidden" id="defId" name="defId" value="${defId}" />
		<form:input path="defVerId" type="hidden" id="defVerId" name="defVerId" value="" />
		<form:input path="folderId" type="hidden" id="folderId" name="folderId" value="" />
	</form:form>
	<!-- Page Content -->
	<div class="container-fluid">
		<table width=100%>
			<tr>
				<td style="width: 230px; vertical-align: top;">
					<div id="content-left" class="content-left">
						<div class="list-group">
							<li class="list-group-item disabled"><span class="glyphicon glyphicon-tasks"><spring:message code="menu.worklist.label" /></span></li>
							<li class="list-group-item" style="overflow-x:auto;"id="definitionTree"></li>
						</div>
					</div>
				</td>
				<td width= 100% style="vertical-align: top;">
					<div id="iframeArea"><iframe id="contentsFrame" src="<c:url value='/processmanager/viewInstanceList.do' />" name="contentsFrame" style="width:100%; border: 0px; overflow-y: hidden;"></iframe></div>
				</td>
			</tr>
		</table>
	</div>
	<div style="display:none;">
		<button type="button" id="modalPopupBtn" class="btn btn-info btn-lg" data-toggle="modal" data-target="#addFolderDiv">open</button>
	</div>
	
	<!-- Add Folder popup -->
	<div class="modal fade" id="addFolderDiv" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header modal-header-primary">
					<button id="addFolderCloseBtn" type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"><spring:message code="definition.tree.context.menu.new.folder" /></h4>
				</div>
				<div class="modal-body">
					<p><spring:message code="menu.foldername.label" /> : <input type="text" id="folderName" name="foldername" /></p>
				</div>
				<div class="modal-footer">
					<button id="addFolderConfirmBtn" type="button" class="btn btn-default pull-center" data-dismiss="modal"><spring:message code="menu.confirm.label" /></button>
				</div>
			</div>		
		</div>
	</div>
	<!-- Add Folder popup -->
</body>
</html>