package com.wa82bj.check_mvvm.data.api

import com.wa82bj.check_mvvm.data.api.response.check24Response.CheckResponse
import io.reactivex.Single
import retrofit2.http.GET

/*
** there are only one endpoint ("products-test.json") in this app to get all products
* so we will handle everything else by Room DB
*/
interface CheckApi {

    @GET("products-test.json")
    fun loadTrendingProducts()
            : Single<CheckResponse>
}