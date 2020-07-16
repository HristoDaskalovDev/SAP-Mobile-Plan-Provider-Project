package com.hristodaskalov.mobileplanprovider.mail;

import com.hristodaskalov.mobileplanprovider.model.PhonePlan;

public interface EmailService {

    void sendPaymentDueReminderEmail(PhonePlan phonePlan, String addressList);

}
