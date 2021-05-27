package com.fariq.moviecatalogue.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fariq.moviecatalogue.databinding.FragmentMoviesBinding
import com.fariq.moviecatalogue.viewmodel.ViewModelFactory
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class MoviesFragment : Fragment() {
    private lateinit var fragmentMoviesBinding : FragmentMoviesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentMoviesBinding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return fragmentMoviesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            val movieAdapter = MovieAdapter()
            viewModel.getMovies().observe(viewLifecycleOwner, { movies ->
                movieAdapter.setMovies(movies)
                movieAdapter.notifyDataSetChanged()
            })
            with(fragmentMoviesBinding.rvMovie) {
                val mLayoutManager = FlexboxLayoutManager(activity)
                mLayoutManager.flexDirection = FlexDirection.ROW
                mLayoutManager.flexWrap = FlexWrap.WRAP
                mLayoutManager.justifyContent = JustifyContent.SPACE_AROUND
                layoutManager = mLayoutManager
                setHasFixedSize(true)
                adapter = movieAdapter
            }

        }
    }
}