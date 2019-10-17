package com.wa82bj.check_mvvm.ui.main.allProduct

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.wa82bj.check_mvvm.data.repository.ProductsRepository
import com.wa82bj.check_mvvm.ui.common.toResult
import com.wa82bj.check_mvvm.util.ext.map
import com.wa82bj.check_mvvm.util.ext.toLiveData
import com.wa82bj.check_mvvm.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository,
    application: Application,
    private val schedulerProvider: SchedulerProvider
) : ViewModel(), LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()
    private val page = MutableLiveData<Int>()
    private val _isLoadingRefresh = ObservableBoolean()
    val isLoadingRefresh: ObservableBoolean = _isLoadingRefresh
    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean> = _refresh

    val cm =  application.getApplicationContext()
        .getSystemService(Context.CONNECTIVITY_SERVICE)
            as (ConnectivityManager)
    val activeNetwork = cm.getActiveNetworkInfo()


    private val _product = Transformations
        .switchMap(page) { page ->
            return@switchMap repository.loadProducts()
                .toResult(schedulerProvider)
                .toLiveData()
        }
    val product = _product

    private val _productDB = Transformations
        .switchMap(page) { page ->
            return@switchMap repository.loadProductsFromDb()
                .toResult(schedulerProvider)
                .toLiveData()
        }

    val productDB = _productDB


    private val _header =
        repository.loadHeaderFromApi()
                .toResult(schedulerProvider)
                .toLiveData()

    val header = _header

    private val _isLoading: LiveData<Boolean> by lazy {
        _product.map {
            _isLoadingRefresh.set(it.inProgress)
            it.inProgress
        }
    }

    val isLoading = _isLoading

    fun loadProducts(page: Int = 1) {
        this.page.value = page
    }

    fun onRefresh() {
        _refresh.value = true
        page.value = 1
    }

    fun retry() {
        _refresh.value = true
        page.value = 1
    }



    // Check if there is Internet or not
    fun isConnected():Boolean {
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return !isConnected

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
