package com.vdsantana.mobilesaudetest.main

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.vdsantana.mobilesaudetest.BaseActivity
import com.vdsantana.mobilesaudetest.R
import com.vdsantana.mobilesaudetest.main.di.DaggerMainActivityComponent
import com.vdsantana.mobilesaudetest.main.di.MainActivityModule
import com.vdsantana.mobilesaudetest.main.pagination.PaginationScrollListener
import com.vdsantana.mobilesaudetest.main.presenter.MainPresenter
import com.vdsantana.mobilesaudetest.main.presenter.MainView
import com.vdsantana.mobilesaudetest.movie.MovieResponse
import com.vdsantana.mobilesaudetest.movie.adapter.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity(), MainView {

    @Inject
    lateinit var mainPresenter: MainPresenter

    private var currentPage = 1
    private var totalPages = -1
    private var isLoading = false
    private var isLastPage = false
    private var isInTopRated = false

    private lateinit var movieAdapter: MovieAdapter
    private var moviesList = arrayListOf<Any>()
    private var gridLayoutManager = GridLayoutManager(this, 3)

    private val filter = arrayListOf("Mais populares", "Melhor classificação")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
    }

    private fun setupView() {
        movieAdapter = MovieAdapter(this, moviesList)
        recyclerMovies.layoutManager = gridLayoutManager
        recyclerMovies.adapter = movieAdapter

        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (movieAdapter.getItemViewType(position)) {
                    MovieAdapter.MOVIE_ITEM -> 1
                    else -> 3
                }
            }
        }

        gridLayoutManager.spanSizeLookup = spanSizeLookup

        recyclerMovies.addOnScrollListener(object : PaginationScrollListener(gridLayoutManager) {
            override val totalPageCount: Int
                get() = totalPages
            override val isLastPage: Boolean
                get() = this@MainActivity.isLastPage
            override val isLoading: Boolean
                get() = this@MainActivity.isLoading

            override fun loadMoreItems() {
                this@MainActivity.isLoading = true
                val nextPage = currentPage + 1

                if (nextPage <= totalPages)
                    loadMovies(nextPage)
            }
        })

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupFilterSpinner()
        loadMovies()
    }

    private fun setupFilterSpinner() {
        val spinnerAdapter = ArrayAdapter(
            supportActionBar!!.themedContext,
            R.layout.spinner_style,
            filter
        )

        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item)

        mainSpinner.adapter = spinnerAdapter

        mainSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                isInTopRated = position == 1
                movieAdapter.clear()
                loadMovies()
            }
        }
    }

    private fun loadMovies(pageToLoad: Int = 1) {
        if (isInTopRated)
            mainPresenter.getTopRatedMovies(pageToLoad)
        else
            mainPresenter.getPopularMovies(pageToLoad)
    }

    override fun showProgress() {
        recyclerMovies.post {
            movieAdapter.addLoadingFooter()
        }
    }

    override fun hideProgress() {
        recyclerMovies.post {
            movieAdapter.removeLoadingFooter()
        }
    }

    override fun onRetrieveMoviesSuccess(movieResponse: MovieResponse) {
        isLoading = false
        currentPage = movieResponse.page
        totalPages = movieResponse.totalPages
        isLastPage = currentPage == totalPages
        movieAdapter.addAll(movieResponse.moviesList)
    }

    override fun onRetrieveMoviesFailed(message: String) {
        isLoading = false
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityInject() {
        DaggerMainActivityComponent.builder().appComponent(getAppComponent())
            .mainActivityModule(MainActivityModule())
            .build()
            .inject(this)

        mainPresenter.attachView(this)
    }

    override fun onError() {
    }
}
