package com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.databinding.MovieItemLongBinding

class TopRatedShowsAdapter : RecyclerView.Adapter<TopRatedShowsAdapter.TopViewHolder>() {

    var gridItemList: List<TvShows.Result?> = emptyList()
    private var movieGenreList: List<Genre?> = mutableListOf()
    private var typeIndicator :DisplayIndicator = DisplayIndicator.NONE
    var onItemClick: ((Int?) -> Unit)? = null

    fun setShowList(topRatedClass: List<TvShows.Result?>,typeIndicator:DisplayIndicator = DisplayIndicator.NONE) {
        topRatedClass.let {
            gridItemList = it
            this.typeIndicator = typeIndicator
        }
        notifyDataSetChanged()

    }

    fun setMovieGenres(movieGenreList: List<Genre?> = mutableListOf()){
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
                Glide.with(holder.binding.root.context)
                    .load(Constants.IMAGE_BASE_URL + it.posterPath)
                    .override(100, 100)


                    .into(holder.binding.imageView)
            }
        }else{
            item?.let {
                holder.binding.constraint.layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(10, 10, 10, 10)
                }

                holder.binding.imageView.minimumHeight = 280
                holder.binding.imageView.minimumWidth = 200
                holder.binding.movieTitle.textSize = 16f
                holder.binding.movieGenre.textSize = 14f
                holder.binding.ratingLayout.visibility = View.VISIBLE

                Glide.with(holder.binding.root.context)
                    .load(Constants.IMAGE_BASE_URL + it.posterPath)
                    .placeholder(R.drawable.movies_item)
                    .override(200, 280)

                    .into(holder.binding.imageView)


                item.voteAverage?.let {
                    holder.binding.ratingBar.stepSize = 0.2f
                    holder.binding.ratingBar.rating = (item.voteAverage.toFloat()/2)
                }
                holder.binding.ratingText.text = "(${item.voteCount.toString()})"



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
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(item?.id)
        }

    }

    override fun getItemCount(): Int {

        return gridItemList.size

    }

}