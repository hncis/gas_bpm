<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String ctxPath = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" media="screen" href="<%=ctxPath%>/script/css/jquery-ui-1.8.18.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=ctxPath%>/script/css/ui.jqgrid.css" />
<link rel='stylesheet' type="text/css" media="screen" href="<%=ctxPath%>/script/css/hncis_common.css" />
<%-- <link rel='stylesheet' type="text/css" media="screen" href="<%=ctxPath%>/script/css/global_sub.css" rel="stylesheet" /> --%>
<script type="text/javascript" src="<%=ctxPath%>/script/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/script/js/i18n/grid.locale-en.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/script/js/jquery.jqGrid.min.js"></script> 
<script type="text/javascript" src="<%=ctxPath%>/script/js/jquery-groupTable.js"></script> 
<link rel="stylesheet" type="text/css" media="screen" href="<%=ctxPath%>/script/css/jquery.ui.datepicker.css" />
<script type="text/javascript" src="<%=ctxPath%>/script/js/jquery.ui.datepicker.js"></script>
<%-- <script type="text/javascript" src="<%=ctxPath%>/script/js/jquery-menu.js"></script> --%>
<script type="text/javascript" src="<%=ctxPath%>/script/js/gas-menu.js"></script>
<script type="text/javascript" src='<%=ctxPath%>/script/js/jquery.numeric.min.js'></script>
<script type="text/javascript" src='<%=ctxPath%>/script/js/jsonUtil.js'></script>
<script type="text/javascript" src='<%=ctxPath%>/script/js/common.js'></script>
<script type="text/javascript" src='<%=ctxPath%>/script/js/jquery.blockUI.js'></script>

<script type="text/javascript" src="<%=ctxPath%>/script/js/jquery.poshytip.js"></script> 
<title>Insert title here</title>
<script>

function systemTest(){
 	//var keyData = {corp_cd: "demo"};
	paramData = {
		paramJson      	: '{corp_cd: "demo"}'
	};
	/*	doCommonAjax("doSystemTest.do", paramData, "testCallBack(jsonData.sendResult);");
 */

	url = "/hncis/system/doSystemTest.do";

	$.ajax( {
		type :'POST'
		,asyn :true
		,url : url
		,data:paramData
		,dataType :"json"
		,beforeSend : function(xhr){
		}
		,success : function(jsonData) {
			testCallBack();
		}
		,error : function(xhr, textStatus) {
			fnEndLoading();
			setBottomMsg(textStatus, true);
		}
		,complete : function(xhr, textStatus) {
		}
	}); 
}
function testCallBack(data){
	//alert("테스트 완료");	
}
function fnSetDocumentReady(){
	systemTest();
}
</script>
</head>
<body onload="javascript:systemTest();">

</body>
</html>