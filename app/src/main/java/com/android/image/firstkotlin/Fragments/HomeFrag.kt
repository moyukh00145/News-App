package com.android.image.firstkotlin.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.image.firstkotlin.Network.NewsNetowerk
import com.android.image.firstkotlin.News
import com.android.image.firstkotlin.NewsAdapter
import com.android.image.firstkotlin.R
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject

class HomeFrag() : Fragment() {

    lateinit var adapter: NewsAdapter;
    val newsList= ArrayList<News>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View=inflater.inflate(R.layout.fragment_home, container, false)


        adapter=NewsAdapter(context,newsList)

        getNews()

        val reecycle:RecyclerView=v.findViewById(R.id.news_recycler_view)

        reecycle.layoutManager=
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        reecycle.adapter=adapter

        return v
    }

    fun getNews()
    {
        Log.w("getNews","called")

//        val url="https://newsapi.org/v2/top-headlines?country=in&apiKey=69c9b953a09745ec8daa0334191f26c0"
        val url="https://newsapi.org/v2/everything?q=India&apiKey=69c9b953a09745ec8daa0334191f26c0"
        val jsonObjectRequest =object : JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                if(response!=null){
                    val jsonArray=response.getJSONArray("articles");


                    for (i in 0 until jsonArray.length()){
                        val jsonObject: JSONObject = jsonArray[i] as JSONObject
                        val news=News(jsonObject.getString("title"),jsonObject.getString("description"),jsonObject.getString("urlToImage")
                            ,jsonObject.getString("url"),jsonObject.getString("publishedAt"),jsonObject.getString("author"))
                        newsList.add(news)
                    }

                    adapter.notifyDataSetChanged()
                }
                else{
                    Log.w("response","failed")
                }

            },
            { error ->

                Log.w("error",error.localizedMessage)

            }
        )


        {
            override fun getHeaders(): MutableMap<String, String> {

                val headers = HashMap<String, String>()
                headers["User-Agent"]="Mozilla/5.0"
                return headers
            }
        }
        context?.let { NewsNetowerk.getInstance(it).addToRequestQueue(jsonObjectRequest) }

    }
}