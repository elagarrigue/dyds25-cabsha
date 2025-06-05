package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.Movie

class LocalData: LocalDataSource {

    private val moviesCache: MutableList<Movie> = mutableListOf()

    override fun getMovies(): List<Movie> {
        return moviesCache
    }

    override fun setMovies(movies: List<Movie>){
        moviesCache.clear()
        moviesCache.addAll(movies)
    }

}