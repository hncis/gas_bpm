function initMenus(){
	$(document).ready(function() {
		$('.lnb li ul').hide();
		$('.lnb>li:last').addClass("last");
		$('.lnb li ul').slideDown('normal');
		
		/*$('.lnb li ul li a').mouseover(
			function() {
				var checkEl = $(this).parent().parent().prev();
				/var checkParent = $('.lnb li a');/
				if((checkEl.is('a'))) {
					/checkParent.removeClass("on");/
					checkEl.addClass("on");
					return false;
				}
			 }
		 );*/
		
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
		
		/*$('.lnb').mouseout(
			function() {
				$('.lnb li a').removeClass("on");
			 }
		 );*/
		
		$('.lnb li>a').click(
			function() {
				var checkElement = $(this).next();
				
				if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
					checkElement.slideUp('normal');
					$(this).removeClass("this_page");
					/*$(this).removeClass("menu_off");*/
					return false;
				}
				if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
					checkElement.slideDown('normal');
					$(this).addClass("this_page");
					/*$(this).addClass("menu_off");*/
					return false;
				}
				/*if(!checkElement.is('ul')){
					$('.lnb li>a').removeClass("this_page");
					$('.lnb li ul li a').removeClass("this_on");
					$(this).addClass("this_page");
				}*/
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
	});
}

function initMainMenus(heightVal){
	initMenus();
}

function initAfterMenus(){
}