package com.hristodaskalov.mobileplanprovider.model;

import java.time.Clock;
import java.time.LocalDateTime;

public class PhonePlan {

    private Long id;
    private String phoneNumber;
    private Integer remainingMinutes;
    private Integer remainingTraffic;
    private Integer remainingSms;
    private Boolean paid;
    private Boolean active;
    private User user;
    private GeneralPlan generalPlan;
    private LocalDateTime createdTs;

    public PhonePlan() {
        this.createdTs = LocalDateTime.now(Clock.systemUTC());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getRemainingMinutes() {
        return remainingMinutes;
    }

    public void setRemainingMinutes(Integer remainingMinutes) {
        this.remainingMinutes = remainingMinutes;
    }

    public Integer getRemainingTraffic() {
        return remainingTraffic;
    }

    public void setRemainingTraffic(Integer remainingTraffic) {
        this.remainingTraffic = remainingTraffic;
    }

    public Integer getRemainingSms() {
        return remainingSms;
    }

    public void setRemainingSms(Integer remainingSms) {
        this.remainingSms = remainingSms;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GeneralPlan getGeneralPlan() {
        return generalPlan;
    }

    public void setGeneralPlan(GeneralPlan generalPlan) {
        this.generalPlan = generalPlan;
    }

    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }
}
