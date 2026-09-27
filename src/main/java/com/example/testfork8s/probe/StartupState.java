package com.example.testfork8s.probe;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 애플리케이션 기동 시점을 기록해서 startupProbe 가 "준비 완료" 여부를 판단할 수 있게 해준다.
 * warm-up 구간(기본 60초) 동안은 아직 준비되지 않은 것으로 간주한다.
 */
@Component
public class StartupState {

	private final Duration warmUpDuration;
	private volatile Instant readyAt;

	public StartupState(@Value("${app.startup-probe.warm-up-duration:30s}") Duration warmUpDuration) {
		this.warmUpDuration = warmUpDuration;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		this.readyAt = Instant.now().plus(this.warmUpDuration);
	}

	/** warm-up 구간이 끝났으면 true. */
	public boolean isStarted() {
		Instant target = this.readyAt;
		return target != null && !Instant.now().isBefore(target);
	}

	/** 준비 완료까지 남은 시간(초). 이미 준비됐으면 0. */
	public long remainingSeconds() {
		Instant target = this.readyAt;
		if (target == null) {
			return this.warmUpDuration.toSeconds();
		}
		long remaining = Duration.between(Instant.now(), target).toSeconds();
		return Math.max(remaining, 0L);
	}

	public Duration getWarmUpDuration() {
		return this.warmUpDuration;
	}
}
