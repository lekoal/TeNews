package com.private_projects.tenews.ui.allnews

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.private_projects.tenews.R
import com.private_projects.tenews.data.allnews.VkAllNewsDTO
import com.private_projects.tenews.databinding.NewsRvItemBinding
import com.private_projects.tenews.utils.TimestampConverter

class AllNewsPagerAdapter :
    PagingDataAdapter<VkAllNewsDTO, AllNewsPagerAdapter.ViewHolder>(NewsComparator) {
    var onItemClick: ((List<String>) -> Unit)? = null

    object NewsComparator : DiffUtil.ItemCallback<VkAllNewsDTO>() {
        override fun areItemsTheSame(
            oldItem: VkAllNewsDTO,
            newItem: VkAllNewsDTO
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: VkAllNewsDTO,
            newItem: VkAllNewsDTO
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = getItem(position)

        val converter = TimestampConverter()
        val date = newsItem?.date?.let { converter.convert(it.toInt()) }

        if (
            newsItem?.newsUrl?.contains("https://www.ixbt.com/news/") == true ||
            newsItem?.newsUrl?.contains("https://www.ferra.ru/news/") == true ||
            newsItem?.newsUrl?.contains("https://3dnews.ru/") == true
        ) {
            holder.title.text = newsItem.title
            holder.dateTime.text = date
            holder.description.text = newsItem.description
            holder.author.text = newsItem.ownerDomain
            if (newsItem.imageUrl != "") {
                holder.image.apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    load(newsItem.imageUrl) {
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
                        newsItem.newsUrl,
                        newsItem.id.toString(),
                        newsItem.ownerDomain,
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