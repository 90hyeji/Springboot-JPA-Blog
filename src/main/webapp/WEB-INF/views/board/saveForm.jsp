<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>


	<div class="container">
		<div>
			글 번호 : <span id="id"><b>${board.id } </b></span>
			작성자 : <span><b>${board.user.username} </b></span>
			<br><br>
		</div>
		<form>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Enter Title" id="title">
		</div>
		<div class="form-group">
		  <textarea class="form-control summernote" rows="5" id="content"></textarea>
		</div>
	</form>
	<button id="btn-save"class="btn btn-primary">저장</button>
	</div>
	
	
	<script>
      $('.summernote').summernote({
        tabsize: 2,
        height: 300
      });
    </script>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>