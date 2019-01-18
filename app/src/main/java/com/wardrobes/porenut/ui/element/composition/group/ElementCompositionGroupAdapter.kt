package com.wardrobes.porenut.ui.element.composition.group

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import kotlinx.android.synthetic.main.element_composition_list_adapter.view.*


class ElementCompositionGroupAdapter(
    private val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<ElementCompositionGroupAdapter.ViewHolder>() {
    private val items: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.element_composition_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemSelected)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String, onItemSelected: (String) -> Unit): View = itemView.apply {
            txtElementCompositionName.text = item
            setOnClickListener { onItemSelected(item) }
        }
    }
}