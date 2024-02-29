package com.example.tgtmoviesapp.application.presentation.recyclerAdapters.searchAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.presentation.recyclerAdapters.movieAdapters.MovieAdapter
import com.example.tgtmoviesapp.databinding.MovieItemDefaultBinding
import com.example.tgtmoviesapp.databinding.SearchItemBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var stringList:List<String> = emptyList()
    class SearchViewHolder(val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


    fun setMovieList(lst: List<String>) {

        stringList = lst



        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        val binding =
            SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return stringList.size


    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = stringList[position]
        holder.binding.searchText.text = item

    }
}