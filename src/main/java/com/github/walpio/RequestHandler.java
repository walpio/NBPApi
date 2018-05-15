package com.github.walpio;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestHandler {
    private static final Logger logger = LogManager.getLogger(RequestHandler.class.getName());

    public String getJsonFile(String uri) {
        String json = "";

        try {

            Client client = Client.create();

            WebResource resource = client.resource(uri);

            ClientResponse response = resource.accept("application/jason").get(ClientResponse.class);
            logger.debug(String.format("Status zapytania: %s", response.getStatus()));

            if (response.getStatus() != 200) {
                logger.warn(String.format("%s %s.","Błąd połączenia. Kod błędu:", response.getStatus()));
                return String.format("%s %s. %s","Błąd połączenia. Kod błędu:", response.getStatus(), "Upewnij się, że wprowadziłeś poprawne dane i posiadasz połączenie z siecią!");
            }

            json = response.getEntity(String.class);

        } catch (Exception e) {
            logger.info(e.getStackTrace());
        }
        logger.info("Pobrano plik JSON.");
        logger.debug(String.format("Pobrany plik: %s", json));
        return json;
    }
}