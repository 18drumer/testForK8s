package com.example.testfork8s.service;

import java.util.List;

import com.example.testfork8s.domain.User;
import com.example.testfork8s.dto.UserRequest;
import com.example.testfork8s.dto.UserResponse;
import com.example.testfork8s.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public UserResponse create(UserRequest request) {
		User saved = this.userRepository.save(new User(request.userName()));
		return UserResponse.from(saved);
	}

	public List<UserResponse> findAll() {
		return this.userRepository.findAll().stream()
				.map(UserResponse::from)
				.toList();
	}

	public List<UserResponse> searchByUserName(String keyword) {
		return this.userRepository.findByUserNameContainingIgnoreCase(keyword).stream()
				.map(UserResponse::from)
				.toList();
	}

	public UserResponse findById(Long id) {
		return UserResponse.from(getOrThrow(id));
	}

	@Transactional
	public UserResponse update(Long id, UserRequest request) {
		User user = getOrThrow(id);
		user.changeUserName(request.userName());
		return UserResponse.from(user);
	}

	@Transactional
	public void delete(Long id) {
		this.userRepository.delete(getOrThrow(id));
	}

	private User getOrThrow(Long id) {
		return this.userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id));
	}
}
