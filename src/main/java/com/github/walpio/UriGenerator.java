package com.github.walpio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UriGenerator {
    private static final Logger logger = LogManager.getLogger(UriGenerator.class.getName());

    public String generateRequestUri(String currencyCode, String startDate, String endDate) {

        String uriBase = "http://api.nbp.pl/api/exchangerates/rates/c/";
        String requestedFormat = "?format=json";

        StringBuilder sb = new StringBuilder(uriBase);
        sb.append(currencyCode)
                .append("/")
                .append(startDate)
                .append("/")
                .append(endDate)
                .append("/")
                .append(requestedFormat);
        String uri = sb.toString();
        logger.info("Wygenerowano URI zapytania.");
        logger.debug(String.format("Wygenerowany URI: %s", uri));
        return uri;
    }
}
