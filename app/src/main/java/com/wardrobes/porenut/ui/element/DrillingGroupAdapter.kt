package com.wardrobes.porenut.ui.element

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import kotlinx.android.synthetic.main.drilling_list_adapter.view.*


class DrillingGroupAdapter(
    private val items: List<DrillingViewEntity>,
    private val onItemSelected: (DrillingViewEntity) -> Unit
) :
    RecyclerView.Adapter<DrillingGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.drilling_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemSelected)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: DrillingViewEntity, onItemSelected: (DrillingViewEntity) -> Unit): View =
            itemView.apply {
                with(item) {
                    txtXPosition.text = xPosition
                    txtYPosition.text = yPosition
                    txtDiameter.text = diameter
                    txtDepth.text = depth
                    setOnClickListener { onItemSelected(this) }
                }
            }
    }
}