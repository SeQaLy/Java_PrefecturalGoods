<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.User"%>
<%if ( session.getAttribute( "isLogin" ) == null || !(boolean)session.getAttribute( "isLogin" ) ) {
	response.sendRedirect( "login.jsp" );
} %>

<%

User user = null;
if ( session.getAttribute( "user" ) != null ) {
	user = (User)session.getAttribute( "user" );
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>都道府県別特産品</title>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<script src="./js/main.js"></script>
<link rel="stylesheet" href="./css/common.css">
<link rel="stylesheet" href="./css/top-style.css">
</head>
<body>
	<div class="header">
		<div class="header-title text">都道府県別特産品</div>
	</div>
	<div class="nav">
		<div class="nav-datalist">
			<% if ( user !=null && user.getType() == 1 ) { %>
			<a class="text no-deco" href="UserList">アカウント管理</a>
			<% } %>
			<a class="text no-deco" href="#">トップページ</a>
			<% if ( user != null && user.getType() <= 2 ) { %>
			<a class="text no-deco" href="SpecialFoodsList">データ一覧</a>
			<% } %>
			<a class="text no-deco" href="Logout">ログアウト</a>
		</div>
	</div>

	<div class="main-container">
		<!-- モーダルウィンドウ -->
		<div class="modal-container deactive">
			<div class="modal-content">
				<div class="modal-header">
					<div class="modal-close">×</div>
					<div class="modal-title text"></div>
				</div>
				<div class="modal-main">
					<ul class="food-list">
					</ul>
				</div>
			</div>
		</div>
		<div class="map-container">
			<div class="map-content">
				<img src="./images/japan_map.png" usemap="#ImageMap" name="" />
				<map name="ImageMap">
				  <area shape="poly" coords="371,26,370,113,405,116,405,108,493,104,491,28,382,24,382,24" href="#" name="北海道" />
				  <area shape="rect" coords="372,131,467,157" href="#" name="青森県" />
				  <area shape="rect" coords="373,159,419,194" href="#" name="秋田県" />
				  <area shape="rect" coords="421,161,468,194" href="#" name="岩手県" />
				  <area shape="poly" coords="373,199,419,199,418,230,393,231,393,222,372,221,373,203,368,207" href="#" name="山形県" />
				  <area shape="rect" coords="421,197,465,232" href="#" name="宮城県" />
				  <area shape="rect" coords="392,235,467,268" href="#" name="福島県" />
				  <area shape="rect" coords="445,271,467,317" href="#" name="茨城県" />
				  <area shape="rect" coords="445,322,467,404" href="#" name="千葉県" />
				  <area shape="rect" coords="373,271,406,308" href="#" name="群馬県" />
				  <area shape="rect" coords="409,273,442,305" href="#" name="栃木県" />
				  <area shape="rect" coords="370,311,442,338" href="#" name="埼玉県" />
				  <area shape="rect" coords="353,339,387,365" href="#" name="山梨県" />
				  <area shape="poly" coords="390,362,390,339,442,339,442,351,427,355,426,365,389,366,396,366" href="#" name="東京都" />
				  <area shape="poly" coords="377,370,378,383,388,384,391,403,425,403,425,372,394,370,394,370" href="#" name="神奈川県" />
				  <area shape="rect" coords="305,235,339,268" href="#" name="富山県" />
				  <area shape="poly" coords="341,236,369,236,374,226,387,226,386,265,340,268,344,243,344,242" href="#" name="新潟県" />
				  <area shape="poly" coords="331,271,369,271,366,333,347,336,347,364,334,365,331,312,334,312" href="#" name="長野県" />
				  <area shape="poly" coords="342,369,344,401,387,401,385,391,376,390,373,370,362,370,362,370" href="#" name="静岡県" />
				  <area shape="rect" coords="305,271,328,367" href="#" name="岐阜県" />
				  <area shape="rect" coords="305,372,339,402" href="#" name="愛知県" />
				  <area shape="rect" coords="280,223,302,268" href="#" name="石川県" />
				  <area shape="poly" coords="256,284,255,304,302,306,302,270,281,270,281,282,281,282" href="#" name="福井県" />
				  <area shape="rect" coords="281,310,302,340" href="#" name="滋賀県" />
				  <area shape="rect" coords="280,346,302,426" href="#" name="三重県" />
				  <area shape="poly" coords="229,283,231,342,275,341,275,307,254,307,252,281,241,282,241,282" href="#" name="京都府" />
				  <area shape="rect" coords="255,347,278,397" href="#" name="奈良県" />
				  <area shape="rect" coords="229,346,252,384" href="#" name="大阪府" />
				  <area shape="poly" coords="229,388,231,425,276,425,275,403,250,403,249,384,241,386,241,386" href="#" name="和歌山県" />
				  <area shape="rect" coords="205,281,226,354" href="#" name="兵庫県" />
				  <area shape="rect" coords="181,284,202,320" href="#" name="鳥取県" />
				  <area shape="rect" coords="181,322,202,354" href="#" name="岡山県" />
				  <area shape="rect" coords="153,283,177,318" href="#" name="島根県" />
				  <area shape="rect" coords="153,319,177,354" href="#" name="広島県" />
				  <area shape="rect" coords="128,283,153,354" href="#" name="山口県" />
				  <area shape="rect" coords="173,368,217,395" href="#" name="香川県" />
				  <area shape="rect" coords="175,399,215,427" href="#" name="徳島県" />
				  <area shape="rect" coords="131,368,171,395" href="#" name="愛媛県" />
				  <area shape="rect" coords="130,399,172,428" href="#" name="高知県" />
				  <area shape="rect" coords="93,307,116,348" href="#" name="大分県" />
				  <area shape="rect" coords="93,351,116,390" href="#" name="宮崎県" />
				  <area shape="poly" coords="56,282,57,333,89,335,92,305,115,303,114,285,88,283,75,276" href="#" name="福岡県" />
				  <area shape="rect" coords="57,338,91,391" href="#" name="熊本県" />
				  <area shape="rect" coords="55,394,118,426" href="#" name="鹿児島県" />
				  <area shape="rect" coords="32,281,55,334" href="#" name="佐賀県" />
				  <area shape="rect" coords="6,285,30,332" href="#" name="長崎県" />
				  <area shape="rect" coords="57,442,78,475" href="#" name="沖縄県" />
				</map>
			</div>
		</div>
	</div>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Modaal/0.4.4/js/modaal.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.js"></script>
</body>
</html>