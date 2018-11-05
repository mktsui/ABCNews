package com.balljoint.mktsui.abcnews.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.balljoint.mktsui.abcnews.Model.News
import com.balljoint.mktsui.abcnews.Utilities.VolleyService
import org.json.JSONObject
import java.time.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*

class NewsViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var newsList: MutableLiveData<List<News>>

    // Singleton pattern to make sure only one newsList
    fun getNews(): LiveData<List<News>> {
        if (!::newsList.isInitialized) {
            newsList = MutableLiveData()
            loadNews()
        }
        return newsList
    }


    // To get news from API...
    fun loadNews() {

        Log.i("ABCNews","Refresh News")
        val url = "https://api.rss2json.com/v1/api.json?rss_url=http://www.abc.net.au/news/feed/51120/rss.xml"

        val allNews : ArrayList<News> = arrayListOf()
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val news = response.getJSONArray("items")
                var newsItem : JSONObject
                for (i in 0..(news.length() - 1)) {
                    newsItem = news.getJSONObject(i)
                    var imgUrl: String?
                    if (i == 0) {
                        imgUrl = newsItem
                            .getJSONObject("enclosure")
                            .getString("link")
                    } else {
                        imgUrl = newsItem
                            .getString("thumbnail")
                    }

                    val title = newsItem.getString("title")
                    val timestamp = newsItem.getString("pubDate")

                    val date = SimpleDateFormat("yyyyy-mm-dd hh:mm:ss").parse(timestamp)
                    val newDate = SimpleDateFormat("MMM d, yyyy hh:mm a", Locale.ENGLISH).format(date)

                    allNews.add(News(i,imgUrl, title, newDate))
                }

                newsList.postValue(allNews)
            },
            Response.ErrorListener { error ->
                Toast.makeText(getApplication(),"Cannot Load News...", Toast.LENGTH_SHORT).show()
            }
        )

        VolleyService.requestQueue.add(jsonObjectRequest)

    }

}