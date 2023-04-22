package com.testapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedSearch(
    val searchWord: String,
    val searchJson: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
)