package com.balljoint.mktsui.abcnews.Utilities

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

object VolleyService {
    private lateinit var context: Context

    val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(context) }

    val imageLoader: ImageLoader by lazy { ImageLoader(requestQueue, LoadNews()) }

    fun initialize(context: Context) {
        this.context = context.applicationContext
    }
}