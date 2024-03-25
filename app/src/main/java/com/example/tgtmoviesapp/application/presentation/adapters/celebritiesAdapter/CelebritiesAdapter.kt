package com.example.tgtmoviesapp.application.presentation.adapters.celebritiesAdapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.setPadding
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

    var onClick:((Int?)->Unit)? = null
    private var personList: List<Person.Result?> = emptyList()
    private var imageType: DisplayIndicator = DisplayIndicator.NONE
    private var minh = 410
    private var minw = 270

    fun setCelebList(
        lstModel: List<Person.Result?>,
        imageType: DisplayIndicator = DisplayIndicator.NONE
    ) {

        lstModel.let {
            personList = it

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


        if (imageType != DisplayIndicator.DETAILS) {
            Glide.with(holder.binding.root.context)

                .load(Constants.IMAGE_BASE_URL + currentItem?.profilePath)
                .override(270, 300)

                .error(
                    Glide.with(holder.binding.root.context)
                        .load(R.drawable.person_item)
                        .override(400, 400)
                        .centerCrop()
                        .transform(RoundedCorners(30))

                )
                .transform(RoundedCorners(30))
                .into(holder.binding.imageView)
            holder.binding.imageView.setPadding(2,2,2,2)
            holder.binding.imageView.minimumHeight = minh
            holder.binding.imageView.minimumWidth = minw

            holder.binding.movieTitle.textSize = 14F
            holder.binding.movieGenre.maxWidth = holder.binding.imageView.width
            holder.binding.movieTitle.maxWidth = holder.binding.imageView.width
            holder.binding.movieTitle.maxLines = 2
            holder.binding.imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            holder.binding.imageView.setBackgroundResource(R.drawable.person_item_stroke)


            holder.binding.movieGenre.text = currentItem?.knownForDepartment
            holder.binding.movieTitle.text = currentItem?.name

            holder.binding.root.setOnClickListener {
                onClick?.invoke(currentItem?.id)
            }
        }else{

            holder.binding.movieGenre.maxWidth = 200
            holder.binding.movieTitle.maxWidth = 200
            holder.binding.movieGenre.textSize = 10f
            holder.binding.movieTitle.textSize = 10f
            holder.binding.movieTitle.gravity = Gravity.CENTER
            holder.binding.movieGenre.gravity = Gravity.CENTER
            Glide.with(holder.binding.root.context)
                .load(Constants.PROFILE_IMAGE_URL + currentItem?.profilePath)
                .override(200, 200)
                .circleCrop()
                .error(
                    Glide.with(holder.binding.root.context)
                        .load(R.drawable.person_item)
                        .override(200, 200)
                        .circleCrop()

                )

                .into(holder.binding.imageView)
            holder.binding.imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            holder.binding.imageView.setBackgroundResource(R.drawable.circle_stroke)

            holder.binding.root.setOnClickListener {
                onClick?.invoke(currentItem?.id)
            }

            holder.binding.movieGenre.text = currentItem?.role
            holder.binding.movieTitle.text = currentItem?.name

        }



    }

}