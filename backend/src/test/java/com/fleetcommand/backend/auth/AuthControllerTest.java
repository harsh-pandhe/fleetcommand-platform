package com.fleetcommand.backend.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fleetcommand.backend.user.Role;
import com.fleetcommand.backend.user.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OtpService otpService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void requestEndpointCreatesAnOtpAndRateLimitsDuplicates() throws Exception {
        String request = "{\"phone\":\"+919876543212\"}";

        mockMvc.perform(post("/auth/otp/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isAccepted());

        mockMvc.perform(post("/auth/otp/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isTooManyRequests());
    }

    @Test
    void verifyEndpointCreatesDefaultHirerAndIssuesJwtWithIdAndRole() throws Exception {
        String phone = "+919876543213";
        String code = otpService.requestOtp(phone).orElseThrow();

        String response = mockMvc.perform(post("/auth/otp/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"phone\":\"" + phone + "\",\"code\":\"" + code + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = response.replaceFirst("^\\{\\\"token\\\":\\\"([^\\\"]+)\\\"}$", "$1");
        Claims claims = jwtService.parseClaims(token);
        var user = userRepository.findByPhone(phone).orElseThrow();

        assertThat(claims.getSubject()).isEqualTo(user.getId().toString());
        assertThat(claims.get("userId", String.class)).isEqualTo(user.getId().toString());
        assertThat(claims.get("role", String.class)).isEqualTo(Role.HIRER.name());
    }

    @Test
    void verifyEndpointRejectsAnInvalidOtp() throws Exception {
        mockMvc.perform(post("/auth/otp/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"phone\":\"+919876543214\",\"code\":\"000000\"}"))
                .andExpect(status().isUnauthorized());
    }
}
