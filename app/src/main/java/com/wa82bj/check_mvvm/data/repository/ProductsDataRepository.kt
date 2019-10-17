package com.wa82bj.check_mvvm.data.repository

import com.wa82bj.check_mvvm.data.api.CheckApi
import com.wa82bj.check_mvvm.data.api.response.check24Response.HeaderEntity
import com.wa82bj.check_mvvm.data.api.response.check24Response.ProductEntity
import com.wa82bj.check_mvvm.data.db.CheckDatabase
import com.wa82bj.check_mvvm.data.db.toAvailableProducts
import com.wa82bj.check_mvvm.data.db.toHeader
import com.wa82bj.check_mvvm.data.db.toProducts
import com.wa82bj.check_mvvm.data.model.HeaderModel
import com.wa82bj.check_mvvm.data.model.ProductModel
import com.wa82bj.check_mvvm.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
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

    override fun loadProducts(): Single<List<ProductModel>> =
        Single.zip(loadProductsApi(), loadProductsDb(), BiFunction { t1, t2 ->
            if (t1.isNotEmpty()) {
                val products = ArrayList<ProductModel>()
                products.addAll(t1)
                return@BiFunction products.toList()
            } else return@BiFunction t2
        })

    override fun loadProductsFromDb(): Single<List<ProductModel>> =
        Single.zip(loadProductsDb(), loadProductsDb(), BiFunction { t1, t2 ->
            if (t1.isNotEmpty()) {
                val products = ArrayList<ProductModel>()
                products.addAll(t1)
                return@BiFunction products.toList()
            } else return@BiFunction t2
        })




    private fun loadProductsDb(): Single<List<ProductModel>> =
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


    // Header Data
    override val header: Single<HeaderModel>
        get() = checkDatabase.getHeader()
            .toHeader()

    override fun loadHeader(): Single<HeaderModel> =
        Single.zip(loadHeaderFromApi(), loadHeaderFromApi(), BiFunction { t1, t2 ->

            val header = HeaderModel()

            return@BiFunction header

        })

    override fun loadHeaderFromApi(): Single<HeaderModel> =
        api.loadTrendingProducts()
            .map {
                val newheader = it.header
                saveHeader(newheader).subscribe()
                return@map newheader.toHeader()

            }
            .onErrorReturn { error ->
                Timber.e(error.toString())

                return@onErrorReturn HeaderModel()
            }
            .subscribeOn(schedulerProvider.io())

    override fun saveHeader (header: HeaderEntity): Completable =
        Completable.create {
            checkDatabase.saveHeaderEntities(header)
            it.onComplete()
        }


    // Available Data
    override fun loadAvailableProducts(): Single<List<ProductModel>> =
        Single.zip(loadAvailableProductsDb(), loadAvailableProductsDb()
            , BiFunction { t1, t2 ->
                if (t1.isNotEmpty()) {
                    val products = ArrayList<ProductModel>()
                    products.addAll(t1)
                    return@BiFunction products.toList()
                } else return@BiFunction t2
            })

    private fun loadAvailableProductsDb(): Single<List<ProductModel>> =
        checkDatabase.getAvailableProductsWithTrueValue()
            .map {
                return@map it.toAvailableProducts()
            }
            .subscribeOn(schedulerProvider.io())


    override fun loadFavoriteProducts(): Single<List<ProductModel>> =
        Single.zip(loadFavoriteProductsDb(), loadFavoriteProductsDb()
            , BiFunction { t1, t2 ->
                if (t1.isNotEmpty()) {
                    val products = ArrayList<ProductModel>()
                    products.addAll(t1)
                    return@BiFunction products.toList()
                } else return@BiFunction t2
            })

    private fun loadFavoriteProductsDb(): Single<List<ProductModel>> =
        checkDatabase.getFavoriteProducts()
            .map {
                return@map it.toAvailableProducts()
            }
            .subscribeOn(schedulerProvider.io())


}