package com.wa82bj.check24_mvvm.di

import com.wa82bj.check24_mvvm.di.DetailActivityModule
import com.wa82bj.check24_mvvm.ui.detail.DetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface DetailActivityBuilder {

    @ContributesAndroidInjector(modules = [
        DetailActivityModule::class
    ])
    fun contributeDetailActivity(): DetailActivity

}