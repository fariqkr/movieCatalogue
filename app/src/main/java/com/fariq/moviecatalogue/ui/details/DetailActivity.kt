package com.fariq.moviecatalogue.ui.details

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.fariq.moviecatalogue.R
import com.fariq.moviecatalogue.data.source.local.entity.FilmEntity
import com.fariq.moviecatalogue.databinding.ActivityDetailBinding
import com.fariq.moviecatalogue.databinding.ContentDetailFilmBinding
import com.fariq.moviecatalogue.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_FILM = "extra_film"
        const val TYPE = "type"
    }

    private lateinit var contentDetailFilmBinding : ContentDetailFilmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        contentDetailFilmBinding = activityDetailBinding.detailContent

        setContentView(activityDetailBinding.root)

        setSupportActionBar(activityDetailBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]


        val extras = intent.extras
        if (extras != null) {
            val type = extras.getString(TYPE)
            Log.d("tess", type.toString())
            if (type != null){
                if (type.equals(resources.getString(R.string.tv_show))) {
                    val filmId = extras.getString(EXTRA_FILM)
                    if (filmId != null) {
                        viewModel.setSelectedFilm(filmId)
                        viewModel.getTvShow().observe(this, { tvShow ->
                            showFilmDetail(tvShow)
                        })
                    }
                }

                if (type.equals(resources.getString(R.string.movies))) {
                    val filmId = extras.getString(EXTRA_FILM)

                    if (filmId != null) {
                        viewModel.setSelectedFilm(filmId)
                        viewModel.getMovie().observe(this, { movie ->
                            showFilmDetail(movie)
                        })
                    }
                }
            }
        }



    }

    @SuppressLint("SetTextI18n", "StringFormatInvalid")
    private fun showFilmDetail(filmEntity: FilmEntity) {
        contentDetailFilmBinding.title.text = filmEntity.title
        contentDetailFilmBinding.year.text = "(${filmEntity.year})"
        Glide.with(this)
                .load(filmEntity.image)
                .centerCrop()
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .transform(RoundedCorners(5))
                .into(contentDetailFilmBinding.image)
        contentDetailFilmBinding.genre.text = filmEntity.genre.toString()
        contentDetailFilmBinding.desc.text = filmEntity.desc

        contentDetailFilmBinding.share.setOnClickListener {
            val mimeType = "text/plain"
            val strContent = "${filmEntity.title} (${filmEntity.year})" +
                    "\n" + "Genre : ${filmEntity.genre}" +
                    "\n" + "${filmEntity.desc}"
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Bagikan Film ini sekarang.")
                    .setText(strContent)
                    .startChooser()
        }
    }

}