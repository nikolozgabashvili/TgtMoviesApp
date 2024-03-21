package com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.databinding.MovieItemDefaultBinding

class TvShowsAdapter() : RecyclerView.Adapter<TvShowsAdapter.TvShowViewHolder>() {


    class TvShowViewHolder(val binding: MovieItemDefaultBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    private var showList: List<TvShows.Result?> = emptyList()
    private var showType: DisplayIndicator = DisplayIndicator.NONE

    fun setShowList(lstModel: List<TvShows.Result?>, movieType: DisplayIndicator = DisplayIndicator.NONE) {

        lstModel.let {
            showList = it
        }

        this.showType = movieType

        notifyDataSetChanged()
    }

    var onItemClick: ((Int?) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {

        val binding =
            MovieItemDefaultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return showList.size


    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {

        var path = ""
        val currentItem = showList[position]
        if (showType != DisplayIndicator.WIDE_IMAGE) {
            holder.binding.imageView.minimumWidth = 270
            holder.binding.imageView.minimumHeight=400
            path = Constants.IMAGE_BASE_URL + (currentItem?.posterPath?:"jbdjadhjbadadsb")
            Glide.with(holder.binding.root.context)

                .load(path)
                .apply(RequestOptions().override(300, 400))
                .placeholder(R.drawable.movies_item)
                .into(holder.binding.imageView)
        } else {
            path = Constants.IMAGE_BASE_URL + (currentItem?.backdropPath?:"jbdjadhjbadadsb")
            holder.binding.imageView.minimumWidth = 500
            holder.binding.imageView.minimumHeight=240
            Glide.with(holder.binding.root.context)

                .load(path)
                .apply(RequestOptions().override(500, 340))
                .placeholder(R.drawable.movies_item)

                .into(holder.binding.imageView)
        }

        val genreList = currentItem?.genre
        holder.binding.movieGenre.text = genreList?.joinToString(separator = ",")


        holder.binding.movieTitle.maxWidth = holder.binding.imageView.width
        holder.binding.movieGenre.maxWidth = holder.binding.imageView.width
        holder.binding.movieTitle.text = currentItem?.name
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(currentItem?.id)
        }
    }

}