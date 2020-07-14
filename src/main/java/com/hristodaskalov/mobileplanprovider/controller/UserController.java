package com.hristodaskalov.mobileplanprovider.controller;

import com.hristodaskalov.mobileplanprovider.dto.PhonePlanDto;
import com.hristodaskalov.mobileplanprovider.model.PhonePlan;
import com.hristodaskalov.mobileplanprovider.service.PhonePlanService;
import com.hristodaskalov.mobileplanprovider.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/phone-plans")
public class UserController {

    private PhonePlanService phonePlanService;

    @Autowired
    public UserController(PhonePlanService phonePlanService) {
        this.phonePlanService = phonePlanService;
    }

    @GetMapping
    public String getUserPanel(Model model) {
        List<PhonePlan> phonePlans = phonePlanService.getLoggedUserPhonePlans();
        List<PhonePlanDto> phonePlanDtos = ObjectMapper.convertList(phonePlans, PhonePlanDto.class);
        model.addAttribute("phonePlansList", phonePlanDtos);

        return "user-panel";
    }
}
