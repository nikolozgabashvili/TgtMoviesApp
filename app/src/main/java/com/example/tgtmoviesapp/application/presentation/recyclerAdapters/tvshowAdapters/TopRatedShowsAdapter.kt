package com.example.tgtmoviesapp.application.presentation.recyclerAdapters.tvshowAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.databinding.MovieItemLongBinding

class TopRatedShowsAdapter : RecyclerView.Adapter<TopRatedShowsAdapter.TopViewHolder>() {

    var gridItemList: List<TvShows.Result?> = emptyList()
    private var movieGenreList: List<TvGenre.Genre?> = mutableListOf()


    fun setShowList(topRatedClass: TvShows) {
        topRatedClass.results?.let {
            gridItemList = it

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
        item?.let {
            holder.binding.movieGenre.maxWidth = 470
            holder.binding.movieTitle.maxWidth = 470
            holder.binding.movieTitle.text = it.name
            Glide.with(holder.binding.root.context)
                .load(Constants.IMAGE_BASE_URL + it.posterPath)
                .override(100, 100)


                .into(holder.binding.imageView)
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


        holder.binding.movieGenre.text = genreList.joinToString(separator = ",")
        holder.binding.movieGenre.maxLines = 1

    }

    override fun getItemCount(): Int {

        return gridItemList.size

    }

}