package com.cody.coroutineswithretrofit.ui.movie.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.cody.coroutineswithretrofit.R
import com.cody.coroutineswithretrofit.databinding.FragmentMovieDetailBinding
import com.cody.coroutineswithretrofit.helper.GsonUtil
import com.cody.coroutineswithretrofit.model.Movie
import com.google.android.material.appbar.AppBarLayout

class MovieDetailFragment : Fragment() {
    private lateinit var viewModel: MovieDetailViewModel

    private lateinit var binding: FragmentMovieDetailBinding

    private val navController by lazy {
        (requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        setupBinding()
        setupViewModel()
        setupInteraction()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)

        val movie = GsonUtil.instance.fromJson(args.movie, Movie::class.java)
        viewModel.setDetail(movie)
    }

    private fun setupBinding() {
        binding.lifecycleOwner = this
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        binding.viewModel = viewModel
    }

    private fun setupInteraction() {
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { layout, offset ->
            val scrollRange = layout?.totalScrollRange ?: 0
            if (scrollRange + offset == 0) {
                viewModel.setCollapsed(true)
            } else {
                viewModel.setCollapsed(false)
            }
        })
    }
}