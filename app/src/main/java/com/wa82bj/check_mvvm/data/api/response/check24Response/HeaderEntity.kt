package com.wa82bj.check24_mvvm.data.api.response.check24Response


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "header_table")
data class HeaderEntity(

    val headerDescription: String,
    @PrimaryKey
    val headerTitle: String
)