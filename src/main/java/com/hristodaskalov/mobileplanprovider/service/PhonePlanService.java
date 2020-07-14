package com.hristodaskalov.mobileplanprovider.service;

import com.hristodaskalov.mobileplanprovider.model.GeneralPlan;
import com.hristodaskalov.mobileplanprovider.model.PhonePlan;
import com.hristodaskalov.mobileplanprovider.model.User;

import java.util.List;

public interface PhonePlanService {

    void deactivateOverduePlans();

    PhonePlan createPhonePlan(GeneralPlan generalPlan, User user, String phoneNum);

    List<PhonePlan> getLoggedUserPhonePlans();

    List<PhonePlan> getPhonePlansByUserId(Long userId);

    List<PhonePlan> getPhonePlansByName(String name);

    List<PhonePlan> getAllDuePhonePlans();
}
