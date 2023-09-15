package com.dnieln7.portfoliomobile.ui.main.portfolio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnieln7.portfoliomobile.R
import com.dnieln7.portfoliomobile.databinding.ListTileSkillBinding
import com.dnieln7.portfoliomobile.domain.constant.SkillType
import com.dnieln7.portfoliomobile.domain.model.Skill

class SkillAdapter(
    private val data: List<Skill>
) : RecyclerView.Adapter<SkillAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListTileSkillBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(skill: Skill) {
            binding.typeIcon.setImageResource(getImageRes(skill.type))
            binding.name.text = skill.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListTileSkillBinding.inflate(
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

    private fun getImageRes(skillType: SkillType): Int {
        return when (skillType) {
            SkillType.Android -> R.drawable.ic_app_android
            SkillType.BackFrontEnd -> R.drawable.ic_web
            SkillType.Other -> R.drawable.ic_app
        }
    }
}