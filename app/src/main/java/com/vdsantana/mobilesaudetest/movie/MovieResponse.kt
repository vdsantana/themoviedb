package com.vdsantana.mobilesaudetest.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results") val moviesList: ArrayList<Movie>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)