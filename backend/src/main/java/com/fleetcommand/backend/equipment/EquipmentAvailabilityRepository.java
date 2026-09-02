package com.fleetcommand.backend.equipment;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentAvailabilityRepository extends JpaRepository<EquipmentAvailability, UUID> {
    List<EquipmentAvailability> findByEquipmentIdAndAvailableFromLessThanEqualAndAvailableToGreaterThanEqual(
            UUID equipmentId, Instant windowEnd, Instant windowStart);
}
