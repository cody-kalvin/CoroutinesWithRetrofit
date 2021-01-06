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
        if (result is MovieSearchResult.Error) {
            Toast.makeText(requireActivity(), result.message, Toast.LENGTH_SHORT).show()
        }

        val list = if (result is MovieSearchResult.Success && result.results.isNotEmpty()) {
            result.results.map { movie ->
                MovieListAdapter.MovieListItem.Body(movie)
            }
        } else {
            listOf(MovieListAdapter.MovieListItem.Empty)
        }
        listAdapter.submitList(list)
    }
}