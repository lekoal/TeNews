package com.private_projects.tenews.data.tdnews


import com.google.gson.annotations.SerializedName

data class VkTdnewsDTO(
    val response: Response
) {
    data class Response(
        val count: Int,
        val items: List<Item>
    ) {
        data class Item(
            val id: Int,
            @SerializedName("from_id")
            val fromId: Int,
            @SerializedName("owner_id")
            val ownerId: Int,
            val date: Int,
            @SerializedName("marked_as_ads")
            val markedAsAds: Int,
            @SerializedName("post_type")
            val postType: String,
            val text: String,
            val attachments: List<Attachment>,
            @SerializedName("post_source")
            val postSource: PostSource,
            val comments: Comments,
            val likes: Likes,
            val reposts: Reposts,
            val views: Views,
            val donut: Donut,
            @SerializedName("short_text_rate")
            val shortTextRate: Double,
            val hash: String
        ) {
            data class Attachment(
                val type: String,
                val link: Link?,
                val video: Video?
            ) {
                data class Link(
                    val url: String,
                    val title: String,
                    val caption: String,
                    val description: String,
                    val photo: Photo?
                ) {
                    data class Photo(
                        @SerializedName("album_id")
                        val albumId: Int,
                        val date: Int,
                        val id: Int,
                        @SerializedName("owner_id")
                        val ownerId: Int,
                        val sizes: List<Size>,
                        val text: String,
                        @SerializedName("user_id")
                        val userId: Int,
                        @SerializedName("has_tags")
                        val hasTags: Boolean
                    ) {
                        data class Size(
                            val height: Int,
                            val type: String,
                            val width: Int,
                            val url: String
                        )
                    }
                }

                data class Video(
                    @SerializedName("access_key")
                    val accessKey: String,
                    @SerializedName("can_comment")
                    val canComment: Int,
                    @SerializedName("can_like")
                    val canLike: Int,
                    @SerializedName("can_repost")
                    val canRepost: Int,
                    @SerializedName("can_subscribe")
                    val canSubscribe: Int,
                    @SerializedName("can_add_to_faves")
                    val canAddToFaves: Int,
                    @SerializedName("can_add")
                    val canAdd: Int,
                    val comments: Int,
                    val date: Int,
                    val description: String,
                    val duration: Int,
                    val image: List<Image>,
                    val id: Int,
                    @SerializedName("owner_id")
                    val ownerId: Int,
                    val title: String,
                    @SerializedName("track_code")
                    val trackCode: String,
                    val type: String,
                    val views: Int,
                    @SerializedName("local_views")
                    val localViews: Int,
                    val platform: String
                ) {
                    data class Image(
                        val url: String,
                        val width: Int,
                        val height: Int,
                        @SerializedName("with_padding")
                        val withPadding: Int
                    )
                }
            }

            data class PostSource(
                val type: String
            )

            data class Comments(
                @SerializedName("can_post")
                val canPost: Int,
                val count: Int,
                @SerializedName("groups_can_post")
                val groupsCanPost: Boolean
            )

            data class Likes(
                @SerializedName("can_like")
                val canLike: Int,
                val count: Int,
                @SerializedName("user_likes")
                val userLikes: Int,
                @SerializedName("can_publish")
                val canPublish: Int
            )

            data class Reposts(
                val count: Int,
                @SerializedName("user_reposted")
                val userReposted: Int
            )

            data class Views(
                val count: Int
            )

            data class Donut(
                @SerializedName("is_donut")
                val isDonut: Boolean
            )
        }
    }
}