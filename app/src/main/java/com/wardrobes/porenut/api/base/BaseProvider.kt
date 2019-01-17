package com.wardrobes.porenut.api.base

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BaseProvider {
    private const val API_URL = "http://192.168.1.138:8080"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}
