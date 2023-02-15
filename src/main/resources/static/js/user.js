let index = {
		init:function(){
			$("#btn-save").on("click", ()=>{
				this.save();
			});
			$("#btn-update").on("click", ()=>{
				this.update();
			});
		},
		save:function(){
				// alert('user의 save 함수 호출됨');
				let data ={
						username: $("#username").val(),
						password: $("#password").val(),
						email: $("#email").val()
				};
			
			// ajax 호출시 default가 비동기 호출
			// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
			// ajax가 통신을 성공하고 json을 리턴해주면 자동으로 자바 오브젝트로 변환
			$.ajax({
				type:"POST",
				url: "/auth/joinProc",
				data: JSON.stringify(data), //http body데이터
				contentType:"application/json; charset=utf-8",//body데이터가 어떤 타입인지
				dataType:"json"//요청을 서버로부터 응답이 왔을 때  기본적으로 모든 것이 문자열(생긴게json이라면)=> javascript 오브젝트로 변경
							
			}).done(function(resp){
				alert("회원가입이 완료되었습니다");
				location.href="/";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		
		},
		
		update:function(){
				let data ={
						id: $("#id").val(),
						username: $("#username").val(),
						password: $("#password").val(),
						email: $("#email").val()
				};		
			
			$.ajax({
				type:"PUT",
				url: "/user",
				data: JSON.stringify(data), //http body데이터
				contentType:"application/json; charset=utf-8",//body데이터가 어떤 타입인지
				dataType:"json"//요청을 서버로부터 응답이 왔을 때  기본적으로 모든 것이 문자열(생긴게json이라면)=> javascript 오브젝트로 변경
							
			}).done(function(resp){
				alert("회원수정이 완료되었습니다");
				location.href="/";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		
		},

}

index.init();


/*
 기본 로그인 방식 
 login:function(){
	// alert('user의 save 함수 호출됨');
	let data ={
			username: $("#username").val(),
			password: $("#password").val()
	};

// consloe.log(data);

// ajax 호출시 default가 비동기 호출
// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
// ajax가 통신을 성공하고 json을 리턴해주면 자동으로 자바 오브젝트로 변환
$.ajax({
	type:"POST",
	url: "/api/user/login",
	data: JSON.stringify(data), // http body데이터
	contentType:"application/json; charset=utf-8",// body데이터가 어떤 타입인지
	dataType:"json"// 요청을 서버로부터 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게json이라면)=>
					// javascript 오브젝트로 변경
				
}).done(function(resp){
	alert("로그인이 완료되었습니다");
	location.href="/";
}).fail(function(error){
	alert(JSON.stringify(error));
}); */