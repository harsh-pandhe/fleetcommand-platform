package com.fleetcommand.backend.user;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(nullable = false, length = 20)
    private String status;

    protected User() {
    }

    public User(String phone, Role role, String status) {
        this.phone = phone;
        this.role = role;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }
}
