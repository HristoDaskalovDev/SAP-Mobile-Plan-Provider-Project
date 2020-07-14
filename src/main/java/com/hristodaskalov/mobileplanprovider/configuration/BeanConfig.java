package com.hristodaskalov.mobileplanprovider.configuration;

import com.hristodaskalov.mobileplanprovider.repository.GeneralPlanRepository;
import com.hristodaskalov.mobileplanprovider.repository.PhonePlanRepository;
import com.hristodaskalov.mobileplanprovider.repository.RoleRepository;
import com.hristodaskalov.mobileplanprovider.repository.UserRepository;
import com.hristodaskalov.mobileplanprovider.service.GeneralPlanService;
import com.hristodaskalov.mobileplanprovider.service.PhonePlanService;
import com.hristodaskalov.mobileplanprovider.service.RoleService;
import com.hristodaskalov.mobileplanprovider.service.UserService;
import com.hristodaskalov.mobileplanprovider.service.implementation.GeneralPlanServiceImpl;
import com.hristodaskalov.mobileplanprovider.service.implementation.PhonePlanServiceImpl;
import com.hristodaskalov.mobileplanprovider.service.implementation.RoleServiceImpl;
import com.hristodaskalov.mobileplanprovider.service.implementation.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanConfig {

    // Basic Security Config
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public RoleService RoleService(RoleRepository roleRepository) {
        return new RoleServiceImpl(roleRepository);
    }

    @Bean
    public UserService UserService(UserRepository userRepository, RoleService roleService,
                                   PhonePlanService phonePlanService) {
        return new UserServiceImpl(userRepository, passwordEncoder(), roleService, phonePlanService);
    }

    @Bean
    public PhonePlanService PhonePlanService(PhonePlanRepository phonePlanRepository) {
        return new PhonePlanServiceImpl(phonePlanRepository);
    }

    @Bean
    public GeneralPlanService GeneralPlanService(GeneralPlanRepository generalPlanRepository) {
        return new GeneralPlanServiceImpl(generalPlanRepository);
    }
}
