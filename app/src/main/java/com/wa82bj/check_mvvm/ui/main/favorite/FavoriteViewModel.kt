package com.wa82bj.check_mvvm.ui.main.favorite

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.wa82bj.check_mvvm.data.repository.ProductsRepository
import com.wa82bj.check_mvvm.ui.common.toResult
import com.wa82bj.check_mvvm.util.ext.map
import com.wa82bj.check_mvvm.util.ext.toLiveData
import com.wa82bj.check_mvvm.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class FavoriteViewModel @Inject constructor(
    private val repository: ProductsRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel(), LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()
    private val page = MutableLiveData<Int>()
    private val _isLoadingRefresh = ObservableBoolean()
    val isLoadingRefresh: ObservableBoolean = _isLoadingRefresh
    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean> = _refresh

    private val _favoriteProduct = Transformations
        .switchMap(page) { page ->
            return@switchMap repository.loadFavoriteProducts()
                .toResult(schedulerProvider)
                .toLiveData()
        }

    val favoriteProduct = _favoriteProduct

    private val _isLoading: LiveData<Boolean> by lazy {
        _favoriteProduct.map {
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
        page.value = page.value
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}


