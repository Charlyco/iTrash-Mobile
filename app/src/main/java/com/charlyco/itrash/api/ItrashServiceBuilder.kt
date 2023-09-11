package com.charlyco.itrash.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ItrashServiceBuilder {
    private val BASE_URL = "http://34.88.193.5/api/v1/"
    //private var token: String? = null


    //Logger
    private val logger: HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    //OkHttp
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    private val gsonDate: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create()

    fun buildService(authToken: String?): Retrofit {
        val client = if (authToken != null) {
           OkHttpClient.Builder()
               .addInterceptor(AuthInterceptor(authToken))
               .build()
        }else{
            okHttpClient
        }
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonDate))
            .client(client)
            .build()
    }
}