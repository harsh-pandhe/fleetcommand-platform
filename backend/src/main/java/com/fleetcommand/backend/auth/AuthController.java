package com.fleetcommand.backend.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fleetcommand.backend.user.Role;
import com.fleetcommand.backend.user.User;
import com.fleetcommand.backend.user.UserRepository;

@RestController
@RequestMapping("/auth/otp")
public class AuthController {

    private final OtpService otpService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(OtpService otpService, JwtService jwtService, UserRepository userRepository) {
        this.otpService = otpService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/request")
    public ResponseEntity<OtpRequestResponse> requestOtp(@Valid @RequestBody OtpRequest request) {
        return otpService.requestOtp(request.phone())
                .map(ignored -> ResponseEntity.accepted().body(new OtpRequestResponse("OTP requested")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                        .body(new OtpRequestResponse("Please wait before requesting another OTP")));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpVerificationRequest request) {
        if (!otpService.verifyOtp(request.phone(), request.code())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new OtpVerificationFailure("Invalid or expired OTP"));
        }

        User user = userRepository.findByPhone(request.phone())
                .orElseGet(() -> userRepository.save(new User(request.phone(), Role.HIRER, "ACTIVE")));
        if (!"ACTIVE".equals(user.getStatus())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new OtpVerificationFailure("User account is not active"));
        }

        return ResponseEntity.ok(new OtpVerificationResponse(jwtService.issueToken(user)));
    }

    public record OtpRequest(
            @NotBlank @Pattern(regexp = "^\\+?[1-9]\\d{7,14}$", message = "Phone must be in international format") String phone) {
    }

    public record OtpVerificationRequest(
            @NotBlank @Pattern(regexp = "^\\+?[1-9]\\d{7,14}$", message = "Phone must be in international format") String phone,
            @NotBlank @Pattern(regexp = "^\\d{6}$", message = "OTP must be six digits") String code) {
    }

    public record OtpRequestResponse(String message) {
    }

    public record OtpVerificationResponse(String token) {
    }

    public record OtpVerificationFailure(String message) {
    }
}
