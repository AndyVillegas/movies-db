package com.andy.movieapp.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.andy.movieapp.R
import com.andy.movieapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragment = PopularMoviesListFragment.newInstance()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, fragment)
        }
    }
}