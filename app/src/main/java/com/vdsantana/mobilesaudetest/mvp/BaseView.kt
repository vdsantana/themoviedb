package com.vdsantana.mobilesaudetest.mvp

interface BaseView {
    fun onError()
    fun setPresenter(presenter: BasePresenter<*>)
}