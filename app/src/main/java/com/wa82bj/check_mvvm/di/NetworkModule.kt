package com.wa82bj.check24_mvvm.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.wa82bj.check24_mvvm.data.api.Check24Api
import com.wa82bj.check24_mvvm.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @NetworkLogger
    @Singleton
    @Provides
    @IntoSet
    fun provideStetho(): Interceptor = StethoInterceptor()

    @NetworkLogger
    @Singleton
    @Provides
    @IntoSet
    fun provideNetworkLogger(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @NetworkLogger loggingInterceptors: Set<@JvmSuppressWildcards
        Interceptor>
    ):
            OkHttpClient =
        OkHttpClient.Builder().apply {
            loggingInterceptors.forEach {
                addNetworkInterceptor(it)
            }
        }.build()


    @Provides
    @Singleton
    fun provideProductsRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideCheck24Api(retrofit: Retrofit): Check24Api =
        retrofit.create(Check24Api::class.java)
}