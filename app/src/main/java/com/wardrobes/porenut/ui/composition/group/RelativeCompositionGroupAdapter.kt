package com.wardrobes.porenut.ui.composition.group

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import kotlinx.android.synthetic.main.relative_composition_list_adapter.view.*

class RelativeCompositionGroupAdapter(
    private val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<RelativeCompositionGroupAdapter.ViewHolder>() {

    private val relativeCompositionNames = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.relative_composition_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(relativeCompositionNames[position], onItemSelected)
    }

    override fun getItemCount(): Int = relativeCompositionNames.size

    fun setItems(relativeCompositionNames: List<String>) {
        this.relativeCompositionNames.clear()
        this.relativeCompositionNames.addAll(relativeCompositionNames)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String, onItemSelected: (String) -> Unit): View = itemView.apply {
            txtRelativeCompositionName.text = item
            setOnClickListener { onItemSelected(item) }
        }
    }
}