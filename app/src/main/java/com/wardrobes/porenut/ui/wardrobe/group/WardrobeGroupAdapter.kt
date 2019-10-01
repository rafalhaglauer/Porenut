package com.wardrobes.porenut.ui.wardrobe.group

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.inflate
import com.wardrobes.porenut.ui.common.extension.isVisibleWhen
import kotlinx.android.synthetic.main.wardrobe_list_adapter.view.*

class WardrobeGroupAdapter(
    private val onItemSelected: (WardrobeViewEntity) -> Unit,
    private val onAddDescription: (WardrobeViewEntity) -> Unit
) : RecyclerView.Adapter<WardrobeGroupAdapter.ViewHolder>() {
    private val items: MutableList<WardrobeViewEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.wardrobe_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemSelected, onAddDescription)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<WardrobeViewEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: WardrobeViewEntity, onItemSelected: (WardrobeViewEntity) -> Unit, onAddDescription: (WardrobeViewEntity) -> Unit) =
            with(itemView) {
                txtWardrobeSymbol.text = item.symbol
                txtWardrobeDescription.text = item.description
                btnAddDescription.isVisibleWhen(item.isAddDescriptionOptionVisible)
                btnAddDescription.setOnClickListener { onAddDescription(item) }
                setOnClickListener { onItemSelected(item) }
            }
    }
}