package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.Movie

class LocalDataCache: LocalDataSource {
    private val moviesCache: MutableList<Movie> = mutableListOf()

    override fun getMovies(): List<Movie> {
        return moviesCache
    }

    override fun setMovies(cache: List<Movie>){
        moviesCache.clear()
        moviesCache.addAll(cache)
    }

}