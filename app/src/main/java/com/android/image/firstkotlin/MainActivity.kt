package com.android.image.firstkotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import com.android.image.firstkotlin.Fragments.HomeFrag
import com.android.image.firstkotlin.Fragments.ProfileFrag
import com.android.image.firstkotlin.Fragments.news_specific_frag
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var search_list: ListView
    lateinit var adapter: ArrayAdapter<String>
    lateinit var topic_name: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val frameLayout: FrameLayout = findViewById(R.id.frame_container)

        search_list = findViewById(R.id.search_list)
        topic_name = ArrayList()
        topic_name.add("Market")
        topic_name.add("Stock")
        topic_name.add("Bitcoin")
        topic_name.add("Prime minister")
        topic_name.add("Narendra Modi")
        topic_name.add("politics")
        topic_name.add("Election")
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, topic_name)
        search_list.adapter = adapter


        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            topAppBar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        val menuItem: MenuItem = topAppBar.menu.findItem(R.id.search_bar_lay)


        val homeFrag = HomeFrag()
        val specific_frag = news_specific_frag(this)
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame_container, homeFrag).commit();


        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true


            when (menuItem.itemId) {
                R.id.home -> fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, homeFrag).commit();
                R.id.movies -> {
                    fragmentManager.beginTransaction().replace(R.id.frame_container, specific_frag)
                        .commit();
                    specific_frag.setData("entertainment", false)
                }
                R.id.Industry -> {
                    fragmentManager.beginTransaction().replace(R.id.frame_container, specific_frag)
                        .commit();
                    specific_frag.setData("Sensex", true)
                }
                R.id.Market -> {
                    fragmentManager.beginTransaction().replace(R.id.frame_container, specific_frag)
                        .commit();
                    specific_frag.setData("Stock Market", true)
                }
                R.id.sports -> {
                    fragmentManager.beginTransaction().replace(R.id.frame_container, specific_frag)
                        .commit();
                    specific_frag.setData("sports", false)
                }
                R.id.science -> {
                    fragmentManager.beginTransaction().replace(R.id.frame_container, specific_frag)
                        .commit();
                    specific_frag.setData("science", false)
                }
                R.id.technology -> {
                    fragmentManager.beginTransaction().replace(R.id.frame_container, specific_frag)
                        .commit();
                    specific_frag.setData("technology", false)
                }
                R.id.health -> {
                    fragmentManager.beginTransaction().replace(R.id.frame_container, specific_frag)
                        .commit();
                    specific_frag.setData("health", false)
                }
                R.id.bollywood -> {
                    fragmentManager.beginTransaction().replace(R.id.frame_container, specific_frag)
                        .commit();
                    specific_frag.setData("Indian Bollywood cinema", true)
                }
                R.id.hollywood -> {
                    fragmentManager.beginTransaction().replace(R.id.frame_container, specific_frag)
                        .commit();
                    specific_frag.setData("Hollywood cinema", true)
                }
            }

            drawerLayout.closeDrawer(Gravity.LEFT)
            true
        }

        val v: View = navigationView.getHeaderView(0)

        val image_btn: ImageView = v.findViewById(R.id.drop_button)
        val btn_view: View = v.findViewById<LinearLayout>(R.id.option_lay)
        val profile_btn: ImageView = v.findViewById(R.id.profile_btn)
        val setting_btn: ImageView = v.findViewById(R.id.setting)
        val log_out: ImageView = v.findViewById(R.id.log_out)
        val signup: Button = v.findViewById(R.id.sign_in)

        image_btn.setOnClickListener {

            val anim: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_down)
            val anim2: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)

            if (btn_view.visibility == View.GONE) {
                btn_view.animation = anim
                btn_view.visibility = View.VISIBLE
                image_btn.setImageResource(R.drawable.drop_up)
            } else {
                btn_view.animation = anim2
                btn_view.visibility = View.GONE
                image_btn.setImageResource(R.drawable.dropdown)
            }
        }

        profile_btn.setOnClickListener {
            drawerLayout.closeDrawer(Gravity.LEFT)
            Toast.makeText(this, "Profile section", Toast.LENGTH_SHORT).show()
            val profileFrag:ProfileFrag= ProfileFrag()
            fragmentManager.beginTransaction().replace(R.id.frame_container, profileFrag).commit()

        }

        signup.setOnClickListener {

            val intent=Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }

        setting_btn.setOnClickListener {
            Toast.makeText(this, "Setting section", Toast.LENGTH_SHORT).show()
            drawerLayout.closeDrawer(Gravity.LEFT)
        }

        log_out.setOnClickListener {
            Toast.makeText(this, "logged out", Toast.LENGTH_SHORT).show()
            drawerLayout.closeDrawer(Gravity.LEFT)
        }

        menuItem.setOnMenuItemClickListener {
            Log.w("MenuItem", "Clicked")

            return@setOnMenuItemClickListener true
        }

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                frameLayout.visibility = View.GONE
                search_list.visibility = View.VISIBLE

                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                search_list.visibility = View.GONE
                frameLayout.visibility = View.VISIBLE
                return true
            }

        })

        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.queryHint = "Search topic of News"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.w("search", "selected")

                if (query != null) {
                    fragmentManager.beginTransaction().replace(R.id.frame_container, specific_frag)
                        .commit()
                    specific_frag.setData(query, true)
                }
                menuItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                adapter.filter.filter(newText)

                return true
            }

        })

        search_list.onItemClickListener=object :AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                fragmentManager.beginTransaction().replace(R.id.frame_container, specific_frag)
                    .commit()
                specific_frag.setData(topic_name.get(p2), true)
                menuItem.collapseActionView()
            }

        }


    }


}