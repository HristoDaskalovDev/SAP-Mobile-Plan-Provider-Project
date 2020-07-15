package com.hristodaskalov.mobileplanprovider.controller;

import com.hristodaskalov.mobileplanprovider.dto.SearchDto;
import com.hristodaskalov.mobileplanprovider.dto.UserDto;
import com.hristodaskalov.mobileplanprovider.model.User;
import com.hristodaskalov.mobileplanprovider.service.UserService;
import com.hristodaskalov.mobileplanprovider.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
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
        return "register-user";
    }

    @PostMapping("/users/create")
    public String registerUserAccount(@ModelAttribute("user") UserDto userDto, Model model) {
        User user = ObjectMapper.convertObject(userDto, User.class);
        userService.createUser(user);
        return "redirect:/admin?success";
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
}