package com.balljoint.mktsui.abcnews.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.toolbox.NetworkImageView
import com.balljoint.mktsui.abcnews.Model.News
import com.balljoint.mktsui.abcnews.R
import com.balljoint.mktsui.abcnews.Utilities.VolleySingleton

/*
    Adapter class for news list LiveData object
 */
class NewsAdapter(context: Context)
                : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    companion object {
        const val REGULAR_NEWS = 1
        const val TOP_NEWS = 0
    }

    private val mContext = context
    private var mNewsList = emptyList<News>()

    inner class NewsViewHolder(newsView: View) : RecyclerView.ViewHolder(newsView)

    internal fun setNews(newsList:List<News>) {
        this.mNewsList = newsList
        notifyDataSetChanged()
    }

    internal fun clear() {
        this.mNewsList = emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                                        : NewsAdapter.NewsViewHolder {
        val vh:View = if (viewType == TOP_NEWS) {
            LayoutInflater.from(mContext)
                .inflate(R.layout.top_news_item, parent, false)
        } else {
            LayoutInflater.from(mContext)
                .inflate(R.layout.news_card_item, parent, false)
        }

        return NewsViewHolder(vh)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current = mNewsList[position]
        val newsTitleView: TextView
        val newsDateView: TextView
        val newsThumbnail: NetworkImageView

        // Only the first news in the list is the top large article
        when(position) {
            0 -> {

                newsTitleView = holder.itemView.findViewById(R.id.top_news_title)
                newsDateView = holder.itemView.findViewById(R.id.top_news_timestamp)
                newsThumbnail = holder.itemView.findViewById(R.id.top_news_thumbnail)
            }
            else -> {

                newsTitleView = holder.itemView.findViewById(R.id.news_title)
                newsDateView = holder.itemView.findViewById(R.id.news_timestamp)
                newsThumbnail = holder.itemView.findViewById(R.id.news_thumbnail)
            }
        }

        newsTitleView.text = current.newsTitle
        newsDateView.text = current.newsTimeStamp

        newsThumbnail.setImageUrl(current.imgUrl, VolleySingleton.getInstance(this.mContext).imageLoader)
    }

    override fun getItemCount() = mNewsList.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TOP_NEWS else REGULAR_NEWS
    }
}