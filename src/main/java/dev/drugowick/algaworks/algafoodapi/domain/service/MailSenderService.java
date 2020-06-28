package dev.drugowick.algaworks.algafoodapi.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

public interface MailSenderService {

    void send(MailMessage message);

    @Getter
    @Builder
    class MailMessage {

        @Singular // Helps the builder to receive one item at a time. Cool.
        private Set<String> recipients;

        @NonNull // Helps builder to demand a value
        private String subject;

        @NonNull // Helps builder to demand a value
        private String body;

        @Singular
        private Map<String, Object> variables;
    }
}
