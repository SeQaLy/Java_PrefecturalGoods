<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dao.SpecialFoods"%>
<%@ page import="dao.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
if ( session.getAttribute( "isLogin" ) == null || !(boolean)session.getAttribute( "isLogin" ) ) {

	response.sendRedirect( "login.jsp" );
}
User user = null;
if ( session.getAttribute( "user" ) != null ) {
	user = (User)session.getAttribute( "user" );
	if ( user.getType() > 2 ) {

		response.sendRedirect( "top.jsp" );
	}
}
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>データ一覧</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"
	integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
	crossorigin="anonymous"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<script src="./js/datalist.js"></script>
<link rel="stylesheet" href="./css/common.css">
<link rel="stylesheet" href="./css/datalist-style.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
	<% if ( user != null ) { %>
		<input type="hidden" name="userId" value="<%=user.getLoginId()%>">
	<% } %>

	<%
		// javascriptに渡す用
		String currentSortColumn = (String) session.getAttribute("sortColumn");
		if (session.getAttribute("sortMethod") != null && session.getAttribute("sortMethod").equals("asc")) {
	%>
	<input type="hidden" name="nextSortType" value="desc">
	<% } else { %>
	<input type="hidden" name="nextSortType" value="asc">
	<%}%>
	<input type="hidden" name="currentSortColumn"
		value="<c:out value='<%=currentSortColumn%>'/>">
	<div class="header">
		<div class="header-title text">特産品一覧</div>
	</div>

	<div class="nav">
		<div class="nav-datalist">
			<% if ( user !=null && user.getType() == 1 ) { %>
			<a class="text no-deco" href="UserList">アカウント管理</a>
			<% } %>
			<a class="text no-deco" href="./top.jsp">トップページ</a>
			<a class="text no-deco" href="#">データ一覧</a>
			<a class="text no-deco" href="Logout">ログアウト</a>
		</div>
	</div>
	<!-- モーダルウィンドウ -->
	<div class="modal-container deactive">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-close">×</div>
				<div class="modal-title text"></div>
			</div>
			<div class="modal-main">
				<div class="foodnumber-content" style="display: none"
					name="food-number"></div>
				<div class="prefectural-content">
					<div class="prefectural-title">都道府県名</div>
					<select class="prefectural-menu no-outline" name="prefectural"></select>
				</div>
				<div class="specialfood-content">
					<div class="specialfood-title">特産品名</div>
					<div class="specialfood-text">
						<input type="text" class="no-outline" name="food-name"
							placeholder="例)もみじ饅頭">
					</div>
				</div>
				<div id="submit">確定</div>
			</div>
		</div>
	</div>
	<div class="menu-container">
		<div class="crud-container">
			<div class="showall-content">
				<a href="SpecialFoodsList" class="text no-deco">全て表示</a>
			</div>
			<form action="SpecialFoodSearch">
				<input type="text" name="searchTerm" placeholder="例)うどん"> <input
					type="submit" class="text" value="検索">
			</form>
			<div class="add-content">追加</div>
			<div class="update-content">編集</div>
			<div class="delete-content">削除</div>
		</div>
	</div>
	<table>
		<tr>
			<th class="table-checkbox"></th>
			<th class="table-id fa fa-sort" id="sort-id" name="tokusanhin_sno">ID</th>
			<th class="table-prefectural fa fa-sort" id="sort-pref"
				name="todofuken_id">都道府県名</th>
			<th class="table-food fa fa-sort" id="sort-food" name="hinmei">特産品名</th>
		</tr>

		<%
			ArrayList<SpecialFoods> list = (ArrayList<SpecialFoods>) session.getAttribute("foodList");
			if (session.getAttribute("foodList") == null) {
		%>
		<c:forEach items="${foodList}" var="data" varStatus="status">
			<tr>
				<td class="table-checkbox"><input type="checkbox"
					name="${data.specialFoodNumber}"></td>
				<td class="table-id"><c:out value="${data.specialFoodNumber}" /></td>
				<td class="table-prefectural"><c:out
						value="${data.prefecturalName}" /></td>
				<td class="table-food"><c:out value="${data.specialFoodName}" /></td>
			</tr>
		</c:forEach>
		<%
			} else {
		%>
		<c:forEach items="<%=list%>" var="data" varStatus="status">
			<tr>
				<td class="table-checkbox"><input type="checkbox"
					name="${data.specialFoodNumber}"></td>
				<td class="table-id"><c:out value="${data.specialFoodNumber}" /></td>
				<td class="table-prefectural"><c:out
						value="${data.prefecturalName}" /></td>
				<td class="table-food"><c:out value="${data.specialFoodName}" /></td>
			</tr>
		</c:forEach>
		<%
			}
		%>
	</table>
	<!-- ページング -->
	<%
		String url = null;
		// 検索中
		if (request.getAttribute("isSearch") != null) {
			// ソート中
			if (session.getAttribute("sortMethod") != null) {
				// 昇順
				if (session.getAttribute("sortMethod").equals("asc")) {
					url = "SpecialFoodSort?sortMethod=asc";
				} else {
					url = "SpecialFoodSort?sortMethod=desc";
				}
			} else {
				url = "SpecialFoodSearch";
			}
		} else {
			url = "SpecialFoodsList";
		}
	%>
	<div class="pages">
		<c:forEach begin="${pageStart}" end="${pageEnd}" varStatus="status">
			<c:if test="${currentPage == status.index}">
				<a
					href="<c:url value='<%=url%>'><c:param name='pageNumber' value='${status.index}'/></c:url>"
					class="no-deco active" id="active">${status.index}</a>
			</c:if>
			<c:if test="${currentPage != status.index}">
				<a
					href="<c:url value='<%=url%>'><c:param name='pageNumber' value='${status.index}'/></c:url>"
					class="no-deco">${status.index}</a>
			</c:if>
		</c:forEach>
	</div>
</body>
</html>