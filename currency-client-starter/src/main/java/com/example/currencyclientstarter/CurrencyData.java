package com.example.currencyclientstarter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyData {
    private String code;
    private BigDecimal value;
}
