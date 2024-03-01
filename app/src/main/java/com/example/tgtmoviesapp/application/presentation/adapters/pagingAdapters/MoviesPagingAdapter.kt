package com.example.tgtmoviesapp.application.presentation.adapters.pagingAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.databinding.MovieItemDefaultBinding

class MoviesPagingAdapter: PagingDataAdapter<Movies, MoviesPagingAdapter.MyViewHolder>(MyDataComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(MovieItemDefaultBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            with(holder.binding){
                Glide.with(root).load(item.results)
            }
        }
    }


    inner class MyViewHolder(val binding: MovieItemDefaultBinding) : RecyclerView.ViewHolder(binding.root)

    companion object MyDataComparator : DiffUtil.ItemCallback<Movies>() {
        override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem.page ==newItem.page
        }
    }

}