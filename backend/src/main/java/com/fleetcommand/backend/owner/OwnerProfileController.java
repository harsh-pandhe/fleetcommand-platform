package com.fleetcommand.backend.owner;

import java.util.UUID;

import com.fleetcommand.backend.user.User;
import com.fleetcommand.backend.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
@RequestMapping("/owners/me")
@PreAuthorize("hasRole('OWNER')")
public class OwnerProfileController {
    private final OwnerProfileRepository profiles;
    private final UserRepository users;

    public OwnerProfileController(OwnerProfileRepository profiles, UserRepository users) {
        this.profiles = profiles;
        this.users = users;
    }

    @GetMapping
    public OwnerProfileResponse get(@AuthenticationPrincipal Jwt jwt) {
        return response(profiles.findById(userId(jwt)).orElseThrow());
    }

    @PutMapping
    public ResponseEntity<OwnerProfileResponse> put(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody OwnerProfileRequest request) {
        UUID userId = userId(jwt);
        OwnerProfile profile = profiles.findById(userId).orElseGet(() -> {
            User user = users.findById(userId).orElseThrow();
            return new OwnerProfile(user, request.contactName(), request.contactPhone());
        });
        profile.updateContact(request.contactName(), request.contactPhone());
        return ResponseEntity.ok(response(profiles.save(profile)));
    }

    private UUID userId(Jwt jwt) { return UUID.fromString(jwt.getSubject()); }
    private OwnerProfileResponse response(OwnerProfile profile) {
        return new OwnerProfileResponse(profile.getContactName(), profile.getContactPhone(), profile.getVerificationStatus());
    }

    public record OwnerProfileRequest(@NotBlank String contactName,
            @NotBlank @Pattern(regexp = "^\\+?[1-9]\\d{7,14}$") String contactPhone) { }
    public record OwnerProfileResponse(String contactName, String contactPhone, String verificationStatus) { }
}
