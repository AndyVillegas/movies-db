package com.andy.movieapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.andy.movieapp.R
import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.databinding.ItemMovieFragmentBinding
import com.andy.movieapp.facades.imageLoader.ImageLoaderFacade

private const val MOVIE_ID = "movieId"
private const val MOVIE_NAME = "movieName"
private const val MOVIE_IMAGE = "movieImage"

class ItemMovieFragment : Fragment() {

    private lateinit var binding: ItemMovieFragmentBinding
    private var movieId: Long = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            movieId = it.getLong(MOVIE_ID)
            binding.movieImage.transitionName = "transition_movie_A$movieId"
            ImageLoaderFacade(requireContext())
                .load(binding.movieImage, it.getString(MOVIE_IMAGE)!!)
            binding.titleTextView.text = it.getString(MOVIE_NAME)
        }
        configListener()
    }

    private fun configListener(){
        binding.card.setOnClickListener {
            val detailMovieFragment = DetailMovieFragment.newInstance(movieId)
            val fragment = activity?.supportFragmentManager?.fragments?.get(0)
            activity?.supportFragmentManager?.commit {
                setReorderingAllowed(true)
                if(fragment != null)
                    hide(fragment)
                add(R.id.fragment_container_view, detailMovieFragment)
                addSharedElement(binding.movieImage,"transition_movie_B$movieId")
                addToBackStack("movie")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ItemMovieFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie) =
            ItemMovieFragment().apply {
                arguments = Bundle().apply {
                    putLong(MOVIE_ID, movie.id)
                    putString(MOVIE_NAME, movie.title)
                    putString(MOVIE_IMAGE, movie.fullPosterPath)
                }
            }
    }
}