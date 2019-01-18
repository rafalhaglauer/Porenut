package com.wardrobes.porenut.ui.drilling.standard.group

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import kotlinx.android.synthetic.main.drilling_list_adapter.view.*

class DrillingGroupAdapter : RecyclerView.Adapter<DrillingGroupAdapter.ViewHolder>() {
    private val items: MutableList<DrillingGroupViewEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.drilling_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<DrillingGroupViewEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: DrillingGroupViewEntity): View =
            itemView.apply {
                with(item) {
                    txtXPosition.text = xPosition
                    txtYPosition.text = yPosition
                    txtDiameter.text = diameter
                    txtDepth.text = depth
                }
            }
    }
}