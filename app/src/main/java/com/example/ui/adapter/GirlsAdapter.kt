package com.example.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class GirlsAdapter(var context: Context, var girls: MutableList<Int>) : RecyclerView.Adapter<GirlsAdapter.ViewHolder>() {


    private var data = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.girl_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return girls.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.girl.setImageResource(girls[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var girl: ImageView = itemView.findViewById(R.id.img_girl)
    }


}