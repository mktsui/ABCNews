package com.balljoint.mktsui.abcnews.Activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.balljoint.mktsui.abcnews.Adapter.NewsAdapter
import com.balljoint.mktsui.abcnews.R
import com.balljoint.mktsui.abcnews.ViewModel.NewsViewModel

class MainActivity : AppCompatActivity(){

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var viewAdapter: NewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val viewManager = LinearLayoutManager(this)
        viewAdapter = NewsAdapter(this)

        // init Recycler view
        findViewById<RecyclerView>(R.id.news_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        // Lookup the swipe container view and set listener to refresh
        swipeContainer = findViewById(R.id.swiperefresh)
        swipeContainer.setOnRefreshListener {
            refresh()
            swipeContainer.isRefreshing = true
        }

        // prepare view model to observe news data
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        newsViewModel.getNews().observe(this, Observer {news ->
            news?.let{ viewAdapter.setNews(it)}
            swipeContainer.isRefreshing = false
        })

    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    // top function to refresh news
    private fun refresh() {
        viewAdapter.clear()
        newsViewModel.loadNews()
    }

}
