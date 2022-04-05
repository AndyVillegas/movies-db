package com.andy.movieapp.ui.view

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.andy.movieapp.R
import com.andy.movieapp.databinding.DetailMovieFragmentBinding
import com.andy.movieapp.facades.imageLoader.ImageLoaderFacade
import com.andy.movieapp.ui.viewmodel.DetailMovieViewModel

private const val MOVIE_ID = "movieId"
class DetailMovieFragment : Fragment() {

    private val viewModel: DetailMovieViewModel by viewModels()
    private lateinit var binding: DetailMovieFragmentBinding
    private var movieId: Long = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailMovieFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)
        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            movieId = it.getLong(MOVIE_ID)
            viewModel.find(movieId)
            binding.movieImage.transitionName = "transition_movie_B$movieId"
        }
        viewModel.currentMovie.observe(viewLifecycleOwner){
            binding.titleTextView.text = it.title
            ImageLoaderFacade(requireContext())
                .load(binding.movieImage, it.fullPosterPath){
                    startPostponedEnterTransition()
                }
            binding.descriptionTextView.text = it.overview
            binding.originalTitleTextView.text = it.originalTitle
            binding.releaseDateTextView.text = it.releaseDate
            paintStars(it.voteAverage.toInt())
        }
    }

    private fun paintStars(number: Int){
        val stars = listOf(
            binding.start1Image,
            binding.start2Image,
            binding.start3Image,
            binding.start4Image,
            binding.start5Image,
            binding.start6Image,
            binding.start7Image,
            binding.start8Image,
            binding.start9Image,
            binding.start10Image
        )
        for (i in 0 until number){
            stars[i].setColorFilter(Color.YELLOW)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(movieId: Long) =
            DetailMovieFragment().apply {
                arguments = Bundle().apply {
                    putLong(MOVIE_ID, movieId)
                }
            }
    }
}