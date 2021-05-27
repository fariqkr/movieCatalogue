package com.fariq.moviecatalogue.ui.movies

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
import com.fariq.moviecatalogue.databinding.ItemMovieBinding
import com.fariq.moviecatalogue.ui.details.DetailActivity

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val listMovie = ArrayList<FilmEntity>()

    fun setMovies(movies: List<FilmEntity>?) {
        if (movies == null) return
        this.listMovie.clear()
        this.listMovie.addAll(movies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        val movies = listMovie[position]
        viewHolder.bind(movies)
    }

    override fun getItemCount(): Int = listMovie.size

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(filmEntity: FilmEntity) {
            binding.title.text = filmEntity.title
            binding.year.text = "(${filmEntity.year})"
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_FILM, filmEntity.id)
                intent.putExtra(DetailActivity.TYPE, "Movies")
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