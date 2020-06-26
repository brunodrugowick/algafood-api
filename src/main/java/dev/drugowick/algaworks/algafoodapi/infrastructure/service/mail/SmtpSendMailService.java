package dev.drugowick.algaworks.algafoodapi.infrastructure.service.mail;

import dev.drugowick.algaworks.algafoodapi.config.EmailProperties;
import dev.drugowick.algaworks.algafoodapi.domain.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class SmtpSendMailService implements MailSenderService {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;

    @Override
    public void send(MailMessage message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(emailProperties.getSender());
            helper.setTo(message.getTo().toArray(new String[0]));
            helper.setSubject(message.getSubject());
            helper.setText(message.getBody(), true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Could  not send email.", e);
        }

    }
}
