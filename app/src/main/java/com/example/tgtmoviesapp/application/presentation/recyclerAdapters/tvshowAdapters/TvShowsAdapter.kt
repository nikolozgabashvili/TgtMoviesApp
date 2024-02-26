package com.example.tgtmoviesapp.application.presentation.recyclerAdapters.tvshowAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.domain.models.MovieModelIndicator
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.databinding.MovieItemDefaultBinding

class TvShowsAdapter() : RecyclerView.Adapter<TvShowsAdapter.TvShowViewHolder>() {


    class TvShowViewHolder(val binding: MovieItemDefaultBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    private var showList: List<TvShows.Result?> = emptyList()
    private var showType: MovieModelIndicator = MovieModelIndicator.NONE


    fun setShowList(lstModel: TvShows, movieType: MovieModelIndicator = MovieModelIndicator.NONE) {

        lstModel.results?.let {
            showList = it
        }

        this.showType = movieType

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {

        val binding =
            MovieItemDefaultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return showList.size


    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {


        val currentItem = showList[position]
        if (showType != MovieModelIndicator.WIDE_IMAGE) {
            Glide.with(holder.binding.root.context)

                .load(Constants.IMAGE_BASE_URL + currentItem?.posterPath)
                .apply(RequestOptions().override(340, 440))
                .into(holder.binding.imageView)
        } else {
            Glide.with(holder.binding.root.context)

                .load(Constants.IMAGE_BASE_URL + currentItem?.backdropPath)
                .apply(RequestOptions().override(600, 440))
                .into(holder.binding.imageView)
        }

        holder.binding.movieTitle.maxWidth = holder.binding.imageView.width
        holder.binding.movieGenre.maxWidth = holder.binding.imageView.width
        holder.binding.movieTitle.text = currentItem?.name
    }

}