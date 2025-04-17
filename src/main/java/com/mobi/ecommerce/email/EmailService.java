//package com.mobi.ecommerce.email;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static java.nio.charset.StandardCharsets.UTF_8;
//import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;
//
//@Service
//public class EmailService {
//    private final JavaMailSender mailSender;
//    private final SpringTemplateEngine templateEngine;
//
//    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
//        this.mailSender = mailSender;
//        this.templateEngine = templateEngine;
//    }
//
//    @Async
//    public void sendEmail(
//            String to,
//            String username,
//            EmailTemplateName emailTemplateName,
//            String confirmationUrl,
//            String activationCode,
//            String subject
//    ) throws MessagingException {
//        String templateName ;
//        if (emailTemplateName == null) {
//            templateName = "confirm-email";
//        } else {
//            templateName = emailTemplateName.name().toLowerCase();
//        }
//
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED, UTF_8.name());
//
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("username", username);
//
//        properties.put("confirmationUrl", confirmationUrl);
//        properties.put("activation_code", activationCode);
//
//        Context context = new Context();
//        context.setVariables(properties);
//
//        String fromEmail = "contact@mobin.com";  // Consider moving this to application.yml
//
//        helper.setFrom(fromEmail);
//        helper.setTo(to);
//        helper.setSubject(subject);
//        String template = templateEngine.process(templateName, context);
//        helper.setText(template, true);
//
//        mailSender.send(mimeMessage);
//    }
//}
