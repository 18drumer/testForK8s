package com.example.testfork8s.repository;

import java.util.List;

import com.example.testfork8s.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByUserNameContainingIgnoreCase(String keyword);
}
