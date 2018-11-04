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
import com.balljoint.mktsui.abcnews.Utilities.VolleyService
import com.balljoint.mktsui.abcnews.ViewModel.NewsViewModel
import java.io.IOException

class MainActivity : AppCompatActivity(){

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var swipeContainer: SwipeRefreshLayout

    private val mSwipeRefreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.swiperefresh) as SwipeRefreshLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // init Volley Services
        VolleyService.initialize(this)
        VolleyService.requestQueue.start()

        val viewManager = LinearLayoutManager(this)
        val viewAdapter = NewsAdapter(this)

        // init Recycler view
        findViewById<RecyclerView>(R.id.news_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        // prepare view model to observe news data
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        newsViewModel.getNews().observe(this, Observer {news ->
            news?.let{ viewAdapter.setNews(it)}
        })


        // Lookup the swipe container view
        swipeContainer = findViewById(R.id.swiperefresh)
        swipeContainer.setOnRefreshListener {
            newsViewModel.loadNews()
            swipeContainer.isRefreshing = false
        }


    }



    override fun onResume() {
        super.onResume()
        newsViewModel.loadNews()
    }

}
