package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.Movie

interface LocalDataSource {
    fun getMovies(): List<Movie>
    fun setMovies(cache: List<Movie>)
}