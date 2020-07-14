package com.hristodaskalov.mobileplanprovider.controller;

import com.hristodaskalov.mobileplanprovider.dto.GeneralPlanDto;
import com.hristodaskalov.mobileplanprovider.dto.PhonePlanDto;
import com.hristodaskalov.mobileplanprovider.dto.SearchDto;
import com.hristodaskalov.mobileplanprovider.dto.UserDto;
import com.hristodaskalov.mobileplanprovider.model.GeneralPlan;
import com.hristodaskalov.mobileplanprovider.model.PhonePlan;
import com.hristodaskalov.mobileplanprovider.model.User;
import com.hristodaskalov.mobileplanprovider.service.GeneralPlanService;
import com.hristodaskalov.mobileplanprovider.service.PhonePlanService;
import com.hristodaskalov.mobileplanprovider.service.UserService;
import com.hristodaskalov.mobileplanprovider.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private PhonePlanService phonePlanService;

    private GeneralPlanService generalPlanService;

    @Autowired
    public AdminController(UserService userService,
                           PhonePlanService phonePlanService,
                           GeneralPlanService generalPlanService) {
        this.userService = userService;
        this.phonePlanService = phonePlanService;
        this.generalPlanService = generalPlanService;
    }

    @GetMapping
    public String getAdminPanel(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("search", new SearchDto());
        return "admin-panel";
    }

    @GetMapping("/users/registration")
    public String getRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    //TODO check of this works
    @PostMapping("/users/create")
    public String registerUserAccount(@ModelAttribute("user") UserDto registrationDto) {
        User user = ObjectMapper.convertObject(registrationDto, User.class);
        userService.createUser(user);
        return "redirect:/registration?success";
    }

    @GetMapping("/users/find")
    public String processFindForm(@ModelAttribute("search") SearchDto search, Model model) {
        List<User> users = userService.getAllUsersByPlanNameOrUsername(search.getParam());
        List<UserDto> userDtos = ObjectMapper.convertList(users, UserDto.class);
        model.addAttribute("selections", userDtos);
        return "user-list";
    }

    @GetMapping("/users/find/due-payment")
    public String processFindFormDuePayment(Model model) {
        List<User> users = userService.getAllUsersWithDuePayment();
        List<UserDto> userDtos = ObjectMapper.convertList(users, UserDto.class);
        model.addAttribute("selections", userDtos);
        return "user-list";
    }

    //TODO FIX
    @GetMapping("/users/{id}/details")
    public String getUserDetails(@PathVariable("id") Long userId, Model model) {
        List<PhonePlan> phonePlans = phonePlanService.getPhonePlansByUserId(userId);
        List<PhonePlanDto> phonePlanDtos = ObjectMapper.convertList(phonePlans, PhonePlanDto.class);
        model.addAttribute("phonePlansList", phonePlanDtos);

        if (!phonePlanDtos.isEmpty()) {
            UserDto userDto = ObjectMapper.convertObject(phonePlanDtos.get(0).getUser(), UserDto.class);
            model.addAttribute("user", userDto);
        }

        List<GeneralPlan> generalPlans = generalPlanService.getAllGeneralPlans();
        List<GeneralPlanDto> generalPlanDtos = ObjectMapper.convertList(generalPlans, GeneralPlanDto.class);
        model.addAttribute("generalPlanList", generalPlanDtos);
        model.addAttribute("generalPlan", new GeneralPlanDto());

        return "user-details";
    }
}