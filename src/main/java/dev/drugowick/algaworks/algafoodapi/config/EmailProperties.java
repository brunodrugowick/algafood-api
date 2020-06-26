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

    @NotBlank
    private String sender;

    private EmailImpl impl = EmailImpl.FAKE;

    public static enum EmailImpl { SMTP, FAKE }
    
}
