package com.fleetcommand.backend.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findsUserByPhone() {
        User user = userRepository.save(new User("+919876543210", Role.HIRER, "ACTIVE"));

        var found = userRepository.findByPhone("+919876543210");

        assertThat(found).contains(user);
        assertThat(found.orElseThrow().getRole()).isEqualTo(Role.HIRER);
        assertThat(found.orElseThrow().getStatus()).isEqualTo("ACTIVE");
    }

    @Test
    void returnsEmptyWhenPhoneIsNotRegistered() {
        assertThat(userRepository.findByPhone("+919876543210")).isEmpty();
    }
}
