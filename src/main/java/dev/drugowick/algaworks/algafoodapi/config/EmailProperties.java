package dev.drugowick.algaworks.algafoodapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Configuration
@ConfigurationProperties("algafoodapi.mail")
@Getter @Setter
public class EmailProperties {

    /**
     * Specifies a sender email address. Every email sent from the plataform will use this address.
     */
    @NotBlank
    private String sender;

    /**
     * Configures the email service for Production (SMTP) or Development (FAKE, SANDBOX).
     */
    private EmailImpl impl = EmailImpl.FAKE;

    /**
     * Specifies a sandbox email to be used when it's necessary to  actually  send an email via SMTP but not to any
     * email addresses existents on the platform.
     */
    private Sandbox sandbox;

    public enum EmailImpl { SMTP, FAKE, SANDBOX }
    @Getter @Setter public static class Sandbox { private String recipient; }
    
}
