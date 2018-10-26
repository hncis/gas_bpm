function initMenus(heightVal){
	/*if(typeof(heightVal) == "undefined")
		heightVal = parseInt($(this).height());
	var tHeight=parseInt($(this).height());
	var tHeight = heightVal;
	
	var lastEvent = null;*/
	setLeftMenu();
	
}
function initMainMenus(heightVal){
	/*var tHeight = heightVal;
	var sHeight=parseInt($(this).height());
	if(sHeight < tHeight){
		$("#GASC").css("height",tHeight+"px");
	}
	
    $("#main_container").css("height",tHeight+"px");
    $("#navi").css("height",tHeight+"px");
    $("#main_timeDifference").css("height",tHeight+"px");*/
    
	setLeftMenu();
	
	
}
function initAfterMenus(){
	/*	var tHeight = document.getElementById("container").offsetHeight;
	var sHeight = parseInt($(this).height());
	if(sHeight > tHeight){
		tHeight = sHeight;
	}
	$("#GASC").css("height",tHeight+"px");
    $("#container_wrap").css("height",tHeight+"px");
    $("#navi").css("height",tHeight+"px");*/
	
}

function setLeftMenu() {
	$('#navi li ul').hide();
 
	$('#navi ul li a').click(
		function() {
			var checkElement = $(this).next();
			var parent = this.parentNode.parentNode.id;
			
			if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
				$('#' + parent + ' ul:visible').slideUp('fast');
					var strImg = $(this).parent().find('img');
					for (i = 0;i < strImg.length;i++){
						var temp = strImg.eq(i).attr("src");
						var length = temp.length;
						var file_name = temp.substring(0, length-4).replace("_on","");
						var file_type = temp.substring(length-6).substring(3);
						strImg.eq(i).attr("src", file_name + "." + file_type);
					 }
				if($('#' + parent).hasClass('collapsible')) {
					
				}
				return false;
			}
			if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
				var strImg = $('#navi ul li a').parent().find('img');
				var temp = $(this).children("img").attr("src");
				var length = temp.length;
				var file_name = temp.substring(0, length-4);
				var file_name2 = temp.substring(0, length-6);
				var file_type = temp.substring(length-6).substring(3);
				for (i = 1;i < strImg.length+1;i++){
				  strImg.eq(i-1).attr("src", file_name2+"0"+i+"." + file_type);
				}
				$(this).children("img").attr("src", file_name + "_on." + file_type);
					
				$('#' + parent + ' ul:visible').slideUp('fast');
				checkElement.slideDown('fast');
				return false;
			}
		 }
	  );
	
	
	$('.lnb li ul').hide();
	$('.lnb>li:last').addClass("last");
	
	$('.lnb li ul li a').mouseover(
		function() {
			var checkEl = $(this).parent().parent().prev();
			var checkParent = $('.lnb li a');
			if((checkEl.is('a'))) {
				checkParent.removeClass("on");
				checkEl.addClass("on");
				return false;
			}
		 }
	 );
	
	/*$('.lnb li').mouseover(
		function() {
			var checkEl = $(this).children('a');
			var checkChild = $('.lnb li a');
			if((checkEl.is('a'))) {
				checkChild.removeClass("on");
				checkEl.addClass("on");
				return false;
			}
		 }
	 );*/
	
	$('.lnb').mouseout(
		function() {
			$('.lnb li a').removeClass("on");
		 }
	 );
	
	$('.lnb li>a').click(
		function() {
			var checkElement = $(this).next();
			
			if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
				checkElement.slideUp('normal');
				$(this).removeClass("this_page");
				$(this).removeClass("menu_off");
				return false;
			}
			if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
				checkElement.slideDown('normal');
				$(this).removeClass("this_page");
				$(this).addClass("menu_off");
				return false;
			}
			if(!checkElement.is('ul')){
				$('.lnb li>a').removeClass("this_page");
				$('.lnb li ul li a').removeClass("this_on");
				$(this).addClass("this_page");
			}
		 }
	 );
	
	$('.lnb li ul li a').click(
		function() {
			var parentEl = $(this).parent().parent().prev();
			$('.lnb li>a').removeClass("this_page");
			$('.lnb li ul li a').removeClass("this_on");
			$(this).addClass("this_on");
			parentEl.addClass("this_page");
		 }
	 );
    }