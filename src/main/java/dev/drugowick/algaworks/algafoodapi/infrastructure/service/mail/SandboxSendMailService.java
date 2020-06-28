package dev.drugowick.algaworks.algafoodapi.infrastructure.service.mail;

import dev.drugowick.algaworks.algafoodapi.config.EmailProperties;
import dev.drugowick.algaworks.algafoodapi.domain.service.MailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Sends the email to a specific email address regardless of the data coming from MailMessage
 */
@Slf4j
public class SandboxSendMailService extends SmtpSendMailService implements MailSenderService {

    public SandboxSendMailService(JavaMailSender mailSender, EmailProperties emailProperties, SpringTemplateEngine templateEngine) {
        super(mailSender, emailProperties, templateEngine);
    }

    @Override
    public void send(MailMessage message) {
        try {
            MimeMessage mimeMessage = getMimeMessage(message);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Could not send email", e);
        }

    }

    @Override
    protected MimeMessage getMimeMessage(MailMessage message) throws MessagingException {
        MimeMessage mimeMessage = super.getMimeMessage(message);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setTo(emailProperties.getSandbox().getRecipient());

        return mimeMessage;
    }

    protected String processTemplate(MailMessage message) {
        try {
            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(message.getVariables());

            return templateEngine.process(message.getBody(), thymeleafContext);
        } catch (Exception e) {
            throw new EmailException("Could not process email template", e);
        }
    }
}
