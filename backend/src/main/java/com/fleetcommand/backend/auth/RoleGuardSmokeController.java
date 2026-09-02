package com.fleetcommand.backend.auth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Temporary endpoints that demonstrate the role-guard convention. Feature endpoints
 * should use the same {@code @PreAuthorize("hasRole('ROLE_NAME')")} form.
 */
@RestController
@RequestMapping("/guarded")
public class RoleGuardSmokeController {

    @GetMapping("/hirer")
    @PreAuthorize("hasRole('HIRER')")
    public RoleGuardResponse hirerOnly() {
        return new RoleGuardResponse("HIRER");
    }

    @GetMapping("/owner")
    @PreAuthorize("hasRole('OWNER')")
    public RoleGuardResponse ownerOnly() {
        return new RoleGuardResponse("OWNER");
    }

    @GetMapping("/driver")
    @PreAuthorize("hasRole('DRIVER')")
    public RoleGuardResponse driverOnly() {
        return new RoleGuardResponse("DRIVER");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public RoleGuardResponse adminOnly() {
        return new RoleGuardResponse("ADMIN");
    }

    public record RoleGuardResponse(String role) {
    }
}
