package com.vdsantana.mobilesaudetest.main.di

import com.vdsantana.mobilesaudetest.di.ActivityScope
import com.vdsantana.mobilesaudetest.di.component.AppComponent
import com.vdsantana.mobilesaudetest.main.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)
}