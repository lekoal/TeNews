package com.private_projects.tenews.ui.ixbt

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.private_projects.tenews.R
import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.ixbt.VkIxbtDTO
import com.private_projects.tenews.databinding.NewsRvItemBinding
import com.private_projects.tenews.utils.TimestampConverter

class IxbtPagerAdapter :
    PagingDataAdapter<VkIxbtDTO.Response.Item, IxbtPagerAdapter.ViewHolder>(NewsComparator) {
    var onItemClick: ((List<String>) -> Unit)? = null

    object NewsComparator : DiffUtil.ItemCallback<VkIxbtDTO.Response.Item>() {
        override fun areItemsTheSame(
            oldItem: VkIxbtDTO.Response.Item,
            newItem: VkIxbtDTO.Response.Item
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: VkIxbtDTO.Response.Item,
            newItem: VkIxbtDTO.Response.Item
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = getItem(position)
        val tempText = newsItem?.text.toString()
        val title = tempText.substringBefore("\n")
        val newsText = tempText.substringAfter("\n")
        var imageUrl = ""
        var newsUrl = ""
        newsItem?.attachments?.forEach { attachment ->
            attachment.photo?.sizes?.forEach { size ->
                if (size.type == "q" || size.type == "r") {
                    imageUrl = size.url
                }
            }
            newsUrl = attachment.link?.url.toString()
        }
        val converter = TimestampConverter()
        val date = newsItem?.date?.let { converter.convert(it) }

        if (
            newsItem?.attachments?.get(1)?.link?.url?.contains("https://www.ixbt.com/news/")
            == true
        ) {
            holder.title.text = title
            holder.dateTime.text = date
            holder.description.text = newsText
            holder.author.text = VkHelpData.IXBT_DOMAIN

            if (imageUrl != "") {
                holder.image.apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    load(imageUrl) {
                        placeholder(R.drawable.placeholder)
                        size(200, 100)
                        crossfade(true)
                        crossfade(500)
                    }
                }
            } else {
                holder.image.apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    load(R.drawable.no_photo) {
                        placeholder(R.drawable.placeholder)
                        size(200, 100)
                        crossfade(true)
                        crossfade(500)
                    }
                }
            }

            holder.itemView.setOnClickListener {
                onItemClick?.invoke(
                    listOf(
                        newsUrl,
                        newsItem.id.toString(),
                        VkHelpData.IXBT_DOMAIN,
                        date ?: ""
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsRvItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(binding: NewsRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val view = binding.root
        val title = binding.rvItemTitle
        val author = binding.rvItemAuthor
        val dateTime = binding.rvItemDate
        val image = binding.rvItemImage
        val description = binding.rvItemDescription
    }
}