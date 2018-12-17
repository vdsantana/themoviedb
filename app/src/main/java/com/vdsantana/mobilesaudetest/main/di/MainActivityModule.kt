package com.vdsantana.mobilesaudetest.main.di

import com.vdsantana.mobilesaudetest.api.ApiInterface
import com.vdsantana.mobilesaudetest.di.ActivityScope
import com.vdsantana.mobilesaudetest.main.presenter.MainPresenter
import com.vdsantana.mobilesaudetest.utils.AppSchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class MainActivityModule {
    @Provides
    @ActivityScope
    internal fun provideMainPresenter(api: ApiInterface, disposable: CompositeDisposable, scheduler: AppSchedulerProvider): MainPresenter = MainPresenter(api, disposable, scheduler)
}