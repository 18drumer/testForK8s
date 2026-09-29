package com.example.testfork8s.controller;

import java.util.Map;

import com.example.testfork8s.probe.StartupState;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kubernetes probe 용 엔드포인트.
 */
@Slf4j
@RestController
public class ProbeController {

	private final StartupState startupState;

	public ProbeController(StartupState startupState) {
		this.startupState = startupState;
	}

	/** livenessProbe / readinessProbe 용: 항상 "OK". */
	@GetMapping(path = "/healthcheck", produces = MediaType.TEXT_PLAIN_VALUE)
	public String helthcheck() {
		log.info("helthcheck() 호출");
		return "OK";
	}

	/** startupProbe 용: 기동 후 warm-up 구간 동안은 500, 이후 200. */
	@GetMapping(path = "/startupProbe", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> startupProbe() {
		log.info("startupProbe() 호출");
		if (this.startupState.isStarted()) {
			return ResponseEntity.ok(Map.of(
					"status", "UP",
					"message", "startup completed"));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
				"status", "STARTING",
				"message", "application is warming up",
				"remainingSeconds", this.startupState.remainingSeconds()));
	}
}
