package com.vdsantana.mobilesaudetest.main.presenter

import com.vdsantana.mobilesaudetest.movie.MovieResponse
import com.vdsantana.mobilesaudetest.mvp.BaseView

interface MainView : BaseView {
    fun showProgress()
    fun hideProgress()
    fun onRetrieveMoviesSuccess(movieResponse: MovieResponse)
    fun onRetrieveMoviesFailed(message: String)
}