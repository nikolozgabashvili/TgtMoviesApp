package com.example.tgtmoviesapp.application.presentation.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tgtmoviesapp.application.commons.constants.Constants.IMAGE_BASE_URL
import com.example.tgtmoviesapp.application.domain.models.MovieModel
import com.example.tgtmoviesapp.application.domain.models.MovieModelIndicator
import com.example.tgtmoviesapp.application.domain.models.PopularMovies
import com.example.tgtmoviesapp.application.domain.models.ResultModel
import com.example.tgtmoviesapp.application.domain.models.UpcomingMovies
import com.example.tgtmoviesapp.databinding.MovieItemDefaultBinding

class MovieAdapter():RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


        class MovieViewHolder(val binding:MovieItemDefaultBinding)
        :RecyclerView.ViewHolder(binding.root)  {

    }
    private var movieList: List<PopularMovies.Result?>? = emptyList()
    private var upcomingMovieList: List<UpcomingMovies.Result?>? = emptyList()
    private  var movieType:MovieModelIndicator = MovieModelIndicator.NONE
    private var movieModel:MovieModel? = null


    fun setMovieList(lstModel:MovieModel,movieType:MovieModelIndicator){
        if (lstModel is UpcomingMovies)
            upcomingMovieList = lstModel.results
        else if (lstModel is PopularMovies)
            movieList = lstModel.results
        movieModel = lstModel

        this.movieType = movieType

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val binding = MovieItemDefaultBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount() :Int{
        return if (movieType ==MovieModelIndicator.UPCOMING_MOVIE)
            (movieModel as UpcomingMovies).results!!.size
        else if (movieType ==MovieModelIndicator.TOP_RATED || movieType==MovieModelIndicator.POPULAR_MOVIE){
            (movieModel as PopularMovies).results!!.size

        }else{
            0
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {


        when(movieType){

            MovieModelIndicator.UPCOMING_MOVIE -> {
                val currentItem = (movieModel as UpcomingMovies).results!![position]
                Glide.with(holder.binding.root.context)

                    .load(IMAGE_BASE_URL+currentItem?.posterPath)
                    .apply( RequestOptions().override(340, 440))
                    .into(holder.binding.imageView)

                holder.binding.movieTitle.text = currentItem?.name
            }
            MovieModelIndicator.POPULAR_MOVIE -> {

                val currentItem = (movieModel as PopularMovies).results!![position]
                Glide.with(holder.binding.root.context)

                    .load(IMAGE_BASE_URL+currentItem?.posterPath)
                    .apply( RequestOptions().override(340, 440))
                    .into(holder.binding.imageView)

                holder.binding.movieTitle.text = currentItem?.title
            }

            MovieModelIndicator.NONE -> {}
            MovieModelIndicator.TOP_RATED -> {
                val currentItem = (movieModel as PopularMovies).results!![position]
                Glide.with(holder.binding.root.context)

                    .load(IMAGE_BASE_URL+currentItem?.posterPath)
                    .apply( RequestOptions().override(340, 440))
                    .into(holder.binding.imageView)

                holder.binding.movieTitle.text = currentItem?.title
            }
        }

    }
}