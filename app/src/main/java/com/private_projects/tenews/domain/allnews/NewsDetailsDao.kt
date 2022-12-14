package com.private_projects.tenews.domain.allnews

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.private_projects.tenews.data.details.NewsDetailsEntity

@Dao
interface NewsDetailsDao {
    @Transaction
    @Query("SELECT * FROM news WHERE newsId = :id")
    fun getDetails(id: Int): LiveData<NewsDetailsEntity>
}