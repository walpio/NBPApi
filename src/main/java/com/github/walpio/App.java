package com.github.walpio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        Pattern datePattern = Pattern.compile("^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
                + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");

        LocalDate localDate = LocalDate.now();
        String todayDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);

        String startDate;
        do {
            System.out.println("Wprowadź datę początkową okresu, który Cię interesuje w formacie RRRR-MM-DD. (Dane archiwalne dostępne od 2002-01-02)");
            startDate = getUserInput();
            String firstDate = "2002-01-02";
            if (datePattern.matcher(startDate).matches() && startDate.compareTo(firstDate) >= 0) {
                logger.debug("Wprowadzono datę początkową pasującą do schematu oraz co najmniej równą 2002-01-02.");
                if (todayDate.compareTo(startDate) <= 0) {
                    logger.debug("Wprowadzona data początkowa jest identyczna jak data dzisiejsza.");
                    System.out.println("Data początkowa powinna być co najmniej dzień przed dzisiejszą datą.");
                } else {
                    inputData.setStartDate(startDate);
                    logger.debug(String.format("Przypisano datę początkową: %s", startDate));
                }
            }
        } while (inputData.getStartDate() == null);
    }

    private static String getUserInput() {
        return scanner.nextLine().trim();
    }
}
