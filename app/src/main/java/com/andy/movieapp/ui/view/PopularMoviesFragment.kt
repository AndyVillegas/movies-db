package com.andy.movieapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.andy.movieapp.databinding.PopularMoviesFragmentBinding
import com.andy.movieapp.ui.viewmodel.PopularMoviesViewModel

class PopularMoviesFragment : Fragment() {
    private lateinit var popularMoviesPageAdapter: PopularMoviesPageAdapter
    private val viewModel: PopularMoviesViewModel by activityViewModels()
    private lateinit var binding: PopularMoviesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PopularMoviesFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularMoviesPageAdapter = PopularMoviesPageAdapter(this)
        binding.pager.adapter = popularMoviesPageAdapter
        observeViewModel()
        binding.pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if(!canScrollMore())
                    viewModel.nextPage()
            }

        })
    }

    private fun canScrollMore(): Boolean {
        return binding.pager.canScrollHorizontally(1)
    }

    private fun observeViewModel(){
        viewModel.movies.observe(viewLifecycleOwner){
            popularMoviesPageAdapter.appendMovies(it)
        }
        viewModel.message.observe(viewLifecycleOwner){
            Toast.makeText(context,it.text,Toast.LENGTH_LONG).show()
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.loadingLayout.loadingProgress.visibility = if(it) View.VISIBLE else View.GONE
        }
        viewModel.fetchMovies()
    }

    companion object {
        fun newInstance() = PopularMoviesFragment()
    }
}