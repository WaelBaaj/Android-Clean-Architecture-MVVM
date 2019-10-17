package com.wa82bj.check_mvvm.di

import android.app.Application
import androidx.room.Room
import com.wa82bj.check_mvvm.data.db.*
import com.wa82bj.check_mvvm.data.db.CheckDao
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
    fun provideCheckDao(db: AppDatabase): CheckDao = db.check24Dao()



    @Singleton
    @Provides
    fun provideCheckDatabase(db: AppDatabase, checkDao: CheckDao): CheckDatabase =
        CheckRoomDatabase(db, checkDao)

}