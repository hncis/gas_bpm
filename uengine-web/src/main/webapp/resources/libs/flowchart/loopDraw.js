
var drawAreas = [];
var CURVE_SIZE = 5;


function PointList() {
	var array = [];
	this.array = array;
	this.push = array.push;
	this.addPoint = function(sPoint, ePoint) {
		array.push([sPoint, ePoint]);
	};
	this.addBezierPoint = function(sPoint, cPoint, ePoint) {
		var point = [sPoint, cPoint, ePoint];
		point.isBezier = true;
		
		array.push(point);
	};
	this.addCurveLine = function(pointList) {
		for (var i = 2, il = pointList.length; i < il; i++) {
			var sPoint = pointList[i - 2];
			var cPoint = pointList[i - 1];
			var ePoint = pointList[i];
			var lineType = null;
			
			var curvePoints = getCurvePoints(sPoint, cPoint, ePoint, lineType);
	
			var tempSPoint;
			var tempEPoint;
			
			switch (i) {
			case (2):
				tempSPoint = sPoint;
				break;
			default:
				if (sPoint[0] < cPoint[0] + 2 && sPoint[0] > cPoint[0] - 2) {
					if (sPoint[1] > cPoint[1]) {
						tempSPoint = [sPoint[0], sPoint[1] - CURVE_SIZE];
					} else {
						tempSPoint = [sPoint[0], sPoint[1] + CURVE_SIZE];
					}
				} else {
					if (sPoint[0] < cPoint[0]) {
						tempSPoint = [sPoint[0] + CURVE_SIZE, sPoint[1]];
					} else {
						tempSPoint = [sPoint[0] - CURVE_SIZE, sPoint[1]];
					}
				}
				break;
			}
			
			switch (i) {
			case (il - 1):
				tempEPoint = ePoint;
				break;
			default:
				if (ePoint[0] < cPoint[0] + 2 && ePoint[0] > cPoint[0] - 2) {
					if (ePoint[1] > cPoint[1]) {
						tempEPoint = [ePoint[0], ePoint[1] - CURVE_SIZE];
					} else {
						tempEPoint = [ePoint[0], ePoint[1] + CURVE_SIZE];
					}
				} else {
					if (ePoint[0] < cPoint[0]) {
						tempEPoint = [ePoint[0] + CURVE_SIZE, ePoint[1]];
					} else {
						tempEPoint = [ePoint[0] - CURVE_SIZE, ePoint[1]];
					}
				}
				break;
			}
			
			this.addPoint(tempSPoint, curvePoints[0]);
			this.addBezierPoint(curvePoints[0], cPoint, curvePoints[1]);
			this.addPoint(curvePoints[1], tempEPoint);
		}
	};
}

function DrawArea(namespace, fcn) {
	var divContentArea = document.getElementById("canvasForProcessDefinition" + namespace);
	var divDrawArea = null;
	var children = [];
	var paper = null;
	
	(function (divDrawAreaId) {
		if (document.getElementById(divDrawAreaId)) {
			divDrawArea = document.getElementById(divDrawAreaId);
		} else {
			divDrawArea = document.createElement("div");
			divDrawArea.id = divDrawAreaId;
			divDrawArea.className = "flowchart-drawarea";
			
			$(document).ready(function() {
				document.body.appendChild(divDrawArea);
			});
		}
	})("divDrawArea" + namespace);
	
	this.appendChild = (function(child) { children.push(child); });
	this.getChildren = (function() { return children; });
	
	this.key = namespace;
	
	this.appendChildCover = (function(c) {
		divContentArea.appendChild(c);
	});
	
	
	this.clear = (function() { if (paper != null) { paper.remove(); paper = null; } });
	
	this.draw = (function() {
		this.clear();
		
		var position = getAbsPosition(divContentArea);
		
		paper = Raphael(
			divDrawArea,
			(position[0] + divContentArea.offsetWidth),
			(position[1] + divContentArea.offsetHeight)
		);
		position = null;
		
		fcn(paper);
	});
	
	this.hide = (function() { /* Js1005.hide(divDrawArea); */ jQuery(divDrawArea).hide(); });
	this.show = (function() { /* Js1005.show(divDrawArea); */ jQuery(divDrawArea).show(); });
};

function setDrawArea(namespace, fcn) {
	drawAreas.push(new DrawArea(namespace, fcn));
}

function getDrawArea(namespace) {
	for (var i = drawAreas.length - 1; i >= 0; i--) {
		if (drawAreas[i].key == namespace) {
			return drawAreas[i];
		}
	}
}

