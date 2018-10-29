//no-cache
localStorage.removeItem('jstree');
var rowCount = 20;

/**
 * worklist
 */

var fn_getWorlist = function(userid, status, grid, rowCount) {
	$.ajax({
		type : "POST",
		url : contextPath+"/service/worklist/list/"+userid+"/"+rowCount+"/"+status,
		cache : false,
		dataType : "JSON",
		success : function(data) {
			$("#"+grid).jqGrid('clearGridData'
					).jqGrid('setGridParam',
				{
					datatype: "local",
					data:data
				}
			).trigger("reloadGrid",[{current:true}]);
		},
		error : function(data, msg) {
			if ( window.console )	
				console.log(data);
		}
	});
}

var fn_getMyInstanceList = function(userid, status, grid, rowCount) {
	$.ajax({
		type : "POST",
		url : contextPath+"/service/instance/list/"+userid+"/"+rowCount+"/"+status,
		cache : false,
		dataType : "JSON",
		success : function(data) {
			$("#"+grid).jqGrid('clearGridData'
			).jqGrid('setGridParam',
					{
				datatype: "local",
				data:data
					}
			).trigger("reloadGrid",[{current:true}]);
		},
		error : function(data, msg) {
			if ( window.console )	
				console.log(data);
		}
	});
}

var fn_getInstanceList = function(jsonParam, grid) {
	$.ajax({
		type : "POST",
		url : contextPath+"/processmanager/list/instanceList",
		data : JSON.stringify(jsonParam),
		cache : false,
		contentType : "application/json;charset=utf-8;",
		dataType : "JSON",
		success : function(data) {
			$("#"+grid).jqGrid('clearGridData'
			).jqGrid('setGridParam',
					{
				datatype: "local",
				data:data
					}
			).trigger("reloadGrid",[{current:true}]);
		},
		error : function(data, msg) {
			if ( window.console )	
				console.log(data);
		}
	});
}

var fn_updateProcessVersion = function(jsonParam) {
	$.ajax({
		type : "POST",
		url : contextPath+"/processmanager/processVersionChange",
		data : JSON.stringify(jsonParam),
		cache : false,
		contentType : "application/json;charset=utf-8;",
		dataType : "JSON",
		success : function(data) {
			window.location.reload();
		},
		error : function(data, msg) {
			if ( window.console )	
				console.log(data);
		}
	});
}

var fn_setMyWorklistCount = function(userId){
	$.ajax({
        url: contextPath+"/service/worklist/count/get/"+userId,
        type: "POST",
        cache : false,
    }).then(function(data) {
    	if ( window.console ){
    		console.log(data);
    	}
    	var myNewWorkTotalcount = 0;
    	for ( var i = 0; i < data.length; i++ ){
			if ( data[i].status == 'NEW' || data[i].status == 'CONFIRMED' || data[i].status == 'DRAFT' ){
				myNewWorkTotalcount += data[i].totalCount; 
			}
			if ( data[i].status == 'CONFIRMED' &&  $("#myConfiremdWorkCount") )
				$("#myConfiremdWorkCount").text(data[i].totalCount);
			if ( data[i].status == 'COMPLETED' &&  $("#myCompletedWorkCount"))
				$("#myCompletedWorkCount").text(data[i].totalCount);
			if ( data[i].status == 'DELAYED' &&  $("#myDelayedWorkCount"))
				$("#myDelayedWorkCount").text(data[i].totalCount);
			if ( data[i].status == 'DELEGATED' &&  $("#myDelegatedWorkCount"))
				$("#myDelegatedWorkCount").text(data[i].totalCount);
		}
    	if ($("#myNewWorkCount"))
			$("#myNewWorkCount").text(myNewWorkTotalcount);
    });
};

/**
 * instancelist
 */

var fn_setMyInstanceCount = function(userId){
	$.ajax({
		url: contextPath+"/service/instance/count/get/"+userId,
		type: "POST",
		cache : false,
	}).then(function(data) {
		
		for ( var i = 0; i < data.length; i++ ){
			if ( data[i].status == 'Running' || $("#myRunningInstanceCount") )
				$("#myRunningInstanceCount").text(data[i].totalCount);
			if ( data[i].status == 'Completed' && $("#myCompletedInstanceCount") )
				$("#myCompletedInstanceCount").text(data[i].totalCount);
			if ( data[i].status == 'Requested' && $("#myRequestedInstanceCount") )
				$("#myRequestedInstanceCount").text(data[i].totalCount);
			if ( data[i].status == 'Stopped' && $("#myStoppedInstanceCount") )
				$("#myStoppedInstanceCount").text(data[i].totalCount);
			if ( data[i].status == 'Failed' && $("#myFailedInstanceCount") )
				$("#myFailedInstanceCount").text(data[i].totalCount);
		}
	});
};

/**
 * instancelist
 */


var fn_viewWorkItem = function(form, type){
	var width=$(document).width()-10;
	var height=$(document).height();
	
	
	if(type == "worklist"){
		var url = contextPath+"/worklist/viewWorkItem.do";	
	}else if(type == "instancelist"){
		var url = contextPath+"/instancelist/viewWorkItem.do";
	}else{
		
	}
	window.open(url,"popup","width="+width+",height="+height);
	console.log("url: " + url);
	form.attr("action", url);
	form.attr("target","popup");
	form.submit();
}

var fn_viewInstanceInfo = function(form){
	var width=$(document).width()-10;
	var height=$(document).height();
	window.open(url,"popup","width="+width+",height="+height);
	var url = contextPath+"/instancelist/viewWorkItem.do";
	form.attr("action", url);
	form.attr("target","popup");
	form.submit();
}

