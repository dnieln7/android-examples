package com.dnieln7.portfoliomobile.ui.main.projectdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dnieln7.portfoliomobile.R
import com.dnieln7.portfoliomobile.databinding.PageItemImageBinding

class ImagePagerAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: PageItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: String) {
            val uri = image.toUri().buildUpon().scheme("https").build()

            binding.image.load(uri) {
                crossfade(true)
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            PageItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}