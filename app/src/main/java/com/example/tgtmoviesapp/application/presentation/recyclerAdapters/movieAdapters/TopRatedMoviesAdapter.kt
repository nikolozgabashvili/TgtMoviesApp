package com.example.tgtmoviesapp.application.presentation.recyclerAdapters.movieAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tgtmoviesapp.application.commons.constants.Constants.IMAGE_BASE_URL
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.databinding.MovieItemLongBinding

class TopRatedMoviesAdapter:RecyclerView.Adapter<TopRatedMoviesAdapter.TopViewHolder>() {

    var gridItemList:List<Movies.Result?> = emptyList()
    var movieGenreList: List<Genre.Genre?> = mutableListOf()
    fun setMovieList(topRatedClass:Movies ){
        topRatedClass.results?.let {
                gridItemList = it
        }

        notifyDataSetChanged()

    }
    fun setMovieGenres(movieGenreList: List<Genre.Genre?> = mutableListOf()){
        this.movieGenreList = movieGenreList
        notifyDataSetChanged()
    }

    class TopViewHolder(val binding: MovieItemLongBinding)
        :RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder {
        val binding = MovieItemLongBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {

        val item = gridItemList[position]
        item?.let {
            holder.binding.movieGenre.maxWidth = 470
            holder.binding.movieTitle.maxWidth = 470
            holder.binding.movieTitle.text = it.title
            Glide.with(holder.binding.root.context)
                .load(IMAGE_BASE_URL+it.posterPath)
                .override(100, 100)

                .into(holder.binding.imageView)

            val genreIds = item.genreIds
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



        }



    }

    override fun getItemCount(): Int{

        return gridItemList.size

    }

}