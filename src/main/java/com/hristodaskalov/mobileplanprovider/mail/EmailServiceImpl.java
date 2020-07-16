package com.hristodaskalov.mobileplanprovider.mail;

import com.hristodaskalov.mobileplanprovider.exception.InvalidInputException;
import com.hristodaskalov.mobileplanprovider.model.PhonePlan;
import com.hristodaskalov.mobileplanprovider.utils.Constants;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {

    private final EmailConfig emailConfig;

    public EmailServiceImpl(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    @Override
    public void sendPaymentDueReminderEmail(PhonePlan phonePlan, String addressList) {
        try {
            Session session = getMailSession();
            Message message = constructMimeMessage(session, addressList, phonePlan);
            Transport.send(message);
        } catch (MessagingException messagingEx) {
            messagingEx.printStackTrace();
        }
    }

    private Session getMailSession() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", this.emailConfig.getHost());
        prop.put("mail.smtp.port", this.emailConfig.getPort());
        prop.put("mail.smtp.auth", this.emailConfig.getAuth());
        prop.put("mail.smtp.starttls.enable", this.emailConfig.getStartTls());

        return Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailConfig.getUsername(), emailConfig.getPassword());
                    }
                }
        );
    }

    private Message constructMimeMessage(Session session, String addressList, PhonePlan phonePlan) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mobileplanprovider@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addressList));
            message.setSubject("Payment Due Reminder");
            message.setText(getEmailContent(phonePlan));

            return message;

        } catch (AddressException adressEx) {
            throw new InvalidInputException("Invalid email address");
        } catch (MessagingException messagingEx) {
            throw new InvalidInputException("Invalid subject or email content");
        }
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
