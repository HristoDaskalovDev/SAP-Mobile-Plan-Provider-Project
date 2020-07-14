package com.hristodaskalov.mobileplanprovider.service;


import com.hristodaskalov.mobileplanprovider.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User createUser(User user);

    User getUserById(Long userId);

    List<User> getAllUsersByPlanNameOrUsername(String name);

    List<User> getAllUsersWithDuePayment();
}
