package com.andy.movieapp.ui.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andy.movieapp.data.model.Movie

class PopularMoviesPageAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private var dataset: MutableList<Movie> = mutableListOf()

    override fun getItemCount(): Int = dataset.size

    override fun createFragment(position: Int): Fragment {
        val item = dataset[position]
        return ItemMovieFragment.newInstance(item)
    }

    fun appendMovies(movies: List<Movie>){
        val positionStart = this.dataset.size
        this.dataset.addAll(movies)
        this.notifyItemRangeInserted(positionStart, this.dataset.size)
    }
}