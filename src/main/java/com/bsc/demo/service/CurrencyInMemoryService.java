package com.bsc.demo.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

@Service
public class CurrencyInMemoryService implements CurrencyService {

    private final Map<String, BigDecimal> storage = new LinkedHashMap<>(256);

    @Override
    public synchronized BigDecimal add(String currency, BigDecimal value) {
        BigDecimal currentValue = storage.get(currency);
        BigDecimal newValue = currentValue == null ? value : currentValue.add(value);
        storage.put(currency, newValue);
        return newValue;
    }

    @Override
    public String getAllAsString() {
        StringJoiner sj = new StringJoiner("\n");
        storage.entrySet()
                .stream()
                .filter((e) -> !e.getValue().equals(BigDecimal.ZERO))
                .forEach(e -> sj.add(e.getKey() + " " + e.getValue()));
        return sj.toString();
    }

}
