package com.fleetcommand.backend.common;

import java.time.Instant;
import java.util.Map;

/**
 * Stable error response returned by all REST endpoints.
 */
public record ApiError(
		String code,
		String message,
		Map<String, String> details,
		Instant timestamp) {

	public static ApiError of(String code, String message, Map<String, String> details) {
		return new ApiError(code, message, Map.copyOf(details), Instant.now());
	}
}
