package com.vdsantana.mobilesaudetest.di.modules

import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.vdsantana.mobilesaudetest.Config
import com.vdsantana.mobilesaudetest.MultiApplication
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class OkHttpModule {
    private fun getBaseBuilder(cache: Cache, tokenInterceptor: TokenInterceptor): OkHttpClient.Builder {
        return OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                //.addInterceptor(tokenInterceptor)
                //.cache(cache)
                .retryOnConnectionFailure(true)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
    }

    class TokenInterceptor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            val originalRequest = chain?.request()

            val requestBuilder = originalRequest?.newBuilder()

           if (!Config.MOVIEDB_API_TOKEN.isEmpty())
                requestBuilder?.addHeader("Authorization", "Bearer " + Config.MOVIEDB_API_TOKEN)

            val request = requestBuilder!!.build()

            val response = chain.proceed(request)

            if (response.code() == 401)
                Log.e("não autorizado", "falha na autenticação")

            return response
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpCache(pApplication: MultiApplication): Cache = Cache(pApplication.cacheDir, 10 * 1024 * 1024)

    @Provides
    @Singleton
    fun providesLogginInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun providesTokenInterceptor(): TokenInterceptor {
        return TokenInterceptor()
    }

    @Provides
    @Singleton
    fun providesOkHttp(cache: Cache, loggingInterceptor: HttpLoggingInterceptor, tokenInterceptor: TokenInterceptor) = getBaseBuilder(cache, tokenInterceptor)
            //.addNetworkInterceptor(CachingControlInterceptor())
            //.addInterceptor(tokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
}