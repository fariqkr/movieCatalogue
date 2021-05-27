package com.fariq.moviecatalogue.ui.tvShows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fariq.moviecatalogue.databinding.FragmentTvShowBinding
import com.fariq.moviecatalogue.ui.movies.MovieAdapter
import com.fariq.moviecatalogue.ui.movies.MovieViewModel
import com.fariq.moviecatalogue.viewmodel.ViewModelFactory
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class TvShowFragment : Fragment() {
    private lateinit var fragmentTvShowBinding: FragmentTvShowBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentTvShowBinding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return fragmentTvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

            val tvShowAdapter = TvShowAdapter()
            viewModel.getTvShows().observe(viewLifecycleOwner, { tvShows ->
                tvShowAdapter.setTvShows(tvShows)
                tvShowAdapter.notifyDataSetChanged()
            })
            with(fragmentTvShowBinding.rvTvShow) {
                val mLayoutManager = FlexboxLayoutManager(activity)
                mLayoutManager.flexDirection = FlexDirection.ROW
                mLayoutManager.flexWrap = FlexWrap.WRAP
                mLayoutManager.justifyContent = JustifyContent.SPACE_AROUND
                layoutManager = mLayoutManager
                setHasFixedSize(true)
                adapter = tvShowAdapter
            }
        }
    }
}