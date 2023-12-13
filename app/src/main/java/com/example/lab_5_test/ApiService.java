package com.example.lab_5_test;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("latest")
    Call<CurrencyRates> getExchangeRates(@Query("base") String base);
}