function cleanAct(act) {
	for (var i = drawAreas.length - 1; i >= 0; i--) {
		if (drawAreas[i].key.indexOf(act) > -1) {
			drawAreas[i].clear();
		}
	}
	
	removeDrawArea(act);
}

function removeDrawArea(act) {
	for (var i = drawAreas.length - 1; i >= 0; i--) {
		if (drawAreas[i].key.indexOf(act) > -1) {
			drawAreas.splice(i, 1);
			removeDrawArea(act);
			break;
		}
	}
}

function drawAll(){for (var i = drawAreas.length-1; i >= 0; i--) { drawAreas[i].draw(); }}
function cleanAll(){for (var i = drawAreas.length-1; i >= 0; i--) { drawAreas[i].clear(); }}
function hideLineAll(){for (var i = drawAreas.length-1; i >= 0; i--) { drawAreas[i].hide(); }}
function showLineAll(){for (var i = drawAreas.length-1; i >= 0; i--) { drawAreas[i].show(); }}

function formatDrawAreas() {
	cleanAll();
	drawAreas = [];
}

function getCurvePoints(sPoint, cPoint, ePoint, lineType) {
	var curveSPoint, curveEPoint;
	
	if (sPoint[0] < cPoint[0] + 2 && sPoint[0] > cPoint[0] - 2) {
		if (sPoint[1] < cPoint[1]) {
			curveSPoint = [cPoint[0], cPoint[1] - CURVE_SIZE];
		} else {
			curveSPoint = [cPoint[0], cPoint[1] + CURVE_SIZE];
		}
		
		if (ePoint[0] > cPoint[0]) {
			curveEPoint = [cPoint[0] + CURVE_SIZE, cPoint[1]];
		} else {
			curveEPoint = [cPoint[0] - CURVE_SIZE, cPoint[1]];
		}
	} else {
		if (sPoint[0] < cPoint[0]) {
			curveSPoint = [cPoint[0] - CURVE_SIZE, cPoint[1]];
		} else {
			curveSPoint = [cPoint[0] + CURVE_SIZE, cPoint[1]];
		}
		
		if (ePoint[1] > cPoint[1]) {
			curveEPoint = [cPoint[0], cPoint[1] + CURVE_SIZE];
		} else {
			curveEPoint = [cPoint[0], cPoint[1] - CURVE_SIZE];
		}
	}
	
	return [curveSPoint, curveEPoint];
}

function drawArrow(paper, point, color, direction) {
	var turnPoint01 = null;
	var turnPoint02 = null;
	var arrowWidth = 4;
	
	switch(direction) {
	case Direction.up :
		turnPoint01 = [point[0] - arrowWidth, point[1] + 10];
		turnPoint02 = [point[0] + arrowWidth, point[1] + 10];
		break;
	case Direction.down :
		turnPoint01 = [point[0] - arrowWidth, point[1] - 10];
		turnPoint02 = [point[0] + arrowWidth, point[1] - 10];
		break;
	case Direction.left :
		turnPoint01 = [point[0] + 10, point[1] + arrowWidth];
		turnPoint02 = [point[0] + 10, point[1] - arrowWidth];
		break;
	case Direction.right :
		turnPoint01 = [point[0] - 10, point[1] + arrowWidth];
		turnPoint02 = [point[0] - 10, point[1] - arrowWidth];
		break;
	}
	
	var pathString = (
			"M" + point[0] + " " + point[1]
			+ "L" + turnPoint01[0] + " " + turnPoint01[1]
			+ "L" + turnPoint02[0] + " " + turnPoint02[1]
			+ "z"
	);
	
	var arrow = paper.path(pathString);
	pathString = null;
	
	arrow.attr({
		stroke : color,
		"fill" : color
	});
	
	return arrow;
}

function drawStrike(paper, sPoint, ePoint, cPoint, isBezier, color, lineWidth) {
	var pathString = "";
	
	if (isBezier) {
    	pathString = "M" + sPoint[0] + " " + sPoint[1] + "Q" + cPoint[0] + " " + cPoint[1] + " " + ePoint[0] + " " + ePoint[1];
	} else {
    	pathString = "M" + sPoint[0] + " " + sPoint[1] + "L" + ePoint[0] + " " + ePoint[1];
	}
	
	var line = paper.path(pathString);
	line.attr({
		"stroke" : color,
		"stroke-width" : lineWidth
	});
}

