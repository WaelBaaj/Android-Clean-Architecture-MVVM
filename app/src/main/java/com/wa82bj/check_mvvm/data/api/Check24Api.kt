package com.wa82bj.check24_mvvm.data.api

import com.wa82bj.check24_mvvm.data.api.response.check24Response.Check24Response
import io.reactivex.Single
import retrofit2.http.GET


interface Check24Api {

    @GET("products-test.json")
    fun loadTrendingProducts(): Single<Check24Response>
}