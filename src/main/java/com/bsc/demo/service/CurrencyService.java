package com.bsc.demo.service;

import java.math.BigDecimal;

public interface CurrencyService {

    BigDecimal add(String currency, BigDecimal value);

    String getAllAsString();

}
