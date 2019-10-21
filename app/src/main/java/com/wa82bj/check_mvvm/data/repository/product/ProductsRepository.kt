package com.wa82bj.check_mvvm.data.repository.product

import com.wa82bj.check_mvvm.data.api.response.check24Response.ProductEntity
import com.wa82bj.check_mvvm.data.model.ProductModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


interface ProductsRepository {

    val product: Flowable<List<ProductModel>>
    fun loadProductsFromDb(): Single<List<ProductModel>>
    fun loadProductsApi(): Single<List<ProductModel>>
    fun saveProducts(product: List<ProductEntity>): Completable

    fun loadAvailableProducts(): Single<List<ProductModel>>

    fun loadFavoriteProducts(): Single<List<ProductModel>>

}