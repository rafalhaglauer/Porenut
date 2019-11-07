package com.wardrobes.porenut.ui.pattern.wardrobe.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.wardrobes.porenut.R
import kotlinx.android.synthetic.main.wardrobe_pattern_gallery_adapter.view.*

class WardrobePatternGalleryAdapter : RecyclerView.Adapter<WardrobePatternGalleryViewHolder>() {

    private var list: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WardrobePatternGalleryViewHolder {
        return WardrobePatternGalleryViewHolder(parent)
    }

    override fun onBindViewHolder(holder: WardrobePatternGalleryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setItem(list: List<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}

class WardrobePatternGalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    constructor(parent: ViewGroup) : this(LayoutInflater.from(parent.context).inflate(R.layout.wardrobe_pattern_gallery_adapter, parent, false))

    fun bind(imgPath: String) {
        val imgView = itemView.imgWardrobe
        Glide
            .with(imgView)
            .load(imgPath)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .centerCrop()
            .into(imgView)
    }
}