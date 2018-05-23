package com.wardrobes.porenut.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static pl.zgora.uz.andek.ArcGISNavigation.util.Objects.isNull;

public class BaseProvider {
    private static final String API_URL = "http://127.0.0.1:8081";

    private static Retrofit retrofit;

    private BaseProvider() {
        // There should be no instance of this class.
    }

    static Retrofit getRetrofit() {
        if (isNull(retrofit)) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
