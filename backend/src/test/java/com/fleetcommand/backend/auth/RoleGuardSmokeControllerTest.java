package com.fleetcommand.backend.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fleetcommand.backend.user.Role;
import com.fleetcommand.backend.user.User;
import com.fleetcommand.backend.user.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RoleGuardSmokeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void rejectsUnauthenticatedRequests() throws Exception {
        mockMvc.perform(get("/guarded/hirer"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void rejectsWrongRole() throws Exception {
        String token = tokenFor(Role.DRIVER, "+919876543221");

        mockMvc.perform(get("/guarded/owner")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void acceptsMatchingRole() throws Exception {
        String token = tokenFor(Role.OWNER, "+919876543222");

        mockMvc.perform(get("/guarded/owner")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("OWNER"));
    }

    private String tokenFor(Role role, String phone) {
        User user = userRepository.save(new User(phone, role, "ACTIVE"));
        return jwtService.issueToken(user);
    }
}
