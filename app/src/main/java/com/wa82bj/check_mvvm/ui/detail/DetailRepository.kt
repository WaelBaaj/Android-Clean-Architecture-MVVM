package com.wa82bj.check_mvvm.ui.detail

import com.wa82bj.check_mvvm.data.api.response.check24Response.ProductEntity
import io.reactivex.Single


interface DetailRepository {

    fun getProductDetail(productId: Int?): Single<ProductEntity>

    fun deleteProduct(product: ProductEntity)

    fun addProduct(product: ProductEntity)

    fun updateFavoriteProduct(fav : Int, productId : Int)

    fun isFavoriteProuduct (fav : Int ,productId : Int): Int

    fun isFavorite(productId: Int): Boolean

}