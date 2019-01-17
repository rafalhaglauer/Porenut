package com.wardrobes.porenut.ui.element.group

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.element.detail.ElementViewEntity
import com.wardrobes.porenut.ui.extension.inflate
import kotlinx.android.synthetic.main.element_list_adapter.view.*


class ElementGroupAdapter(private val onItemSelected: (ElementViewEntity) -> Unit) : RecyclerView.Adapter<ElementGroupAdapter.ViewHolder>() {
    private val items: MutableList<ElementViewEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.element_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemSelected)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<ElementViewEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ElementViewEntity, onItemSelected: (ElementViewEntity) -> Unit): View =
            itemView.apply {
                with(item) {
                    txtElementName.text = name
                    txtElementLength.text = length
                    txtElementWidth.text = width
                    txtElementHeight.text = height
                    setOnClickListener { onItemSelected(this) }
                }
            }
    }
}