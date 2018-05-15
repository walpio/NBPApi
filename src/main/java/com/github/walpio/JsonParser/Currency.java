package com.github.walpio.JsonParser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "table",
        "country",
        "currency",
        "code",
        "rates"
})
public class Currency {

    @JsonProperty("table")
    private String table;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("code")
    private String code;
    @JsonProperty("country")
    private String country;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("rates")
    private List<Rate> rates = null;

    @JsonProperty("table")
    public String getTable() {
        return table;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }


    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("rates")
    public List<Rate> getRates() {
        return rates;
    }
}