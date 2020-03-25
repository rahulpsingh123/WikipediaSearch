package com.l.wikipediasearch.network

import com.l.wikipediasearch.network.MyLoggingInterceptor.provideOkHttpLogging
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

class APIManager private constructor() {

    private val baseUrl = "https://en.wikipedia.org/"
    private fun createRetrofitService(): JoshApiClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(provideOkHttpLogging())
        val client = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(JoshApiClient::class.java)
    }

    private val service: JoshApiClient
        get() = createRetrofitService()

    @JvmSuppressWildcards
    private interface JoshApiClient {
        @GET("w/api.php")
        fun search(@QueryMap queryMap: Map<String?, Any?>?): Call<ResponseBody>
    }

    fun searchApi(queryMap: Map<String?, Any?>?): Call<ResponseBody> {
        return service.search(queryMap)
    }

    companion object {
        private var myInstance: APIManager? = null
        val instance: APIManager?
            get() {
                if (myInstance == null) {
                    myInstance =
                        APIManager()
                }
                return myInstance
            }
    }
}