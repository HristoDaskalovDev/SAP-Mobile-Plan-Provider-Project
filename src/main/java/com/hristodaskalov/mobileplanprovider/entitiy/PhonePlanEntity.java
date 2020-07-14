package com.hristodaskalov.mobileplanprovider.entitiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "phone_plan")
public class PhonePlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_num")
    private String phoneNumber;

    @Column(name = "remaining_minutes")
    private Integer remainingMinutes;

    @Column(name = "remaining_traffic")
    private Integer remainingTraffic;

    @Column(name = "remaining_sms")
    private Integer remainingSms;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "general_plan_id")
    private GeneralPlanEntity generalPlan;

    @Column(name = "createdTs")
    private LocalDateTime createdTs;

    public PhonePlanEntity() {
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public GeneralPlanEntity getGeneralPlan() {
        return generalPlan;
    }

    public void setGeneralPlan(GeneralPlanEntity generalPlan) {
        this.generalPlan = generalPlan;
    }

    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }
}
