package com.bsc.demo.service;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ValidationDefaultServiceTest {

    private ValidationDefaultService validationDefaultService = new ValidationDefaultService();

    @Test
    public void validateInputLineTest() {
        Assert.assertTrue(validationDefaultService.isInputValid("USD 10"));
        Assert.assertTrue(validationDefaultService.isInputValid("CZK -10"));
        Assert.assertTrue(validationDefaultService.isInputValid("USD 10.1"));
        Assert.assertTrue(validationDefaultService.isInputValid("USD 10.12"));
        Assert.assertTrue(validationDefaultService.isInputValid("USD 10.123"));
        Assert.assertTrue(validationDefaultService.isInputValid("USD 1000000"));
        Assert.assertTrue(validationDefaultService.isInputValid("USD -10000.123"));

        Assert.assertFalse(validationDefaultService.isInputValid("usd 10"));
        Assert.assertFalse(validationDefaultService.isInputValid("US 10"));
        Assert.assertFalse(validationDefaultService.isInputValid("USDQ 10"));
        Assert.assertFalse(validationDefaultService.isInputValid("USD10"));
        Assert.assertFalse(validationDefaultService.isInputValid("USD 10."));
        Assert.assertFalse(validationDefaultService.isInputValid("USD 10.1234"));
    }

    @Test
    public void validateEmptyArgsTest() {
        validationDefaultService.validateArgs(null);
        validationDefaultService.validateArgs(new String[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateTooManyArgsTest() {
        validationDefaultService.validateArgs("1", "2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateNoSuchFileTest() {
        validationDefaultService.validateArgs("no-such-file");
    }

    @Test
    public void validateFileSuccessTest() {
        validationDefaultService.validateArgs("currency-test.txt");
    }

    @Test
    public void readFileTest() throws Exception {
        List<String> result = validationDefaultService.readAndCheckFile(new File("currency-test.txt"));
        Assert.assertEquals("USD 10", result.get(0));
        Assert.assertEquals("CZK 5", result.get(1));
        Assert.assertEquals("USD -5", result.get(2));
        Assert.assertEquals("CZK -10", result.get(3));
    }

}
