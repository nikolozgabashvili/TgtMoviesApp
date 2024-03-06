package com.example.tgtmoviesapp.application.presentation.adapters.movieAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.commons.constants.Constants.IMAGE_BASE_URL
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.databinding.MovieItemLongBinding

class TopRatedMoviesAdapter : RecyclerView.Adapter<TopRatedMoviesAdapter.TopViewHolder>() {

    var gridItemList: List<Movies.Result?> = emptyList()
    var movieGenreList: List<Genre.Genre?> = mutableListOf()
    var typeIndicator: DisplayIndicator = DisplayIndicator.NONE
    fun setMovieList(
        topRatedClass: List<Movies.Result?>,
        typeIndicator: DisplayIndicator = DisplayIndicator.NONE
    ) {
        topRatedClass.let {
            gridItemList = it
            this.typeIndicator = typeIndicator

            notifyDataSetChanged()
        }

    }

    fun setMovieGenres(movieGenreList: List<Genre.Genre?> = mutableListOf()) {
        this.movieGenreList = movieGenreList
        notifyDataSetChanged()
    }

    class TopViewHolder(val binding: MovieItemLongBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder {
        val binding =
            MovieItemLongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {

        val item = gridItemList[position]
        if (typeIndicator != DisplayIndicator.FOUND_IMAGE_TYPE) {
            item?.let {


                holder.binding.movieTitle.text = it.title
                Glide.with(holder.binding.root.context)
                    .load(IMAGE_BASE_URL + it.posterPath)
                    .override(100, 100)

                    .into(holder.binding.imageView)
            }
            holder.binding.constraint.minWidth = 700
        } else {
            item?.let {

                holder.binding.movieTitle.textSize = 16f
                holder.binding.movieGenre.textSize = 14f
                holder.binding.movieTitle.text = it.title
                Glide.with(holder.binding.root.context)
                    .load(IMAGE_BASE_URL + it.posterPath)
                    .placeholder(R.drawable.movies_item)
                    .override(200, 280)

                    .into(holder.binding.imageView)
                holder.binding.constraint.layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(10, 10, 10, 10)
                }
                holder.binding.ratingLayout.visibility = View.VISIBLE
                item.voteAverage?.let {
                    holder.binding.ratingBar.stepSize = 2f
                    holder.binding.ratingBar.rating = item.voteAverage.toFloat()
                }
                holder.binding.imageView.minimumHeight = 280
                holder.binding.imageView.minimumWidth = 200


            }
        }


        val genreIds = item?.genreIds
        val genreList = mutableListOf<String>()
        genreIds?.map { int ->
            movieGenreList.map { genre ->
                if (int == genre?.id)
                    genre?.name?.let {
                        genreList.add(it)

                    }
            }
        }

        holder.binding.movieGenre.text = genreList.joinToString(separator = ",")


    }

    override fun getItemCount(): Int {

        return gridItemList.size

    }


}