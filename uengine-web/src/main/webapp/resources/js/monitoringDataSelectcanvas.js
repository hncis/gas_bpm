
var fn_canvascolumn = function(windowName, canvasid,
		xLabel, yLabel, titleText) {
	$.ajax({
		type : "POST",
		url : contextPath+"/monitoring/canvasjs/" + windowName,
		cache : false,
		dataType : "JSON",
		success : function(result) {
			createcanvascolumnjs(canvasid, xLabel, yLabel, titleText, result.datas);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        	 alert('There is an error : method(group)에 에러가 있습니다. (windowName_' + windowName + ')');
        }
	});
}

var createcanvascolumnjs = function(canvasid, xLabel, yLabel, titleText, data){
	var datatest = [{        
		type: "column",  
		showInLegend: true, 
		legendMarkerColor: "grey",
		legendText: xLabel,
		dataPoints: data
	}];
	var chart = new CanvasJS.Chart(canvasid, {
		animationEnabled: true,
		theme: "light2", // "light1", "light2", "dark1", "dark2"
		title:{ text: titleText},
		axisY: { title: yLabel},
		data: datatest
	});
	chart.render();
}


var fn_canvasdoughnut = function(windowName, canvasid, titleText) {
	$.ajax({
		type : "POST",
		url : contextPath+"/monitoring/canvasjs/" + windowName,
		cache : false,
		dataType : "JSON",
		success : function(result) {
			createcanvasdoughnutjs(canvasid, titleText, result.datas);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert('There is an error : method(group)에 에러가 있습니다. (windowName_' + windowName + ')');
        }
	});
}

var createcanvasdoughnutjs = function(canvasid, titleText, data){
	console.log(data);
	var datatest = [{        
		type: "doughnut",  
		startAngle: 60,
		//innerRadius: 60,
		indexLabelFontSize: 17,
		indexLabel: "{label} - #percent%",
		toolTipContent: "<b>{label}:</b> {y} (#percent%)",
		dataPoints: data
	}];
	var chart = new CanvasJS.Chart(canvasid, {
		animationEnabled: true,
		title:{
			text: titleText,
			horizontalAlign: "left"
		},
		data: datatest
	});
	chart.render();
}

var dateConverter = function(input){
	var resultValue = input;
	for(var i =0; i<resultValue.length; i++){
		var resultSubValue = resultValue[i].datas;
		for(var j =0; j< resultSubValue.length;  j++){
			var date = new Date(resultSubValue[j].x);
			resultSubValue[j].x = date;
		}
		
	}
	return resultValue;
}

function toolTipContent(e) {
	var str = "";
	var total = 0;
	var str2, str3;
	for (var i = 0; i < e.entries.length; i++){
		var  str1 = "<span style= 'color:"+e.entries[i].dataSeries.color + "'> "+e.entries[i].dataSeries.name+"</span>: <strong>"+e.entries[i].dataPoint.y+"</strong>건<br/>";
		total = e.entries[i].dataPoint.y + total;
		str = str.concat(str1);
	}
	str2 = "<span style = 'color:DodgerBlue;'><strong>"+(e.entries[0].dataPoint.x).getFullYear()+"</strong></span><br/>";
	total = Math.round(total * 100) / 100;
	str3 = "<span style = 'color:Tomato'>Total:</span><strong> "+total+"</strong>건<br/>";
	return (str2.concat(str)).concat(str3);
}


var fn_canvasstackedcolumn = function(windowName, canvasid, titleText) {
	$.ajax({
		type : "POST",
		url : contextPath+"/monitoring/canvasjs/" + windowName,
		cache : false,
		dataType : "JSON",
		success : function(result) {
			var returnValue = dateConverter(result.result);
			createcanvasstackedcolumn(canvasid, titleText, returnValue);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert('There is an error : method(group)에 에러가 있습니다. (windowName_' + windowName + ')');
        }
	});
}


var createcanvasstackedcolumn = function(canvasid, titleText, data){
	    var datatest = new Array();
	    for(var i =0; i<data.length; i++){
	    	var object = new Object();
	    	var label = data[i].label;
	    	object.type ="stackedColumn";
	    	object.showInLegend= true;
	    	object.name = data[i].label;
			object.color = data[i].color;	
			object.dataPoints = data[i].datas;
			datatest[i] = object;
	    }
	    
	var chart = new CanvasJS.Chart(canvasid, {
		animationEnabled: true,
		title:{
			text: titleText,
			fontFamily: "arial black",
			fontColor: "#695A42"
		},
		axisX: {
			   valueFormatString: "YYYY-MM-DD" ,
	            labelAngle: -50
		},
		axisY:{
			valueFormatString:"$#0bn",
			gridColor: "#B6B1A8",
			tickColor: "#B6B1A8"
		},
		toolTip: {
			shared: true,
			content: toolTipContent
		},
		data: datatest
	});
	chart.render();
}


var fn_canvasline = function(windowName, canvasid, titleText) {
	$.ajax({
		type : "POST",
		url : contextPath+"/monitoring/canvasjs/" + windowName,
		cache : false,
		dataType : "JSON",
		success : function(result) {
			var returnValue = dateConverter(result.result);
			createcanvasline(canvasid, titleText, returnValue);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        	 alert('There is an error : method(group)에 에러가 있습니다. (windowName_' + windowName + ')');
        }
	});
}
function toogleDataSeries(e){
	if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
		e.dataSeries.visible = false;
	} else{
		e.dataSeries.visible = true;
	}
	chart.render();
}


var createcanvasline = function(canvasid, titleText, data){
    var datatest = new Array();
    for(var i =0; i<data.length; i++){
    	var object = new Object();
    	var label = data[i].label;
    	object.type ="line";
    	object.showInLegend= true;
    	object.name = data[i].label;
    	object.markerType = 'square';
    	object.xValueFormatString="DD MMM, YYYY";
		object.color = data[i].color;	
		object.dataPoints = data[i].datas;
		console.log(data[i].datas);
		datatest[i] = object;
    }
    console.log(datatest);
    
var chart = new CanvasJS.Chart(canvasid, {
	animationEnabled: true,
	theme: "light2",
	title:{
		text: titleText
	},
	axisX:{
		valueFormatString: "DD MMM, YYYY",
		crosshair: {
			enabled: true,
			snapToDataPoint: true
		}
	},
	axisY: {
		title: '건수',
		includeZero: true,
		crosshair: {
			enabled: true
		},
		scaleBreaks: {
			autoCalculate: true
		}
	},
	toolTip:{
		shared:true
	},  
	legend:{
		cursor:"pointer",
		verticalAlign: "bottom",
		horizontalAlign: "left",
		dockInsidePlotArea: true,
		itemclick: toogleDataSeries
	},
	data: datatest
});
chart.render();
}
