package com.l.wikipediasearch.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.l.wikipediasearch.R
import com.l.wikipediasearch.database.SearchPageEntity
import com.l.wikipediasearch.helper.click
import kotlinx.android.synthetic.main.search_list_item.view.*


class WikiListAdapter(private val fragment: WikiSearchFragment) :
    RecyclerView.Adapter<WikiListAdapter.ViewHolder>() {

    private var resultList: MutableList<SearchPageEntity> = mutableListOf()

    private val circleTransformation = RequestOptions.circleCropTransform()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(R.drawable.gallery_placeholder)
        .placeholder(R.drawable.gallery_placeholder)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.search_list_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pageEntity = resultList[position]
        holder.itemView.tv_title.text = pageEntity.title
        holder.itemView.tv_description.text = pageEntity.description

        Glide.with(holder.itemView)
            .asBitmap()
            .load(pageEntity.thumbUrl)
            .apply(circleTransformation)
            .into(holder.itemView.img_profile)

        holder.itemView.click {
            (fragment.activity as? BaseActivity)?.addFragment(WebViewFragment.newInstance(pageEntity.title), addToBackStack = true, transition = BaseActivity.TRANSITION.NONE)
        }
    }

    override fun getItemCount(): Int {
        return if (resultList.size == 0) 0 else resultList.size
    }

    fun setData(items: List<SearchPageEntity>?) {
        resultList.addAll(items ?: mutableListOf())
        notifyDataSetChanged()
    }

    fun clear() {
        resultList.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)


}