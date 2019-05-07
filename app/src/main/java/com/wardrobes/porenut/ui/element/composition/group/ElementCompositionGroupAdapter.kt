package com.wardrobes.porenut.ui.element.composition.group

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.inflate
import com.wardrobes.porenut.ui.common.extension.isVisibleWhen
import kotlinx.android.synthetic.main.element_composition_list_adapter.view.*


class ElementCompositionGroupAdapter(
    private val onItemSelected: (Long) -> Unit
) : RecyclerView.Adapter<ElementCompositionGroupAdapter.ViewHolder>() {
    private val items: MutableList<ElementDrillingSetCompositionItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.element_composition_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemSelected)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<ElementDrillingSetCompositionItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ElementDrillingSetCompositionItem, onItemSelected: (Long) -> Unit): View =
            itemView.apply {
                val isHeader = item is ElementDrillingSetCompositionHeader
                isClickable = !isHeader
                txtElementCompositionHeader isVisibleWhen isHeader
                txtElementCompositionName isVisibleWhen !isHeader
                when (item) {
                    is ElementDrillingSetCompositionHeader -> {
                        txtElementCompositionHeader.text = item.name
                    }
                    is ElementDrillingSetCompositionViewEntity -> {
                        txtElementCompositionName.text = item.name
                        setOnClickListener { onItemSelected(item.id) }
                    }
                }
            }
    }
}

sealed class ElementDrillingSetCompositionItem

class ElementDrillingSetCompositionHeader(val name: String) : ElementDrillingSetCompositionItem()

class ElementDrillingSetCompositionViewEntity(
    val id: Long,
    val name: String
) : ElementDrillingSetCompositionItem()