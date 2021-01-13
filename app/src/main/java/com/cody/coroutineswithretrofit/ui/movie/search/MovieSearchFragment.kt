package com.cody.coroutineswithretrofit.ui.movie.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.cody.coroutineswithretrofit.R
import com.cody.coroutineswithretrofit.databinding.FragmentMovieSearchBinding
import com.cody.coroutineswithretrofit.helper.GsonUtil

class MovieSearchFragment : Fragment() {
    private lateinit var viewModel: MovieSearchViewModel

    private lateinit var binding: FragmentMovieSearchBinding

    private val navController by lazy {
        (requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    private val listAdapter = MovieListAdapter(object : MovieListAdapter.OnItemClickListener {
        override fun onClick(item: MovieListAdapter.MovieListItem) {
            when (item) {
                is MovieListAdapter.MovieListItem.Empty -> {
                }
                is MovieListAdapter.MovieListItem.Body -> {
                    val movie = GsonUtil.instance.toJson(item.movie)
                    val direction =
                        MovieSearchFragmentDirections.actionMovieSearchFragmentToMovieDetailFragment(
                            movie
                        )
                    navController.navigate(direction)
                }
                MovieListAdapter.MovieListItem.Error -> {
                    viewModel.search()
                }
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieSearchBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()
        initViews()
        initInteractions()
    }

    private fun initViewModel() {
        val factory = MovieSearchViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory).get(MovieSearchViewModel::class.java)
        binding.viewModel = viewModel
    }

    private fun initViews() {
        binding.listResults.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = listAdapter
        }
    }

    private fun initInteractions() {
        binding.inputQuery.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search()
                closeKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun closeKeyboard() {
        val editText = binding.inputQuery
        editText.clearFocus()

        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}