package com.private_projects.tenews.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.private_projects.tenews.data.details.VideoBlockEntity

@Dao
interface NewsDetailsVideoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(videoBlockEntity: VideoBlockEntity)
}