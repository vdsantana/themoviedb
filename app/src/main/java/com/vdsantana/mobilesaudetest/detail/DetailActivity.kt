package com.vdsantana.mobilesaudetest.detail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.squareup.picasso.Picasso
import com.vdsantana.mobilesaudetest.Config
import com.vdsantana.mobilesaudetest.R
import com.vdsantana.mobilesaudetest.movie.Movie
import com.vdsantana.mobilesaudetest.rate.RateActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private val movie by lazy { intent.getParcelableExtra<Movie>(Config.MOVIE_DETAILS_KEY) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupView()
    }

    private fun setupView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        originalTitle.text = movie.originalTitle
        overview.text = movie.overview
        usersRating.text = movie.voteAverage.toString()
        releaseDate.text = movie.releaseDate

        Picasso.get().load(Config.MOVIEDB_POSTERS_BASE_URL + movie.posterPath)
            .placeholder(R.drawable.poster_placeholder).into(posterImage)

        btRate.setOnClickListener { startActivity(Intent(this, RateActivity::class.java)) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
