package com.hyeji.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyeji.blog.model.RoleType;
import com.hyeji.blog.model.User;
import com.hyeji.blog.repository.UserRepository;

//html 파일이 아니라 data를 리턴해주는 컨트롤러
@RestController
public class DummyControllerTest {
	
	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;
	
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 ID가 없습니다";
		}
		
		return "삭제되었습니다";
	}
	
	
	
	
	
	//save 함수는 id를 전달하지 않으면 insert를 해주고
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 함
	//email,password	
	@Transactional // @Transactional을 걸면 save를 하지 않아도 update가 됨 그게 더티 체킹 // 함수 종료시 자동 commit이 됨
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id,@RequestBody User requestUser) {
		//json데이터를 요청 => Java Object(MessageConverter의 Jackson라이브러리가 변환해서 받아줌 (@RequestBody)
		System.out.println("id: "+id);
		System.out.println("password: "+requestUser.getPassword());
		System.out.println("email: "+requestUser.getEmail());
		
		
		//save로 업데이트 할때
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다");
		}); //영속화 됨
		//user 오브젝트 가져오기
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);
		
		// 더티 체킹
		
		return user;
		
	}
	
	
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	} 
	
	
	// 한페이지당 2건에 데이터를 리턴 받아 볼 예정
	@GetMapping("dummy/user")
	public List<User> pageList(@PageableDefault(size = 2, sort = "id",direction = Sort.Direction.DESC)Pageable pageable){
		
		Page<User> pagingUsers = userRepository.findAll(pageable);
		
	
		List<User> users = pagingUsers.getContent();
		return users;
	}
	
	
	
	//{id}주소로 파라메터를 전달 받을 수 있음.
	// http://localhost:9999/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/4을 찾으면 내가 데이터 베이스에서 못찾아오게 되면 user가 null이 됨
		// 그럼 return null이 리턴 됨 문제생김
		// Optional로 너의 user객체를 감싸서 가져올테니 null인지 아닌지 판단해서 리턴해
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당유저는 없습니다.");
			}
		});
		//요청: 웹브라우저
		//user 객체 = 자바 오브젝트
		//변환(웹브라우저가 이해할 수 있는 데이터) -> json
		return user;
		
		
		
		
		
		
		
	}
	
	// http://localhost:9999/blog/dummy/join (요청)
	// http의 body에 username, password, email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) {		
		System.out.println("username: "+user.getUsername());
		System.out.println("password: "+user.getPassword());
		System.out.println("email: "+user.getEmail());
		System.out.println("role: "+user.getRole());
		System.out.println("createDate: "+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return"회원가입이 완료되었습니다";
	}

}
