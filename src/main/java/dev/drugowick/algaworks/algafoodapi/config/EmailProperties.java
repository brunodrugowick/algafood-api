package dev.drugowick.algaworks.algafoodapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Component
@ConfigurationProperties("algafoodapi.mail")
@Getter @Setter
public class EmailProperties {

    @NotNull
    private String sender;
}
