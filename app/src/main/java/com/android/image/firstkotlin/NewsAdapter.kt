package com.android.image.firstkotlin

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.image.firstkotlin.Interfaces.OnNewsSelected
import com.bumptech.glide.Glide
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class NewsAdapter(
    val context: Context?,
    val news: ArrayList<News>,
    val newsSelected: OnNewsSelected
) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {



    inner class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView =itemView.findViewById(R.id.title_news)
        val des:TextView=itemView.findViewById(R.id.description)
        val thumbnail:ImageView=itemView.findViewById(R.id.news_image)
        val time:TextView=itemView.findViewById(R.id.time_news)
        val image_lay:CardView=itemView.findViewById(R.id.image_lay)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);

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
                holder.time.text = timeDiff(news.get(position).time)
            }
            else{

                holder.time.text =news.get(position).author+" ."+timeDiff(news.get(position).time)
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

    fun timeDiff(time_format:String?):String{

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

        val date = Calendar.getInstance().time
        var actual: Long = 0
        try {
            val date2 = sdf.parse(time_format)
            actual = date2.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val now = date.time
        val difference_In_Time =now - actual

        val difference_In_Seconds = (TimeUnit.MILLISECONDS
            .toSeconds(difference_In_Time)
                % 60)

        val difference_In_Minutes = (TimeUnit.MILLISECONDS
            .toMinutes(difference_In_Time)
                % 60)

        val difference_In_Hours = (TimeUnit.MILLISECONDS
            .toHours(difference_In_Time)
                % 24)

        val difference_In_Days = (TimeUnit.MILLISECONDS
            .toDays(difference_In_Time)
                % 365)

        val difference_In_Years = (TimeUnit.MILLISECONDS
            .toDays(difference_In_Time)
                / 365)

//        t_day.setText(difference_In_Days.toString())
//        t_hour.setText(difference_In_Hours.toString())
//        t_mint.setText(difference_In_Minutes.toString())
//        t_sec.setText(difference_In_Seconds.toString())

        if (difference_In_Days==0L){
            if (difference_In_Hours==0L){
                if (difference_In_Minutes==0L){
                    return "Few moments before."
                }
                else{
                    return "$difference_In_Minutes m."
                }

            }
            else{
                return "$difference_In_Hours hr."
            }
        }
        else{
            return "$difference_In_Days d."
        }

    }
}