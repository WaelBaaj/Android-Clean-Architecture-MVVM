package com.wa82bj.check_mvvm.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wa82bj.check_mvvm.ui.main.allProduct.ProductsViewModel
import com.wa82bj.check_mvvm.ui.main.available.AvailableViewModel
import com.wa82bj.check_mvvm.ui.main.favorite.FavoriteViewModel
import com.wa82bj.check_mvvm.vmFactory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProductsViewModel::class)
    fun bindProductsViewModel(
        productsViewModel: ProductsViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AvailableViewModel::class)
    fun bindAvailableViewModel(
        availableViewModel: AvailableViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    fun bindFavoriteViewModel(
        favoriteViewModel: FavoriteViewModel
    ): ViewModel


    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}