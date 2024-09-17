package com.example.examenfinal.Interfaz;

import com.example.examenfinal.Models.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CountryApi {
    @GET("v3.1/name/{country}")
    Call<List<Country>> getCountryInfo(@Path("country") String country);
}