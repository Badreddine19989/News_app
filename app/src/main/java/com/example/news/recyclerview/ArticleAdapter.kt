package com.example.news.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.databinding.ItemArticlePreviewBinding
import com.example.news.database.Article
import com.example.news.newsarticle.NewsArticleViewModel

class ArticleAdapter(val clickListener: ArticleListener) :
    ListAdapter<Article, ArticleAdapter.ViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!, clickListener)
    }

    class ViewHolder private constructor(val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Article, clickListener: ArticleListener
        ) {
            binding.article = item
            binding.clickListener = clickListener
            Glide.with(itemView).load(item.urlToImage).into(binding.ivArticleImage)
            binding.tvDescription.text = item.description
            binding.tvTitle.text = item.title
            binding.tvSource.text = item.source?.name
            binding.tvPublishedAt.text = item.publishedAt
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = ItemArticlePreviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(view)
            }
        }
    }
}

class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}

class ArticleListener(val clickListener: (article: Article) -> Unit) {
    fun onClick(article: Article) = clickListener(article)
}
