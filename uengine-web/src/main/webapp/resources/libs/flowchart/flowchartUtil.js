
var flowchart = new FlowChartDefinition();

function showActivityDetails(instId, trcTag) {
	window.open('viewActivityDetails.jsp?instanceID=' + instId + '&tracingTag=' + trcTag,'activitydetails','width=500,height=400,scrollbars=yes,resizable=yes');
}

function locateWorkItem(defId, defVersionId, tracingTag, instanceId, propertyString){
	window.open(
			WEB_CONTEXT_ROOT + "/processparticipant/worklist/workitemHandler.jsp?instanceId=" + instanceId + "&tracingTag=" + tracingTag, 
			"wihspace", 
			"width=800,height=550,scrollbars=yes,resizable=yes"
	);
}

function initateProcess(defverid){
	var url = WEB_CONTEXT_ROOT + "/processparticipant/initiateForm.jsp?processDefinition=" + defverid;
	if (window.opener) {
		window.top.location.href = url;
	} else {
		window.open(url,'initiateProcess','top=100,left=500,width=1014,height=700,resizable=true,scrollbars=yes');
	}
}

function openDetailActivity(activityId) {
	$("#" + activityId).toggle();
}

function setTableDrawAreas(instId, defVerId) {
	$tableDrawAreas = [];
	
	var flowchartId = "";
	if(instId.isTrue()) {
		var instIds = instId.split(";");
		for (var i = instIds.length - 1; i >= 0; i--) {
			$tableDrawAreas.push($("#tableFlowchartCanvas_instance_" + instIds[i]));
		}
	} else if(defVerId.isTrue()) {
		$tableDrawAreas.push($("#tableFlowchartCanvas_definition_" + defVerId));
	}
	
	return $tableDrawAreas;
}

function swapSubProcessView(instId, defVerId, activityName, trcTag, parentElement) {
	var parentFlowchartId = "divOutLineArea_" + trcTag;
	
	if (document.getElementById(parentFlowchartId)) {
		formatDrawAreas();
		//cleanAll();
		$("#" + parentFlowchartId).slideToggle(flowchart.SLIDE_TIME, function() {$("#"+parentFlowchartId).parent().remove();});
	}
	
	flowchart.appendParentDefVerId();
	getProcessFlowchart(
			instId,
			'',
			defVerId,
			flowchart.getParentDefVerId(),
			flowchart.getViewType(),
			flowchart.getViewOption(),
			flowchart.getLastInstId(),
			flowchart.getInitiate(),
			''
	);
}

function getSubProcess(instId, defVerId, activityName, trcTag, parentElement) {
	var parentFlowchartId = "divOutLineArea_" + trcTag;

	if (document.getElementById(parentFlowchartId)) {
		//cleanAct(trcTag);
		
		//$("#" + parentFlowchartId).slideToggle(flowchart.SLIDE_TIME, function() {$("#"+parentFlowchartId).parent().parent().parent().parent("table").remove();});
		
		cleanAll();
		$("#" + parentFlowchartId).slideToggle(flowchart.SLIDE_TIME, drawAll);
		setTimeout(drawAll, flowchart.SLIDE_TIME + 100);
	} else {
		$.get(
			WEB_CONTEXT_ROOT + "/processmanager/flowchart/processFlowchartData.jsp",
			{
				"instanceId": instId,
				"definitionVersionId": defVerId,
				"parentTracingTag": trcTag,
				"viewType": flowchart.getViewType(),
				"viewOption": flowchart.getViewOption()
			},
			function(result){
				drawSubProcessFlowchart(
						result,
						instId,
						defVerId,
						activityName,
						trcTag,
						parentElement
				);
			}
		);
	}
}


function viewSubProcess(instId, defVerId, activityName, trcTag, parentElement) {
	switch (flowchart.getSubProcessViewType()) {
		case "multiple":
			getSubProcess(instId, defVerId, activityName, trcTag, parentElement);
		break;
		case "cascade":
			swapSubProcessView(instId, defVerId, activityName, trcTag, parentElement);
		break;
	}
}

