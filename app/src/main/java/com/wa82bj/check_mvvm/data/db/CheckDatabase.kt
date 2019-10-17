package com.wa82bj.check_mvvm.data.db

import com.wa82bj.check_mvvm.data.api.response.check24Response.HeaderEntity
import com.wa82bj.check_mvvm.data.api.response.check24Response.ProductEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface CheckDatabase {

        fun saveProductsEntities(product: List<ProductEntity>)
        fun getAllProducts(): Flowable<List<ProductEntity>>
        fun getProductsLessThanAndEqualPage(): Single<List<ProductEntity>>

        fun getAvailableProductsWithTrueValue(): Single<List<ProductEntity>>

        fun updateFavoriteProducts(fav:Int , productId : Int)

        fun getFavoriteProducts(): Single<List<ProductEntity>>

        fun saveHeaderEntities(header: HeaderEntity)
        fun getHeader(): Single<HeaderEntity>

    }