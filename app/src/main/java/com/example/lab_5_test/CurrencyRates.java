package com.example.lab_5_test;

import java.io.Serializable;

public class CurrencyRates implements Serializable {
    private String currency;
    private String rate;

    public CurrencyRates(String currency, String rate) {
        this.currency = currency;
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public String getRate() {
        return rate;
    }
}
