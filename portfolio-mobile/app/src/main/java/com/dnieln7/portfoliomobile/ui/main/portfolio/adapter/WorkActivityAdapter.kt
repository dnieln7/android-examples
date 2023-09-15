package com.dnieln7.portfoliomobile.ui.main.portfolio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnieln7.portfoliomobile.databinding.ListTileWorkLogActivityBinding

class WorkActivityAdapter(
    private val data: List<String>
) : RecyclerView.Adapter<WorkActivityAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ListTileWorkLogActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(activity: String) {
            binding.root.text = activity
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListTileWorkLogActivityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}