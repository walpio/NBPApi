package com.github.walpio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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

        String endDate;
        String dateLimit;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(inputData.getStartDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, 367);
        dateLimit = sdf.format(calendar.getTime());
        logger.info("Określono datę ograniczającą okres 367 dni.");
        logger.debug(String.format("Maksymalna data dla okresu 367 dni: %s", dateLimit));
        do {
            System.out.println("Wprowadź datę końcową okresu, który Cię interesuje w formacie RRRR-MM-DD. (Maksymany dopuszczalny przedział wynosi 367 dni!)");
            endDate = getUserInput();
            if (datePattern.matcher(endDate).matches()) {
                if (endDate.compareTo(inputData.getStartDate()) <= 0) {
                    logger.debug("Wprowadzono błędną datę końcową.");
                    System.out.println("Data końcowa powinna być co najmniej dzień po dacie początkowej.");
                } else if (dateLimit.compareTo(endDate) < 0){
                    logger.debug("Wprowadzono błędną datę końcową.");
                    System.out.println(String.format("Maksymalna data dla okresu 367 dni: %s. Wprowadź inną datę!", dateLimit));
                } else {
                    logger.debug("Wprowadzono datę końcową pasującą do schematu i mieszczącą się w przedziale 367 dni.");
                    inputData.setEndDate(endDate);
                    logger.debug(String.format("Przypisano datę końcową: %s", endDate));
                }
            }
        } while (inputData.getEndDate() == null);

        UriGenerator uriGenerator = new UriGenerator();
        String uri = uriGenerator.generateRequestUri(inputData.getCurrency(), inputData.getStartDate(), inputData.getEndDate());
    }

    private static String getUserInput() {
        return scanner.nextLine().trim();
    }
}
