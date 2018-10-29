var isIE = (navigator.userAgent.indexOf('MSIE') > 0);


/**
 *
 */
function StringBuffer(){
    var buffer = [];
    
    this.append = function(string){
        buffer.push(string);
        return this;
    };
    
    this.toString = function(){
        return buffer.join("");
    }
};

String.prototype.isTrue = function(){
    var isTrue = false;
    var tempString = this.trim();
    
    isTrue = (tempString.length != 0 &&
    tempString != "-1" &&
    tempString != "0" &&
    tempString != "false" &&
    tempString != "no" &&
    tempString != "null" &&
    tempString != "undefined");
    
    return isTrue;
}

String.prototype.replaceAll = function(str1, str2){
    var temp_str = this.trim();
    temp_str = temp_str.replace(new RegExp(str1, "gi"), str2);
    
    return temp_str;
}

String.prototype.trim = function(){
    return this.replace(/(^\s*)|(\s*$)/gi, "");
}

String.prototype.encodeHtml = function(){
    var str = this;
    if (str.isTrue()) {
        str = str.replace(/&/gim, "&amp;").replace(/</gim, "&lt;").replace(/>/gim, "&gt;").replace(/\"/gim, "&quot;");
    }
    return str;
}

String.prototype.decodeHtml = function(){
    var str = this;
    if (str.isTrue()) {
        str = str.replace(/&quot;/gim, "\"");
        str = str.replace(/&gt;/gim, ">");
        str = str.replace(/&lt;/gim, "<");
        str = str.replace(/&amp;/gim, "&");
    }
    return str;
}

String.prototype.toHtml = function(){
    var str = this;
    if (str.isTrue()) {
        str = str.replace(/ /gim, "&nbsp;");
        str = str.replace(/\t/gim, " &nbsp; &nbsp;");
        str = str.replace(/\n/gim, "<br />");
    }
    return str;
}

String.prototype.encodeURI = function(){
    return encodeURIComponent(this);
}
String.prototype.decodeURI = function(){
    return decodeURIComponent(this);
}
String.prototype.unescape = function(){
    return unescape(this);
}
String.prototype.escape = function(){
    return escape(this);
}

/* *********************************** Default Element *********************************** */
function getElement(elementId){
    var element = null;
    
    if (elementId.id) {
        element = elementId;
    }
    else {
        element = document.getElementById(elementId);
        if (element == null) {
            element = document.getElementsByName(elementId)[0];
        }
        
    }
    
    return element;
}

function copyAttributes(originalObj, targetObj){
    var oAttr = originalObj.attributes;
    
    for (var i = 0; i < oAttr.length; i++) {
        if (oAttr[i].specified) {
            if (oAttr[i].nodeName == "class") {
                targetObj.className = oAttr[i].nodeValue;
            }
            else 
                if (oAttr[i].nodeName == "style") {
                    targetObj.style.cssText = oAttr[i].nodeValue;
                }
                else {
                    targetObj.setAttribute(oAttr[i].nodeName, oAttr[i].nodeValue);
                }
        }
    }
    
    return targetObj;
}

/* *********************************** Layer Element *********************************** */
function appendTextNode(parentNode, stringValue){
    var codeTextNode = document.createTextNode(stringValue);
    parentNode.appendChild(codeTextNode);
    
    return codeTextNode;
}

function appendTextNodeWithAlign(parentNode, stringValue, align){
    var codeTextNode = appendTextNode(parentNode, stringValue);
    parentNode.style.textAlign = align;
    
    return codeTextNode;
}

function getAbsPosition(e){
    var l = t = 0;
    
    do {
        l += e.offsetLeft;
        t += e.offsetTop;
    }
    while (e = e.offsetParent)
    
    if (l < 0) {
        l = 0;
    }
    if (t < 0) {
        t = 0;
    }
    
    var p = [l, t];
    p.x = l;
    p.y = t;
	
	return p;
}

function showElement(elementId){
    var element = getElement(elementId);
    
    if (element) {
        element.style.display = "";
    }
    
    return element;
}

function hideElement(elementId){
    var element = getElement(elementId);
    
    if (element) {
        element.style.display = "none";
    }
    
    return element;
}

function setPositionForMouse(elementId, relPositionX, relPositionY, evt){
    var element = getElement(elementId);
    
    var clientX = 0;
    var clientY = 0;
    
    if (isIE) { // Explorer
        evt = window.event;
    }
    
    element.style.left = (evt.clientX + relPositionX + document.body.scrollLeft) + "px";
    element.style.top = (evt.clientY + relPositionY + document.body.scrollTop) + "px";
}

/* *********************************** Table Element *********************************** */
function appendCell(row){
    var cell = null;
    
    if (isIE) {
        cell = row.insertCell();
    }
    else {
        cell = document.createElement("td");
        row.appendChild(cell);
    }
    
    return cell;
}

function appendCellWithIndex(parent, index){
    var cell = null;
    
    if (isIE) {
        cell = parent.insertCell(index);
    }
    else {
        try {
            cell = document.createElement("td");
            parent.insertBefore(cell, parent.cells[index]);
        } 
        catch (e) {
            alert(e);
        }
    }
    
    return cell;
}

function getCrossClassName(rowCount, className01, className02){
    var rowClass = null;
    
    if (rowCount % 2 != 0) {
        rowClass = className01;
    }
    else {
        rowClass = className02;
    }
    
    return rowClass;
}

function appendRow(parent){
    var row = null;
    
    if (isIE) {
        row = parent.insertRow();
    }
    else {
        row = document.createElement("tr");
        parent.appendChild(row);
    }
    
    return row;
}

function appendRowWithIndex(parent, index){
    var row = null;
    
    if (isIE) {
        row = parent.insertRow(index);
    }
    else {
        row = document.createElement("tr");
        parent.insertBefore(row, parent.rows[index]);
    }
    
    return row;
}

function formatTable(table){
    if (table.rows) {
        while (0 < table.rows.length) {
            table.deleteRow(0);
        }
    }
    
    return table;
}

function getCellCount(table){
    var iCount = 0;
    
    for (var i = 0; i < table.rows.length; i++) {
        var row = table.rows[i];
        iCount += row.cells.length;
    }
    return iCount;
}

/* *********************************** Find Element *********************************** */
function getParentByTagName(element, tagName){
    element = element.parentNode;
    tagName = tagName.toUpperCase();
    
    while (element && element.tagName != tagName) {
        element = element.parentNode;
    }
    
    return element;
}

function getParentByName(element, elementName){
    while (element != null && element.name != elementName) {
        element = element.parentNode;
    }
    
    return element;
}

/* *********************************** Event Control *********************************** */
function removeEvent(element, strEventName, objFunction){
    strEventName = strEventName.replace("on", "");
    
    if (element.detachEvent) {
        element.detachEvent("on" + strEventName, objFunction);
    }
    else {
        element.removeEventListener(strEventName, objFunction, false);
    }
}

function addEvent(element, strEventName, objFunction){
    strEventName = strEventName.replace("on", "");
    
    if (element.attachEvent) {
        element.attachEvent("on" + strEventName, objFunction);
    }
    else {
        element.addEventListener(strEventName, objFunction, false);
    }
}

function addEventWithFunctionString(element, strEventName, functionString){
    strEventName = strEventName.replace("on", "");
    
    if (element.attachEvent) {
        element.attachEvent("on" + strEventName, function(event){
            eval(functionString);
        });
    }
    else {
        element.addEventListener(strEventName, function(event){
            eval(functionString);
        }, false);
    }
}

/* *********************************** Form Element *********************************** */
function insertOptionToSelect(parent, text, value){
    var objOption = document.createElement("option");
    objOption.text = text;
    objOption.value = value;
    
    if (isIE) {
        parent.add(objOption);
    }
    else {
        parent.appendChild(objOption);
    }
}

/* *********************************** Load Script *********************************** */
function insertJavascript(srcWindow, srcJavascript){
    var nowDate = new Date(); // 캐쉬문제로 인한 변수 호출
    var headID = srcWindow.document.getElementsByTagName("head")[0]; // 해더 사이에 위치 지정
    var jsNode = srcWindow.document.createElement('script');
    
    nowDate = nowDate.getTime();
    jsNode.type = 'text/javascript';
    jsNode.src = srcJavascript + '?' + nowDate;
    headID.appendChild(jsNode);
}

function insertCss(srcWindow, srcCss){
    var nowDate = new Date(); // 캐쉬문제로 인한 변수 호출
    var headID = srcWindow.document.getElementsByTagName("head")[0];
    var cssNode = srcWindow.document.createElement('link');
    
    nowDate = nowDate.getTime();
    cssNode.type = 'text/css';
    cssNode.rel = 'stylesheet';
    cssNode.href = srcCss + '?' + nowDate;
    headID.appendChild(cssNode);
}

var Js1005 = {
    hide: function(el){
        var s = el.style;
        s.display = "none"
        
        if (isIE) {
            s.visibility = "hidden";
        }
        else {
            s.visibility = "hide";
        }
    },
    show: function(el){
        var s = el.style;
        s.display = "inline"
        
        if (isIE) {
            s.visibility = "visible";
        }
        else {
            s.visibility = "show";
        }
    },
    viewAttr: function(el){
        var attrs = el.attributes, sb = new StringBuffer();
        ;
        for (var i = attrs.length - 1, attr = null; attr = attrs[i]; i--) {
            sb.append("<br>name: " + attr.name +
            "<br>value: " +
            attr.value)
        }
        
        jDialog = $(document.createElement("div")).html(sb.toString()).dialog();
    },
    Test: {
        viewObj: function(obj){
            var sb = new StringBuffer();
            ;
            for (var x in obj) {
                sb.append("<B>" + x + ":</B> " + obj[x] + "<br />");
            }
            var div = document.createElement("div");
            div.title = (obj.title) ? obj.title : obj.name;
            jDialog = $(div).html(sb.toString()).dialog();
        }
    }
};

function getouterHtml(obj) {
	var html = null;
	if (obj == null) return null; 
 
	if (typeof(obj.outerHTML) == "string"){
		html =  obj.outerHTML;
	} else {
		html = (new XMLSerializer).serializeToString(obj); 
	}
	return html;
}

function setOuterHtml(obj, html) {
	if (obj == null) return;
 
	if (typeof(obj.outerHTML) == "string") {
		obj.outerHTML = html
	} else {
		var el = document.createElement('div');
		el.innerHTML = html;
		var range = document.createRange();
		range.selectNodeContents(el);
		var documentFragment = range.extractContents();
		obj.parentNode.insertBefore(documentFragment, obj);
		obj.parentNode.removeChild(obj);
	}
}