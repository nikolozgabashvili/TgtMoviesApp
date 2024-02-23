package com.example.tgtmoviesapp.application.presentation.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
    private var movieList: List<ResultModel> = emptyList()
    private lateinit var movieType:MovieModelIndicator


    fun setMovieList(lst:MovieModel,movieType:MovieModelIndicator){
        if (lst is UpcomingMovies)
            movieList = lst.results as List<UpcomingMovies.Result2>
        else if (lst is PopularMovies)
            movieList = lst.results as List<PopularMovies.Result>
        this.movieType = movieType

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val binding = MovieItemDefaultBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount() =  movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {


        when(movieType){

            MovieModelIndicator.UPCOMING_MOVIE -> {
                val currentItem = movieList[position] as UpcomingMovies.Result2
                Glide.with(holder.binding.root.context)

                    .load(IMAGE_BASE_URL+currentItem.posterPath)

                    .into(holder.binding.imageView)

                holder.binding.movieTitle.text = currentItem.name
            }
            MovieModelIndicator.POPULAR_MOVIE -> {

                val currentItem = movieList[position] as PopularMovies.Result
                Glide.with(holder.binding.root.context)

                    .load(IMAGE_BASE_URL+currentItem.posterPath)

                    .into(holder.binding.imageView)

                holder.binding.movieTitle.text = currentItem.title
            }
        }

    }
}