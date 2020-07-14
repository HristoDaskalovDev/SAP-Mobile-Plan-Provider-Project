package com.hristodaskalov.mobileplanprovider.repository;

import com.hristodaskalov.mobileplanprovider.entitiy.GeneralPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneralPlanRepository extends JpaRepository<GeneralPlanEntity, Long> {

    Optional<GeneralPlanEntity> findById(Long id);
}
