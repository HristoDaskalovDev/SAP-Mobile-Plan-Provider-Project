package com.hristodaskalov.mobileplanprovider.service.implementation;

import com.hristodaskalov.mobileplanprovider.entitiy.UserEntity;
import com.hristodaskalov.mobileplanprovider.exception.InvalidInputException;
import com.hristodaskalov.mobileplanprovider.model.PhonePlan;
import com.hristodaskalov.mobileplanprovider.model.Role;
import com.hristodaskalov.mobileplanprovider.model.User;
import com.hristodaskalov.mobileplanprovider.repository.UserRepository;
import com.hristodaskalov.mobileplanprovider.service.PhonePlanService;
import com.hristodaskalov.mobileplanprovider.service.RoleService;
import com.hristodaskalov.mobileplanprovider.service.UserService;
import com.hristodaskalov.mobileplanprovider.utils.Constants;
import com.hristodaskalov.mobileplanprovider.utils.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.hristodaskalov.mobileplanprovider.utils.Validation.fieldIsNotEmptyAndHasValidLength;
import static com.hristodaskalov.mobileplanprovider.utils.Validation.validateFieldByRegex;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final PhonePlanService phonePlanService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                           RoleService roleService, PhonePlanService phonePlanService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.phonePlanService = phonePlanService;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        validateFields(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        setRoleUser(user);

        UserEntity userEntity = ObjectMapper.convertObject(user, UserEntity.class);
        userEntity = userRepository.save(userEntity);
        return ObjectMapper.convertObject(userEntity, User.class);
    }

    private void validateFields(User user) {
        fieldIsNotEmptyAndHasValidLength(user.getName(), Constants.NAME_MAX_LENGTH, "name");
        validateFieldByRegex(user.getUsername(), Constants.EMAIL_REGEX, "email");
        validateFieldByRegex(user.getPassword(), Constants.PASSWORD_REGEX, "password");
        validateFieldByRegex(user.getNationalId(), Constants.NATIONAL_ID_REGEX, "national_id");
        fieldIsNotEmptyAndHasValidLength(user.getAddress(), Constants.ADDRESS_MAX_LENGTH, "address");
    }

    private void setRoleUser(User user) {
        Role role = roleService.getRoleByName(Constants.ROLE_USER);
        user.setRole(role);
    }

    @Override
    public User getUserById(Long userId) {
        UserEntity existingUserEntity = userRepository.findById(userId).orElseThrow(
                () -> new InvalidInputException(
                        String.format("User with id: %d does not exist.", userId)
                )
        );
        return ObjectMapper.convertObject(existingUserEntity, User.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUserByUsername(email);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(
                        Collections.singletonList(user.getRole())
                )
        );
    }

    private User getUserByUsername(String email) {
        UserEntity userEntity = userRepository.findByUsername(email).orElseThrow(
                () -> new InvalidInputException(
                        String.format("User with email: %s does not exist.", email)
                )
        );
        return ObjectMapper.convertObject(userEntity, User.class);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsersByPlanNameOrUsername(String name) {
        List<User> usersByName = getAllUsersByName(name);
        List<User> usersByPlanName = getUsersByPhonePlanName(name);
        List<User> result = new ArrayList<>();
        result.addAll(usersByName);
        result.addAll(usersByPlanName);
        return result;
    }

    private List<User> getAllUsersByName(String name) {
        List<UserEntity> userEntities = userRepository.findAllByName(name);
        return ObjectMapper.convertList(userEntities, User.class);
    }

    private List<User> getUsersByPhonePlanName(String planName) {
        List<PhonePlan> phonePlans = phonePlanService.getPhonePlansByName(planName);
        return getDistinctUsersFromPhonePlans(phonePlans);
    }

    //TODO CHECK DB LOADING
    private List<User> getDistinctUsersFromPhonePlans(List<PhonePlan> phonePlans) {
        List<Long> distinctUserIds = phonePlans.stream().map(
                phonePlan -> phonePlan.getUser().getId()).collect(Collectors.toList()
        );
        return getAllUsersByIdsIn(distinctUserIds);
    }

    private List<User> getAllUsersByIdsIn(List<Long> userIds) {
        List<UserEntity> userEntities = userRepository.findAllByIdIn(userIds);
        return ObjectMapper.convertList(userEntities, User.class);
    }

    @Override
    public List<User> getAllUsersWithDuePayment() {
        List<PhonePlan> phonePlans = phonePlanService.getAllDuePhonePlans();
        return getDistinctUsersFromPhonePlans(phonePlans);
    }
}