function drawLine(paper, points, color, lineWidth, lineType, viewOption) {
    for (var i = 0, il = points.length; i < il; i++) {
    	var point = points[i];
    	var sPoint = point[0];
		var ePoint = point[point.length - 1];
    	
		drawStrike(paper, sPoint, ePoint, point[1], (point.isBezier), color, lineWidth);
    	
    	/* draw arrow only swim lane type */
    	switch (i) {
    	case (il - 1):
        	switch (lineType) {
    		case JsFlowchartOptions.LINE_SWIMLANE:
    		case JsFlowchartOptions.LINE_LOOP:
    		case JsFlowchartOptions.LINE_BACK:
                var direction = null;
    			switch (viewOption) {
    			case "horizontal":
                	if(sPoint[0] < ePoint[0]){direction = Direction["right"];}else{direction = Direction["down"];}
    				break;
    			default:
                	if(sPoint[1] < ePoint[1]){direction = Direction["down"];}else{direction = Direction["left"];}
    				break;
    			}
            	
				drawArrow(paper, ePoint, color, direction);
    		default:
    			break;
    		}
    		break;
    	default:
    		break;
    	}
    	/*--------------------------------*/
    }
}

function showSubProcessOutline(paper, parentElement, childFlowchartDiv) {
	var pointS = getAbsPosition(parentElement);
	var pointT = getAbsPosition(childFlowchartDiv);
	var outLineColor = "#555555";

	var leftPoints = new PointList();
	var rightPoints = new PointList();
	leftPoints.addPoint(
			[pointS[0], pointS[1] + parentElement.offsetHeight],
			[pointT[0], pointT[1]]
	);

	rightPoints.addPoint(
			[pointS[0] + parentElement.offsetWidth, pointS[1] + parentElement.offsetHeight],
			[pointT[0] + childFlowchartDiv.offsetWidth, pointT[1]]
	);
	
	var draw = drawLine;
	draw(paper, leftPoints.array, outLineColor, 2, JsFlowchartOptions.LINE_SUBPROCESS, 0);
	draw(paper, rightPoints.array, outLineColor, 2, JsFlowchartOptions.LINE_SUBPROCESS, 0);
	
	leftPoints = null;
	rightPoints = null;
	
	var pathString = (
			"M" + (pointS[0]) + " " + (pointS[1] + parentElement.offsetHeight)
			+ "L" + (pointT[0]) + " " + (pointT[1])
	        + "L" + (pointT[0] + childFlowchartDiv.offsetWidth) + " " + (pointT[1])
            + "L" + (pointS[0] + parentElement.offsetWidth) + " " + (pointS[1] + parentElement.offsetHeight)
            + "z"
    );

	var line = paper.path(pathString);
	line.attr({
		"stroke" : outLineColor,
		"fill" : outLineColor,
		"fill-opacity" : 0.2
	});
}

