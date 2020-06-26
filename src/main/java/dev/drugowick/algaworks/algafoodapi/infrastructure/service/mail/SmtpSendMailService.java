package dev.drugowick.algaworks.algafoodapi.infrastructure.service.mail;

import dev.drugowick.algaworks.algafoodapi.config.EmailProperties;
import dev.drugowick.algaworks.algafoodapi.domain.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
public class SmtpSendMailService implements MailSenderService {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void send(MailMessage message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            String processedBody = processTemplate(message);

            helper.setFrom(emailProperties.getSender());
            helper.setTo(message.getRecipients().toArray(new String[0]));
            helper.setSubject(message.getSubject());
            helper.setText(processedBody, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Could not send email", e);
        }

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
