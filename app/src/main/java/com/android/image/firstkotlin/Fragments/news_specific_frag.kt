package com.android.image.firstkotlin.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.image.firstkotlin.Interfaces.OnNewsSelected
import com.android.image.firstkotlin.Network.NewsNetowerk
import com.android.image.firstkotlin.News
import com.android.image.firstkotlin.NewsAdapter
import com.android.image.firstkotlin.R
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject


class news_specific_frag(c:Context) : Fragment() {

    lateinit var adapter: NewsAdapter;
    val newsList= ArrayList<News>()
    val con: Context =c
    lateinit var reecycle: RecyclerView
    lateinit var progressBar:ProgressBar
    lateinit var no_text_view:TextView
    lateinit var onNewsSelected: OnNewsSelected


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View?
    {
        val view:View=inflater.inflate(R.layout.fragment_news_specific_frag, container, false)


        onNewsSelected=object : OnNewsSelected{
            override fun newsSelected(url: String?) {
                if (url != null) {
                    Log.w("url",url)




                }
            }

        }

        adapter=NewsAdapter(context,newsList,onNewsSelected)
        Log.w("frag","context")

         reecycle =view.findViewById(R.id.news_recycler_view)
        progressBar=view.findViewById(R.id.prog_bar)
        no_text_view=view.findViewById(R.id.no_element_text)
        reecycle.visibility=View.GONE
        progressBar.visibility=View.VISIBLE
        no_text_view.visibility=View.GONE


        reecycle.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        reecycle.adapter=adapter

        return view
    }

    fun setData(selection:String,check:Boolean){

        if (this::reecycle.isInitialized&&this::progressBar.isInitialized){
            reecycle.visibility=View.GONE
            progressBar.visibility=View.VISIBLE
        }
        if (check){
            getNews("https://newsapi.org/v2/everything?q=$selection&apiKey=69c9b953a09745ec8daa0334191f26c0")

        }
        else {

            getNews("https://newsapi.org/v2/top-headlines?country=in&category=$selection&apiKey=69c9b953a09745ec8daa0334191f26c0")
        }
    }

    fun getNews(news_url:String) {
        Log.w("getNews","calledfrag")
        if (this::no_text_view.isInitialized){
            no_text_view.visibility=View.GONE
        }

//        val url="https://newsapi.org/v2/top-headlines?country=in&apiKey=69c9b953a09745ec8daa0334191f26c0"
        val jsonObjectRequest =object : JsonObjectRequest(
            Request.Method.GET, news_url, null,
            { response ->

                if(response!=null){
                    val jsonArray=response.getJSONArray("articles");


                    newsList.clear()


                    for (i in 0 until jsonArray.length()){
                        val jsonObject: JSONObject = jsonArray[i] as JSONObject
                        val news= News(jsonObject.getString("title"),jsonObject.getString("description"),jsonObject.getString("urlToImage")
                            ,jsonObject.getString("url"),jsonObject.getString("publishedAt"),jsonObject.getString("author"))
                        newsList.add(news)
                    }

                    Log.w("size",newsList.size.toString())

                    adapter.notifyDataSetChanged()
                   if (newsList.size>0){
                       reecycle.visibility=View.VISIBLE
                       progressBar.visibility=View.GONE
                   }
                    else{
                       progressBar.visibility=View.GONE
                       no_text_view.visibility=View.VISIBLE
                   }

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
//        context?.let { NewsNetowerk.getInstance(it).addToRequestQueue(jsonObjectRequest) }

        if (con != null) {
            NewsNetowerk.getInstance(con).addToRequestQueue(jsonObjectRequest)
        }
        else{
            Log.w("context","null")
        }
    }

    override fun onPause() {
        Log.w("onPause","called")
        super.onPause()
    }

    override fun onResume() {
        Log.w("onResume","called")
        super.onResume()
    }

    override fun onStart() {
        Log.w("onstart","called")
        super.onStart()
    }

}