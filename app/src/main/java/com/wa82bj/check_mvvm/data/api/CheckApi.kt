package com.wa82bj.check_mvvm.data.api

import com.wa82bj.check_mvvm.data.api.response.check24Response.CheckResponse
import io.reactivex.Single
import retrofit2.http.GET


interface CheckApi {

    @GET("products-test.json")
    fun loadTrendingProducts(): Single<CheckResponse>
}