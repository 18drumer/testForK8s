package com.example.testfork8s.dto;

import com.example.testfork8s.domain.User;

public record UserResponse(Long id, String userName) {

	public static UserResponse from(User user) {
		return new UserResponse(user.getId(), user.getUserName());
	}
}
