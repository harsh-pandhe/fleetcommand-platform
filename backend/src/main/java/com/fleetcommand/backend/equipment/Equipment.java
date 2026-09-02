package com.fleetcommand.backend.equipment;

import java.math.BigDecimal;
import java.util.UUID;

import com.fleetcommand.backend.owner.OwnerProfile;
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
@Table(name = "equipment")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private OwnerProfile owner;
    @Column(nullable = false, length = 100)
    private String type;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal rate;
    @Column(nullable = false, length = 255)
    private String location;
    @Column(name = "photos_reference", columnDefinition = "TEXT")
    private String photosReference;

    protected Equipment() { }
    public Equipment(OwnerProfile owner, String type, BigDecimal rate, String location, String photosReference) {
        this.owner = owner;
        this.type = type;
        this.rate = rate;
        this.location = location;
        this.photosReference = photosReference;
    }
    public UUID getId() { return id; }
    public OwnerProfile getOwner() { return owner; }
    public String getType() { return type; }
    public BigDecimal getRate() { return rate; }
    public String getLocation() { return location; }
    public String getPhotosReference() { return photosReference; }
}
