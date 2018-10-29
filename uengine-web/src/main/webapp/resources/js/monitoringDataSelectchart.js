
var barOptionFunction = function(textinput){
	var barOption = {
		title: {
			display: true,
			text: textinput
		},
		tooltips: {
			mode: 'index',
			intersect: false
		},
		responsive: true,
		scales: {
			xAxes: [{
				stacked: true,
			}],
			yAxes: [{
				stacked: true
			}]
		}
	};
	return barOption;
}


var lineOptionFunction = function(textinput, xLabel, yLabel){
	var lineOption = {
		title: {
			display: true,
			text: textinput
		},
		tooltips: {
			mode: 'index',
			intersect: false
		},
		hover: {
			mode: 'nearest',
			intersect: true
		},
		scales: {
			xAxes: [{
				display: true,
				scaleLabel: {
					display: true,
					labelString: xLabel
				}
			}],
			yAxes: [{
				display: true,
				scaleLabel: {
					display: true,
					labelString: yLabel
				}
			}]
		}
	};
	return lineOption;
}

var doughnutOptionFunction = function(data, textinput){
	var oughnutOption = {
		responsive: true,
		legend: {
			position: 'right',
		},
		title: {
			display: true,
			text: textinput
		},
		animation: {
			animateScale: true,
			animateRotate: true
		},
		tooltips: {
		  callbacks: {
			label: function(tooltipItem, data) {
			  //get the concerned dataset
			  var dataset = data.datasets[tooltipItem.datasetIndex];
			  //calculate the total of this data set
			  var total = dataset.data.reduce(function(previousValue, currentValue, currentIndex, array) {
				return previousValue + currentValue;
			  });
			  //get the current items value
			  
			  var currentValue = dataset.data[tooltipItem.index];
			  //calculate the precentage based on the total and current item, also this does a rough rounding to give a whole number
			  var precentage = Math.floor(((currentValue/total) * 100)+0.5);

			  return data.labels[tooltipItem.index]+ ": "+currentValue+"("+ precentage + "%)";
			}
		  }
		} 
	};
	return oughnutOption;
} 

var fn_chartjs = function(chartType, text, windowName, xLabel, yLabel) {
	$.ajax({
		type : "POST",
		url : contextPath+"/monitoring/chartjs/" + windowName,
		cache : false,
		dataType : "JSON",
		success : function(result) {
			createChartjs(chartType, result.data.labels, result.data.datasets,
					text, xLabel, yLabel);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert('There is an error : method(group)에 에러가 있습니다.');
        }
	});
}

var getChartOption =function(chartType, textinput, data, xLabel, yLabel){
	if(chartType == 'bar'){
		return barOptionFunction(textinput);
	}else if(chartType == 'doughnut'){
		return doughnutOptionFunction(data, textinput);
	}else if(chartType == 'line'){
		return lineOptionFunction(textinput, xLabel, yLabel);
	}else{
		return null;
	}
}

var createChartjs = function(chartType, jsonLabels,
		jsonDatasets, textinput, xLabel, yLabel){
	
	var barChartData = {
			labels: jsonLabels,
			datasets: jsonDatasets
		};
	var ctx = document.getElementById('canvas').getContext('2d');
	console.log(chartType);
	console.log(barChartData);
	console.log(getChartOption(chartType, textinput, barChartData, xLabel, yLabel));
	window.myBar = new Chart(ctx, {
		type: chartType,
		data: barChartData,
		options: getChartOption(chartType, textinput, barChartData, xLabel, yLabel)
	});
}

