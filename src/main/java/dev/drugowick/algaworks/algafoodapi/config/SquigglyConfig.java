package dev.drugowick.algaworks.algafoodapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SquigglyConfig {

    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {
        Squiggly.init(objectMapper, new RequestSquigglyContextProvider());
        // You can also configure the name of the URL param to use:
        //Squiggly.init(objectMapper, new RequestSquigglyContextProvider("fieldsList", null));

        FilterRegistrationBean<SquigglyRequestFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new SquigglyRequestFilter());
        filterRegistration.setOrder(1);
        // You can define the Filter to operate only for some endpoints. If not set, works for all requests.
        filterRegistration.setUrlPatterns(Arrays.asList(
                "/orders/*",
                "/restaurants/*",
                "/cuisines/*"));

        return filterRegistration;
    }
}
