package com.wa82bj.check_mvvm.di

import android.app.Application
import com.wa82bj.check_mvvm.CheckApp
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
interface AppComponent : AndroidInjector<CheckApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: CheckApp)
}