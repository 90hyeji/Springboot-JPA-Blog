package com.hyeji.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM -> Java(다른언어 포함) Object -> 테이블로 매핑해주는 기술

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더패턴!!
@Entity //User 클래스가 MySQL에 자동으로 테이블이 생성됨
//@DynamicInsert insert 시에 null 인 값을 제외시켜 준다
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라감
	private int id;
	
	@Column(nullable = false, length = 100, unique = true)
	private String username; //id
	
	@Column(nullable = false,length = 100)
	private String password;
	
	@Column(nullable = false,length = 50)
	private String email;
	
//	@ColumnDefault("user")
	//DB는 RoleType라는게 없다
	@Enumerated(EnumType.STRING)
	private RoleType role; 
	
	@CreationTimestamp //시간이 자동 입력
	private Timestamp createDate;
	
}
