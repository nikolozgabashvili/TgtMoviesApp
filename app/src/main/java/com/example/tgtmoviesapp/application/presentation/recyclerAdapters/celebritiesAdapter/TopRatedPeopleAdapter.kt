package com.example.tgtmoviesapp.application.presentation.recyclerAdapters.celebritiesAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.presentation.recyclerAdapters.movieAdapters.TopRatedMoviesAdapter
import com.example.tgtmoviesapp.databinding.MovieItemLongBinding

class TopRatedPeopleAdapter: RecyclerView.Adapter<TopRatedPeopleAdapter.TopViewHolder>() {

    var gridItemList:List<Person.Result?> = emptyList()

    fun setPersonList(topRatedClass: Person){
        topRatedClass.results?.let {
            gridItemList = it

        }
        notifyDataSetChanged()




    }

    class TopViewHolder(val binding: MovieItemLongBinding)
        : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder {
        val binding = MovieItemLongBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {

        val item = gridItemList[position]
        item?.let {
            holder.binding.movieTitle.text = it.name
            holder.binding.movieGenre.text = it.knownForDepartment

            Glide.with(holder.binding.root.context)
                .load(Constants.PROFILE_IMAGE_URL +it.profilePath)
                .override(180, 200)
                .circleCrop()
                .error(
                    Glide.with(holder.binding.root.context)
                        .load(R.drawable.person_item)
                        .override(180,200)
                        .circleCrop()

                )

                .into(holder.binding.imageView)
            holder.binding.imageView.scaleType=ImageView.ScaleType.FIT_CENTER
            holder.binding.imageView.setBackgroundResource(R.drawable.circle_stroke)
        }

    }

    override fun getItemCount(): Int{

        return gridItemList.size

    }

}