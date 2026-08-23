package com.fleetcommand.backend.common;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.NoSuchElementException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebMvcTest(GlobalExceptionHandlerWebMvcTest.TestController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandlerWebMvcTest.TestController.class)
class GlobalExceptionHandlerWebMvcTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void validationErrorsIncludeFieldDetails() throws Exception {
		mockMvc.perform(post("/test/validate")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"name\":\"\"}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
				.andExpect(jsonPath("$.message").value("Validation failed"))
				.andExpect(jsonPath("$.details.name").value("must not be blank"))
				.andExpect(jsonPath("$.timestamp").isString());
	}

	@Test
	void notFoundErrorsUseTheStandardEnvelope() throws Exception {
		mockMvc.perform(get("/test/not-found"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value("NOT_FOUND"))
				.andExpect(jsonPath("$.details").isEmpty());
	}

	@Test
	void accessDeniedErrorsUseTheStandardEnvelope() throws Exception {
		mockMvc.perform(get("/test/denied"))
				.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.code").value("ACCESS_DENIED"));
	}

	@Test
	void unhandledErrorsDoNotExposeInternalDetails() throws Exception {
		mockMvc.perform(get("/test/unhandled"))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.code").value("INTERNAL_SERVER_ERROR"))
				.andExpect(jsonPath("$.message").value("An unexpected error occurred"))
				.andExpect(jsonPath("$.details").isEmpty())
				.andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("sensitive"))));
	}

	@RestController
	static class TestController {

		@PostMapping("/test/validate")
		String validate(@Valid @RequestBody ValidationRequest request) {
			return request.name();
		}

		@GetMapping("/test/not-found")
		void notFound() {
			throw new NoSuchElementException("resource missing");
		}

		@GetMapping("/test/denied")
		void denied() {
			throw new AccessDeniedException("not permitted");
		}

		@GetMapping("/test/unhandled")
		void unhandled() {
			throw new IllegalStateException("sensitive internal detail");
		}

		record ValidationRequest(@NotBlank String name) {
		}
	}
}
