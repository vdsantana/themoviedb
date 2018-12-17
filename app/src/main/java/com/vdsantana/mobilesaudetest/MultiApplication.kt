package com.vdsantana.mobilesaudetest

import android.support.multidex.MultiDexApplication
import com.vdsantana.mobilesaudetest.di.component.AppComponent
import com.vdsantana.mobilesaudetest.di.component.DaggerAppComponent

import com.vdsantana.mobilesaudetest.di.modules.AppModule

class MultiApplication : MultiDexApplication() {

    companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent
        lateinit var instance: MultiApplication
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
        instance = this
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}