function swapViewType(viewType, viewOption, instId, defVerId, viewInstId) {
	getProcessFlowchart(
			instId,
			flowchart.getDefId(),
			defVerId,
			flowchart.getParentDefVerId(),
			viewType,
			viewOption,
			flowchart.getLastInstId(),
			flowchart.getInitiate(),
			viewInstId
	);
}

function getProcessFlowchart(
		instId,
		defId,
		defVerId,
		parentDefVerId,
		viewType,
		viewOption,
		lastInstId,
		initiate,
		viewInstId
) {
	formatDrawAreas();
	
	$.post(
			WEB_CONTEXT_ROOT + "/processmanager/flowchart/getProcessFlowchart.jsp",
			{
				"instanceId"			: instId,
				"processDefinition"		: defId,
				"definitionVersionId"	: defVerId,
				"parentPdver"			: parentDefVerId,
				"viewType"				: viewType,
				"viewOption"			: viewOption,
				"lastInstance"			: lastInstId,
				"initiate"				: initiate,
				"viewInstId"			: viewInstId
			},
			function(resultString) {
				$("blink").unblink();
				
				if($oZoom != null) {
					$oZoom.hide();
					$oZoom.slideUp(
							flowchart.SLIDE_TIME,
							function() {
								for (var i = $tableDrawAreas.length - 1; i >= 0; i--) {
									$tableDrawAreas[i].hide();
								}
								
								clearChildLine(function(){drawProcessFlowchart(resultString, viewOption, instId, defId, defVerId);});
							}
					);
				} else {
					drawProcessFlowchart(resultString, viewOption, instId, defId, defVerId);
				}
				
				$("blink").blink();
			}
	);
}

function getParentNameSpace(nameSpace) {
	var tempTracingTags = nameSpace.split("act");
	var parentNameSpace = "";
	
	for (var i = 0, il = tempTracingTags.length - 1; i < il; i++) {
		if (i != 0) { parentNameSpace += "act"; }
		parentNameSpace += tempTracingTags[i];
	}
	
	return parentNameSpace;
}

function drawSubProcessFlowchart(result, instId, defVerId, activityName, parentTracingTag, parentElement) {
	var flowchartId = "divOutLineArea_" + parentTracingTag;
	var parentDrawArea = getDrawArea(getParentNameSpace(parentTracingTag));
	
	var table = document.createElement("table");
	parentDrawArea.appendChildCover(table);
	var cell = appendCell(appendRow(table));
	table.border = "0";
	table.align = "center";
	
	var divOutLineCanvas = document.createElement("div");
	var divFlowchart = document.createElement("div");
	
	cell.appendChild(divOutLineCanvas);
	divOutLineCanvas.appendChild(divFlowchart);
	
	divOutLineCanvas.id = "canvasForProcessDefinition" + flowchartId;
	divFlowchart.id = flowchartId;
	divFlowchart.className = "flowchart-subprocess-cover";
	
	var drawArea = setDrawArea(
		flowchartId,
		function(paper) {
			if (divFlowchart.offsetHeight > 0) {
				showSubProcessOutline(paper, parentElement.parentNode.parentNode, divFlowchart);
			}
		}
	);
	
	parentDrawArea.appendChild(drawArea);

	var $divSubFlowchart = $(divFlowchart);
	$divSubFlowchart.hide();
	
	insertFlowchartData(result, divFlowchart);
	
	$divSubFlowchart.slideDown(
			flowchart.SLIDE_TIME,
			function() {
				//drawAll();
				setTimeout(drawAll, flowchart.SLIDE_TIME);
			}
	);
}

var oZoom = null;
var $oZoom = null;
var $tableDrawAreas = null;

