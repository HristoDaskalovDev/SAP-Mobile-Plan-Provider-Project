package com.hristodaskalov.mobileplanprovider.controller;

import com.hristodaskalov.mobileplanprovider.dto.GeneralPlanDto;
import com.hristodaskalov.mobileplanprovider.dto.PhonePlanDto;
import com.hristodaskalov.mobileplanprovider.model.GeneralPlan;
import com.hristodaskalov.mobileplanprovider.model.User;
import com.hristodaskalov.mobileplanprovider.service.GeneralPlanService;
import com.hristodaskalov.mobileplanprovider.service.PhonePlanService;
import com.hristodaskalov.mobileplanprovider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
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

    @GetMapping("/users/{id}/plans")
    public String getPlanDetailsForm(@PathVariable("id") Long userId,
                                     @ModelAttribute GeneralPlanDto generalPlanDto,
                                     Model model) {

        GeneralPlan generalPlan = generalPlanService.getGeneralPlanById(generalPlanDto.getId());
        model.addAttribute("generalPlan", generalPlan);
        model.addAttribute("phonePlan", new PhonePlanDto());
        model.addAttribute("userId", userId);

        return "plan-details";
    }

    @PostMapping("/users/{id}/plans/{generalPlanId}")
    public String createPlan(@PathVariable("id") Long userId,
                             @PathVariable("generalPlanId") Long generalPlanId,
                             @ModelAttribute PhonePlanDto phonePlanDto) {

        User user = userService.getUserById(userId);
        GeneralPlan generalPlan = generalPlanService.getGeneralPlanById(generalPlanId);
        phonePlanService.createPhonePlan(generalPlan, user, phonePlanDto.getPhoneNumber());

        return "redirect:/admin?success";
    }
}
