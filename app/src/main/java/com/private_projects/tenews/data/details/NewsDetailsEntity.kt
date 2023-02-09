package com.private_projects.tenews.data.details

import androidx.room.Embedded
import androidx.room.Relation

data class NewsDetailsEntity(
    @Embedded
    val header: HeaderBlockEntity,
    @Relation(
        parentColumn = "newsId",
        entity = TextBlockEntity::class,
        entityColumn = "ownerId"
    )
    val textBlocks: List<TextBlockEntity>?,
    @Relation(
        parentColumn = "newsId",
        entity = ImageBlockEntity::class,
        entityColumn = "ownerId"
    )
    val imageBlocks: List<ImageBlockEntity>?,
    @Relation(
        parentColumn = "newsId",
        entity = VideoBlockEntity::class,
        entityColumn = "ownerId"
    )
    val videoBlocks: List<VideoBlockEntity>?
)
