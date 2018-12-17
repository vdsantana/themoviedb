package com.vdsantana.mobilesaudetest.movie.adapter

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import com.vdsantana.mobilesaudetest.Config
import com.vdsantana.mobilesaudetest.R
import com.vdsantana.mobilesaudetest.detail.DetailActivity
import com.vdsantana.mobilesaudetest.movie.Movie
import com.vdsantana.mobilesaudetest.utils.LoadingData

class MovieAdapter(private val context: Context, private val moviesList: ArrayList<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoadingAdded = false
    private val loadingItem = LoadingData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return when (viewType) {
            LOADING_ITEM -> getLoadingViewHolder(parent, inflater)
            else -> getMovieViewHolder(parent, inflater)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (moviesList[position] is LoadingData) LOADING_ITEM else MOVIE_ITEM
    }

    @NonNull
    private fun getMovieViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    @NonNull
    private fun getLoadingViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.item_list_loading, parent, false)
        return LoadingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MOVIE_ITEM -> {
                val movie = moviesList[position] as Movie
                val movieViewHolder = holder as MovieViewHolder

                Picasso.get().load(Config.MOVIEDB_POSTERS_BASE_URL + movie.posterPath)
                    .placeholder(R.drawable.poster_placeholder).into(movieViewHolder.posterImage)

                movieViewHolder.movieView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(Config.MOVIE_DETAILS_KEY, movie)
                    })
                }
            }
            LOADING_ITEM -> {
            }
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun add(item: Any) {
        moviesList.add(item)
        notifyItemInserted(moviesList.size - 1)
    }

    fun addAll(list: ArrayList<Movie>) {
        val previousDataSize = moviesList.size
        moviesList.addAll(list)
        notifyItemRangeChanged(previousDataSize, list.size)
    }

    fun remove(item: Any?) {
        val position = moviesList.indexOf(item)
        if (position > -1) {
            moviesList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }

    fun isEmpty(): Boolean {
        return itemCount == 0
    }

    fun addLoadingFooter() {
        removeLoadingFooter()
        isLoadingAdded = true
        add(loadingItem)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        moviesList.removeAll(arrayListOf(loadingItem))
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Any? {
        return moviesList[position]
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val movieView = itemView
        internal var posterImage: RoundedImageView = itemView.findViewById(R.id.posterImage)
    }

    protected inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        const val MOVIE_ITEM = 0
        const val LOADING_ITEM = 1
    }
}