package com.wa82bj.check24_mvvm.ui.detail

import com.wa82bj.check24_mvvm.data.api.response.check24Response.ProductEntity
import com.wa82bj.check24_mvvm.data.db.AppDatabase
import io.reactivex.Single


class ProductRepositoryImp(
    private val database: AppDatabase
) : DetailRepository {


    override fun deleteProduct(product: ProductEntity) {
        database.check24Dao().deleteAll()   }

    override fun addProduct(product: ProductEntity) {

        database.check24Dao().insert(product)
    }

    override fun updateFavoriteProduct(fav: Int,productId : Int) {
        database.check24Dao().update(fav , productId)    }

    override fun isFavorite(productId: Int): Boolean {
        val loadOneByPhotoId = database.check24Dao().loadOneByProductId(productId)
        return loadOneByPhotoId != null  }

    override fun isFavoriteProuduct(fav : Int ,productId : Int) : Int {
        return fav  }


    override fun getProductDetail(productId: Int?): Single<ProductEntity> {
        return database.check24Dao().loadOneByProductId(productId!!)
    }



}