package com.fleetcommand.backend.driver;

import java.util.UUID;

import com.fleetcommand.backend.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "driver_profiles")
public class DriverProfile {
    @Id
    private UUID userId;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false, length = 100)
    private String fullName;
    @Column(nullable = false, length = 50)
    private String licenseNumber;
    @Column(nullable = false, length = 20)
    private String status;

    protected DriverProfile() { }
    public DriverProfile(User user, String fullName, String licenseNumber, String status) {
        this.user = user;
        this.fullName = fullName;
        this.licenseNumber = licenseNumber;
        this.status = status;
    }
    public void update(String fullName, String licenseNumber, String status) {
        this.fullName = fullName;
        this.licenseNumber = licenseNumber;
        this.status = status;
    }
    public UUID getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getLicenseNumber() { return licenseNumber; }
    public String getStatus() { return status; }
}