function drawLoopLine(paper, namespace, tracingTag) {
	var points = new PointList();
	var position = getAbsPosition;

	//determines whether it should be draw vertically or not.
	var leftSpacer = document.getElementById("leftSpacer" + tracingTag);
	var rightLabel = document.getElementById("rightLabel" + tracingTag);
	
	var sElement = document.getElementById('switch' + tracingTag);
	var tElement = document.getElementById('start' + tracingTag);
	var lElement = document.getElementById('loop' + tracingTag);

	var isVertical = (leftSpacer != null);
	var spacerwidth = 0;

	if (isVertical) {
		leftSpacer.width = rightLabel.offsetWidth;
		spacerwidth = leftSpacer.width;
	}

	
	var sPosition = position(sElement);
	var tPosition = position(tElement);
	var lPosition = position(lElement);
	
	var startX = sPosition[0];
	var startY = sPosition[1];
	
	var switchX = tPosition[0];
	var switchY = tPosition[1];

	var loopX = lPosition[0];
	var loopY = lPosition[1];
	
	if (isVertical) {
		startX += sElement.offsetWidth;
		startY += (sElement.offsetHeight / 2);
		
		switchX += tElement.offsetWidth;
		switchY += (tElement.offsetHeight / 2);
		
		var loopTopRightX = loopX + lElement.offsetWidth;
		var loopTopRightY = loopY;
		var lablePoint = position(rightLabel);
		loopTopRightX = lablePoint[0] + spacerwidth / 2;
	} else {
		startX += (sElement.offsetWidth / 2);
		switchX += (tElement.offsetWidth / 2);
	}

	var lineWidth = 10,
		startPoint,
		curve01SPoint,
		curve01CPoint,
		curve01EPoint,
		curve02SPoint,
		curve02CPoint,
		curve02EPoint,
		pointTarget
	;
	
	if (isVertical) {
		loopTopRightX -= 10;

		startPoint = [startX, startY];
		curve01SPoint = [loopTopRightX, startY];
		curve01CPoint = [loopTopRightX + lineWidth, startY];
		curve01EPoint = [loopTopRightX + lineWidth, startY - lineWidth];
		curve02SPoint = [loopTopRightX + lineWidth, switchY + lineWidth];
		curve02CPoint = [loopTopRightX + lineWidth, switchY];
		curve02EPoint = [loopTopRightX - lineWidth, switchY];
		pointTarget = [switchX, switchY];
	} else {
		startPoint = [startX, startY];
		curve01SPoint = [startX, loopY];
		curve01CPoint = [startX, loopY - lineWidth];
		curve01EPoint = [startX - lineWidth, loopY - lineWidth];
		curve02SPoint = [switchX + lineWidth, loopY - lineWidth];
		curve02CPoint = [switchX, loopY - lineWidth];
		curve02EPoint = [switchX, loopY];
		pointTarget = [switchX, startY];
	}
	
	points.addPoint(startPoint, curve01SPoint);
	points.addBezierPoint(curve01SPoint, curve01CPoint, curve01EPoint);
	points.addPoint(curve01EPoint, curve02SPoint);
	points.addBezierPoint(curve02SPoint, curve02CPoint, curve02EPoint);
	points.addPoint(curve02EPoint, pointTarget);
	
	drawLine(paper, points.array, "#888888", 2, JsFlowchartOptions.LINE_LOOP, "");
	points = null;
}

