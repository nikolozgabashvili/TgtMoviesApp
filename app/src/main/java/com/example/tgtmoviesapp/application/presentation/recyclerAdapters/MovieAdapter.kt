package com.example.tgtmoviesapp.application.presentation.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tgtmoviesapp.application.commons.constants.Constants.IMAGE_BASE_URL
import com.example.tgtmoviesapp.application.domain.models.MovieModelIndicator
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.databinding.MovieItemDefaultBinding

class MovieAdapter() : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    class MovieViewHolder(val binding: MovieItemDefaultBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    private var movieList: List<Movies.Result?> = emptyList()
    private var movieType: MovieModelIndicator = MovieModelIndicator.NONE


    fun setMovieList(lstModel: Movies, movieType: MovieModelIndicator = MovieModelIndicator.NONE) {

        lstModel.results?.let {
            movieList = it
        }

        this.movieType = movieType

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
        if (movieType !=MovieModelIndicator.PIT_MOVIE) {
            Glide.with(holder.binding.root.context)

                .load(IMAGE_BASE_URL + currentItem?.posterPath)
                .apply(RequestOptions().override(340, 440))
                .into(holder.binding.imageView)
        }else{
            Glide.with(holder.binding.root.context)

                .load(IMAGE_BASE_URL + currentItem?.backdropPath)
                .apply(RequestOptions().override(600, 440))
                .into(holder.binding.imageView)
        }

        holder.binding.movieTitle.maxWidth = holder.binding.imageView.width
        holder.binding.movieGenre.maxWidth = holder.binding.imageView.width
        holder.binding.movieTitle.text = currentItem?.title


    }


}


