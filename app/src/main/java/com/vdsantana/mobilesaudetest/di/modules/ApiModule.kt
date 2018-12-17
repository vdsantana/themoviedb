package com.vdsantana.mobilesaudetest.di.modules

import com.vdsantana.mobilesaudetest.api.ApiInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun providesEndpoints(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)
}