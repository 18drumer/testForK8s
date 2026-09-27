package com.example.testfork8s.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
		@NotBlank(message = "userName is required")
		@Size(max = 100, message = "userName must be 100 characters or less")
		String userName) {
}
