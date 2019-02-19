package com.bsc.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ValidationService {

    /**
     * Validates if input is in format CZK 34.56, USD -5 etc.
     * @param input
     * @return
     */
    boolean isInputValid(String input);

    void validateArgs(String... args);

    List<String> readAndCheckFile(File f) throws IOException;

}
