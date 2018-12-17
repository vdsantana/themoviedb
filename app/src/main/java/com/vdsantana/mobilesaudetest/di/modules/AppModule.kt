package com.vdsantana.mobilesaudetest.di.modules

import com.google.gson.GsonBuilder
import com.vdsantana.mobilesaudetest.MultiApplication
import com.vdsantana.mobilesaudetest.utils.AppSchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class AppModule(val application: MultiApplication) {

    @Provides
    @Singleton
    fun providesGson() = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    @Provides
    @Singleton
    fun providesMultiApplication() = application

    @Provides
    @Singleton
    fun providesResources() = application.resources

    @Provides
    @Singleton
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    @Singleton
    fun provideSchedulerProvider() = AppSchedulerProvider()
}