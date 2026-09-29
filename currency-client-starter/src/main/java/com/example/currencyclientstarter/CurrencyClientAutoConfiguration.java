package com.example.currencyclientstarter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Slf4j
@AutoConfiguration
@ConditionalOnClass(CurrencyService.class)
@ConditionalOnProperty(
        prefix = "app.currency-client",
        name = "enabled",
        matchIfMissing = true
)
@EnableConfigurationProperties(CurrencyClientProperties.class)
public class CurrencyClientAutoConfiguration {

    private final CurrencyClientProperties properties;

    public CurrencyClientAutoConfiguration(CurrencyClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate currencyRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean
    public CurrencyService currencyService(RestTemplate currencyRestTemplate) {
        return new CurrencyService(currencyRestTemplate, properties);
    }
}
