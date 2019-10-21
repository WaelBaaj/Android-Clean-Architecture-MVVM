package com.wa82bj.check_mvvm.data.db

import androidx.room.RoomDatabase
import com.wa82bj.check_mvvm.data.api.response.check24Response.ProductEntity
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

@Suppress("NAME_SHADOWING")
class CheckRoomDatabase @Inject constructor(
    private val database: RoomDatabase,
    private val checkDao: CheckDao
) : CheckDatabase {

    override fun getProductsLessThanAndEqualPage(): Single<List<ProductEntity>> =
        Flowable.just(checkDao.getProductsFromRoomDB())
            .flatMapIterable {
                return@flatMapIterable it
            }
            .map {
                return@map it
            }
            .toList()


    override fun getAvailableProductsWithTrueValue(): Single<List<ProductEntity>> =
        Flowable.just(checkDao.getAvailableProductsWithhTrueValue())
            .flatMapIterable {
                return@flatMapIterable it
            }
            .map {
                return@map it
            }
            .toList()

    override fun getFavoriteProducts(): Single<List<ProductEntity>> =
        Flowable.just(checkDao.getFavoriteProducts())
            .flatMapIterable {
                return@flatMapIterable it
            }
            .map {

                return@map it
            }
            .toList()


    override fun saveProductsEntities(product: List<ProductEntity>) {
        database.runInTransaction {
            for (products in product) {

                checkDao.insert(product)
            }
        }
    }

    override fun getAllProducts(): Flowable<List<ProductEntity>> =
        checkDao.getAllProduct()

    override fun updateFavoriteProducts(fav : Int,productId : Int) =
        checkDao.update(fav , productId)


    }
