function fnSetDocumentReady(){
	
}

function doSave(){
	
	if($("#comment_txt").val() == ""){
		alertUI("Please enter Comment.");
		return;
	}
	
	var keyData = {
		eeno : sess_empno,
		txt  : changeToUni($("#comment_txt").val())
	};
	
	var params = {
		paramJson : util.jsonToString(keyData)
	};
	
	doCommonAjax("/doInsertEventToReply.do", params, "doSaveCallBack(jsonData.sendResult);");
}

function doSaveCallBack(result){
	alertUI("Obrigado por Participar do Nosso Evento de Abertura do GASC !!!");
	$("#comment_txt").empty();
	
	var form = $("<form/>");
    form.attr("method" , "post");
    form.attr("id"     , "submitForm").attr("name", "submitForm");
    form.attr("action" , ctxPath+"/main.gas");
    var token = $("<input type='hidden' id='hid_csrfToken' name='hid_csrfToken'/>").val($("#csrfToken").val());
    form.append(token);
    $("body").append(form);
    form.submit();
    form.remove();
}