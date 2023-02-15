package com.hyeji.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyeji.blog.model.KakaoProfile;
import com.hyeji.blog.model.OAuthToken;
import com.hyeji.blog.model.User;
import com.hyeji.blog.service.UserService;


// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 "/"이면 index.jsp 허용
// static 이하에 있는 /js/** , /css/**, /image/** 허용

@Controller
public class UserController {
	
	@Value("${hyeji.key}")
	 private String hyejiKey;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm"; 
	}
	
	@GetMapping("/auth/kakao/callback")
	public  String kakaoCallback(String code) { //Data를 리턴해주는 컨트롤러 함수
		
		//POST 방식으로 key=value 데이터를 요청(카카오쪽으로)		
		// Retrofit2
		// OkHttp
		// RestTemplate
	
		//토큰받기
		RestTemplate rt = new RestTemplate();
		
		//HttpHeaders 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		 
		params.add("grant_type", "authorization_code");
		params.add("client_id", "0da28a4375b252b9707180d88561f84d");
		params.add("redirect_uri", "http://localhost:9999/auth/kakao/callback");
		params.add("code",code);


		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params,headers);
		
		//Http 요청하기 - Post 방식 - 그리고 responce변수의 응답 받음.
		ResponseEntity<String> responce = rt.exchange(
				 "https://kauth.kakao.com/oauth/token",
				 HttpMethod.POST,
				 kakaoTokenRequest,
				 String.class
		);
		
		//Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(responce.getBody(), OAuthToken.class);
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토큰 : "+oauthToken.getAccess_token());
		
		
		RestTemplate rt2 = new RestTemplate();
		
		//HttpHeaders 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);
		
		//Http 요청하기 - Post 방식 - 그리고 responce변수의 응답 받음.
		ResponseEntity<String> responce2 = rt2.exchange(
				 "https://kapi.kakao.com/v2/user/me",
				 HttpMethod.POST,
				 kakaoProfileRequest2,
				 String.class
		);
		
		
		//Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile KakaoProfile = null;
		try {
			KakaoProfile = objectMapper2.readValue(responce2.getBody(), KakaoProfile.class);
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		
		// User 오브젝트 : username, password, email
		System.out.println("카카오아이디(번호): "+KakaoProfile.getId());
		System.out.println("카카오이메일(번호): "+KakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그서버 유저네임: "+KakaoProfile.getKakao_account().getEmail()+"_"+KakaoProfile.getId());
		System.out.println("블로그서버 이메일: "+KakaoProfile.getKakao_account().getEmail());
		//UUID -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
//		UUID garbagePassword = UUID.randomUUID();
		System.out.println("블로그서버 패스워드: "+hyejiKey);
				
		
		User kakaoUser = User.builder()
					.username(KakaoProfile.getKakao_account().getEmail()+"_"+KakaoProfile.getId())
					.password(hyejiKey)
					.email(KakaoProfile.getKakao_account().getEmail())
					.build();
		
		// 가입자 혹은 비가입자 체크해서 처리
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		
		if(originUser.getUsername() == null) {
			System.out.println("기존 회원이 아닙니다");
			userService.회원가입(kakaoUser);
		}
		
		// 로그인 처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), hyejiKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "ridirect:/";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "/user/updateForm";
	}
	
}
