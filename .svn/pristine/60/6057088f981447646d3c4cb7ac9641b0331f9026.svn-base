// gnb, lnb
function lnbControl(num1, num2, num3){
	var obj = $(".headSec, .leftSec");
	var gnbDep = $("#gnbSec > li");
	var lnbDep ="";
	
	if(num2 == 1){
		lnbDep = $("#lnbSec1 ul > li a");
	}else if(num2 == 2){
		lnbDep = $("#lnbSec2 ul > li a");
	}else if(num2 == 3){
		lnbDep = $("#lnbSec3 ul > li a");
	}else if(num2 == 4){
		lnbDep = $("#lnbSec4 ul > li a");
	}else if(num2 == 5){
		lnbDep = $("#lnbSec5 ul > li a");
	}else{
		lnbDep = $("#lnbSec1 ul > li a");
	}

	$(gnbDep).hover(function(){
		$(gnbDep).removeClass("on");
		//$(this).find("a").removeClass("on");
		$(this).addClass("hover");
	}, function(){
		$(this).removeClass("hover");
	});

	$(lnbDep).hover(function(){
		$(lnbDep).removeClass("on");
	}, function(){
	});

	var view = function(num1, num2, num3){
		if(num1 > 0 && num1 != 4){
			$(gnbDep).eq(num1 - 1).addClass("on");
		}
		if(num2 > 0){
			$(gnbDep).eq(num1 - 1).find("div a").eq(num2 - 1).addClass("on");
			if(num3 > 0){
				$(lnbDep).eq(num3 - 1).addClass("on");
			}
		}
	};

	var reView = function(){
		view(num1, num2, num3);
	};

	$(obj).hover(function(){
		clearInterval($(obj).attr("timer"));
	}, function(){
		$(obj).attr("timer", setInterval(reView, 1000));
	});
	view(num1, num2, num3);
}

// layer popup
function dimLayerView(type, url){
	var obj = "";
	if(url == "#printPopupSec1" || url == "#printPopupSec2"){
		obj = $(url);
	} else {
		obj = $("#popupSec");
	}
	if(type == "show"){
		var top = (($("body").height() - $(obj).height()) / 2 < 0)?0:($("body").height() - $(obj).height()) / 2;
		$(obj).css("top", top + $(document).scrollTop());
		$("#dimLayer").height($("body").height() + $(document).scrollTop());
		if(url != "#printPopupSec1" || url != "#printPopupSec2"){
			$("iframe[name='popup']").attr("src", url);
		}
		$(obj).show();
		$("#dimLayer").show();
	} else if(type == "hide"){
		if(url == 'popup'){
			$("#popupSec, #dimLayer", parent.document).hide();
		} else {
			$("#popupSec").hide();
			if(url != null){
				var obj = $(url).parent().parent();
				$(obj).hide();
			}
			$("#dimLayer").hide();
		}
	}
}

// tab Control
function tabControl(obj){
	var view = function(num){
		$("." + obj).hide();
		$("." + obj).eq(num).show();

		$("#" + obj).find("li").removeClass("on");
		$("#" + obj).find("li").eq(num).addClass("on");
	};
	view(0);
	$("#" + obj).find("li").each(function(i){
		$(this).click(function(){
			view(i);
		});
	});
}

// popup
function popupOpen(url, w, h, s){
	window.open(url, "", "width=" + w + ", height=" + h +", scrollbars=" + s);
}

$(function(){
	$(".datepicker").datepicker();
	$(".hoverOn td").hover(function(){
		$(this).parent().addClass("hover");
	}, function(){
		$(this).parent().removeClass("hover");
	});
	$(".oddSec tr:even").addClass("even");

	$(".hoverImg").hover(function(){
		$(this).attr("src", $(this).attr("src").replace("_off.", "_on."));
	}, function(){
		$(this).attr("src", $(this).attr("src").replace("_on.", "_off."));
	});

});
	

jQuery(document).ready(function(){ 	
	var site_location = location.href;
	$(".lnbSec").hide();
	if (site_location.indexOf("xuf")!=-1) {
		$(".xuf").show();
	} else if(site_location.indexOf("xpc")!=-1) {
		$(".xpc").show();
	} else if(site_location.indexOf("xts")!=-1) {
		$(".xts").show();
	} else if(site_location.indexOf("xbs")!=-1) {
		$(".xbs").show();
	}else if(site_location.indexOf("xst")!=-1) {
		$(".xst").show();
	}
});
$(function(){

	var gnbNm=$("#gnbNm").val();
	var lnbNm=$("#lnbNm").val();
	var pgNm=$("#pgNm").val();
	if(gnbNm!=undefined && gnbNm!=''){
		gnbNm=xssFilter(gnbNm);
	}
	if(lnbNm!=undefined && lnbNm!=''){
		lnbNm=xssFilter(lnbNm);
	}
	if(pgNm!=undefined && pgNm!=''){
		pgNm=xssFilter(pgNm);
	}
	lnbControl(gnbNm, lnbNm, pgNm);
});    

function xssFilter ( content ) {	 
    return content.replace(/</g, "&lt;").replace(/>/g, "&gt;");
};

