package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.MovieItem

interface LocalDataSource {
    fun getMovies(): List<MovieItem>
    fun setMovies(cache: List<MovieItem>)
}