package com.dnieln7.portfoliomobile.ui.main.portfolio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.dnieln7.portfoliomobile.databinding.ListTileAcademicLogBinding
import com.dnieln7.portfoliomobile.domain.model.AcademicLog
import com.dnieln7.portfoliomobile.utils.openBrowser

class AcademicAdapter(
    private val data: List<AcademicLog>
) : RecyclerView.Adapter<AcademicAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ListTileAcademicLogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(academicLog: AcademicLog) {
            binding.name.text = academicLog.name
            binding.date.text = academicLog.date
            binding.description.text = academicLog.description

            if (academicLog.url != null) {
                binding.download.setOnClickListener {
                    binding.root.context.openBrowser(academicLog.url)
                }

                binding.download.isVisible = true
            } else {
                binding.download.isVisible = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListTileAcademicLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}