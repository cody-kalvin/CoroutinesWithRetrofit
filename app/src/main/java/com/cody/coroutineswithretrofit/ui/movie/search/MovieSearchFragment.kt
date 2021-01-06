package com.cody.coroutineswithretrofit.ui.movie.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cody.coroutineswithretrofit.data.movie.MovieSearchResult
import com.cody.coroutineswithretrofit.databinding.FragmentMovieSearchBinding

class MovieSearchFragment : Fragment() {
    private lateinit var viewModel: MovieSearchViewModel

    private lateinit var binding: FragmentMovieSearchBinding

    private val listAdapter = MovieListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()
        initViews()
        initObservers()
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

    private fun initObservers() {
        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            loadSearchResults(result)
        }
    }

    private fun initInteractions() {
        binding.inputQuery.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search()
                true
            } else {
                false
            }
        }
    }

    private fun loadSearchResults(result: MovieSearchResult) {
        when (result) {
            is MovieSearchResult.Initial -> {
                binding.progressbarLoading.visibility = View.GONE
                listAdapter.submitList(listOf(MovieListAdapter.MovieListItem.Empty))
            }
            is MovieSearchResult.Loading -> {
                binding.progressbarLoading.visibility = View.VISIBLE
            }
            is MovieSearchResult.Success -> {
                binding.progressbarLoading.visibility = View.GONE
                val movies = result.results
                val list = if (movies.isEmpty()) {
                    listOf(MovieListAdapter.MovieListItem.Empty)
                } else {
                    movies.map { movie ->
                        MovieListAdapter.MovieListItem.Body(movie)
                    }
                }
                listAdapter.submitList(list)
            }
            is MovieSearchResult.Error -> {
                binding.progressbarLoading.visibility = View.GONE
                Toast.makeText(requireActivity(), result.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}