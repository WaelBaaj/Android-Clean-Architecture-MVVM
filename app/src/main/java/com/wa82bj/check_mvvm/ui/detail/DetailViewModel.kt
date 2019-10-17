package com.wa82bj.check_mvvm.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wa82bj.check_mvvm.data.api.response.check24Response.ProductEntity
import javax.inject.Inject


class DetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {

    private val TAG = DetailViewModel::class.java.simpleName
    val productData = MutableLiveData<ProductEntity>()
    val isLoad = MutableLiveData<Boolean>()
    val isFavorite = MutableLiveData<Int>()

    init {
        isLoad.value = false
    }

    fun getDetail(id: Int?) {
        if (id == null) return
        getProductDetailUseCase.saveProductId(id)
        getProductDetailUseCase.execute(
            onSuccess = {
                isLoad.value = true
                productData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun updateFavoriteStatus() {
        productData.value?.let { product ->
            if (product.fav == true) {

                getProductDetailUseCase.updateAsFavorite(0 , product.id)

            }else{
                getProductDetailUseCase.updateAsFavorite(1 , product.id)

            }
            isFavorite.value?.let {
                isFavorite.value = it
            }
        }
    }

    fun checkFavoriteStatus(fav: Int,productId: Int) {
        isFavorite.value = getProductDetailUseCase.isFavoriteProduct(fav,productId)
    }
}
