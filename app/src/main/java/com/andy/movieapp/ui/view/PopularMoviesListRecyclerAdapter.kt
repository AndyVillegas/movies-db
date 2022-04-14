package com.andy.movieapp.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andy.movieapp.R
import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.facades.imageLoader.ImageLoaderFacade

class PopularMoviesListRecyclerAdapter(private val context: Context):
    RecyclerView.Adapter<PopularMoviesListRecyclerAdapter.ViewHolder>() {
    private var mListener: OnItemSelectedListener? = null
    private var dataset: MutableList<Movie> = mutableListOf()
    class ViewHolder(val view: View, val context: Context, val listener: OnItemSelectedListener?): RecyclerView.ViewHolder(view){
        private val movieImage: ImageView = view.findViewById(R.id.movie_image)
        private val titleTextView: TextView = view.findViewById(R.id.title_text_view)
        private val overviewTextView: TextView = view.findViewById(R.id.overview_text_view)
        fun bind(movie: Movie){
            ImageLoaderFacade(context)
                .load(movieImage, movie.fullPosterPath)
            movieImage.transitionName = "transition_movie_A${movie.id}"
            titleTextView.text = movie.title
            overviewTextView.text = movie.overview
            view.setOnClickListener {
                listener?.onItemSelected(movie.id, movieImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.popular_movie_item, parent, false)
        return ViewHolder(view, context, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

    fun appendMovies(movies: List<Movie>){
        val positionStart = this.dataset.size
        this.dataset.addAll(movies)
        this.notifyItemRangeInserted(positionStart, this.dataset.size)
    }

    fun resetList(){
        this.notifyItemRangeRemoved(0, this.dataset.size)
        this.dataset.clear()
    }

    fun addOnItemSelectedListener(listener: OnItemSelectedListener){
        mListener = listener
    }

    interface OnItemSelectedListener{
        fun onItemSelected(movieId: Long, imageView: ImageView)
    }
}