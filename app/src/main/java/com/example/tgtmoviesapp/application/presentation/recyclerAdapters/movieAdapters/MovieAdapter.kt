package com.example.tgtmoviesapp.application.presentation.recyclerAdapters.movieAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.commons.constants.Constants.BACKDROP_IMAGE_BASE_URL
import com.example.tgtmoviesapp.application.commons.constants.Constants.IMAGE_BASE_URL
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.databinding.MovieItemDefaultBinding

class MovieAdapter() : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    class MovieViewHolder(val binding: MovieItemDefaultBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    private var movieList: List<Movies.Result?> = emptyList()
    private var movieType: DisplayIndicator = DisplayIndicator.NONE
    private var movieGenreList: List<Genre.Genre?> = mutableListOf()


    fun setMovieList(lstModel: Movies,  movieType: DisplayIndicator = DisplayIndicator.NONE) {

        lstModel.results?.let {
            movieList = it
        }

        this.movieType = movieType

        notifyDataSetChanged()
    }
    fun setMovieGenres(movieGenreList: List<Genre.Genre?> = mutableListOf()){
        this.movieGenreList = movieGenreList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val binding =
            MovieItemDefaultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size


    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {


        val currentItem = movieList[position]
        var path = ""
        if (movieType !=DisplayIndicator.WIDE_IMAGE) {
            holder.binding.imageView.minimumWidth = 300
            holder.binding.imageView.minimumHeight=440
            path = IMAGE_BASE_URL + (currentItem?.posterPath?:"jbdjadhjbadadsb")
            Glide.with(holder.binding.root.context)

                .load(path)
                .override(340, 440)
                .placeholder(R.drawable.movies_item)
                .into(holder.binding.imageView)

        }else{
            path = IMAGE_BASE_URL + (currentItem?.backdropPath?:"jbdjadhjbadadsb")
            holder.binding.imageView.minimumWidth = 600
            holder.binding.imageView.minimumHeight=350
            Glide.with(holder.binding.root.context)

                .load(path)
                .override(600, 440)
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
        holder.binding.movieTitle.text = currentItem?.title


    }


}