function drawAllActivityLine(paper, namespace, parentId, childId, isVertical) {
	var points = new PointList();
	
	var parentObj = document.getElementById(parentId),
		childObj = document.getElementById(childId),
		pPoint = getAbsPosition(parentObj),
		cPoint = getAbsPosition(childObj)
	;

	var centerPointX = 0,
		centerPointY = 0,
		PointX = 0,
		pointY = 0,
		lineWidth = 10,
		roundCornerDiff = 0,
		isNotCurve = false
	;

	if (!isVertical) {
		pPoint[1] -= 2;
		cPoint[1] -= 2;
		var iconSize = 20;
		centerPointX = pPoint[0] + iconSize;
		centerPointY = pPoint[1] + (parentObj.offsetHeight / 2);
		var allActWidth = parentObj.offsetWidth;

		PointX = centerPointX + lineWidth;
		pointY = cPoint[1] + (childObj.offsetHeight / 2);
		var actWidth = childObj.offsetWidth + 2;

		roundCornerDiff = (centerPointY - pointY);
		if (roundCornerDiff < -10) roundCornerDiff = -10;
		if (roundCornerDiff > 10) roundCornerDiff = 10;
		
		// front
		isNotCurve = ((centerPointY - pointY) < CURVE_SIZE) && ((centerPointY - pointY) > - CURVE_SIZE);
		if (isNotCurve) {
			pointY = centerPointY;
			var sPoint = [centerPointX, centerPointY];
			var ePoint = [PointX + lineWidth, pointY];
			points.addPoint(sPoint, ePoint);
		} else {
			var curve01SPoint = [centerPointX, centerPointY];
			var curve01CPoint = [PointX, centerPointY];
			var curve01EPoint = [PointX, centerPointY - roundCornerDiff];
			
			var curve02SPoint = [PointX, pointY + roundCornerDiff];
			var curve02CPoint = [PointX, pointY];
			var curve02EPoint = [PointX + lineWidth, pointY];
			
			points.addBezierPoint(curve01SPoint, curve01CPoint, curve01EPoint);
			points.addPoint(curve01EPoint, curve02SPoint);
			points.addBezierPoint(curve02SPoint, curve02CPoint, curve02EPoint);
		}
		
		points.addPoint([PointX + lineWidth, pointY], [PointX + lineWidth + lineWidth, pointY]);
		
		
		// back
		PointX = PointX + lineWidth + lineWidth + actWidth;
		var lineGap = (centerPointX + allActWidth) - (PointX + lineWidth + lineWidth + lineWidth) - (iconSize - 3);
		points.addPoint([PointX, pointY], [PointX + lineGap, pointY]);

		if (isNotCurve) {
			points.addPoint([PointX + lineGap, pointY], [PointX + lineGap + lineWidth + lineWidth, centerPointY]);
		} else {
			var curve01SPoint = [PointX + lineGap, pointY];
			var curve01CPoint = [PointX + lineGap + lineWidth, pointY];
			var curve01EPoint = [PointX + lineGap + lineWidth, pointY + roundCornerDiff];
			
			var curve02SPoint = [PointX + lineGap + lineWidth,  centerPointY - roundCornerDiff];
			var curve02CPoint = [PointX + lineGap + lineWidth, centerPointY];
			var curve02EPoint = [PointX + lineGap + lineWidth + lineWidth, centerPointY];
			
			points.addBezierPoint(curve01SPoint, curve01CPoint, curve01EPoint);
			points.addPoint(curve01EPoint, curve02SPoint);
			points.addBezierPoint(curve02SPoint, curve02CPoint, curve02EPoint);
		}
		
		points.addPoint(
				[PointX + lineGap + lineWidth + lineWidth, centerPointY],
				[PointX + lineGap + lineWidth + lineWidth + lineWidth, centerPointY]
		);
	} else {
		var iconSize = 23;
		centerPointX = pPoint[0] + (document.getElementById(parentId).offsetWidth / 2) + 1;
		centerPointY = pPoint[1] + iconSize;
		allActHeight = document.getElementById(parentId).offsetHeight;

		PointX = cPoint[0] + (document.getElementById(childId).offsetWidth / 2);
		pointY = centerPointY + lineWidth;
		actHeight = document.getElementById(childId).offsetHeight;

		roundCornerDiff = (centerPointX - PointX);

		if (roundCornerDiff < -10) roundCornerDiff = -10;
		if (roundCornerDiff > 10) roundCornerDiff = 10;
		
		isNotCurve = ((centerPointX - PointX) < CURVE_SIZE) && ((centerPointX - PointX) > - CURVE_SIZE);
		if (isNotCurve) {
			PointX = centerPointX;
			points.addPoint(
					[centerPointX, centerPointY],
					[PointX, pointY + lineWidth]
			);
		} else {
			var curve01SPoint = [centerPointX, centerPointY];
			var curve01CPoint = [centerPointX, pointY];
			var curve01EPoint = [centerPointX - roundCornerDiff, pointY];
			
			var curve02SPoint = [PointX + roundCornerDiff, pointY];
			var curve02CPoint = [PointX, pointY];
			var curve02EPoint = [PointX, pointY + lineWidth];
			
			points.addBezierPoint(curve01SPoint, curve01CPoint, curve01EPoint);
			points.addPoint(curve01EPoint, curve02SPoint);
			points.addBezierPoint(curve02SPoint, curve02CPoint, curve02EPoint);
		}
		
		points.addPoint(
				[PointX, pointY + lineWidth],
				[PointX, pointY + lineWidth + lineWidth]
		);
		points.addPoint(
				[PointX - lineWidth / 2, pointY + lineWidth],
				[PointX, pointY + lineWidth + lineWidth]
		);
		points.addPoint(
				[PointX + lineWidth / 2, pointY + lineWidth],
				[PointX, pointY + lineWidth + lineWidth]
		);

		// back
		pointY = pointY + lineWidth + lineWidth + actHeight;
		var lineGap = (centerPointY + allActHeight) - (pointY + lineWidth + lineWidth + lineWidth) - (iconSize - 3);

		points.addPoint(
				[PointX, pointY],
				[PointX, pointY + lineGap]
		);

		if (isNotCurve) {
			points.addPoint(
					[PointX, pointY + lineGap],
					[centerPointX, pointY + lineGap + lineWidth + lineWidth]
			);
		} else {
			var curve01SPoint = [PointX, pointY + lineGap];
			var curve01CPoint = [PointX, pointY + lineGap + lineWidth];
			var curve01EPoint = [PointX + roundCornerDiff, pointY + lineGap + lineWidth];
			
			var curve02SPoint = [centerPointX - roundCornerDiff, pointY + lineGap + lineWidth];
			var curve02CPoint = [centerPointX, pointY + lineGap + lineWidth];
			var curve02EPoint = [centerPointX, pointY + lineGap + lineWidth + lineWidth];
			
			points.addBezierPoint(curve01SPoint, curve01CPoint, curve01EPoint);
			points.addPoint(curve01EPoint, curve02SPoint);
			points.addBezierPoint(curve02SPoint, curve02CPoint, curve02EPoint);
		}

		points.addPoint(
				[centerPointX, pointY + lineGap + lineWidth + lineWidth],
				[centerPointX, pointY + lineGap + lineWidth + lineWidth + lineWidth]
		);
	}
	
	drawLine(paper, points.array, "#888888", 2, JsFlowchartOptions.LINE_ALL, 0);
	points = null;
}

