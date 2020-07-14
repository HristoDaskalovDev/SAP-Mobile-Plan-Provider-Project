package com.hristodaskalov.mobileplanprovider.service;

import com.hristodaskalov.mobileplanprovider.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRoleByName(String name);
}
