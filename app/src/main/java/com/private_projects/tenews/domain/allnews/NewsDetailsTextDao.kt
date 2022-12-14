package com.private_projects.tenews.domain.allnews

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.private_projects.tenews.data.details.TextBlockEntity

@Dao
interface NewsDetailsTextDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(textBlockEntity: TextBlockEntity)
}