function insertFlowchartData(result, div) {
	var htmlData = new StringBuffer();
	var scriptData = new StringBuffer();
	
	var splitData = result.split("\n<script type=\"text/javascript\">\n\/\/<![CDATA[\n");
	
	htmlData.append(splitData[0]);
	
	for (var i = 1, il = splitData.length; i < il; i++) {
		var commentData = splitData[i].split("\n \/\/]]>\n</script>");
		scriptData.append(commentData[0]);
		htmlData.append(commentData[1]);
	}
	
	div.innerHTML = htmlData.toString();
	eval(scriptData.toString());
	enableTooltips();
}

function drawProcessFlowchart(reqData, viewOption, instId, defId, defVerId) {
	var divFlowchartArea = document.getElementById("divFlowchartArea");
	
	insertFlowchartData(reqData, divFlowchartArea);
	window.onresize = drawAll;
	
	var instId = document.getElementById("flowchartVariable_instanceId").value;
	var defVerId = document.getElementById("flowchartVariable_definitionVersionId").value;
	
	flowchart.setInstId(instId);
	flowchart.setDefVerId(defVerId);
	
	flowchart.setDefId(document.getElementById("flowchartVariable_processDefinition").value);
	flowchart.setViewType(document.getElementById("flowchartVariable_viewType").value);
	flowchart.setViewOption(document.getElementById("flowchartVariable_viewOption").value);
	flowchart.setLastInstId(document.getElementById("flowchartVariable_lastInstance").value);
	flowchart.setInitiate(document.getElementById("flowchartVariable_initiate").value);
	flowchart.setParentDefVerId(document.getElementById("flowchartVariable_parentPdver").value);

	setTableDrawAreas(instId, defVerId);
	
	var radios = document.getElementsByName("subProcessViewType");
	for(var i = radios.length - 1; i >= 0; i--) {
		if (radios[i].value == flowchart.getSubProcessViewType()) {
			radios[i].checked = true;
			break;
		}
	}
	
	drawChildLine();
}

var chartCount = 0;

function drawChildLine() {
	chartCount++;
	var tableParantTitle = document.getElementById("tableParantTitle_" + chartCount);
	
	if(tableParantTitle) {
		$(tableParantTitle).show();
		
		if(document.getElementById("tableParantTitle_" + (chartCount + 1))) {
			$("#divImgLine_" + chartCount).show();
			drawChildLine();
		} else {
			$("#divImgLine_" + chartCount).slideDown(flowchart.SLIDE_TIME);
			drawChildLine();
		}
	} else {
		chartCount--;
		
		$oZoom = $("#oZoom");
		$oZoom.hide();
		
		for (var i = $tableDrawAreas.length - 1; i >= 0; i--) {
			$tableDrawAreas[i].show();
		}
		
		$oZoom.slideDown(flowchart.SLIDE_TIME);
		
		if(isIE) {
			// 익스플로러 버그
			setTimeout(function(){$oZoom.css("border", "");}, flowchart.SLIDE_TIME+100);
		}
		
		// 선 재정열
		setTimeout(drawAll, flowchart.SLIDE_TIME+200);
	}
}

function clearChildLine(objFunction) {
	var tableParantTitle = document.getElementById("tableParantTitle_" + chartCount);
	
	if(tableParantTitle) {
		var $tableParantTitle = $(tableParantTitle);
		var $divImgLine = $("#divImgLine_" + chartCount);

		$divImgLine.hide();
		$.removeData($divImgLine);
		$tableParantTitle.hide();
		$.removeData($tableParantTitle);
		
		chartCount--;
		clearChildLine(objFunction);
	} else {
		chartCount = 0;
		objFunction();
	}
}

function setUnkwonImage(image){image.src = contextPath + "/resources/images/portraits/unknown_user.gif";}
function setDefaultActivityImage(image){image.src = contextPath + "/resources/images/flowchart/images/DefaultActivity.png";image.width="20";image.height="20"}
