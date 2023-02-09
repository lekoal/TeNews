package com.private_projects.tenews.data.details

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "videos", foreignKeys = [ForeignKey(
        entity = HeaderBlockEntity::class,
        parentColumns = ["newsId"],
        childColumns = ["ownerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class VideoBlockEntity(
    @PrimaryKey(autoGenerate = true)
    val videoId: Long = 0,
    val ownerId: Int,
    val position: Int,
    val url: String
)
