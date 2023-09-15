package com.dnieln7.portfoliomobile.ui.main.portfolio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnieln7.portfoliomobile.databinding.ListTileWorkLogBinding
import com.dnieln7.portfoliomobile.domain.model.WorkLog

class WorkAdapter(
    private val data: List<WorkLog>
) : RecyclerView.Adapter<WorkAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ListTileWorkLogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(workLog: WorkLog) {
            binding.name.text = workLog.name
            binding.date.text = workLog.date
            binding.description.text = workLog.description
            binding.activities.adapter = WorkActivityAdapter(workLog.activities)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListTileWorkLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}