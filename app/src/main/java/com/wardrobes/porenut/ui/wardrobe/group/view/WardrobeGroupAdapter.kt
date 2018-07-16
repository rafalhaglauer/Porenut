package com.wardrobes.porenut.ui.wardrobe.group.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import com.wardrobes.porenut.ui.wardrobe.group.model.WardrobeViewEntity
import kotlinx.android.synthetic.main.wardrobe_list_adapter.view.*


class WardrobeGroupAdapter(private val items: List<WardrobeViewEntity>, private val onItemSelected: (WardrobeViewEntity) -> Unit) : RecyclerView.Adapter<WardrobeGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.wardrobe_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemSelected)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: WardrobeViewEntity, onItemSelected: (WardrobeViewEntity) -> Unit) = with(itemView) {
            item.apply {
                txtWardrobeSymbol.text = symbol
                txtWardrobeWidth.text = width
                txtWardrobeHeight.text = height
                txtWardrobeDepth.text = depth
                imgWardrobeType.setImageDrawable(context.getDrawable(icon))
                setOnClickListener { onItemSelected(this) }
            }
        }
    }
}