function isAvoidBelow(sourceDiv, targetDiv, viewOption) {
	var avoidBelow = false;
	var parent = getParentByTagName;
	
	if (viewOption == "horizontal") {
		var sourceRow = parent(sourceDiv, "TR");
		var targetRow = parent(targetDiv, "TR");
		var sourceCellIndex = parent(sourceDiv, "TD").cellIndex;
		var targetCellIndex = parent(targetDiv, "TD").cellIndex;
		
		for (var cellIndex = sourceCellIndex + 1; cellIndex < targetCellIndex; cellIndex++) {
			var cell = sourceRow.cells[cellIndex];
			
			if(cell) {
				var tableTemp = cell.getElementsByTagName("DIV");
				avoidBelow = (tableTemp.length != 0);
			}
			
		}
	} else {
		var sourceRoleTable = parent(sourceDiv, "TABLE");
		var targetRoleTable = parent(targetDiv, "TABLE");
		
		var sourceCell = parent(sourceDiv, "TD");
		var targetCell = parent(targetDiv, "TD");
		var sourceRowIndex = parent(sourceDiv, "TR").rowIndex;
		var targetRowIndex = parent(targetDiv, "TR").rowIndex;
		
		for (var rowIndex = sourceRowIndex + 1; rowIndex < targetRowIndex; rowIndex++) {
			var row = sourceRoleTable.rows[rowIndex];
			
			if(row != null && row.cells[sourceCell.cellIndex]) {
				var tableTemp = row.cells[sourceCell.cellIndex].getElementsByTagName("DIV");
				avoidBelow = (tableTemp.length != 0);
			}
		}
	}
	
	return avoidBelow;
}

function getHorizontalLinePoints(sourceDiv, targetDiv) {
	var position = getAbsPosition;
	var point_s = position(sourceDiv);
	var point_t = position(targetDiv);
	var sourceX, sourceY, targetX, targetY;
	
	if (point_s[0] < point_t[0]) {
		sourceX = point_s[0] + (sourceDiv.offsetWidth);
		sourceY = point_s[1] + (sourceDiv.offsetHeight / 2);
		targetX = point_t[0];
		targetY = point_t[1] + (targetDiv.offsetHeight / 2);
	} else {
		sourceX = point_s[0] + (sourceDiv.offsetWidth / 2);
		sourceY = point_s[1];
		targetX = point_t[0] + (targetDiv.offsetWidth) / 2;
		targetY = point_t[1];
	}
	
	return {source:[sourceX, sourceY], target:[targetX, targetY]};
}

function getVirticalLinePoints(sourceDiv, targetDiv) {
	var position = getAbsPosition;
	var point_s = position(sourceDiv);
	var point_t = position(targetDiv);
	var sourceX, sourceY, targetX, targetY;
	
	if (point_s[1] < point_t[1]) {
		sourceX = point_s[0] + (sourceDiv.offsetWidth / 2);
		sourceY = point_s[1] + (sourceDiv.offsetHeight);
		targetX = point_t[0] + (targetDiv.offsetWidth / 2);
		targetY = point_t[1];
	} else {
		sourceX = point_s[0] + (sourceDiv.offsetWidth);
		sourceY = point_s[1] + (sourceDiv.offsetHeight / 2);
		targetX = point_t[0] + (targetDiv.offsetWidth);
		targetY = point_t[1] + (targetDiv.offsetHeight / 2);
	}
	
	return {source:[sourceX, sourceY], target:[targetX, targetY]};
}

