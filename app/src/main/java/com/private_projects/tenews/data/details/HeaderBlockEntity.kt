package com.private_projects.tenews.data.details

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class HeaderBlockEntity(
    @PrimaryKey
    val newsId: Int,
    val ownerDomain: String,
    val firstTitle: String,
    val secondTitle: String?
)
