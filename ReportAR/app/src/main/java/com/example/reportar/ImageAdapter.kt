package com.example.reportar

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reportar.databinding.ItemImageBinding

class ImageAdapter(
    private val onDelete: (String) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private var images = emptyList<String>()

    fun submitList(newImages: List<String>) {

        images = newImages
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(
        private val binding: ItemImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: String) {

            binding.ivImage.setImageURI(
                Uri.parse(uri)
            )

            binding.btnDelete.setOnClickListener {

                onDelete(uri)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {

        val binding = ItemImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ImageViewHolder,
        position: Int
    ) {

        holder.bind(images[position])
    }

    override fun getItemCount() =
        images.size
}