function getVirticalPoints(point, sourceDiv, targetDiv) {
	var points = new PointList();
	var pointSource = point.source;
	var pointTarget = point.target;
	var sourceCell = getParentByTagName(sourceDiv, "TD");
	var targetCell = getParentByTagName(targetDiv, "TD");
	var tempPointX = 0;
	
	if (pointSource[1] < pointTarget[1]) {
		var halfOfDistanceY = (pointTarget[1] - pointSource[1]) - 15;
		
		if (isAvoidBelow(sourceDiv, targetDiv, "virtical")) {
			tempPointX = getAbsPosition(sourceCell)[0] + 5;
			
			points.addCurveLine([
      			pointSource,
    			[pointSource[0], pointSource[1] + 15],
    			[tempPointX, pointSource[1] + 15],
    			[tempPointX, pointSource[1] + halfOfDistanceY],
    			[pointTarget[0], pointSource[1] + halfOfDistanceY],
    			pointTarget
			]);
		} else {
			if (pointSource[0] < pointTarget[0] + 2 && pointSource[0] > pointTarget[0] - 2) {
				points.addPoint(pointSource, pointTarget);
			} else {
				points.addCurveLine([
      				pointSource,
    				[pointSource[0], pointSource[1] + halfOfDistanceY],
    				[pointTarget[0], pointSource[1] + halfOfDistanceY],
    				pointTarget
				]);
			}
		}
		
	} else {
		tempPointX = 0;
		
		pointSource[1] -= 5;
		
		if (pointSource[0] < pointTarget[0]) {
			tempPointX = pointTarget[0] + 15;
		} else
		{
			tempPointX = pointSource[0] + 15;
		}
		
		points.addCurveLine([
      		pointSource,
    		[tempPointX, pointSource[1]],
    		[tempPointX, pointTarget[1]],
    		pointTarget
		]);
	}

	return points;
}

function getHorizontalPoints(point, sourceDiv, targetDiv) {
	var points = new PointList();
	var pointSource = point.source;
	var pointTarget = point.target;
	var curveGap = 20;
	var tempPointY = 0;
	var position = getAbsPosition;
	
	var halfOfDistanceX = (pointTarget[0] - pointSource[0]) - curveGap;
	var sourceCell = getParentByTagName(sourceDiv, "TD");
	var targetCell = getParentByTagName(targetDiv, "TD");
	
	if (pointSource[0] < pointTarget[0]) {
		if (isAvoidBelow(sourceDiv, targetDiv, "horizontal")) {
			if(pointSource[1] > pointTarget[1] - 5) {
				tempPointY = position(sourceCell)[1] + 5;
			} else {
				tempPointY = position(sourceCell)[1] + sourceCell.offsetHeight - 5;
			}
			
			points.addCurveLine([
      			pointSource,
    			[pointSource[0] + curveGap, pointSource[1]],
    			[pointSource[0] + curveGap, tempPointY],
    			[pointTarget[0] - curveGap,  tempPointY],
    			[pointTarget[0] - curveGap,  pointTarget[1]],
    			pointTarget
			]);
		} else {
			if (pointSource[1] < pointTarget[1] + 2 && pointSource[1] > pointTarget[1] - 2) {
				points.addPoint(pointSource, pointTarget);
			} else {
				points.addCurveLine([
      				pointSource,
    				[pointSource[0] + halfOfDistanceX, pointSource[1]],
    				[pointSource[0] + halfOfDistanceX, pointTarget[1]],
    				pointTarget
				]);
			}

		}
		
	} else {
		pointSource[0] -= 5;
		
		if (pointSource[1] > pointTarget[1]) {
			tempPointY = pointTarget[1] - curveGap;
		} else {
			tempPointY = pointSource[1] - curveGap;
		}
		
		points.addCurveLine([
      		pointSource,
    		[pointSource[0], tempPointY],
    		[pointTarget[0], tempPointY],
    		pointTarget
		]);
	}
	
	return points;
	
}

function drawSwimlaneLine(paper, sourceDivName, targetDivName, viewOption, actStatus, roleNumber) {
	var sourceDiv = document.getElementById(sourceDivName);
	var targetDiv = document.getElementById(targetDivName);
	var arrowSize = CURVE_SIZE / 2;
	
	if (sourceDiv && targetDiv) {
		var points = null;
		
		if (viewOption == "horizontal") {
			points = getHorizontalPoints(getHorizontalLinePoints(sourceDiv, targetDiv), sourceDiv, targetDiv);
		} else {
			points = getVirticalPoints(getVirticalLinePoints(sourceDiv, targetDiv), sourceDiv, targetDiv);
		}
		
		var attr = getLineAttribute(actStatus);
		drawLine(paper, points.array, attr.color, attr.strokWidth, JsFlowchartOptions.LINE_SWIMLANE, viewOption);
		
		sourceDiv = null;
		targetDiv = null;
		arrowSize = null;
		points = null;
		attr = null;
	}
}

function getLineAttribute(status)
{
	var attr = null;
	
	switch (status) {
	case ("Completed"):
	case ("Timeout"):
	case ("Running"):
		attr = {strokWidth : 2, color : "#4488ff"};
		break;
	default:
		attr = {strokWidth : 1, color : "#aaaaaa"};
		break;
	}
	
	return attr;
}


