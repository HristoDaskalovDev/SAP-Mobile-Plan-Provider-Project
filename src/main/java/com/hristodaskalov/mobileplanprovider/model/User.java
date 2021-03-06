package com.hristodaskalov.mobileplanprovider.model;

import java.time.Clock;
import java.time.LocalDateTime;

public class User {

    private Long id;
    private String name;
    private String username;
    private String password;
    private String nationalId;
    private String address;
    private Role role;
    private LocalDateTime createdTs;

    public User() {
        this.createdTs = LocalDateTime.now(Clock.systemUTC());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedTs() {
        return createdTs;
    }
}
