package com.vdsantana.mobilesaudetest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vdsantana.mobilesaudetest.di.component.AppComponent
import com.vdsantana.mobilesaudetest.mvp.BasePresenter
import com.vdsantana.mobilesaudetest.mvp.BaseView

abstract class BaseActivity : AppCompatActivity(), BaseView {

    private var presenter: BasePresenter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onActivityInject()
    }

    protected abstract fun onActivityInject()

    fun getAppComponent(): AppComponent = MultiApplication.appComponent

    override fun setPresenter(presenter: BasePresenter<*>) {
        this.presenter = presenter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
        presenter = null
    }
}