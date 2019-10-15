package com.wa82bj.check24_mvvm.data.api.response.check24Response


data class Check24Response(

    val filters: List<String>,
    val header: HeaderEntity,
    val products: List<ProductEntity>
)