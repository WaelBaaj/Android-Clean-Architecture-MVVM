package com.wa82bj.check24_mvvm.di

import android.app.Application
import com.wa82bj.check24_mvvm.Check24App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    MainActivityBuilder::class,
    DetailActivityBuilder::class
])
interface AppComponent : AndroidInjector<Check24App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: Check24App)
}