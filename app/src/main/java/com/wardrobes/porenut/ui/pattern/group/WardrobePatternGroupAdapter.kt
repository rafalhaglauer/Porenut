package com.wardrobes.porenut.ui.pattern.group

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.inflate
import com.wardrobes.porenut.ui.common.extension.isVisibleWhen
import kotlinx.android.synthetic.main.wardrobe_pattern_list_adapter.view.*

class WardrobePatternGroupAdapter(
    private val onItemSelected: (WardrobePatternViewEntity) -> Unit,
    private val onAddDescription: (WardrobePatternViewEntity) -> Unit
) : RecyclerView.Adapter<WardrobePatternGroupAdapter.ViewHolder>() {
    private val items: MutableList<WardrobePatternViewEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.wardrobe_pattern_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemSelected, onAddDescription)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<WardrobePatternViewEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: WardrobePatternViewEntity, onItemSelected: (WardrobePatternViewEntity) -> Unit, onAddDescription: (WardrobePatternViewEntity) -> Unit) =
            with(itemView) {
                txtWardrobeSymbol.text = item.symbol
                txtWardrobeDescription.text = item.description
                btnAddDescription.isVisibleWhen(item.isAddDescriptionOptionVisible)
                btnAddDescription.setOnClickListener { onAddDescription(item) }
                setOnClickListener { onItemSelected(item) }
            }
    }
}