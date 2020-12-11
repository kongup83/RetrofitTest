package com.kongup.common.server

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.kongup.common.server.data.GetLocation
import com.kongup.common.server.data.GetLocationSearch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface WeatherAPIs
{

    companion object  {

        const val GET_LOCATION_SEARCH   = 1
        const val GET_LOCATION          = 2


//        fun create():WeatherAPIs
//        {
//            val mOkHttpClient = OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .build()
//
//            val mGson = GsonBuilder()
//                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                    .create()
//
//            val mRetrofit = Retrofit.Builder()
//                    .client(mOkHttpClient)
//                    .baseUrl("https://www.metaweather.com/api/")
//                    .addConverterFactory(ScalarsConverterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create(mGson))
//                    .build()
//
//            val mWeatherService = mRetrofit.create(WeatherAPIs::class.java)
//
//            return mWeatherService
//        }


    }

    @GET("location/search/")
    fun getLocationSearch(@Query("query") strQuery: String): Call<GetLocationSearch>

    @GET("location/{location}")
    fun getLocation(@Path("location") nLocation: Int): Call<GetLocation>

}