package com.hristodaskalov.mobileplanprovider.dto;

import java.time.LocalDateTime;

public class PhonePlanDto {

    private Long id;
    private String phoneNumber;
    private Integer remainingMinutes;
    private Integer remainingTraffic;
    private Integer remainingSms;
    private Boolean paid;
    private Boolean active;
    private UserDto user;
    private GeneralPlanDto generalPlan;
    private LocalDateTime createdTs;


    public PhonePlanDto() {
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public GeneralPlanDto getGeneralPlan() {
        return generalPlan;
    }

    public void setGeneralPlan(GeneralPlanDto generalPlan) {
        this.generalPlan = generalPlan;
    }

    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }
}
