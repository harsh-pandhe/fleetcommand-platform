package com.fleetcommand.backend.common.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TestExceptionController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void whenValidationFails_returnsHttp400WithValidationErrorEnvelope() throws Exception {
        mockMvc.perform(post("/test-errors/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("VALIDATION_ERROR")))
                .andExpect(jsonPath("$.message", is("Request validation failed")))
                .andExpect(jsonPath("$.details.name", is("Name must not be blank")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    void whenEntityNotFound_returnsHttp404WithNotFoundEnvelope() throws Exception {
        mockMvc.perform(get("/test-errors/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is("NOT_FOUND")))
                .andExpect(jsonPath("$.message", is("Resource not found")))
                .andExpect(jsonPath("$.details", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    void whenAccessDenied_returnsHttp403WithAccessDeniedEnvelope() throws Exception {
        mockMvc.perform(get("/test-errors/access-denied"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is("ACCESS_DENIED")))
                .andExpect(jsonPath("$.message", is("Access denied")))
                .andExpect(jsonPath("$.details", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    void whenUnhandledException_returnsHttp500WithInternalErrorEnvelopeAndHidesInternals() throws Exception {
        mockMvc.perform(get("/test-errors/unhandled"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code", is("INTERNAL_ERROR")))
                .andExpect(jsonPath("$.message", is("An unexpected error occurred")))
                .andExpect(jsonPath("$.details", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.*", hasSize(4))) // Only contains code, message, details, timestamp
                .andExpect(content().string(not(containsString("RuntimeException"))))
                .andExpect(content().string(not(containsString("Sensitive database connection details"))));
    }
}
