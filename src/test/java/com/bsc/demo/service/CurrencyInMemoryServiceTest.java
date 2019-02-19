package com.bsc.demo.service;


import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CurrencyInMemoryServiceTest {

    private CurrencyInMemoryService currencyInMemoryService = new CurrencyInMemoryService();

    @Test
    public void addTest() {
        String czk = "CZK";
        String usd = "USD";

        Assert.assertEquals(currencyInMemoryService.add(czk, BigDecimal.ZERO), BigDecimal.ZERO);
        Assert.assertEquals(currencyInMemoryService.add(czk, new BigDecimal("-10")), new BigDecimal("-10"));
        Assert.assertEquals(currencyInMemoryService.add(czk, new BigDecimal("20")), BigDecimal.TEN);
        Assert.assertEquals(currencyInMemoryService.add(czk, new BigDecimal("500.999")), new BigDecimal("510.999"));

        Assert.assertEquals(currencyInMemoryService.add(usd, BigDecimal.ZERO), BigDecimal.ZERO);
        Assert.assertEquals(currencyInMemoryService.add(usd, new BigDecimal("-10")), new BigDecimal("-10"));
        Assert.assertEquals(currencyInMemoryService.add(usd, new BigDecimal("20")), BigDecimal.TEN);
        Assert.assertEquals(currencyInMemoryService.add(usd, new BigDecimal("500.999")), new BigDecimal("510.999"));
    }

    @Test
    public void getAsStringTest() {
        currencyInMemoryService.add("CZK", new BigDecimal("5"));
        currencyInMemoryService.add("USD", new BigDecimal("10"));
        currencyInMemoryService.add("GBP", new BigDecimal("0"));

        String result = currencyInMemoryService.getAllAsString();
        String expectedResult = "CZK 5" + "\n" + "USD 10";

        Assert.assertEquals(expectedResult, result);

    }

}
