package xyz.v.socialx.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import xyz.v.socialx.R
import xyz.v.socialx.models.Article
import xyz.v.socialx.utils.TimeAgo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NewsAdapter(private var items: ArrayList<Article>): RecyclerView.Adapter<NewsAdapter.Mvh>() {

    inner class Mvh(view: View): RecyclerView.ViewHolder(view){
        val image:ImageView = view.findViewById(R.id.image)
        val titleTV: TextView = view.findViewById(R.id.title_tv)
        val descTV: TextView = view.findViewById(R.id.desc_tv)
        val sorceTV: TextView = view.findViewById(R.id.sorce_tv)
        val timeTV: TextView = view.findViewById(R.id.time_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Mvh {
        return Mvh(LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent, false))
    }

    override fun onBindViewHolder(holder: Mvh, position: Int) {

        val obj = items[position]

        holder.titleTV.text = obj.title
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val d = inputFormat.parse(obj.publishedAt)
        val agoString = TimeAgo.getTimeAgo(d!!.time)
        holder.timeTV.text = agoString
        holder.descTV.text = obj.description
        holder.sorceTV.text = obj.source.name

        Glide.with(holder.timeTV)
            .load(obj.urlToImage)
            .into(holder.image)


    }

    override fun getItemCount(): Int {
        return items.size
    }

}