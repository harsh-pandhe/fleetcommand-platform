package com.fleetcommand.backend.auth;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    private static final Logger log = LoggerFactory.getLogger(OtpService.class);
    private static final Duration OTP_TTL = Duration.ofMinutes(5);
    private static final Duration REQUEST_COOLDOWN = Duration.ofMinutes(1);

    private final SecureRandom secureRandom = new SecureRandom();
    private final ConcurrentMap<String, OtpChallenge> challenges = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Instant> lastRequestedAt = new ConcurrentHashMap<>();

    /**
     * Creates a new OTP unless the phone number is still within its request cooldown.
     * The OTP is deliberately logged while SMS delivery is stubbed for Phase 1.
     */
    public Optional<String> requestOtp(String phone) {
        Instant now = Instant.now();
        Instant previousRequest = lastRequestedAt.get(phone);
        if (previousRequest != null && previousRequest.plus(REQUEST_COOLDOWN).isAfter(now)) {
            return Optional.empty();
        }

        String code = "%06d".formatted(secureRandom.nextInt(1_000_000));
        challenges.put(phone, new OtpChallenge(code, now.plus(OTP_TTL)));
        lastRequestedAt.put(phone, now);
        log.info("OTP requested for {}: {}", phone, code);
        return Optional.of(code);
    }

    public boolean verifyOtp(String phone, String code) {
        OtpChallenge challenge = challenges.get(phone);
        if (challenge == null || challenge.expiresAt().isBefore(Instant.now())) {
            challenges.remove(phone);
            return false;
        }
        if (!challenge.code().equals(code)) {
            return false;
        }
        return challenges.remove(phone, challenge);
    }

    private record OtpChallenge(String code, Instant expiresAt) {
    }
}
