package com.wa82bj.check24_mvvm.di

import android.app.Application
import androidx.room.Room
import com.wa82bj.check24_mvvm.data.db.*
import com.wa82bj.check24_mvvm.data.db.Check24Dao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "check.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()


    @Singleton
    @Provides
    fun provideNewsDao(db: AppDatabase): Check24Dao = db.check24Dao()



    @Singleton
    @Provides
    fun provideProductsDatabase(db: AppDatabase, check24Dao: Check24Dao): CheckDatabase =
        Check24RoomDatabase(db, check24Dao)

}