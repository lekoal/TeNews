package com.private_projects.tenews.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.private_projects.tenews.data.details.ImageBlockEntity

@Dao
interface NewsDetailsImageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(imageBlockEntity: ImageBlockEntity)
}