package com.wardrobes.porenut.ui.wardrobe.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.wardrobes.porenut.R

class WardrobeGalleryAdapter(private val context: Context, private val images: ArrayList<String>) :
    RecyclerView.Adapter<WardrobeGalleryAdapter.ColorViewHolder>() {


    override fun getItemCount(): Int {
        return images.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false))
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val path = images[position]

        Picasso.get()
            .load(path)
            .resize(360, 360)
            .centerCrop()
            .into(holder.imgPhoto)

        holder.imgPhoto.setOnClickListener {
            //handle click event on image
        }
    }

    class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPhoto = view as ImageView
    }
}