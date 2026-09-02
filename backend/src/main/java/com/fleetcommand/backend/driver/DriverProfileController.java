package com.fleetcommand.backend.driver;

import java.util.UUID;

import com.fleetcommand.backend.user.User;
import com.fleetcommand.backend.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drivers/me")
@PreAuthorize("hasRole('DRIVER')")
public class DriverProfileController {
    private final DriverProfileRepository profiles;
    private final UserRepository users;
    public DriverProfileController(DriverProfileRepository profiles, UserRepository users) {
        this.profiles = profiles;
        this.users = users;
    }
    @GetMapping
    public DriverProfileResponse get(@AuthenticationPrincipal Jwt jwt) {
        return response(profiles.findById(userId(jwt)).orElseThrow());
    }
    @PutMapping
    public ResponseEntity<DriverProfileResponse> put(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody DriverProfileRequest request) {
        UUID userId = userId(jwt);
        DriverProfile profile = profiles.findById(userId).orElseGet(() -> {
            User user = users.findById(userId).orElseThrow();
            return new DriverProfile(user, request.fullName(), request.licenseNumber(), request.status());
        });
        profile.update(request.fullName(), request.licenseNumber(), request.status());
        return ResponseEntity.ok(response(profiles.save(profile)));
    }
    private UUID userId(Jwt jwt) { return UUID.fromString(jwt.getSubject()); }
    private DriverProfileResponse response(DriverProfile profile) {
        return new DriverProfileResponse(profile.getFullName(), profile.getLicenseNumber(), profile.getStatus());
    }
    public record DriverProfileRequest(@NotBlank String fullName, @NotBlank String licenseNumber, @NotBlank String status) { }
    public record DriverProfileResponse(String fullName, String licenseNumber, String status) { }
}
