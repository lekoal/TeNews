package com.private_projects.tenews.data.allnews

data class VkAllNewsDTO(
    val id: Int,
    val ownerDomain: String,
    val date: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val newsUrl: String
)
