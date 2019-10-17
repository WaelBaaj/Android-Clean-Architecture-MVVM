package com.wa82bj.check_mvvm.di

import android.app.Application
import android.content.Context
import com.wa82bj.check_mvvm.data.api.CheckApi
import com.wa82bj.check_mvvm.data.db.AppDatabase
import com.wa82bj.check_mvvm.data.db.CheckDatabase
import com.wa82bj.check_mvvm.data.repository.ProductsDataRepository
import com.wa82bj.check_mvvm.data.repository.ProductsRepository
import com.wa82bj.check_mvvm.ui.detail.DetailRepository
import com.wa82bj.check_mvvm.ui.detail.ProductRepositoryImp
import com.wa82bj.check_mvvm.util.rx.AppSchedulerProvider
import com.wa82bj.check_mvvm.util.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
object AppModule {
    @Singleton
    @Provides
    @JvmStatic
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    @JvmStatic
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()


    @Singleton
    @Provides
    @JvmStatic
    fun provideDetailRepository(appDatabase: AppDatabase): DetailRepository {
        return ProductRepositoryImp (appDatabase)
    }


    @Singleton
    @Provides
    @JvmStatic
    fun provideProductRepository(
        checkApi: CheckApi,
        checkDatabase: CheckDatabase,
        schedulerProvider: SchedulerProvider
    ): ProductsRepository =
        ProductsDataRepository(
            checkApi,
            checkDatabase,
            schedulerProvider
        )
}