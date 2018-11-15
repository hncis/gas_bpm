
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

var fn_toastStackedColumn = function(windowName, canvasid, titleText) {
	$.ajax({
		type : "POST",
		url : contextPath+"/monitoring/toastjs/" + windowName,
		cache : false,
		dataType : "JSON",
		success : function(result) {
			var returnValue = dateConverter(result.result);
			
			createToastStackedColumn(canvasid, titleText, returnValue);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert('There is an error : method(group)에 에러가 있습니다. (windowName_' + windowName + ')');
        }
	});
}

var getColor = function(inputData){
	let data = new Array();
	for(eachObejct in inputData){
		data[eachObejct] = inputData[eachObejct].color;
	}	
	return data;
}

var toastPercentColumnchartData = function(inputData){
	var data = new Object();
	let tempDate;
	tempDate = inputData[0].datas;
	let dateSample = tempDate.map(function(v, i){return v.x});
	let categories = new Array();
	let tempEditDate = "";
	let resultObjectName = "" ;
	let resultObjectDataEditBefore;
	let resultObject;
	let series =  new Array();
	for(eachDate in dateSample){
		tempEditDate = dateSample[eachDate].toString();
		tempEditDate = tempEditDate.substr(0, 10);
		categories[eachDate] = enDateToKrDate(tempEditDate);
	}
	for(eachObjectCounter in inputData){
		resultObjectName = inputData[eachObjectCounter].label;
		resultObjectDataEditBefore =  inputData[eachObjectCounter].datas.map(function(v, i){return v.y});
		let resultObjectData = new Array();
		for(eachObjectData in resultObjectDataEditBefore){
			resultObjectData[eachObjectData] = resultObjectDataEditBefore[eachObjectData];
		} 
		resultObject = new Object();
		resultObject.name = resultObjectName;
		resultObject.data = resultObjectData;
		series[eachObjectCounter] = resultObject;
	}
	data.categories = categories;
	data.series = series;
	return data;
}

var enDateToKrDate = function(inputDate){
	let inputMonth = inputDate.toString().substring(4,7);
	let inputDay = inputDate.toString().substring(8,10);
	let editMonth;
	let editDay;
	let isError = false;
	if(inputMonth.trim() == "Jan"){
		editMonth = "1월";
	} else if (inputMonth.trim() == "Feb") {
		editMonth = "2월";
	} else if (inputMonth.trim() == "Mar") {
		editMonth = "3월";
	} else if (inputMonth.trim() == "Apr") {
		editMonth = "4월";
	} else if (inputMonth.trim() == "May") {
		editMonth = "5월";
	} else if (inputMonth.trim() == "Jun") {
		editMonth = "6월";
	} else if (inputMonth.trim() == "Jul") {
		editMonth = "7월";
	} else if (inputMonth.trim() == "Aug") {
		editMonth = "8월";
	} else if (inputMonth.trim() == "Sept") {
		editMonth = "9월";
	} else if (inputMonth.trim() == "Oct") {
		editMonth = "10월";
	} else if (inputMonth.trim() == "Nov") {
		editMonth = "11월";
	} else if (inputMonth.trim() == "Dec") {
		editMonth = "12월";
	} else {
		isError = true;
	}
	editDay = inputDay + " 일";
	
	if(isError){
		return inputDate;
	} else {
		return editMonth + " " + editDay;
	}
}

var createToastStackedColumn = function(canvasid, titleText, inputData){
	var container = document.getElementById(canvasid);
	if(inputData.length != 0){
		var data = toastPercentColumnchartData(inputData);
		var options = {
			    chart: {
			        width: 700,
			        height: 350,
			        title: titleText,
			        format: '1,000'
			    },
			    yAxis: {
			        title: '신규업무건'
			    },
			    xAxis: {
			        title: '최근 7일'
			    },
			    series: {
			        barWidth: 30,
			        stackType: 'normal'
			    },
			    tooltip: {
			        grouped: true
			    },
			    legend: {
			        align: 'bottom4000'
			    }
			};
			tui.chart.columnChart(container, data, options);
	} else {
		$("#" + canvasid).html("<h4>신규 신청 현황</h4><br><h5>데이터가 존재하지 않습니다</h5>");
	}	
	
}

var fn_toastDoughnut = function(windowName, canvasid, titleText) {
	$.ajax({
		type : "POST",
		url : contextPath+"/monitoring/toastjs/" + windowName,
		cache : false,
		dataType : "JSON",
		success : function(result) {
			createToastDoughnutJs(canvasid, titleText, result.datas);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert('There is an error : method(group)에 에러가 있습니다. (windowName_' + windowName + ')');
        }
	});
}

var toastDonutChartData = function(inputData){
	var data = new Object();
	let categories = ["처리지연 프로세스"];
	let resultObjectName = "" ;
	let resultObjectCount;
	let resultObject;
	let series =  new Array();
	
	for(eachObjectCounter in inputData){
		resultObjectName = inputData[eachObjectCounter].label;
		resultObjectCount =  inputData[eachObjectCounter].y;
		resultObject = new Object();
		resultObject.name = resultObjectName;
		resultObject.data = resultObjectCount;
		series[eachObjectCounter] = resultObject;
	}
	data.categories = categories;
	data.series = series;
	return data;
}

