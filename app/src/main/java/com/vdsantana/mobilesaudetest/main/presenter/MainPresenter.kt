package com.vdsantana.mobilesaudetest.main.presenter

import com.vdsantana.mobilesaudetest.api.ApiInterface
import com.vdsantana.mobilesaudetest.mvp.BasePresenter
import com.vdsantana.mobilesaudetest.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainPresenter @Inject constructor(var api: ApiInterface, disposable: CompositeDisposable, scheduler: SchedulerProvider) : BasePresenter<MainView>(disposable, scheduler) {

    fun getPopularMovies(pageToLoad: Int = 1) {
        view?.showProgress()
        disposable.add(api.getPopularMovies(pageToLoad.toString())
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(
                        { result ->
                            view?.hideProgress()

                            if (result != null)
                                view?.onRetrieveMoviesSuccess(result)
                        },
                        { _ ->
                            view?.hideProgress()
                            view?.onError()
                        })
        )
    }

    fun getTopRatedMovies(pageToLoad: Int = 1) {
        view?.showProgress()
        disposable.add(api.getTopRatedMovies(pageToLoad.toString())
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe(
                { result ->
                    view?.hideProgress()

                    if (result != null)
                        view?.onRetrieveMoviesSuccess(result)
                },
                { _ ->
                    view?.hideProgress()
                    view?.onError()
                })
        )
    }
}