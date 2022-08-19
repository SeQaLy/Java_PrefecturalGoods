<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン</title>
<link rel="stylesheet" href="./css/common.css">
<link rel="stylesheet" href="./css/login.css">
<%
if ( session.getAttribute( "isLogin" ) != null ) {
	response.sendRedirect( "top.jsp" );
}
%>
<script src="https://code.jquery.com/jquery-3.6.0.js"
	integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
	crossorigin="anonymous"></script>
<script
src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<script src="./js/login.js"></script>
</head>
<body>
	<!-- モーダルウィンドウ -->
	<div class="modal-container deactive">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-close">×</div>
				<div class="modal-title text"></div>
			</div>
			<div class="modal-main">
				<div class="toggleMenu">
					<div class="toggleLogin check">ログイン</div>
					<div class="toggleRegister">新規登録</div>
				</div>
				<div class="loginForm">
					<div class="input-name-container">
					</div>
					<div class="input-id-container">
						<input type="text" placeholder="ID" name="id" limit=10>
					</div>
					<div class="input-password-container">
						<input type="password" placeholder="PASSWORD" name="password" limit=20>
					</div>
				</div>

				<button id="submit" class="text disabled" name="login" disabled="disabled">ログイン</button>
			</div>
		</div>
	</div>

	<div class="header">
		<div class="nav">
			<div class="nav-datalist">
				<div class="btn-login text">ログイン</div>
			</div>
		</div>
	</div>
	<div class="main-container">
		<div class="main-content">
			<div class="text-content">
				<div class="text-description">このサイトは、日本の特産品を見ることができます。</div>
			</div>
			<div class="btn-content">
				<div class="btn-start">特産品を探す</div>
			</div>
		</div>
	</div>
</body>
</html>