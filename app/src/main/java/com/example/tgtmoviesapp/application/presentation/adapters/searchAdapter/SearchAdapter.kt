package com.example.tgtmoviesapp.application.presentation.adapters.searchAdapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.databinding.SearchItemBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var stringList: List<String> = emptyList()
    private var displayIndicator: DisplayIndicator = DisplayIndicator.NONE

    class SearchViewHolder(val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


    fun setMovieList(
        lst: List<String>,
        displayIndicator: DisplayIndicator = DisplayIndicator.NONE
    ) {
        this.displayIndicator = displayIndicator
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
        if (displayIndicator == DisplayIndicator.DETAILS) {
            holder.binding.searchImage.visibility = View.GONE
            holder.binding.searchText.textSize = 12f
            holder.binding.searchText.setTextColor(Color.GRAY)
            holder.binding.root.setBackgroundResource(R.drawable.image_stroke)
            holder.binding.root.isFocusable = false
            holder.binding.root.isClickable = false
            holder.binding.root.foreground = null
            holder.binding.root.layoutParams =ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(10, 0, 10, 0)
            }
        }else{
            holder.binding.searchImage.visibility = View.VISIBLE
            holder.binding.root.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
        holder.binding.searchText.text = item
    }
}