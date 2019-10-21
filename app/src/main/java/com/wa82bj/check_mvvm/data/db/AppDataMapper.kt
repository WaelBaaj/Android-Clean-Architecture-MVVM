package com.wa82bj.check_mvvm.data.db

import com.wa82bj.check_mvvm.data.api.response.check24Response.HeaderEntity
import com.wa82bj.check_mvvm.data.api.response.check24Response.ProductEntity
import com.wa82bj.check_mvvm.data.model.HeaderModel
import com.wa82bj.check_mvvm.data.model.ProductModel
import io.reactivex.Flowable

fun Flowable<List<ProductEntity>>.toProducts(): Flowable<List<ProductModel>> = map {
    return@map it.toProducts()
}

fun List<ProductEntity>.toProducts(): List<ProductModel> =
    map { ProductModel(it.available,it.fav  , it.description,it.id, it.imageURL, it.longDescription ,it.name ,
        it.price,it.rating  , it.releaseDate,it.type) }

fun HeaderEntity.toHeader(): HeaderModel =
     HeaderModel(headerTitle,headerDescription)

fun List<ProductEntity>.toAvailableProducts(): List<ProductModel> =
    map { ProductModel(
        it.available ,it.fav, it.description,it.id, it.imageURL, it.longDescription ,it.name ,
        it.price,it.rating  , it.releaseDate,it.type)
    }

