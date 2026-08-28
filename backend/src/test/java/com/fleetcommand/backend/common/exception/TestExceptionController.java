package com.fleetcommand.backend.common.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-errors")
public class TestExceptionController {

    @PostMapping("/validation")
    public void testValidation(@Valid @RequestBody TestRequest request) {}

    @GetMapping("/not-found")
    public void testNotFound() {
        throw new EntityNotFoundException("Entity not found");
    }

    @GetMapping("/access-denied")
    public void testAccessDenied() {
        throw new AccessDeniedException("Access denied test");
    }

    @GetMapping("/unhandled")
    public void testUnhandled() {
        throw new RuntimeException("Sensitive database connection details or SQL syntax error");
    }

    public static class TestRequest {
        @NotBlank(message = "Name must not be blank")
        private String name;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
