package dev.drugowick.algaworks.algafoodapi.infrastructure.service.mail;

import dev.drugowick.algaworks.algafoodapi.config.EmailProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Does not send an email. Instead, prints the processed template to the console.
 */
@Slf4j
public class FakeEmailService extends SmtpSendMailService {

    public FakeEmailService(JavaMailSender mailSender, EmailProperties emailProperties, SpringTemplateEngine templateEngine) {
        super(mailSender, emailProperties, templateEngine);
    }

    @Override
    public void send(MailMessage message) {
        log.info("Fake email created to: {}\n{}", message.getRecipients(), processTemplate(message));
    }
}
