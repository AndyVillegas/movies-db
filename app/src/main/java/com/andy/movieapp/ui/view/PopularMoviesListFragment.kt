package com.andy.movieapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andy.movieapp.R
import com.andy.movieapp.databinding.PopularMoviesListFragmentBinding
import com.andy.movieapp.ui.viewmodel.PopularMoviesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PopularMoviesListFragment : Fragment() {
    private var searchJob: Job? = null
    private lateinit var popularMoviesListRecyclerAdapter: PopularMoviesListRecyclerAdapter
    private lateinit var binding: PopularMoviesListFragmentBinding
    private val viewModel: PopularMoviesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PopularMoviesListFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configRecyclerView()
        configSearchText()
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.movies.observe(viewLifecycleOwner){
            popularMoviesListRecyclerAdapter.appendMovies(it)
        }
        viewModel.message.observe(viewLifecycleOwner){
            Toast.makeText(context,it.text, Toast.LENGTH_LONG).show()
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.loadingLayout.loadingProgress.visibility = if(it) View.VISIBLE else View.GONE
        }
        viewModel.filteredMovies.observe(viewLifecycleOwner){
            popularMoviesListRecyclerAdapter.resetList()
            popularMoviesListRecyclerAdapter.appendMovies(it)
        }
        viewModel.fetchMovies()
    }

    private fun configSearchText(){
        binding.searchTextView.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchInMemory(binding.searchTextView.text.toString())
            }
        }
    }

    private fun configRecyclerView(){
        popularMoviesListRecyclerAdapter = PopularMoviesListRecyclerAdapter(requireContext())
        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.moviesRecyclerView.adapter = popularMoviesListRecyclerAdapter
        binding.moviesRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1) && !viewModel.isFiltered){
                    viewModel.nextPage()
                }
            }
        })
        popularMoviesListRecyclerAdapter.addOnItemSelectedListener(object: PopularMoviesListRecyclerAdapter.OnItemSelectedListener{
            override fun onItemSelected(movieId: Long, imageView: ImageView) {
                val detailMovieFragment = DetailMovieFragment.newInstance(movieId)
                val fragment = activity?.supportFragmentManager?.fragments?.get(0)
                activity?.supportFragmentManager?.commit {
                    setReorderingAllowed(true)
                    if(fragment != null)
                        hide(fragment)
                    add(R.id.fragment_container_view, detailMovieFragment)
                    addSharedElement(imageView,"transition_movie_B$movieId")
                    addToBackStack("movie")
                }
            }
        })
    }

    companion object {
        fun newInstance() = PopularMoviesListFragment()
    }
}