package com.wa82bj.check_mvvm.data.api.response.check24Response


data class CheckResponse(

    val filters: List<String>,
    val header: HeaderEntity,
    val products: List<ProductEntity>
)