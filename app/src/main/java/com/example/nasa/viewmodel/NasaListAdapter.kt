package com.example.nasa.viewmodel


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasa.databinding.NasaItemBinding
import com.example.nasa.model.Photo

class NasaListAdapter(private val context: Context) :
    PagingDataAdapter<Photo, PhotoViewHolder>(DiffUtilCallback()) {

    var onItemClickListener: ((String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NasaItemBinding.inflate(layoutInflater, parent, false)
        return PhotoViewHolder(binding, context)

    }


    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {

        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
        }

        holder.itemView.setOnClickListener {
            if (item != null) {
                onItemClickListener?.invoke(item.img_src)
            }
        }

    }

}


class PhotoViewHolder(private val binding: NasaItemBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Photo) {
        Glide
            .with(context)
            .load(item.img_src)
            .into(binding.photo)
        binding.rover.text = item.rover.name
        binding.camera.text = item.camera.name
        binding.sol.text = item.sol.toString()
        binding.data.text = item.rover.landing_date

    }
}


class DiffUtilCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
        oldItem.img_src == newItem.img_src

}




