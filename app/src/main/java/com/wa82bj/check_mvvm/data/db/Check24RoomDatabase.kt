package com.wa82bj.check24_mvvm.data.db

import androidx.room.RoomDatabase
import com.wa82bj.check24_mvvm.data.api.response.check24Response.HeaderEntity
import com.wa82bj.check24_mvvm.data.api.response.check24Response.ProductEntity
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

@Suppress("NAME_SHADOWING")
class Check24RoomDatabase @Inject constructor(
    private val database: RoomDatabase,
    private val check24Dao: Check24Dao
) : CheckDatabase {

    override fun getProductsLessThanAndEqualPage(): Single<List<ProductEntity>> =
        Flowable.just(check24Dao.getProductsFromRoomDB())
            .flatMapIterable {
                return@flatMapIterable it
            }
            .map {
                return@map it
            }
            .toList()


    override fun getAvailableProductsWithTrueValue(): Single<List<ProductEntity>> =
        Flowable.just(check24Dao.getAvailableProductsWithhTrueValue())
            .flatMapIterable {
                return@flatMapIterable it
            }
            .map {
                return@map it
            }
            .toList()

    override fun getFavoriteProducts(): Single<List<ProductEntity>> =
        Flowable.just(check24Dao.getFavoriteProducts())
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

                check24Dao.insert(product)
            }
        }
    }

    override fun getAllProducts(): Flowable<List<ProductEntity>> =
        check24Dao.getAllProduct()

    override fun updateFavoriteProducts(fav : Int,productId : Int) =
        check24Dao.update(fav , productId)

    override fun saveHeaderEntities(header: HeaderEntity) {
        database.runInTransaction {

                check24Dao.insertHeader(header)

        }    }

    override fun getHeader(): Single<HeaderEntity> =
        check24Dao.getHeader()



}