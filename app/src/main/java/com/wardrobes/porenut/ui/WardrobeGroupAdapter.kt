package com.wardrobes.porenut.ui

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wardrobes.porenut.R
import com.wardrobes.porenut.model.Wardrobe
import kotlinx.android.synthetic.main.wardrobe_list_adapter.view.*


class WardrobeGroupAdapter(private val items: List<Wardrobe>, private val listener: (Wardrobe) -> Unit) : RecyclerView.Adapter<WardrobeGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.wardrobe_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Wardrobe, listener: (Wardrobe) -> Unit) = with(itemView) {
            item.apply {
                txtWardrobeSymbol.text = symbol
                txtWardrobeWidth.text = width.toString()
                txtWardrobeHeight.text = height.toString()
                txtWardrobeDepth.text = depth.toString()
                //TODO SET IMG WARDROBE SYMBOL
                setOnClickListener { listener(this) }
            }
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}