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
    private var movieGenreList: List<Genre?> = mutableListOf()


    fun setShowList(lstModel: TvShows, movieType: DisplayIndicator = DisplayIndicator.NONE) {

        lstModel.results?.let {
            showList = it
        }

        this.showType = movieType

        notifyDataSetChanged()
    }



    fun setMovieGenres(movieGenreList: List<Genre?> = mutableListOf()){
        this.movieGenreList = movieGenreList
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

        var path = ""
        val currentItem = showList[position]
        if (showType != DisplayIndicator.WIDE_IMAGE) {
            holder.binding.imageView.minimumWidth = 300
            holder.binding.imageView.minimumHeight=350
            path = Constants.IMAGE_BASE_URL + (currentItem?.posterPath?:"jbdjadhjbadadsb")
            Glide.with(holder.binding.root.context)

                .load(path)
                .apply(RequestOptions().override(340, 440))
                .placeholder(R.drawable.movies_item)
                .into(holder.binding.imageView)
        } else {
            path = Constants.IMAGE_BASE_URL + (currentItem?.backdropPath?:"jbdjadhjbadadsb")
            holder.binding.imageView.minimumWidth = 600
            holder.binding.imageView.minimumHeight=340
            Glide.with(holder.binding.root.context)

                .load(path)
                .apply(RequestOptions().override(600, 440))
                .placeholder(R.drawable.movies_item)

                .into(holder.binding.imageView)
        }
        val genreIds = currentItem?.genreIds
        val genreList = mutableListOf<String>()
        genreIds?.map { int->
            movieGenreList.map {genre->
                if (int ==genre?.id)
                    genre?.name?.let {
                        genreList.add(it)

                    }
            }
        }
        holder.binding.movieGenre.text = genreList.joinToString(separator = ",")


        holder.binding.movieTitle.maxWidth = holder.binding.imageView.width
        holder.binding.movieGenre.maxWidth = holder.binding.imageView.width
        holder.binding.movieTitle.text = currentItem?.name
    }

}