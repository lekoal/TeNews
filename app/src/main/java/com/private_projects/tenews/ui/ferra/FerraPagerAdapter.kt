package com.private_projects.tenews.ui.ferra

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.private_projects.tenews.R
import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.ferra.VkFerraDTO
import com.private_projects.tenews.databinding.NewsRvItemBinding
import com.private_projects.tenews.utils.TimestampConverter

class FerraPagerAdapter :
    PagingDataAdapter<VkFerraDTO.Response.Item, FerraPagerAdapter.ViewHolder>(NewsComparator) {
    var onItemClick: ((List<String>) -> Unit)? = null

    object NewsComparator : DiffUtil.ItemCallback<VkFerraDTO.Response.Item>() {
        override fun areItemsTheSame(
            oldItem: VkFerraDTO.Response.Item,
            newItem: VkFerraDTO.Response.Item
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: VkFerraDTO.Response.Item,
            newItem: VkFerraDTO.Response.Item
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = getItem(position)
        val title = newsItem?.attachments?.get(0)?.link?.title.toString()
        val newsText = newsItem?.attachments?.get(0)?.link?.description.toString()
        var imageUrl = ""
        val newsUrl = newsItem?.attachments?.get(0)?.link?.url.toString()
        val converter = TimestampConverter()
        val date = newsItem?.date?.let { converter.convert(it) }
        newsItem?.attachments?.get(0)?.link?.photo?.sizes?.forEach { size ->
            if (size.type == "p" || size.type == "l") {
                imageUrl = size.url
            }
        }

        holder.title.text = title
        holder.author.text = VkHelpData.FERRA_DOMAIN
        holder.dateTime.text = date
        holder.description.text = newsText
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
                    newsItem?.id.toString(),
                    VkHelpData.FERRA_DOMAIN
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