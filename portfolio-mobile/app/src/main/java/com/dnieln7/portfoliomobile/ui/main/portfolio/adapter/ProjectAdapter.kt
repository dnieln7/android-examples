package com.dnieln7.portfoliomobile.ui.main.portfolio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dnieln7.portfoliomobile.R
import com.dnieln7.portfoliomobile.databinding.GridTileProjectBinding
import com.dnieln7.portfoliomobile.domain.model.Project
import com.google.android.material.imageview.ShapeableImageView

class ProjectAdapter(
    private val onClick: (Project, ShapeableImageView) -> Unit
) : ListAdapter<Project, ProjectAdapter.ViewHolder>(Diff()) {

    inner class ViewHolder(private val binding: GridTileProjectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(project: Project) {
            val uri = project.thumbnail.toUri().buildUpon().scheme("https").build()

            binding.image.load(uri) {
                crossfade(true)
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
            binding.root.setOnClickListener {
                onClick(project, binding.image)
            }

            binding.title.text = project.name
            binding.summary.text = project.summary
        }
    }

    private class Diff : DiffUtil.ItemCallback<Project>() {

        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            GridTileProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}