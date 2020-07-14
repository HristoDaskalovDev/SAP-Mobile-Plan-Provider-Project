package com.hristodaskalov.mobileplanprovider.repository;

import com.hristodaskalov.mobileplanprovider.entitiy.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String email);

    List<UserEntity> findAllByName(String name);

    List<UserEntity> findAllByIdIn(List<Long> userIds);
}
