package com.private_projects.tenews.ui.allnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.private_projects.tenews.R
import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.ixbt.VkIxbtDTO
import com.private_projects.tenews.databinding.NewsRvItemBinding
import com.private_projects.tenews.utils.TimestampConverter

class AllNewsPagerAdapter :
    PagingDataAdapter<VkIxbtDTO.Response.Item, AllNewsPagerAdapter.ViewHolder>(NewsComparator) {
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
        val tempText = newsItem?.text?.split("\n\n")
        val title = tempText?.get(0).toString()
        val newsText = tempText?.get(1).toString()
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

        holder.title.text = title
        holder.dateTime.text = date
        holder.description.text = newsText
        holder.author.text = VkHelpData.IXBT_DOMAIN
        Glide.with(holder.view)
            .load(imageUrl)
            .override(200, 100)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(
                listOf(
                    newsUrl,
                    title
                )
            )
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