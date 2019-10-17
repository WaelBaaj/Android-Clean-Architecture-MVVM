package com.wa82bj.check_mvvm.di

import androidx.appcompat.app.AppCompatActivity
import com.wa82bj.check_mvvm.ui.main.MainActivity
import com.wa82bj.check_mvvm.ui.main.allProduct.ProductsFragment
import com.wa82bj.check_mvvm.ui.main.available.AvailableFragment
import com.wa82bj.check_mvvm.ui.main.favorite.FavoriteFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityModule {

    @Binds
    fun providesAppCompatActivity(mainActivity: MainActivity): AppCompatActivity


    @ContributesAndroidInjector
    fun contributeProductsFragment(): ProductsFragment

    @ContributesAndroidInjector
    fun contributeAvailableFragment(): AvailableFragment

    @ContributesAndroidInjector
    fun contributeFavoriteFragment(): FavoriteFragment


}