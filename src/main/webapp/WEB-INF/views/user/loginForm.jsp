<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>


<div class="container">
	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">User Name</label> <input type="text" name="username" class="form-control" placeholder="Enter User Name" id="username">
		</div>
		<div class="form-group">
			<label for="password">Password</label> <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		<button id="btn-login"class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=0da28a4375b252b9707180d88561f84d&redirect_uri=http://localhost:9999/auth/kakao/callback&response_type=code"><img height="39px" src="/image/kakao_login_button.png"></a>
	</form>

</div>	

<%@ include file="../layout/footer.jsp" %>