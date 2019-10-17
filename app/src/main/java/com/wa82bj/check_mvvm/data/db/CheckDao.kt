package com.wa82bj.check_mvvm.data.db

import androidx.room.*
import com.wa82bj.check_mvvm.data.api.response.check24Response.HeaderEntity
import com.wa82bj.check_mvvm.data.api.response.check24Response.ProductEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class CheckDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(product: List<ProductEntity>)

    @Query("SELECT * FROM product_table")
    abstract fun getAllProduct(): Flowable<List<ProductEntity>>

    @Query("SELECT * FROM product_table ")
    abstract fun getProductsFromRoomDB(): List<ProductEntity>


    @Query("SELECT * FROM product_table where id = :productId")
    abstract fun loadOneByProductId(productId: Int): Single<ProductEntity>


    @Query("DELETE FROM product_table")
    abstract fun deleteAll()


    // Header
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertHeader(header: HeaderEntity)

    @Query("SELECT * FROM header_table")
    abstract fun getHeader(): Single<HeaderEntity>

    // Available Products
    @Query("SELECT * FROM product_table where available_product = 1")
    abstract fun getAvailableProductsWithhTrueValue(): List<ProductEntity>

    // Favorite Products
    @Query("SELECT * FROM product_table where fav = 1")
    abstract fun getFavoriteProducts(): List<ProductEntity>

    @Update
    abstract fun update(product: ProductEntity)

    @Query("UPDATE product_table SET fav = :fav  WHERE id =:productId")
    abstract fun update( fav : Int ,productId : Int)

    @Query("SELECT * FROM product_table  where fav = :fav and id = :productId")
    abstract fun isFavoriteProuduct(fav : Int ,productId : Int): Single<ProductEntity>



}