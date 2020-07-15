package com.hristodaskalov.mobileplanprovider.mail;

import com.hristodaskalov.mobileplanprovider.model.PhonePlan;
import com.hristodaskalov.mobileplanprovider.utils.Constants;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailService {

    private final JavaMailSender javaMailSender = new JavaMailSenderImpl();


    public void sendPaymendDueReminderEmail(PhonePlan phonePlan) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mobileplanprovider@gmail.com");
        message.setTo(phonePlan.getUser().getUsername());
        message.setSubject("Due payment");
        message.setText(getEmailContent(phonePlan));
        javaMailSender.send(message);
    }

    public void sendTestEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mobileplanprovider@gmail.com");
        message.setTo("itso9813@gmail.com");
        message.setSubject("Due payment");
        message.setText("Test");
        javaMailSender.send(message);
    }

    private String getEmailContent(PhonePlan phonePlan) {

        return String.format("Dear %s,\nWe are reminding you that your payment for number %s is due on %s.",
                phonePlan.getUser().getName(),
                phonePlan.getPhoneNumber(),
                getFormattedDueDate(phonePlan.getCreatedTs())
        );
    }

    private String getFormattedDueDate(LocalDateTime planCreatedTs) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        return planCreatedTs.format(formatter);
    }
}
