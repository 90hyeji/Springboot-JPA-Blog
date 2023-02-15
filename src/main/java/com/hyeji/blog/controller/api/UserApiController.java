package com.hyeji.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyeji.blog.config.auth.PrincipalDetail;
import com.hyeji.blog.config.auth.PrincipalDetailService;
import com.hyeji.blog.dto.ResponseDto;
import com.hyeji.blog.model.User;
import com.hyeji.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	

	
	// 이렇게 사용도 가능
//	@Autowired
//	private HttpSession session;
	

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		
		System.out.println("UserApiController: save 요청됨");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);// 자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user, HttpSession session){ //key=value, x-www-form-uriencoded
		userService.회원수정(user);
		// 여기서 트랜젝션이 종료되기 때문에 DB의 값은 변경이 됨
		// 하지만 세션값은 변경되지 않은 상태이기 때문에 로그아웃 후 로그인해야 값이 변해보임
		// 우리가 직접 세션값을 변경해줄 거임
		
		
		//세션 등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
}


// 스프링 시큐리티 이용해서 로그인

// 전통적인 로그인 방식
/*
 * @PostMapping("/api/user/login") public ResponseDto<Integer>
 * login(@RequestBody User user, HttpSession session){
 * System.out.println("UserApiController: login 요청됨");
 * 
 * User principal = userService.로그인(user); //principal(접근주체)
 * 
 * if(principal != null) { session.setAttribute("principal", principal); }
 * return new ResponseDto<Integer>(HttpStatus.OK.value(),1); }
 * 
 * 
 * 
 
		
	
 */
