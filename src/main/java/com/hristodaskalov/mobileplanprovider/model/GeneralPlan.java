package com.hristodaskalov.mobileplanprovider.model;

import java.time.Clock;
import java.time.LocalDateTime;

public class GeneralPlan {

    private Long id;
    private String name;
    private Integer minutes;
    private Integer traffic;
    private Integer sms;
    private Double price;
    private LocalDateTime createdTs;

    public GeneralPlan() {
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

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getTraffic() {
        return traffic;
    }

    public void setTraffic(Integer traffic) {
        this.traffic = traffic;
    }

    public Integer getSms() {
        return sms;
    }

    public void setSms(Integer sms) {
        this.sms = sms;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    //TODO MAYBE DELETE TIMESTAMP FIELDS WHERE THEY ARE NOT NEEDED
}
