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
import com.balljoint.mktsui.abcnews.Utilities.Constants
import com.balljoint.mktsui.abcnews.Utilities.VolleySingleton
import org.json.JSONObject
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
        val url = Constants.API_LINK_ABC_NEWS

        // get data from API and parse JSON object
        val allNews : ArrayList<News> = arrayListOf()
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val news = response.getJSONArray("items")
                var newsItem : JSONObject
                for (i in 0..(news.length() - 1)) {
                    newsItem = news.getJSONObject(i)
                    val imgUrl: String = if (i == 0) {
                        newsItem
                            .getJSONObject("enclosure")
                            .getString("link")
                    } else {
                        newsItem
                            .getString("thumbnail")
                    }

                    val title = newsItem.getString("title")
                    val timestamp = newsItem.getString("pubDate")

                    val date = SimpleDateFormat("yyyyy-MM-dd hh:mm:ss", Locale.UK).parse(timestamp)
                    val newDate = SimpleDateFormat("MMM d, yyyy hh:mm a", Locale.ENGLISH).format(date)

                    allNews.add(News(i,imgUrl, title, newDate))
                }

                // put results in LiveData
                newsList.postValue(allNews)
            },
            Response.ErrorListener { error ->
                Toast.makeText(getApplication(),"Cannot Load News...", Toast.LENGTH_SHORT).show()
            }
        )

        // call Volley to send request
        VolleySingleton.getInstance(getApplication()).addToRequestQueue(jsonObjectRequest)

    }

}