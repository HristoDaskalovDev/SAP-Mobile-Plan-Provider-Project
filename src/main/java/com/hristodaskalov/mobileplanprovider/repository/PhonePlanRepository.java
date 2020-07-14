package com.hristodaskalov.mobileplanprovider.repository;

import com.hristodaskalov.mobileplanprovider.entitiy.PhonePlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhonePlanRepository extends JpaRepository<PhonePlanEntity, Long> {

    @Query(value = "select * from phone_plan " +
            "where (extract(day from created_ts) - extract(day from NOW())) > 0 " +
            "AND paid = false " +
            "AND active = true", nativeQuery = true)
    List<PhonePlanEntity> getAllOverduePlans();

    @Query(value = "select * from phone_plan " +
            "where paid = false AND active = true", nativeQuery = true)
    List<PhonePlanEntity> getAllDuePlans();

    List<PhonePlanEntity> getAllByUserUsername(String email);

    List<PhonePlanEntity> getAllByGeneralPlan_name(String name);

    List<PhonePlanEntity> getAllByUserId(Long userId);
}
