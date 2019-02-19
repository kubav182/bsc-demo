package com.bsc.demo;

import com.bsc.demo.service.CurrencyService;
import com.bsc.demo.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.StringJoiner;

@EnableScheduling
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ValidationService validationService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        try {
            processArgs(args);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            System.out.println(getFormatHelp());
            System.exit(0);
        }

        Scanner sc = new Scanner(System.in);
        String input;
        System.out.println("type 'quit' to quit program or type input for example CZK 50");
        while (!(input = sc.nextLine()).equals("quit")) {
            if (validationService.isInputValid(input)) {
                processLine(input);
            } else {
                System.out.println(getFormatHelp());
            }
        }
        System.exit(0);
    }

    @Scheduled(initialDelay = 60_000, fixedDelay = 60_000)
    public void printCurrencies() {
        String currencies = currencyService.getAllAsString();
        if (!StringUtils.isEmpty(currencies)) {
            System.out.println();
            System.out.println(currencyService.getAllAsString());
            System.out.println();
        }
    }

    private void processArgs(String... args) throws IOException, IllegalArgumentException {
        if (args == null || args.length == 0) {
            return;
        }
        validationService.validateArgs(args);
        System.out.println("Reading file");
        validationService.readAndCheckFile(new File(args[0])).forEach(this::processLine);
        System.out.println("Reading file finished");
        printCurrencies();
    }

    private void processLine(String line) {
        String[] split = line.split(" ");
        String currency = split[0];
        BigDecimal value = new BigDecimal(split[1]);
        currencyService.add(currency, value);
    }

    private static final String[] INPUT_EXAMPLES = {"CZK 0", "CZK 1", "CZK -5", "CZK 6.4", "CZK 6.45", "CZK 634.234",  "CZK -1000000"};

    private String getFormatHelp() {
        StringJoiner sj = new StringJoiner("\n")
                .add("Format is 3 uppercase letters, space and positive or negative number with max 7 integer digits and max 3 decimal places:");
        for (String example : INPUT_EXAMPLES) {
            sj.add(example);
        }
        return sj.toString();
    }

}
