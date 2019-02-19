package com.bsc.demo.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ValidationDefaultService implements ValidationService {

    @Override
    public boolean isInputValid(String input) {
        return Pattern.matches("^[A-Z]{3} [-+]?\\d{1,7}(\\.\\d{1,3})?$", input);
    }

    @Override
    public void validateArgs(String... args) {
        if (args == null || args.length == 0) {
            return;
        }
        if (args.length > 1) {
            throw new IllegalArgumentException("Expects max 1 argument. Run java -jar bscdemo.jar path_to_file");
        }
        File f = new File(args[0]);
        if (!f.exists() || !f.isFile()) {
            throw new IllegalArgumentException("File does not exists");
        }
        if (!f.canRead()) {
            throw new IllegalArgumentException("File is not readable");
        }
        if (f.length() > 1024 * 1024) {
            throw new IllegalArgumentException("Max file size is 1 MB");
        }
    }

    @Override
    public List<String> readAndCheckFile(File f) throws IOException {
        List<String> lines = FileUtils.readLines(f, "UTF-8");

        if (!lines.parallelStream().allMatch(this::isInputValid)) {
            throw new IllegalArgumentException("File contains lines with bad format");
        }

        return lines;
    }

}
