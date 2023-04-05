package com.private_projects.tenews.data.rssnews

import java.util.*

data class RssDTO(
    val id: Int,
    val ownerDomain: String,
    val date: Date,
    val title: String,
    val description: String,
    val imageUrl: String,
    val newsUrl: String
)
