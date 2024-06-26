package com.example.tgtmoviesapp.application.presentation.adapters.celebritiesAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.databinding.MovieItemLongBinding

class TopRatedPeopleAdapter() :
    RecyclerView.Adapter<TopRatedPeopleAdapter.TopViewHolder>() {

    var gridItemList: List<Person.Result?> = emptyList()
    var typeIndicator: DisplayIndicator = DisplayIndicator.NONE


    fun setPersonList(
        topRatedClass: List<Person.Result?>,
        typeIndicator: DisplayIndicator = DisplayIndicator.NONE,

        ) {
        topRatedClass.let {

            gridItemList = it
            this.typeIndicator = typeIndicator
        }
        notifyDataSetChanged()
    }
    var onClick:((Int?)->Unit)?= null

    class TopViewHolder(val binding: MovieItemLongBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder {
        val binding =
            MovieItemLongBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {

        val item = gridItemList[position]
        if (typeIndicator != DisplayIndicator.FOUND_IMAGE_TYPE) {

            item?.let {
                holder.binding.movieTitle.text = it.name
                holder.binding.movieGenre.text = it.knownForDepartment
                holder.binding.constraint.minWidth = 800
                Glide.with(holder.binding.root.context)
                    .load(Constants.PROFILE_IMAGE_URL + it.profilePath)
                    .override(180, 200)
                    .circleCrop()
                    .error(
                        Glide.with(holder.binding.root.context)
                            .load(R.drawable.person_item)
                            .override(180, 200)
                            .circleCrop()

                    )

                    .into(holder.binding.imageView)
                holder.binding.imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                holder.binding.imageView.setBackgroundResource(R.drawable.circle_stroke)
            }
        } else {
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
                holder.binding.ratingLayout.visibility = View.GONE
                holder.binding.movieGenre.text = item.knownForDepartment
                holder.binding.movieTitle.text = item.name


                Glide.with(holder.binding.root.context)
                    .load(Constants.IMAGE_BASE_URL + it.profilePath)
                    .placeholder(R.drawable.person_item)
                    .override(200, 280)

                    .into(holder.binding.imageView)


            }

        }
        holder.binding.root.setOnClickListener {
            onClick?.invoke(item?.id)
        }

    }

    override fun getItemCount(): Int {

        return gridItemList.size

    }

}