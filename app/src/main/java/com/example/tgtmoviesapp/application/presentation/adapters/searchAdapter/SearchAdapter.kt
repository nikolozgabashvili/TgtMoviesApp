package com.example.tgtmoviesapp.application.presentation.adapters.searchAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    var onItemClick: ((String) -> Unit)? = null


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
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }
}