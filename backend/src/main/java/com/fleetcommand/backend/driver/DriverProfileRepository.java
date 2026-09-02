package com.fleetcommand.backend.driver;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverProfileRepository extends JpaRepository<DriverProfile, UUID> {
}
