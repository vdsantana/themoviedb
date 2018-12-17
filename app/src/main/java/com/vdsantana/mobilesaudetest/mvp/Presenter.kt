package com.vdsantana.mobilesaudetest.mvp

interface Presenter<V : BaseView> {
    fun attachView(view: V)
    fun detachView()
}