package com.fleetcommand.backend.equipment;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;

import com.fleetcommand.backend.owner.OwnerProfile;
import com.fleetcommand.backend.owner.OwnerProfileRepository;
import com.fleetcommand.backend.user.Role;
import com.fleetcommand.backend.user.User;
import com.fleetcommand.backend.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class EquipmentRepositoryTest {
    @Autowired private UserRepository users;
    @Autowired private OwnerProfileRepository owners;
    @Autowired private EquipmentRepository equipment;
    @Autowired private EquipmentAvailabilityRepository availability;

    @Test
    void findsEquipmentForOwnerAndAvailabilityOverlappingWindow() {
        User user = users.save(new User("+919876543230", Role.OWNER, "ACTIVE"));
        OwnerProfile owner = owners.save(new OwnerProfile(user, "Owner", "+919876543230"));
        Equipment machine = equipment.save(new Equipment(owner, "Excavator", new BigDecimal("2500.00"), "Pune", "photos/e-1"));
        Instant from = Instant.parse("2026-09-10T00:00:00Z");
        Instant to = Instant.parse("2026-09-15T00:00:00Z");
        availability.save(new EquipmentAvailability(machine, from, to));

        assertThat(equipment.findByOwner_UserId(user.getId())).containsExactly(machine);
        assertThat(availability.findByEquipmentIdAndAvailableFromLessThanEqualAndAvailableToGreaterThanEqual(
                machine.getId(), Instant.parse("2026-09-12T00:00:00Z"), Instant.parse("2026-09-11T00:00:00Z")))
                .hasSize(1);
    }
}
