package com.wa82bj.check_mvvm.data.api.response.check24Response

import androidx.room.*


@Entity(tableName = "product_table")
data class ProductEntity(

    @ColumnInfo(name="available_product")
    val available: Boolean,
    val description: String,
    @PrimaryKey
    val id: Int,
    val imageURL: String,
    val longDescription: String,
    val name: String,
    @Embedded(prefix = "Price_")
    val price: Price,
    val rating: Double,
    val releaseDate: Int,
    val type: String,
    var fav: Boolean

)