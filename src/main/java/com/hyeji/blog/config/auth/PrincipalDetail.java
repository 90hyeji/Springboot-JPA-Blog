package com.hyeji.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hyeji.blog.model.User;

import lombok.Data;


// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면
// UserDetails 타입의 오브젝트를 스프링 시큐리티의 고유한 세션 저장소에 저장을 해줌
@Data
public class PrincipalDetail implements UserDetails{
	
	private User user; // 컴포지션(객체를 품고 있는것)
	
	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	// 계정이 만료되지 않았는지 리턴한다(true:만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	// 계정이 잠겨있지 않았는지 리턴한다(true: 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 계정의 비밀번호가 만료되지 않았는지 리턴한다(true: 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정 활성화(사용가능)인지 리턴한다(true: 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// 계정의 가지고 있는 권한 목록을 리턴한다.(권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 우리는 한개만)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(()->{return "ROLE_"+user.getRole();});
		
		return collectors;
	}
	
	
	
}

// 자바는 오브젝트를 담을 수 있지만 메서드만 넘길 순 없다.
// collectors.add(()->{return "ROLE_"+user.getRole();});


/*
 * collectors.add(new GrantedAuthority() {
 * 
 * @Override public String getAuthority() { // 스프링에서 ROLE_ 넣는게 규칙 꼭 넣어줘야함 return
 * "ROLE_"+user.getRole(); //ROLE_USER } });
 */