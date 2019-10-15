package com.wa82bj.check24_mvvm.data.model

import com.wa82bj.check24_mvvm.data.api.response.check24Response.Price


data class ProductModel(

    val available: Boolean = false,
    val fav: Boolean ? = null,
    val description: String? = null,
    val id: Int? = null,
    val imageURL: String ? = null,
    val longDescription: String ? = null,
    val name: String ? = null,
    val price: Price? = null,
    val rating: Double? = null,
    val releaseDate: Int? = null,
    val type: String? = null
)