function drawBackLine(paper, isVertical, namespace, startId, backId) {
	var points = new PointList();
	
	var elStart = document.getElementById(startId);
	var elBack = document.getElementById(backId);

	if (!elStart || !elBack) {
		return;
	}

	var sPoint = getAbsPosition(elStart);
	var bPoint = getAbsPosition(elBack);

	if (isVertical == "true") {
		var startX = sPoint[0] + (elStart.offsetWidth / 2);
		var startY = sPoint[1] + (elStart.offsetHeight / 2);
		var backX = bPoint[0] + (elBack.offsetWidth / 2);
		var backY = bPoint[1] + (elBack.offsetHeight / 2);
	} else {
		var startX = sPoint[0] + (elStart.offsetWidth / 2);
		var startY = sPoint[1];
		var backX = bPoint[0] + (elBack.offsetWidth / 2);
		var backY = bPoint[1];
	}

	var lineX = startX + 25;
	var lineY = startY - 20;

	if (isVertical == "true") {
		var sPoint = [startX, startY];
		var point1 = [lineX, startY];
		var point2 = [lineX, backY];
		var ePoint = [backX, backY];
		
		points.addPoint(sPoint, point1);
		points.addPoint(point1, point2);
		points.addPoint(point2, ePoint);
		
		points.addPoint(ePoint, [startX + 8, startY - 3]);
		points.addPoint(ePoint, [startX + 8, startY + 3]);
	} else {
		var sPoint = [startX, startY];
		var point1 = [startX, lineY];
		var point2 = [backX, lineY];
		var ePoint = [backX, backY];
		
		points.addPoint(sPoint, point1);
		points.addPoint(point1, point2);
		points.addPoint(point2, ePoint);
		
		points.addPoint(ePoint, [startX - 8, startY - 3]);
		points.addPoint(ePoint, [startX - 8, startY + 3]);
	}

	drawLine(paper, points.array, "#888888", 1, JsFlowchartOptions.LINE_BACK, "");
}

function setRowHeight(e, viewOption) {
	if("horizontal" == viewOption) { return; }
	
	var table = e;
	var rowIndex;
	var tableCount = 0;
	
	while (table != null && "tableUserRole" != table.id) {
		table = table.parentNode;
		
		if ("TR" == table.tagName) {
			rowIndex = table.rowIndex;
		}
	}
	
	var idCount = 0;
	table.rows[rowIndex].cells[0].style.height = "";
	var height = table.rows[rowIndex].cells[0].offsetHeight;
	
	if (height > 80) {
		height += 20;
	}
	
	var tables = getParentByTagName(table, "TR").getElementsByTagName("table");
	for (var tableCount = 0; tableCount < tables.length; tableCount++) {
		var t = tables[tableCount];
		
		if("tableUserRole" == t.id) {
			var r = t.rows[rowIndex];
			
			if (r) {
				r.cells[0].style.height = height;
			}
		}
	}
}

function drawLoopCountForSwimlane(paper, divId01, divId02, viewOption, title, count) {
	var div01 = document.getElementById(divId01);
	var div02 = document.getElementById(divId02);
	var point_s = getAbsPosition(div01);
	var point_t = getAbsPosition(div02);
	var X, Y;
	
	if (viewOption == "horizontal") {
		point_s[0] += (div01.offsetWidth / 2);
		point_t[0] += (div02.offsetWidth / 2);
		
		X = point_s[0] - ((point_s[0] - point_t[0]) / 2);
		
		if (point_s[1] < point_t[1]) {
			Y = point_s[1];
		} else {
			Y = point_t[1];
		}
		
		Y -= 10;
	} else {
		point_s[0] += div01.offsetWidth;
		point_t[0] += div02.offsetWidth;
		point_s[1] += (div01.offsetHeight / 2);
		point_t[1] += (div02.offsetHeight / 2);
		
		Y = point_s[1] - ((point_s[1] - point_t[1]) / 2);
		
		if (point_s[0] > point_t[0]) {
			X = point_s[0];
		} else {
			X = point_t[0];
		}
		
		X += 30;
	}
	
	var attr = null;
	
	if (count == 0) {
		attr = getLineAttribute("Ready");
	} else {
		attr = getLineAttribute("Completed");
		title = title + " (" + count + ")";
		X += 20;
	}
	
	paper.text(X, Y, title).attr({
		"stroke" : attr.color,
		"font-size" : "14px",
		"stroke-width" : attr.strokWidth
	});
}
