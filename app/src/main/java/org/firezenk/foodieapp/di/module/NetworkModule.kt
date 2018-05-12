package org.firezenk.foodieapp.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.firezenk.foodieapp.BuildConfig
import org.firezenk.foodieapp.data.net.FoursquareApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
open class NetworkModule {

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder
            = Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))

    @Provides
    fun providesOkHttpClient(logInterceptor: Interceptor): OkHttpClient
            = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .build()

    @Provides
    fun providesLogInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun providesFoursquareApi(retrofit: Retrofit): FoursquareApi
            = retrofit.create(FoursquareApi::class.java)
}