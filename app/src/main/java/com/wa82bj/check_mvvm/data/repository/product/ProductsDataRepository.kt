package com.wa82bj.check_mvvm.data.repository.product

import com.wa82bj.check_mvvm.data.api.CheckApi
import com.wa82bj.check_mvvm.data.api.response.check24Response.ProductEntity
import com.wa82bj.check_mvvm.data.db.CheckDatabase
import com.wa82bj.check_mvvm.data.db.toAvailableProducts
import com.wa82bj.check_mvvm.data.db.toProducts
import com.wa82bj.check_mvvm.data.model.ProductModel
import com.wa82bj.check_mvvm.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class ProductsDataRepository @Inject constructor(
    private val api: CheckApi,
    private val checkDatabase: CheckDatabase,
    private val schedulerProvider: SchedulerProvider

) : ProductsRepository {


    override val product: Flowable<List<ProductModel>>
        get() = checkDatabase.getAllProducts()
            .toProducts()

    override fun loadProductsFromDb(): Single<List<ProductModel>> =
        checkDatabase.getProductsLessThanAndEqualPage()
            .map {
                return@map it.toProducts()
            }
            .subscribeOn(schedulerProvider.io())


    override fun loadProductsApi(): Single<List<ProductModel>> =
        api.loadTrendingProducts()
            .map {
                val newProducts = it.products
                saveProducts(newProducts).subscribe()
                return@map newProducts.toProducts()

            }
            .onErrorReturn { error ->
                Timber.e(error.toString())

                return@onErrorReturn emptyList<ProductModel>()
            }
            .subscribeOn(schedulerProvider.io())


    override fun saveProducts (product: List<ProductEntity>): Completable =
        Completable.create {
            checkDatabase.saveProductsEntities(product)
            it.onComplete()
        }



    // Available Products
    override fun loadAvailableProducts(): Single<List<ProductModel>> =
        checkDatabase.getAvailableProductsWithTrueValue()
            .map {
                return@map it.toAvailableProducts()
            }
            .subscribeOn(schedulerProvider.io())


    // Favorite Products
    override fun loadFavoriteProducts(): Single<List<ProductModel>> =
        checkDatabase.getFavoriteProducts()
            .map {
                return@map it.toAvailableProducts()
            }
            .subscribeOn(schedulerProvider.io())


}