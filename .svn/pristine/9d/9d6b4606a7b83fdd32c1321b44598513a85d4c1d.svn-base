/**
 * 페이징 디자인
 * @param result
 */
function pageNavigation(result, id){
    if(typeof(id) == "undefined"){
        navigationHtml(result);
    }else{
        navigationHtml(result, id);
    }
}

function navigationHtml(result, id){
    var npage = "";
    var nJob  = "search";
    if(typeof(id) != "undefined"){
        nJob = id;
    }

    npage += "<li> \n";
    npage += "  <a href=\"javascript:retrieve('"+nJob+"', '1')\"> \n";
    npage += "  <span title='처음'>《</span></a> \n";
    npage += "  <a href=\"javascript:prePage('"+nJob+"', '"+(parseInt(curPage,10)-1)+"')\"> \n";
    npage += "  <span title='이전'>〈</span></a> \n";
    npage += "</li> \n";
    for(var i = result.blockStart; i <= result.blockEnd ;i++){
        if(curPage == i){
            npage +="<li><a href=javascript:retrieve('"+nJob+"','"+i+"') class=\"selected\"><span>"+i+"</span></a></li> \n";
        }else{
            npage +="<li><a href=javascript:retrieve('"+nJob+"','"+i+"')><span>"+i+"</span></a></li> \n";
        }
    }

    npage += "<li> \n";
    npage += "  <a href=\"javascript:nextPage('"+nJob+"', '"+(parseInt(curPage,10)+1)+"', '"+result.numOfPage+"')\"> \n";
    npage += "  <span title='다음'>〉</span></a> \n";
    npage += "  <a href=\"javascript:retrieve('"+nJob+"', '"+result.numOfPage+"')\"> \n";
    npage += "  <span title='마지막'>》</span></a> \n";
    npage += "</li> \n";

    if(typeof(id) == "undefined"){
        $("#navigation").html(npage);
    }else{
        $("#"+id).html(npage);
    }
}

/**
 * 이전버튼 클릭
 * @param job
 * @param curSeq
 */
function prePage(job, curSeq){
    if(curSeq == "0"){
        alertUI("페이지의 맨처음 입니다.");
        return;
    }
    retrieve(job, curSeq);
}

/**
 * 다음버튼 클릭
 * @param job
 * @param curSeq
 * @param lastSeq
 */
function nextPage(job, curSeq, lastSeq){
    if(parseInt(curSeq,10) > parseInt(lastSeq,10)){
        alertUI("페이지의 맨마지막 입니다.");
        return;
    }
    retrieve(job, curSeq);
}