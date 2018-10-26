function fnSetDocumentReady(){
	doSearch();
}

function doSearch(){
	doCommonAjax("/getEventQuizList.do", "", "doSearchCallBack(jsonData.sendResult);");
}

function doSearchCallBack(result){
	var qIdx = 1;
	var html = "";
	
	$.each(result.QUIZ, function(idx, opt){
		if(idx > 0 && idx % 3 == 0){
			html += "	</ul>";
			html += "</li>";
		}
		if(idx % 3 == 0){
			html += "<li>";
			html += "	<input type=\"hidden\" name=\"question_id\" value=\""+opt.q_seq+"\">";
			html += "	<span class=\"quiz_num\">Q"+(qIdx++)+".</span><span class=\"quiz_txt\">"+opt.quiz_txt+"</span>";
			html += "	<ul>";
		}
		
		html += "    		<li><label><input type=\"radio\" name=\"quiz"+opt.q_seq+"\" value=\""+opt.a_seq+"\"/> <span>"+opt.answer_txt+"</span></label></li>";
	});
	
	html += "	</ul>";
	html += "</li>";
	
	$("#quiz_area").html(html);
}

function doSave(){
	var qSeqArr = $("input[name=question_id]");
	
	var iParams = [];
	var data;
	
	for(var i=0; i<qSeqArr.length; i++){
		var aSeqArr = $("input[name=quiz"+$(qSeqArr[i]).val()+"]");
		for(var j=0; j<aSeqArr.length; j++){
			if($(aSeqArr[j]).is(":checked")){
				data = {
					eeno       : sess_empno,
					q_seq      : $(qSeqArr[i]).val(),
					answer_num : $(aSeqArr[j]).val()
				};
				
				iParams.push(data);
			}
		}
	}
	
	if(iParams.length < 2){
		alertUI("Please answer all question.");
		return;
	}
	
	var params = {
		iParams : util.jsonToList(iParams)
	};
	
	doCommonAjax("/doInsertEventToQuizAnswer.do", params, "doSaveCallBack(jsonData.sendResult);");
}

function doSaveCallBack(result){
	alertUI("Obrigado por Participar do Nosso Evento de Abertura do GASC !!!");
	//doSearch();
	
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