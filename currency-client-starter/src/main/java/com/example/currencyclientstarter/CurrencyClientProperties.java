package com.example.currencyclientstarter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.currency-client")
public class CurrencyClientProperties {

    private String baseUrl = "https://api.currencyapi.com/v3";

    private String apiKey = "cur_live_ezjnnmN8kccEPWYbVRdSvrDjnmE0nFw3V6pFPgUr";

}
