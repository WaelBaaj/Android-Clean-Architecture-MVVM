package com.wa82bj.check_mvvm.di

import com.wa82bj.check_mvvm.ui.detail.DetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface DetailActivityBuilder {

    @ContributesAndroidInjector(modules = [
        DetailActivityModule::class
    ])
    fun contributeDetailActivity(): DetailActivity

}