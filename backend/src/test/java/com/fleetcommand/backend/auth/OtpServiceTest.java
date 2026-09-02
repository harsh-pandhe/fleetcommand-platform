package com.fleetcommand.backend.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OtpServiceTest {

    private final OtpService otpService = new OtpService();

    @Test
    void verifiesAnIssuedOtpOnlyOnce() {
        String code = otpService.requestOtp("+919876543210").orElseThrow();

        assertThat(otpService.verifyOtp("+919876543210", code)).isTrue();
        assertThat(otpService.verifyOtp("+919876543210", code)).isFalse();
    }

    @Test
    void limitsRepeatOtpRequests() {
        assertThat(otpService.requestOtp("+919876543211")).isPresent();

        assertThat(otpService.requestOtp("+919876543211")).isEmpty();
    }
}
