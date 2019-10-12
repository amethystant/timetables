package com.patlejch.timetables.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.patlejch.timetables.Constants
import com.patlejch.timetables.data.network.ApiServices
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import wiki.depasquale.responsesanitizer.ResponseSanitizer

val networkingModule = module {
    single { ResponseSanitizer() }

    single { createOkHttpClient() }

    single { createConverterFactory() }
    single { createCallAdapterFactory() }

    single { createRetrofit(get(), get(), get()) }

    single { createApiService<ApiServices>(get(), Constants.API_URL) }
}

fun createOkHttpClient(): OkHttpClient {

    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun createConverterFactory(): Converter.Factory {
    val moshi = Moshi.Builder().build()
    return MoshiConverterFactory.create(moshi)
}

fun createCallAdapterFactory(): CallAdapter.Factory {
    return CoroutineCallAdapterFactory()
}

fun createRetrofit(
    okHttpClient: OkHttpClient,
    converterFactory: Converter.Factory,
    callAdapterFactory: CallAdapter.Factory
): Retrofit.Builder {
    return Retrofit.Builder()
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .client(okHttpClient)
}

inline fun <reified T> createApiService(retrofitBuilder: Retrofit.Builder, baseUrl: String): T {
    return retrofitBuilder
        .baseUrl(baseUrl)
        .build()
        .create(T::class.java)
}
