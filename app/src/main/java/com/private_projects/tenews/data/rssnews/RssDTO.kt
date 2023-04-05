package com.private_projects.tenews.data.rssnews

data class RssDTO(
    val id: Int,
    val ownerDomain: String,
    val date: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val newsUrl: String
)
