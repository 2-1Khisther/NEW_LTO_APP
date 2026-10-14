package com.example.new_lto_app.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// One shared connection to the Spring Boot API.
object ApiClient {

    // Your computer's IP address on the home Wi-Fi (from ipconfig) + the API port.
    // If the app can't connect one day, run ipconfig again and update this line.
    const val BASE_URL = "http://192.168.1.10:8080/"

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(8, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    val api: LtoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LtoApi::class.java)
    }
}
