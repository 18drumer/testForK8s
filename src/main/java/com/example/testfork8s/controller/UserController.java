package com.example.testfork8s.controller;

import java.net.URI;
import java.util.List;

import com.example.testfork8s.dto.UserRequest;
import com.example.testfork8s.dto.UserResponse;
import com.example.testfork8s.service.UserService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TB_USER CRUD.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
		UserResponse created = this.userService.create(request);
		return ResponseEntity.created(URI.create("/api/users/" + created.id())).body(created);
	}

	@GetMapping
	public List<UserResponse> findAll(@RequestParam(name = "userName", required = false) String userName) {
		if (userName == null || userName.isBlank()) {
			return this.userService.findAll();
		}
		return this.userService.searchByUserName(userName);
	}

	@GetMapping("/{id}")
	public UserResponse findById(@PathVariable Long id) {
		return this.userService.findById(id);
	}

	@PutMapping("/{id}")
	public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
		return this.userService.update(id, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
