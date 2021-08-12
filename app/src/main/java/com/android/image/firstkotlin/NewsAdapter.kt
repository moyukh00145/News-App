package com.android.image.firstkotlin

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.image.firstkotlin.Interfaces.OnNewsSelected
import com.bumptech.glide.Glide

class NewsAdapter(val context: Context?,val news:ArrayList<News>,val newsSelected: OnNewsSelected) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {



    inner class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView =itemView.findViewById(R.id.title_news)
        val des:TextView=itemView.findViewById(R.id.description)
        val thumbnail:ImageView=itemView.findViewById(R.id.news_image)
        val time:TextView=itemView.findViewById(R.id.time_news)
        val image_lay:CardView=itemView.findViewById(R.id.image_lay)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.single_item,parent,false);

        return NewsHolder(view)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {


        holder.time.text=news.get(position).time
        if (news.get(position).news_title.equals("null")){
            holder.itemView.visibility=View.GONE
        }
        else{
            holder.name.text = news.get(position).news_title
        }

        if (news.get(position).news_description.equals("null")){
            holder.des.visibility=View.GONE
        }
        else{
                holder.des.text = news.get(position).news_description
        }

        if (news.get(position).time.equals("null")){
            holder.time.visibility=View.GONE
        }
        else{
            if (news.get(position).author.equals("null")){
                holder.time.text = news.get(position).time
            }
            else{
                val content: String? =news.get(position).time;
                holder.time.text =content+"\nAuthor :- "+ news.get(position).author
            }

        }

        if (news.get(position).image_url.equals("null")){

            holder.image_lay.visibility=View.GONE

        }
        else{
            if (context != null) {
                Glide.with(context).load(news.get(position).image_url).into(holder.thumbnail)
            }
        }

        holder.itemView.setOnClickListener {
           newsSelected.newsSelected(news.get(position).news_url)
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }
}