package com.example.currencyclientstarter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
public class CurrencyService {

    private final RestTemplate restTemplate;
    private final CurrencyClientProperties clientProperties;

    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        log.info("💱 Запрос курса {} -> {}", fromCurrency, toCurrency);

        String url = UriComponentsBuilder
                .fromUriString(clientProperties.getBaseUrl() + "/latest")
                .queryParam("apikey", clientProperties.getApiKey())
                .queryParam("base_currency", fromCurrency)
                .queryParam("currencies", toCurrency)
                .toUriString();

        log.info("GET {}", url);

        CurrencyApiResponse response = restTemplate.getForObject(url, CurrencyApiResponse.class);
        BigDecimal rate = response.getData().get(toCurrency).getValue();
        return rate;
    }

}
