package com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.databinding.MovieItemLongBinding

class TopRatedShowsAdapter : RecyclerView.Adapter<TopRatedShowsAdapter.TopViewHolder>() {

    var gridItemList: List<TvShows.Result?> = emptyList()
    private var movieGenreList: List<TvGenre.Genre?> = mutableListOf()
    private var typeIndicator :DisplayIndicator = DisplayIndicator.NONE


    fun setShowList(topRatedClass: List<TvShows.Result?>,typeIndicator:DisplayIndicator = DisplayIndicator.NONE) {
        topRatedClass.let {
            gridItemList = it
            this.typeIndicator = typeIndicator
        }
        notifyDataSetChanged()

    }

    fun setMovieGenres(movieGenreList: List<TvGenre.Genre?> = mutableListOf()){
        this.movieGenreList = movieGenreList
        notifyDataSetChanged()

    }

    class TopViewHolder(val binding: MovieItemLongBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder {
        val binding =
            MovieItemLongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {

        val item = gridItemList[position]
        if (typeIndicator!=DisplayIndicator.FOUND_IMAGE_TYPE) {
            item?.let {
                holder.binding.constraint.minWidth=700
                holder.binding.movieGenre.maxWidth = 470
                holder.binding.movieTitle.maxWidth = 470

                Glide.with(holder.binding.root.context)
                    .load(Constants.IMAGE_BASE_URL + it.posterPath)
                    .override(100, 100)


                    .into(holder.binding.imageView)
            }
        }else{
            item?.let {
                holder.binding.movieGenre.maxWidth = 600
                holder.binding.movieTitle.maxWidth = 600
                holder.binding.constraint.minHeight = 400
                holder.binding.constraint.minWidth = 1100
                holder.binding.imageView.minimumHeight = 400
                holder.binding.imageView.minimumWidth = 300
                holder.binding.movieTitle.textSize = 18f
                holder.binding.movieGenre.textSize = 16f
                holder.binding.ratingLayout.visibility = View.VISIBLE

                Glide.with(holder.binding.root.context)
                    .load(Constants.IMAGE_BASE_URL + it.posterPath)
                    .placeholder(R.drawable.movies_item)
                    .override(300, 400)

                    .into(holder.binding.imageView)


                item.voteAverage?.let {
                    holder.binding.ratingBar.stepSize = 2f
                    holder.binding.ratingBar.rating = item.voteAverage.toFloat()
                }

                holder.binding.ratingText.text = "(${item.voteCount})"

            }
        }
        val genreIds = item?.genreIds
        val genreList = mutableListOf<String>()
        genreIds?.map { int->
            movieGenreList.map {genre->
                if (int ==genre?.id)
                    genre?.name?.let {
                        genreList.add(it)

                    }
            }
        }

        holder.binding.movieTitle.text = item?.name
        holder.binding.movieGenre.text = genreList.joinToString(separator = ",")
        holder.binding.movieGenre.maxLines = 1

    }

    override fun getItemCount(): Int {

        return gridItemList.size

    }

}