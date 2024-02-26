package com.example.tgtmoviesapp.application.presentation.recyclerAdapters.tvshowAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.databinding.MovieItemLongBinding

class TopRatedShowsAdapter : RecyclerView.Adapter<TopRatedShowsAdapter.TopViewHolder>() {

    var gridItemList: List<TvShows.Result?> = emptyList()

    fun setShowList(topRatedClass: TvShows) {
        topRatedClass.results?.let {
            gridItemList = it

        }
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
            holder.binding.movieTitle.text = it.name
            Glide.with(holder.binding.root.context)
                .load(Constants.IMAGE_BASE_URL + it.posterPath)
                .override(100, 100)


                .into(holder.binding.imageView)
        }

    }

    override fun getItemCount(): Int {

        return gridItemList.size

    }

}