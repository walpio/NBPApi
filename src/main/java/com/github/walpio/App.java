package com.github.walpio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.regex.Pattern;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class.getName());
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        InputData inputData = new InputData();

        Pattern currencyPattern = Pattern.compile("[a-zA-Z]{3}");
        String currencyCode;
        do {
            System.out.println("Wprowadź kod waluty, która Cię interesuje (np. EUR, USD, CHF, GBP).");
            currencyCode = getUserInput();
            if (currencyPattern.matcher(currencyCode).matches()) {
                inputData.setCurrency(currencyCode);
                logger.debug(String.format("Przypisano walutę: %s", currencyCode));
            }
        } while (inputData.getCurrency() == null);
    }

    private static String getUserInput() {
        return scanner.nextLine().trim();
    }
}
