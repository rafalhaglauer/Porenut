package com.wardrobes.porenut.ui.wardrobe.group

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import kotlinx.android.synthetic.main.wardrobe_list_adapter.view.*


class WardrobeGroupAdapter(private val onItemSelected: (WardrobeViewEntity) -> Unit) : RecyclerView.Adapter<WardrobeGroupAdapter.ViewHolder>() {
    private val items: MutableList<WardrobeViewEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.wardrobe_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemSelected)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<WardrobeViewEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: WardrobeViewEntity, onItemSelected: (WardrobeViewEntity) -> Unit) =
            with(itemView) {
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