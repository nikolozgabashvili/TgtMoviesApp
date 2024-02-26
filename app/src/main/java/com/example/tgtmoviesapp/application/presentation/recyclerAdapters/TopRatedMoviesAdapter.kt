package com.example.tgtmoviesapp.application.presentation.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tgtmoviesapp.application.commons.constants.Constants.IMAGE_BASE_URL
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.databinding.MovieItemLongBinding

class TopRatedMoviesAdapter:RecyclerView.Adapter<TopRatedMoviesAdapter.TopViewHolder>() {

    var gridItemList:List<Movies.Result?> = emptyList()

    fun setMovieList(topRatedClass:Movies ){
        topRatedClass.results?.let {
                gridItemList = it

        }
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
            holder.binding.movieTitle.text = it.title
            Glide.with(holder.binding.root.context)
                .load(IMAGE_BASE_URL+it.posterPath)
                .apply( RequestOptions().override(60, 60))
                .apply(RequestOptions().centerCrop())
                .into(holder.binding.imageView)
        }

    }

    override fun getItemCount(): Int{

        return gridItemList.size

    }

}