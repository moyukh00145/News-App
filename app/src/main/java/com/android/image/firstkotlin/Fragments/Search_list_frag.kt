package com.android.image.firstkotlin.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.android.image.firstkotlin.R

class Search_list_frag : Fragment() {

    lateinit var search_list:ListView
    lateinit var adapter: ArrayAdapter<String>
    lateinit var topic_name:ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View=inflater.inflate(R.layout.fragment_search_list_frag, container, false)
        search_list=view.findViewById(R.id.search_list)
        topic_name= ArrayList()
        topic_name.add("moyukh")
        topic_name.add("Moyukh")
        topic_name.add("dipa")
        topic_name.add("Dipa")
        topic_name.add("Priyangshu")
        adapter= context?.let { ArrayAdapter(it,android.R.layout.simple_list_item_1,topic_name) }!!
        search_list.adapter=adapter



        return view
    }


}