package com.example.cryotoapp.di

import com.example.cryotoapp.data.AppConstants
import com.example.cryotoapp.data.api.ApiService
import com.example.cryotoapp.data.datasource.CryptoDataSource
import com.example.cryotoapp.data.datasource.CryptoDataSourceImpl
import com.example.cryotoapp.repository.CryptoRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesRetrofit():Retrofit{
        val httpLoggingInterceptor= HttpLoggingInterceptor().apply {
            level=HttpLoggingInterceptor.Level.BASIC
        }
        val httpClient= OkHttpClient().newBuilder().connectTimeout(3,TimeUnit.MINUTES)
            .writeTimeout(3,TimeUnit.MINUTES).readTimeout(3,TimeUnit.MINUTES)
            .apply {
            addInterceptor(httpLoggingInterceptor)
        }

        val moshi= Moshi.Builder()
            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder()
            .baseUrl(AppConstants.App_Base_Url)
            .client(httpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // provides
    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }

    // provides apiService to CryptoDataSource
    @Provides
    @Singleton
    fun providesCryptoDataSource(apiService: ApiService):CryptoDataSource{
        return CryptoDataSourceImpl(apiService)
    }

    // provides cryptoDataSource to CryptoRepository
    @Provides
    @Singleton
    fun providesCryptoRepositories(cryptoDataSource: CryptoDataSource):CryptoRepository{
        return CryptoRepository(cryptoDataSource)
    }
}