var createToastDoughnutJs = function(canvasid, titleText, inputData){
	var container = document.getElementById(canvasid);
	if(inputData.length != 0){
		var data = toastDonutChartData(inputData);
		var options = {
		    chart: {
		    	width: 700,
		        height: 450,
		        title: titleText,
		        format: function(value, chartType, areaType, valuetype, legendName) {
		            if (areaType === 'makingSeriesLabel') { // formatting at series area
		                value = value + '건';
		            }

		            return value;
		        }
		    },
		    series: {
		        radiusRange: ['40%', '100%'],
		        showLabel: true
		    },
		    legend: {
		        align: 'bottom'
		    }
		};
		var theme = {
		    series: {
		        series: {
		            colors: [
		                '#83b14e', '#458a3f', '#295ba0', '#2a4175', '#289399',
		                '#289399', '#617178', '#8a9a9a', '#516f7d', '#dddddd'
		            ]
		        },
		        label: {
		            color: '#fff',
		            fontFamily: 'sans-serif'
		        }
		    }
		};
		tui.chart.registerTheme('myTheme', theme);
		options.theme = 'myTheme';
		tui.chart.pieChart(container, data, options);
	} else {
		$("#" + canvasid).html("<h4>업무별 처리 지연현황</h4><br><h5>데이터가 존재하지 않습니다</h5>");
	}
}

var fn_toastColumnChartByTask = function(windowName, canvasid,
		xLabel, yLabel, titleText) {
	$.ajax({
		type : "POST",
		url : contextPath+"/monitoring/toastjs/" + windowName,
		cache : false,
		dataType : "JSON",
		success : function(result) {
			createToastColumnChartByTask(canvasid, xLabel, yLabel, titleText, result.datas);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        	 alert('There is an error : method(group)에 에러가 있습니다. (windowName_' + windowName + ')');
        }
	});
}

var toastColumnChartByTaskData = function(inputData){
	var data = new Object();
	let categories = [""];
	let resultObjectName = "" ;
	let resultObjectCount;
	let resultObject;
	let series =  new Array();
	let tempArrayList;
	for(eachObjectCounter in inputData){
		resultObjectName = inputData[eachObjectCounter].label;
		resultObjectCount =  inputData[eachObjectCounter].y;
		tempArrayList = [resultObjectCount];
		resultObject = new Object();
		resultObject.name = resultObjectName;
		resultObject.data = tempArrayList;
		series[eachObjectCounter] = resultObject;
	}
	data.categories = categories;
	data.series = series;
	return data;	
}

var toastColumnChartByTaskMaxCounter = function(inputData){
	let resultObjectCount;
	let maxCounter = 0;
	for(eachObjectCounter in inputData){
		resultObjectCount =  inputData[eachObjectCounter].y;
		if(maxCounter < resultObjectCount){
			maxCounter = resultObjectCount;
		}
	}
	return maxCounter;
}

var createToastColumnChartByTask = function(canvasid, xLabel, yLabel, titleText, inputData){
	if(inputData.length != 0){
		let container = document.getElementById(canvasid);
		let data = toastColumnChartByTaskData(inputData);
		let options = {
		    chart: {
		    	width: 700,
		        height: 350,
		        title: titleText,
		        format: '1,000'
		    },
		    yAxis: {
		        title: yLabel,
		        min: 0,
		        max: toastColumnChartByTaskMaxCounter(inputData)
		    },
		    xAxis: {
		        title: xLabel
		    },
		    legend: {
		        align: 'top'
		    }
		};	
		tui.chart.columnChart(container, data, options);		
	} else {
		$("#" + canvasid).html("<h4>업무별 진행현황</h4><br><h5>데이터가 존재하지 않습니다</h5>");
	}
	
}

var fn_toastline = function(windowName, canvasid, titleText) {
	$.ajax({
		type : "POST",
		url : contextPath+"/monitoring/toastjs/" + windowName,
		cache : false,
		dataType : "JSON",
		success : function(result) {
			var returnValue = dateConverter(result.result);
			createToastline(canvasid, titleText, returnValue);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        	 alert('There is an error : method(group)에 에러가 있습니다. (windowName_' + windowName + ')');
        }
	});
}

var createToastline =  function(canvasid, titleText, inputData){
	var container = document.getElementById(canvasid);
	if(inputData.length != 0){
		var data = toastLineData(inputData);
		var options = {
		    chart: {
		    	width: 700,
		        height: 350,
		        title: titleText
		    },
		    yAxis: {
		        title: '완료건',
		        pointOnColumn: true
		    },
		    xAxis: {
		        title: '최근 7일'
		    },
		    series: {
		        showDot: false,
		        zoomable: true
		    },
		    tooltip: {
		        suffix: '건'
		    }
		};
		var chart = tui.chart.lineChart(container, data, options);
	} else {
		$("#" + canvasid).html("<h4>평균 업무완료 시간 추이</h4><br><h5>데이터가 존재하지 않습니다</h5>");
	}
	
}

var toastLineData =  function(inputData){
	return toastPercentColumnchartData(inputData);
}
