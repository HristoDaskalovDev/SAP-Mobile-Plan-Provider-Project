package com.hristodaskalov.mobileplanprovider.controller;

import com.hristodaskalov.mobileplanprovider.dto.GeneralPlanDto;
import com.hristodaskalov.mobileplanprovider.dto.PhonePlanDto;
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
@RequestMapping("/admin/users/{id}/plans")
public class PlanController {

    private final GeneralPlanService generalPlanService;
    private final UserService userService;
    private final PhonePlanService phonePlanService;

    @Autowired
    public PlanController(GeneralPlanService generalPlanService,
                          UserService userService,
                          PhonePlanService phonePlanService) {
        this.generalPlanService = generalPlanService;
        this.userService = userService;
        this.phonePlanService = phonePlanService;
    }

    @GetMapping
    public String getUserDetails(@PathVariable("id") Long userId, Model model) {
        List<PhonePlan> phonePlans = phonePlanService.getPhonePlansByUserId(userId);
        phonePlanService.setPaymentDueDateFromCreatedTs(phonePlans);
        List<PhonePlanDto> phonePlanDtos = ObjectMapper.convertList(phonePlans, PhonePlanDto.class);
        model.addAttribute("phonePlansList", phonePlanDtos);

        UserDto userDto = ObjectMapper.convertObject(userService.getUserById(userId), UserDto.class);
        model.addAttribute("user", userDto);

        List<GeneralPlan> generalPlans = generalPlanService.getAllGeneralPlans();
        List<GeneralPlanDto> generalPlanDtos = ObjectMapper.convertList(generalPlans, GeneralPlanDto.class);
        model.addAttribute("generalPlanList", generalPlanDtos);
        model.addAttribute("generalPlan", new GeneralPlanDto());

        return "user-phone-plans";
    }

    @GetMapping("/create")
    public String getCreatePlanForm(@PathVariable("id") Long userId,
                                    @ModelAttribute GeneralPlanDto generalPlanDto,
                                    Model model) {

        GeneralPlan generalPlan = generalPlanService.getGeneralPlanById(generalPlanDto.getId());
        model.addAttribute("generalPlan", generalPlan);
        model.addAttribute("phonePlan", new PhonePlanDto());
        model.addAttribute("userId", userId);

        return "plan-details";
    }

    @PostMapping("/{generalPlanId}/create")
    public String createPlan(@PathVariable("id") Long userId,
                             @PathVariable("generalPlanId") Long generalPlanId,
                             @ModelAttribute PhonePlanDto phonePlanDto) {

        User user = userService.getUserById(userId);
        GeneralPlan generalPlan = generalPlanService.getGeneralPlanById(generalPlanId);
        phonePlanService.createPhonePlan(generalPlan, user, phonePlanDto.getPhoneNumber());

        return "redirect:/admin?success";
    }
}
