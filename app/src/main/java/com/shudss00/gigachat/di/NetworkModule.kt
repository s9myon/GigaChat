package com.shudss00.gigachat.di

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.shudss00.gigachat.BuildConfig
import kotlinx.serialization.json.Json
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.*
import javax.inject.Named

@Module
class NetworkModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideJsonConverterFactory(): Converter.Factory {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            prettyPrint = true
        }
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Singleton
    @Provides
    @Named("Logging")
    fun provideOkHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    @Named("Auth")
    fun provideOkHttpAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.addHeader(
                "Authorization",
                Credentials.basic(BuildConfig.USER_EMAIL, BuildConfig.API_KEY)
            )
            chain.proceed(requestBuilder.build())
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Named("Logging") loggingInterceptor: Interceptor,
        @Named("Auth") authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, factory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(factory)
            .build()
    }
}