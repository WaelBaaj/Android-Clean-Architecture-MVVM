package com.wa82bj.check_mvvm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wa82bj.check_mvvm.data.api.response.check24Response.HeaderEntity
import com.wa82bj.check_mvvm.data.api.response.check24Response.ProductEntity

@Database(
    entities = [
        ProductEntity::class,
        HeaderEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun check24Dao(): CheckDao
}