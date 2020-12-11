package com.kongup.common

import androidx.multidex.MultiDexApplication
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.kongup.common.server.WeatherAPIs
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class MyApplication:MultiDexApplication()
{
    private val mOkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val mGson by lazy {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    private val mRetrofit by lazy {
        Retrofit.Builder()
            .client(mOkHttpClient)
            .baseUrl("https://www.metaweather.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(mGson))
            .build()
    }

    val mWeatherService: WeatherAPIs by lazy {
        mRetrofit.create(WeatherAPIs::class.java)
    }

    override fun onCreate() {
        super.onCreate()
    }

}