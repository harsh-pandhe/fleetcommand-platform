package com.fleetcommand.backend.owner;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerProfileRepository extends JpaRepository<OwnerProfile, UUID> {
}
