package dev.drugowick.algaworks.algafoodapi.config;

import dev.drugowick.algaworks.algafoodapi.domain.service.MailSenderService;
import dev.drugowick.algaworks.algafoodapi.infrastructure.service.mail.FakeEmailService;
import dev.drugowick.algaworks.algafoodapi.infrastructure.service.mail.SandboxSendMailService;
import dev.drugowick.algaworks.algafoodapi.infrastructure.service.mail.SmtpSendMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {

    private final JavaMailSender javaMailSender;
    private final EmailProperties emailProperties;
    private final SpringTemplateEngine templateEngine;

    @Bean
    public MailSenderService mailSenderService() {
        switch (emailProperties.getImpl()) {
            case SMTP:
                return new SmtpSendMailService(javaMailSender, emailProperties, templateEngine);
            case FAKE:
                return new FakeEmailService(javaMailSender, emailProperties, templateEngine);
            case SANDBOX:
                return new SandboxSendMailService(javaMailSender, emailProperties, templateEngine);
            default:
                return null;
        }
    }

}
