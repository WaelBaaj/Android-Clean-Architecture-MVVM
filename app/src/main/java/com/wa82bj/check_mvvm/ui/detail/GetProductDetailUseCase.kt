package com.wa82bj.check_mvvm.ui.detail

import com.wa82bj.check_mvvm.data.api.response.check24Response.ProductEntity
import io.reactivex.Single
import javax.inject.Inject


class GetProductDetailUseCase @Inject constructor(
    private val repository: DetailRepository
) : SingleUseCase<ProductEntity>() {

    private var productId: Int? = null

    fun saveProductId(id: Int) {
        productId = id
    }

    override fun buildUseCaseSingle(): Single<ProductEntity> {
        return repository.getProductDetail(productId)
    }

    fun updateAsFavorite( fav : Int, productId : Int) {
        repository.updateFavoriteProduct(fav, productId)
    }

    fun isFavorite(productId: Int): Boolean {
       return repository.isFavorite(productId)
    }

    fun isFavoriteProduct(fav: Int,productId: Int): Int {
        return repository.isFavoriteProuduct(fav,productId)
    }
}