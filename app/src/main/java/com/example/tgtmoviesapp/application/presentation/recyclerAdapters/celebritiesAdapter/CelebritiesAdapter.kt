package com.example.tgtmoviesapp.application.presentation.recyclerAdapters.celebritiesAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.databinding.MovieItemDefaultBinding

class CelebritiesAdapter : RecyclerView.Adapter<CelebritiesAdapter.CelebViewHolder>() {


    class CelebViewHolder(val binding: MovieItemDefaultBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    private var personList: List<Person.Result?> = emptyList()
    private var imageType: DisplayIndicator = DisplayIndicator.NONE
    private var minh = 400
    private var minw = 250

    fun setCelebList(
        lstModel: List<Person.Result?>,
        imageType: DisplayIndicator = DisplayIndicator.NONE
    ) {

        lstModel.let {
            personList = it
            println(it)
        }

        this.imageType = imageType

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CelebViewHolder {

        val binding =
            MovieItemDefaultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CelebViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return personList.size


    }

    override fun onBindViewHolder(holder: CelebViewHolder, position: Int) {


        val currentItem = personList[position]

        Glide.with(holder.binding.root.context)

            .load(Constants.IMAGE_BASE_URL + currentItem?.profilePath)
            .override(270, 300)

            .error(
                Glide.with(holder.binding.root.context)
                    .load(R.drawable.person_item)
                    .override(400,400)
                    .centerCrop()
                    .transform(RoundedCorners(30))

            )
            .transform(RoundedCorners(30))
            .into(holder.binding.imageView)

        holder.binding.imageView.minimumHeight = minh
        holder.binding.imageView.minimumWidth = minw

        holder.binding.movieTitle.textSize = 14F
        holder.binding.movieGenre.maxWidth = holder.binding.imageView.width
        holder.binding.movieTitle.maxWidth = holder.binding.imageView.width
        holder.binding.movieTitle.maxLines = 2


        holder.binding.movieGenre.text = currentItem?.knownForDepartment
        holder.binding.movieTitle.text = currentItem?.name
        println(currentItem?.name)


    }

}