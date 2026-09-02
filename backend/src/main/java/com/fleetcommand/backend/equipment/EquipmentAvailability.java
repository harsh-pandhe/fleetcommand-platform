package com.fleetcommand.backend.equipment;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipment_availability")
public class EquipmentAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;
    @Column(nullable = false)
    private Instant availableFrom;
    @Column(nullable = false)
    private Instant availableTo;

    protected EquipmentAvailability() { }
    public EquipmentAvailability(Equipment equipment, Instant availableFrom, Instant availableTo) {
        this.equipment = equipment;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }
    public UUID getId() { return id; }
    public Equipment getEquipment() { return equipment; }
    public Instant getAvailableFrom() { return availableFrom; }
    public Instant getAvailableTo() { return availableTo; }
}
