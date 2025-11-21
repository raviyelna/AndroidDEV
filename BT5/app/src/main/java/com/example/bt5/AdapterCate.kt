package com.example.bt5

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AdapterCate(
    private val context: Context,
    private val categoryList: List<Category>
) : RecyclerView.Adapter<AdapterCate.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = categoryList[position]
        holder.txt_category.text = category.name

        Glide.with(context)
            .load(category.images)
            .into(holder.img_category)
    }

    override fun getItemCount(): Int = categoryList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_category: TextView = itemView.findViewById(R.id.NameCag)
        val img_category: ImageView = itemView.findViewById(R.id.image_cate)

        init {
            itemView.setOnClickListener {
                // Safe position check
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    Toast.makeText(context, categoryList[pos].name, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
