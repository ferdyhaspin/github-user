package com.ferdyhaspin.githubuserapp.data.remote.config

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.ferdyhaspin.githubuserapp.BuildConfig
import com.ferdyhaspin.githubuserapp.util.NoInternetException
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by ferdyhaspin  on 06/02/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */

//Network constants
private const val TIMEOUT_CONNECT = 100   //In seconds
private const val TIMEOUT_READ = 100   //In seconds
private const val CONTENT_TYPE = "Content-Type"
private const val CONTENT_TYPE_VALUE = "application/json"

class ServiceGenerator(
    private val context: Context,
    private val gson: Gson
) {

    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

    private val interceptor = Interceptor { chain ->
        if (!isInternetAvailable())
            throw NoInternetException("No Internet Connection")

        val original = chain.request()
        val request = original.newBuilder()
            .header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply {
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
                }.level = HttpLoggingInterceptor.Level.BODY
            }
            return loggingInterceptor
        }

    init {
        okHttpBuilder.addInterceptor(interceptor)
        okHttpBuilder.addInterceptor(logger)
        okHttpBuilder.connectTimeout(TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
    }

    fun <S> createService(serviceClass: Class<S>, baseUrl: String): S {
        val client = okHttpBuilder.build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(serviceClass)
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                        result = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            else -> false
                        }
                    }
                } else {
                    result = true
                }
            } else {
                result = true
            }
        }
        return result
    }
}