package com.fleetcommand.backend.common.exception;

import java.time.Instant;

public record ApiError(
    String code,
    String message,
    Object details,
    Instant timestamp
) {
    public ApiError(String code, String message, Object details) {
        this(code, message, details, Instant.now());
    }
}
