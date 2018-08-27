package com.wardrobes.porenut.ui.relative.composition

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import kotlinx.android.synthetic.main.relative_composition_list_adapter.view.*


class RelativeCompositionGroupAdapter(
    private val items: List<String>,
    private val onItemSelected: (String) -> Unit
) :
    RecyclerView.Adapter<RelativeCompositionGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.relative_composition_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemSelected)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String, onItemSelected: (String) -> Unit): View = itemView.apply {
            txtRelativeCompositionName.text = item
            setOnClickListener { onItemSelected(item) }
        }
    }
}