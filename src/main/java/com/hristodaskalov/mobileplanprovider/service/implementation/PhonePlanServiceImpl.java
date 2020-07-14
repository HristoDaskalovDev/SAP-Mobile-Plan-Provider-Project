package com.hristodaskalov.mobileplanprovider.service.implementation;

import com.hristodaskalov.mobileplanprovider.entitiy.PhonePlanEntity;
import com.hristodaskalov.mobileplanprovider.model.GeneralPlan;
import com.hristodaskalov.mobileplanprovider.model.PhonePlan;
import com.hristodaskalov.mobileplanprovider.model.User;
import com.hristodaskalov.mobileplanprovider.repository.PhonePlanRepository;
import com.hristodaskalov.mobileplanprovider.service.PhonePlanService;
import com.hristodaskalov.mobileplanprovider.utils.Constants;
import com.hristodaskalov.mobileplanprovider.utils.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.hristodaskalov.mobileplanprovider.utils.Validation.fieldIsNotEmptyAndHasValidLength;
import static com.hristodaskalov.mobileplanprovider.utils.Validation.validateFieldByRegex;

public class PhonePlanServiceImpl implements PhonePlanService {

    private final PhonePlanRepository phonePlanRepository;

    public PhonePlanServiceImpl(PhonePlanRepository phonePlanRepository) {
        this.phonePlanRepository = phonePlanRepository;
    }

    @Override
    @Transactional
    public void deactivateOverduePlans() {
        List<PhonePlan> overduePlans = getOverduePlans();
        for (PhonePlan overduePlan : overduePlans) {
            overduePlan.setActive(false);
        }

        List<PhonePlanEntity> overduePlanEntities = ObjectMapper.convertList(overduePlans, PhonePlanEntity.class);
        phonePlanRepository.saveAll(overduePlanEntities);
    }

    private List<PhonePlan> getOverduePlans() {
        List<PhonePlanEntity> phonePlanEntities = phonePlanRepository.getAllOverduePlans();
        return ObjectMapper.convertList(phonePlanEntities, PhonePlan.class);
    }

    @Override
    @Transactional
    public PhonePlan createPhonePlan(GeneralPlan generalPlan, User user, String phoneNum) {
        PhonePlan phonePlan = copyPhonePlanFromGeneral(generalPlan);

        validatePhoneNumber(phoneNum);
        phonePlan.setPhoneNumber(phoneNum);
        phonePlan.setUser(user);

        return savePhonePlan(phonePlan);
    }

    private void validatePhoneNumber(String phoneNumber) {
        fieldIsNotEmptyAndHasValidLength(phoneNumber, Constants.PHONE_MAX_LENGTH, "phone number");
        validateFieldByRegex(phoneNumber, Constants.PHONE_NUMBER_REGEX, "phone number");
    }

    private PhonePlan copyPhonePlanFromGeneral(GeneralPlan generalPlan) {
        PhonePlan phonePlan = new PhonePlan();
        phonePlan.setGeneralPlan(generalPlan);
        phonePlan.setRemainingMinutes(generalPlan.getMinutes());
        phonePlan.setRemainingTraffic(generalPlan.getTraffic());
        phonePlan.setRemainingSms(generalPlan.getSms());
        phonePlan.setActive(true);
        phonePlan.setPaid(true);
        return phonePlan;
    }

    private PhonePlan savePhonePlan(PhonePlan phonePlan) {
        PhonePlanEntity phonePlanEntity = ObjectMapper.convertObject(phonePlan, PhonePlanEntity.class);
        phonePlanEntity = phonePlanRepository.save(phonePlanEntity);
        return ObjectMapper.convertObject(phonePlanEntity, PhonePlan.class);
    }

    @Override
    public List<PhonePlan> getLoggedUserPhonePlans() {
        String email = getUsernameFromPrincipal();
        List<PhonePlanEntity> phonePlanEntities = phonePlanRepository.getAllByUserUsername(email);
        List<PhonePlan> phonePlans = ObjectMapper.convertList(phonePlanEntities, PhonePlan.class);
        setPaymentDueDateFromCreatedTs(phonePlans);
        return phonePlans;
    }

    private String getUsernameFromPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    private void setPaymentDueDateFromCreatedTs(List<PhonePlan> phonePlans) {
        if (!phonePlans.isEmpty()) {

            //TODO see to format date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
            for (PhonePlan phonePlan : phonePlans) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime paymentDueDate = phonePlan.getCreatedTs();
                int years = now.getYear() - paymentDueDate.getYear();
                int months = now.getMonthValue() - paymentDueDate.getMonthValue();
                paymentDueDate = paymentDueDate.plusYears(years);
                paymentDueDate = paymentDueDate.plusMonths(months);
//                paymentDueDate = paymentDueDate.re
                phonePlan.setCreatedTs(paymentDueDate);
            }
        }
    }

    @Override
    public List<PhonePlan> getPhonePlansByName(String name) {
        List<PhonePlanEntity> phonePlanEntities = phonePlanRepository.getAllByGeneralPlan_name(name);
        return ObjectMapper.convertList(phonePlanEntities, PhonePlan.class);
    }

    @Override
    public List<PhonePlan> getPhonePlansByUserId(Long userId) {
        List<PhonePlanEntity> phonePlanEntities = phonePlanRepository.getAllByUserId(userId);
        return ObjectMapper.convertList(phonePlanEntities, PhonePlan.class);
    }

    @Override
    public List<PhonePlan> getAllDuePhonePlans() {
        List<PhonePlanEntity> phonePlanEntities = phonePlanRepository.getAllDuePlans();
        return ObjectMapper.convertList(phonePlanEntities, PhonePlan.class);
    }
}
