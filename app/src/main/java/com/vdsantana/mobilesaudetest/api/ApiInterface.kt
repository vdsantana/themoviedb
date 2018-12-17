package com.vdsantana.mobilesaudetest.api

import com.vdsantana.mobilesaudetest.Config
import com.vdsantana.mobilesaudetest.movie.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Vinicius on 14/12/18.
 */

interface ApiInterface {

    @GET(Config.API_VERSION + "/movie/popular")
    fun getPopularMovies(@Query("page") page: String, @Query("api_key") apiKey: String = Config.MOVIEDB_API_KEY): Observable<MovieResponse>

    @GET(Config.API_VERSION + "/movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: String, @Query("api_key") apiKey: String = Config.MOVIEDB_API_KEY): Observable<MovieResponse>
}