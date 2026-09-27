package com.example.testfork8s.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * TB_USER 테이블 매핑.
 */
@Entity
@Table(name = "TB_USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "USER_NAME", nullable = false, length = 100)
	private String userName;

	protected User() {
		// JPA 기본 생성자
	}

	public User(String userName) {
		this.userName = userName;
	}

	public Long getId() {
		return this.id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void changeUserName(String userName) {
		this.userName = userName;
	}
}
