package com.hristodaskalov.mobileplanprovider.model;

import java.time.Clock;
import java.time.LocalDateTime;

public class Role {

    private Long id;
    private String name;
    private LocalDateTime createdTs;

    public Role() {
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

    public LocalDateTime getCreatedTs() {
        return createdTs;
    }
}
