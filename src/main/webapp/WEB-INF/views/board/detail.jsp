<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>


	<div class="container">
		<button class="btn btn-dark" onclick="history.back()">목록</button>
		<c:if test="${board.user.id == principal.user.id }">
			<a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
			<button id="btn-delete" class="btn btn-secondary">삭제</button>
		</c:if>
		<br><br>
		<div>
			글 번호 : <span id="id"><b>${board.id } </b></span>
			작성자 : <span><b>${board.user.username} </b></span>
			<br><br>
		</div>
		<div>
			<h3>${board.title }</h3>
		</div>
		<hr>
		<div>
		  <div>${board.content }</div>
		</div>
		<hr>
	</div>
	
	
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>