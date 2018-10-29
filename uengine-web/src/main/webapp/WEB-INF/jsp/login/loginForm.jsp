<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>

<!-- Bootstrap Butterfly-->
<link href="<c:url value='/resources/libs/bootstrap-3.3.2-dist/theme/Butterfly/css/bootstrap.css'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/resources/libs/bootstrap-3.3.2-dist/theme/Butterfly/css/style.css'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/resources/libs/bootstrap-3.3.2-dist/theme/Butterfly/css/linecons.css'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/resources/libs/bootstrap-3.3.2-dist/theme/Butterfly/css/font-awesome.css'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/resources/libs/bootstrap-3.3.2-dist/theme/Butterfly/css/responsive.css'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/resources/libs/bootstrap-3.3.2-dist/theme/Butterfly/css/animate.css'/>" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<c:url value='/resources/libs/bootstrap-3.3.2-dist/theme/Butterfly/js/jquery.1.8.3.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/bootstrap-3.3.2-dist/theme/Butterfly/js/bootstrap.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/bootstrap-3.3.2-dist/theme/Butterfly/js/jquery-scrolltofixed.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/bootstrap-3.3.2-dist/theme/Butterfly/js/jquery.easing.1.3.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/bootstrap-3.3.2-dist/theme/Butterfly/js/jquery.isotope.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/bootstrap-3.3.2-dist/theme/Butterfly/js/wow.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/bootstrap-3.3.2-dist/theme/Butterfly/js/classie.js'/>"></script>

<!--[if lt IE 9]>
    <script src="js/respond-1.1.0.min.js"></script>
    <script src="js/html5shiv.js"></script>
    <script src="js/html5element.js"></script>
<![endif]-->
<script>
	$(document).ready(function(e) {
	
		$("#frm").on("submit", function(e) {
			e.preventDefault();
			fn_login();
		});
	});

	fn_login = function(){
		$.ajax({
			type : "POST",
			url : "<c:url value='/login/loginProc.do' />",
			cache : false,
			data : $("#frm").serialize(),
			success:function(data){
				var result =  eval("("+data+")");
				if ( result.status == 'success' ) {
					document.location.href=result.redirectUrl;
				} else {
					alert('로그인 실패');
				}
			},
			error:function(data){
				alert("서버와의 통신에 실패했습니다.");
			}
		});
	}


</script>
</head>
<body>
	<!--Top_content-->
	<section id="top_content" class="top_cont_outer">
	<div class="top_cont_inner">
		<div class="container">
			<div class="top_content" style="padding-top: 0px;">
				<div class="row">
					<div class="col-lg-5 col-sm-7">
						<div class="top_left_cont flipInY wow animated">
							<img
								src="<c:url value='/resources/images/page/logo/uEngine_logo.png'/>" />
							<h2>Enterprise Business Process Management System</h2>
							<p>Welcome to uEngine BPMS 4.0. uEngine BPMS is opensource
								Enterprise BPMS Solution!</p>
							<div class="form" style="margin: 0 66px 0 0px;">
								<form:form commandName="loginVO" id="frm" name="frm">
									<input class="input-text animated wow flipInY delay-02s"
										type="text" name="userId" value="Your Identification *"
										onFocus="if(this.value==this.defaultValue)this.value='';"
										onBlur="if(this.value=='')this.value=this.defaultValue;">
									<input class="input-text animated wow flipInY delay-04s"
										type="password" name="userPassword" value="Your Password *"
										onFocus="if(this.value==this.defaultValue)this.value='';"
										onBlur="if(this.value=='')this.value=this.defaultValue;">
									<input class="input-btn animated wow flipInY delay-08s"
										type="submit" value="Log-In">
								</form:form>
							</div>
						</div>

					</div>
					<div class="col-lg-7 col-sm-5"></div>
				</div>
			</div>
		</div>
	</div>
	</section>
	<!--Top_content-->
	<section class="twitter-feed"> <!--footer-->
	<div class="container  animated fadeInDown delay-07s wow">
		<p>© 2000-2016 OpenSource BPMS uengine.org , overseas@uengine.org,
			+82-2-567-8301~4</p>
		<span>Contact Us</span>
	</div>
	</section>

</body>
</html>