package com.hristodaskalov.mobileplanprovider.service.implementation;

import com.hristodaskalov.mobileplanprovider.entitiy.RoleEntity;
import com.hristodaskalov.mobileplanprovider.exception.InvalidInputException;
import com.hristodaskalov.mobileplanprovider.model.Role;
import com.hristodaskalov.mobileplanprovider.repository.RoleRepository;
import com.hristodaskalov.mobileplanprovider.service.RoleService;
import com.hristodaskalov.mobileplanprovider.utils.ObjectMapper;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        return ObjectMapper.convertList(roleEntities, Role.class);
    }

    @Override
    public Role getRoleByName(String name) {
        RoleEntity roleEntity = roleRepository.getRoleEntityByName(name).orElseThrow(
                ()-> new InvalidInputException(
                        String.format("Role with name: %s does not exist.", name)
                )
        );
        return ObjectMapper.convertObject(roleEntity, Role.class);
    }
}
