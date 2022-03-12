package com.example.lenskart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.lenskart.model.ItemModel
import kotlinx.android.synthetic.main.trend_item.view.*
import java.lang.String
import java.util.*
import kotlin.collections.ArrayList


class DetailsAdapter(private val context: Context, list: ArrayList<ItemModel> = ArrayList()) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>()
{
    private var list: ArrayList<ItemModel> = ArrayList()
    private var items: ArrayList<ItemModel> = ArrayList()
    init {
        this.list = list
        this.items.addAll(list)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: ItemModel = list.get(position)
        val count =
            if (data.star_count >= 1000) data.star_count.toString() + "" else String.valueOf(data.star_count)

        if (data.name != null) holder.author.setText(data.owners.name)
        if (data.name != null) holder.name_repository.setText(data.name)
        if (data.description != null) holder.description.setText(data.description)
        if (data.owners != null) Glide.with(context).load(data.owners.avatar_url).into(holder.img_profile)
        if (data.language != null) holder.language.setText(data.language)
        if (data.licenses != null) holder.licenseView.setText(data.licenses.name)
        holder.stars.setText(count)

        val isExpandable: Boolean = list.get(position).isExpandable()
        holder.expand_layout.setVisibility(if (isExpandable) View.VISIBLE else View.GONE)

        holder.constraint_layout.setOnClickListener(View.OnClickListener {
            val expender: ItemModel = list.get(position)
            expender.isExpandable = !expender.isExpandable
            notifyItemChanged(position)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.trend_item,
                parent,
                false
            )
        )
    }
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val constraint_layout:ConstraintLayout = v.constraint_layout
        val img_profile:ImageView = v.img_profile
        val author:TextView = v.author
        val name_repository:TextView = v.name_repository
        val expand_layout:RelativeLayout = v.expand_layout
        val description:TextView = v.description
        val language:TextView = v.language
        val stars:TextView = v.stars
        val licenseView:TextView = v.licenseView
    }

    fun filter(text: kotlin.String?) {
        var text = text!!.toLowerCase(Locale.getDefault())
        list.clear()
        if (text.isEmpty()) {
            list.addAll(items)
        } else {
            for (wp in items) {
                if (wp.name.startsWith(text) || wp.owners.name.startsWith(text)
                ) {
                    list.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }
   }