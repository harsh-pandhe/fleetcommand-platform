package com.fleetcommand.backend.owner;

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
@Table(name = "owner_profiles")
public class OwnerProfile {

    @Id
    private UUID userId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 100)
    private String contactName;

    @Column(nullable = false, length = 20)
    private String contactPhone;

    @Column(nullable = false, length = 20)
    private String verificationStatus;

    protected OwnerProfile() {
    }

    public OwnerProfile(User user, String contactName, String contactPhone) {
        this.user = user;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.verificationStatus = "PENDING";
    }

    public void updateContact(String contactName, String contactPhone) {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    public UUID getUserId() { return userId; }
    public String getContactName() { return contactName; }
    public String getContactPhone() { return contactPhone; }
    public String getVerificationStatus() { return verificationStatus; }
}
