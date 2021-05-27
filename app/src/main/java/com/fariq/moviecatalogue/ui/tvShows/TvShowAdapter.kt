package com.fariq.moviecatalogue.ui.tvShows

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.fariq.moviecatalogue.R
import com.fariq.moviecatalogue.data.source.local.entity.FilmEntity
import com.fariq.moviecatalogue.databinding.ItemTvShowBinding
import com.fariq.moviecatalogue.ui.details.DetailActivity

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TVShowViewHolder>() {

    private val listTvShow = ArrayList<FilmEntity>()

    fun setTvShows(tvShow: List<FilmEntity>?) {
        if (tvShow == null) return
        this.listTvShow.clear()
        this.listTvShow.addAll(tvShow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val itemTvShowBinding = ItemTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TVShowViewHolder(itemTvShowBinding)
    }

    override fun onBindViewHolder(viewHolder: TVShowViewHolder, position: Int) {
        val tvShows = listTvShow[position]
        viewHolder.bind(tvShows)
    }

    override fun getItemCount(): Int = listTvShow.size

    inner class TVShowViewHolder(private val binding: ItemTvShowBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(filmEntity: FilmEntity) {
            binding.title.text = filmEntity.title
            binding.year.text = "(${filmEntity.year})"
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_FILM, filmEntity.id)
                intent.putExtra(DetailActivity.TYPE, "TV Show")
                itemView.context.startActivity(intent)
            }
            Glide.with(itemView.context)
                    .load(filmEntity.image)
                    .centerCrop()
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .transform(RoundedCorners(5))
                    .into(binding.image)

        }
    }
}