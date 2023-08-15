package com.testapp.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SavedSearch")
internal data class SavedSearch(
    @ColumnInfo(name = "searchWord")
    val searchWord: String,
    @ColumnInfo(name = "searchJson")
    val searchJson: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
)