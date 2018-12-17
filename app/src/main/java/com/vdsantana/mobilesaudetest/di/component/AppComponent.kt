package com.vdsantana.mobilesaudetest.di.component

import android.content.res.Resources
import com.google.gson.Gson
import com.vdsantana.mobilesaudetest.MultiApplication
import com.vdsantana.mobilesaudetest.api.ApiInterface
import com.vdsantana.mobilesaudetest.di.modules.ApiModule
import com.vdsantana.mobilesaudetest.di.modules.AppModule
import com.vdsantana.mobilesaudetest.di.modules.OkHttpModule
import com.vdsantana.mobilesaudetest.di.modules.RetrofitModule
import com.vdsantana.mobilesaudetest.utils.AppSchedulerProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (RetrofitModule::class), (ApiModule::class), (OkHttpModule::class)])
interface AppComponent {
    fun multiApplication(): MultiApplication
    fun gson(): Gson
    fun resources(): Resources
    fun retrofit(): Retrofit
    fun apiInterface(): ApiInterface
    fun cache(): Cache
    fun client(): OkHttpClient
    fun loggingInterceptor(): HttpLoggingInterceptor
    fun compositeDisposable(): CompositeDisposable
    fun schedulerProvider(): AppSchedulerProvider
}