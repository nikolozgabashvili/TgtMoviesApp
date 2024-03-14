package com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.domain.models.MovieVideo
import com.example.tgtmoviesapp.databinding.MovieItemDefaultBinding

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideosViewHolder>() {


    class VideosViewHolder(val binding: MovieItemDefaultBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    private var videoList: List<MovieVideo.Result?> = emptyList()

    var onItemClick: ((String?) -> Unit)? = null

    fun setShowList(videos: List<MovieVideo.Result?>) {

        videoList = videos


        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {

        val binding =
            MovieItemDefaultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val item = videoList[position]
        Glide.with(holder.binding.root.context)
            .load("https://img.youtube.com/vi/${item?.key.toString()}/0.jpg")
            .override(400, 260)
            .centerCrop()
            .placeholder(R.drawable.movies_item)

            .into(holder.binding.imageView)

        holder.binding.imageView.minimumWidth = 100
        holder.binding.imageView.minimumHeight = 200
        holder.binding.movieTitle.visibility = View.GONE
        holder.binding.movieGenre.visibility = View.GONE
        holder.binding.imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        holder.binding.imageView.setPadding(5,5,5,5)
        holder.binding.imageView.setOnClickListener {
            onItemClick?.invoke(item?.key)
        }
    }


    override fun getItemCount(): Int {
        return videoList.size


    }


}