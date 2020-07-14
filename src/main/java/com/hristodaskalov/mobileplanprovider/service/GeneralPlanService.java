package com.hristodaskalov.mobileplanprovider.service;

import com.hristodaskalov.mobileplanprovider.model.GeneralPlan;

import java.util.List;

public interface GeneralPlanService {

    List<GeneralPlan> getAllGeneralPlans();

    GeneralPlan getGeneralPlanById(Long id);
}
