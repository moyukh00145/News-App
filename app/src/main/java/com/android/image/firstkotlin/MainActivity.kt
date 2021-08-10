package com.android.image.firstkotlin

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.android.image.firstkotlin.Fragments.HomeFrag
import com.android.image.firstkotlin.Fragments.news_specific_frag
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


//    val newsList= ArrayList<News>()
//    lateinit var adapter:NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            topAppBar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        val homeFrag:HomeFrag= HomeFrag()
        val specific_frag:news_specific_frag= news_specific_frag(this)
        val fragmentManager:FragmentManager=supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame_container,homeFrag).commit();


        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true


            when(menuItem.itemId){
                R.id.home->fragmentManager.beginTransaction().replace(R.id.frame_container,homeFrag).commit();
                R.id.movies->{
                    fragmentManager.beginTransaction().replace(R.id.frame_container,specific_frag).commit();
                    specific_frag.setData("entertainment")
                }
                R.id.sports->{
                    fragmentManager.beginTransaction().replace(R.id.frame_container,specific_frag).commit();
                    specific_frag.setData("sports")
                }
                R.id.science->{
                    fragmentManager.beginTransaction().replace(R.id.frame_container,specific_frag).commit();
                    specific_frag.setData("science")
                }
                R.id.technology->{
                    fragmentManager.beginTransaction().replace(R.id.frame_container,specific_frag).commit();
                    specific_frag.setData("technology")
                }
                R.id.health->{
                    fragmentManager.beginTransaction().replace(R.id.frame_container,specific_frag).commit();
                    specific_frag.setData("health")
                }
            }

            drawerLayout.closeDrawer(Gravity.LEFT)
            true
        }

//        getNews()


//        adapter=NewsAdapter(this,newsList)
//
//        news_recycler_view.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
//        news_recycler_view.adapter=adapter

        val v: View =navigationView.getHeaderView(0)

        val image_btn:ImageView=v.findViewById(R.id.drop_button)
        val btn_view: View = v.findViewById<LinearLayout>(R.id.option_lay)

        image_btn.setOnClickListener {

            val anim:Animation=AnimationUtils.loadAnimation(this, R.anim.slide_down)
            val anim2:Animation=AnimationUtils.loadAnimation(this, R.anim.slide_up)

            if (btn_view.visibility==View.GONE) {
                btn_view.animation=anim
                btn_view.visibility = View.VISIBLE
                image_btn.setImageResource(R.drawable.drop_up)
            } else {
                btn_view.animation=anim2
                btn_view.visibility=View.GONE
                image_btn.setImageResource(R.drawable.dropdown)
            }
        }




    }

//    fun getNews()
//    {
//        Log.w("getNews","called")
//
//        val url="https://newsapi.org/v2/top-headlines?country=in&apiKey="
//        val jsonObjectRequest =object :JsonObjectRequest(
//            Request.Method.GET, url, null,
//            { response ->
//
//               if(response!=null){
//                   val jsonArray=response.getJSONArray("articles");
//
//
//                   for (i in 0 until jsonArray.length()){
//                       val jsonObject:JSONObject= jsonArray[i] as JSONObject
//                       val news=News(jsonObject.getString("title"),jsonObject.getString("description"),jsonObject.getString("urlToImage")
//                           ,jsonObject.getString("url"),jsonObject.getString("publishedAt"),jsonObject.getString("author"))
//                       newsList.add(news)
//                   }
//
//                   adapter.notifyDataSetChanged()
//               }
//                else{
//                    Log.w("response","failed")
//               }
//
//            },
//            { error ->
//
//                Log.w("error",error.localizedMessage)
//
//            }
//        )
//
//
//        {
//            override fun getHeaders(): MutableMap<String, String> {
//
//                val headers = HashMap<String, String>()
//                headers["User-Agent"]="Mozilla/5.0"
//                return headers
//            }
//        }
//        NewsNetowerk.getInstance(this).addToRequestQueue(jsonObjectRequest)
//
//    }
}