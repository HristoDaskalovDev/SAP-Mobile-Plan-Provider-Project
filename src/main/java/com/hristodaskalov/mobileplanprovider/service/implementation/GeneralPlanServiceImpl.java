package com.hristodaskalov.mobileplanprovider.service.implementation;

import com.hristodaskalov.mobileplanprovider.entitiy.GeneralPlanEntity;
import com.hristodaskalov.mobileplanprovider.exception.InvalidInputException;
import com.hristodaskalov.mobileplanprovider.model.GeneralPlan;
import com.hristodaskalov.mobileplanprovider.repository.GeneralPlanRepository;
import com.hristodaskalov.mobileplanprovider.service.GeneralPlanService;
import com.hristodaskalov.mobileplanprovider.utils.ObjectMapper;

import java.util.List;

public class GeneralPlanServiceImpl implements GeneralPlanService {

    private final GeneralPlanRepository generalPlanRepository;

    public GeneralPlanServiceImpl(GeneralPlanRepository generalPlanRepository) {
        this.generalPlanRepository = generalPlanRepository;
    }

    @Override
    public List<GeneralPlan> getAllGeneralPlans() {
        List<GeneralPlanEntity> generalPlanEntities = generalPlanRepository.findAll();
        return ObjectMapper.convertList(generalPlanEntities, GeneralPlan.class);
    }

    @Override
    public GeneralPlan getGeneralPlanById(Long id) {
        GeneralPlanEntity generalPlanEntity = generalPlanRepository.findById(id).orElseThrow(
                () -> new InvalidInputException(
                        String.format("General plan with id: %d does not exist.", id)
                )
        );
        return ObjectMapper.convertObject(generalPlanEntity, GeneralPlan.class);
    }
}
