package com.private_projects.tenews.data.details

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "texts", foreignKeys = [ForeignKey(
        entity = HeaderBlockEntity::class,
        parentColumns = ["newsId"],
        childColumns = ["ownerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TextBlockEntity(
    @PrimaryKey(autoGenerate = true)
    val textId: Long = 0,
    val ownerId: Int,
    val position: Int,
    val content: String
)
