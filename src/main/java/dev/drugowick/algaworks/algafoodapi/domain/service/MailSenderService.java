package dev.drugowick.algaworks.algafoodapi.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

public interface MailSenderService {

    void send(MailMessage message);

    @Getter
    @Builder
    class MailMessage {
        private Set<String> to;
        private String subject;
        private String body;
    }
}
