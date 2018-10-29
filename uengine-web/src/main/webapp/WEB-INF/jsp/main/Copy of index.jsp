<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<script>
	fn_openApprovalLineEditor = function() {
		var url = "<c:url value='/organization/popup/approvalLineEditor.do' />";
		window.open(url, "_blank", "width=800,height=700;");
	}
</script>
<title>BPM</title>
</head>
<body>
	<!-- Page Content -->
	<div class="container-fluid">
		<div class="content-left">
			<div class="panel panel-default">
				<div class="panel-heading">
					<p>
						<span class="glyphicon glyphicon-user"> Logon Information</span>
					</p>
				</div>
				<div class="panel-body">
					<table>
						<tr>
							<td style="vertical-align: middle;"><img width="90px"
								height="100px" src="<c:url value='${portraitPath}'/>" /></td>
							<td style="padding: 5px"><p>
									<span class="glyphicon glyphicon-expand"> 아이디 :
										${sessionScope.loggedUser.userId}</span>
								</p>
								<p>
									<span class="glyphicon glyphicon-expand"> 이 름 :
										${sessionScope.loggedUser.userName}</span>
								</p>
								<p>
									<span class="glyphicon glyphicon-expand"> 직 위 :
										${sessionScope.loggedUser.jikName}</span>
								</p>
								<p>
									<span class="glyphicon glyphicon-expand"> 부 서 :
										${sessionScope.loggedUser.partName}</span>
								</p></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="content-middle">
			<button class="btn btn-primary"><span
				class="glyphicon glyphicon-user"
				onClick="fn_openApprovalLineEditor();return false;"> 결재선 편집</span></button>
			<button class="btn btn-primary" data-toggle="modal"
				data-target="#applovalLineEditor">결재선 편집</button>
			<div id="applovalLineEditor" class="modal fade" role="dialog">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button id="applovalLineCloseButton" type="button" class="close"
								data-dismiss="modal">&times;</button>
							<h5 class="modal-title">결재선 편집기</h5>
							<div class="modal-body">
								<table width="100%">
									<tr>
										<td width="50%">
											<table>
												<tr>
													<td colspan="3" style="padding: 5px;"><span
														class="glyphicon glyphicon-globe"
														style="font-size: 20px; color: gray"></span> <font
														size="3px">조직도</font></td>
												</tr>
												<tr>
													<td style="padding: 5px;"><select class="form-control"
														id="approvalLineSearchOption" style="width: auto;">
															<option>이름</option>
															<option>사번</option>
															<option>부서명</option>
													</select></td>
													<td style="padding: 5px;"><input type="text"
														class="form-control" id="approvalLineSearchKeyword"
														name="approvalLineSearchKeyword" /></td>
													<td style="padding: 5px;">
														<button type="button" class="btn btn-info"><span
															class="glyphicon glyphicon-search"></span> Search</button>
													</td>
												</tr>
												<tr>
													<td colspan="3" style="padding: 5px;"><div
															class="panel panel-info"
															style="height: 200px; overflow: auto;">
															<div class="panel-body">324234</div>
														</div></td>
												</tr>
											</table>
										</td>
										<td width="50%"></td>
									</tr>
								</table>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary"
									onClick="alert('적용하였습니다.');$('#applovalLineCloseButton').click();">적용</button>
								<button type="button" class="btn btn-danger"
									data-dismiss="modal">닫기</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="content-right">content-middle</div>

	</div>
	<!-- /.container -->
</body>
</html>