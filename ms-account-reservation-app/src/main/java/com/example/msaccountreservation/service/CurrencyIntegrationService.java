package com.example.msaccountreservation.service;

import com.example.currencyclientstarter.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyIntegrationService {

    private final CurrencyService currencyService;

    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        log.info("Запрос курса {} -> {}", fromCurrency, toCurrency);

        try {
            BigDecimal rate = currencyService.getExchangeRate(fromCurrency, toCurrency);
            log.info("Курс получен: {} -> {} = {}", fromCurrency, toCurrency, rate);
            return rate;

        } catch (Exception e) {
            log.error("Ошибка при получении курса {} -> {}: {}",
                    fromCurrency, toCurrency, e.getMessage(), e);
            throw new RuntimeException("Не удалось получить курс валют", e);
        }
    }
}
