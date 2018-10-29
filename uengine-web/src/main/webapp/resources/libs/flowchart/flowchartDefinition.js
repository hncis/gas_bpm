/**
 * @author erim1005
 */
function FlowChartDefinition() {
	var instId = "",
		defId = "",
		defVerId = "",
		viewOption = "",
		viewType = "",
		lastInstId = "",
		subProcessViewType = "cascade"
	;
	
	this.setInstId = function(s) {instId = s;};
	this.getInstId = function() {return instId};
	
	this.setDefId = function(s) {defId = s;};
	this.getDefId = function() {return defId;};
	
	this.setDefVerId = function(s) {defVerId = s;};
	this.getDefVerId = function() {return defVerId;};
	
	this.setViewOption = function(s) {viewOption = s;};
	this.getViewOption = function() {return viewOption;};
	
	this.setViewType = function(s) {viewType = s;};
	this.getViewType = function() {return viewType;};
	
	this.setLastInstId = function(s) {lastInstId = s;};
	this.getLastInstId = function() {return lastInstId;};
	
	var initiate = "";
	this.setInitiate = function(s) {initiate = s;};
	this.getInitiate = function() {return initiate;};
	
	this.setParentDefVerId = function(s) {parentDefVerId = s;};
	this.getParentDefVerId = function() {return parentDefVerId;};
	this.appendParentDefVerId = function() {
		if (!instId.isTrue()) {
			switch (parentDefVerId) {
				case "":
					parentDefVerId = defVerId;
				break;
				default:
					parentDefVerId += ";" + defVerId;
				break;
			}
		}
	};
	
	this.setSubProcessViewType = function(s) {subProcessViewType = s;};
	this.getSubProcessViewType = function(s) {return subProcessViewType;};
	this.SLIDE_TIME = 500;
}


var Direction = {
	up: 1,
	down: 2,
	left: 3,
	right: 4
};
var JsFlowchartOptions = {
	VIEW_VERTICAL:1,
	VIEW_HORIZONTAL:2,
	VIEW_FLOWCHART:1,
	VIEW_SWIMLANE:2,
	LINE_NONE:1,
	LINE_ALL:2,
	LINE_LOOP:3,
	LINE_BACK:4,
	LINE_SWIMLANE:5,
	LINE_SUBPROCESS:6,
	STATUS_READY:1,
	STATUS_COMPLETED:2,
	STATUS_FAULT:3,
	STATUS_RETRYING:4,
	STATUS_RUNNING:5,
	STATUS_SUSPENDED:6,
	STATUS_SKIPPED:7,
	STATUS_STOPPED:8,
	STATUS_TIMEOUT:9,
	STATUS_CANCELLED:10,
	STATUS_ALLCOMMIT:11
};

