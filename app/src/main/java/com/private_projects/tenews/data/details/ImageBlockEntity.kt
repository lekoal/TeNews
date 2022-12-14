package com.private_projects.tenews.data.details

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "images", foreignKeys = [ForeignKey(
        entity = HeaderBlockEntity::class,
        parentColumns = ["newsId"],
        childColumns = ["ownerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ImageBlockEntity(
    @PrimaryKey(autoGenerate = true)
    val imageId: Long = 0,
    val ownerId: Int,
    val position: Int,
    val url: String
)