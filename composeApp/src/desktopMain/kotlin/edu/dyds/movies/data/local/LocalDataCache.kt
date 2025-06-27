package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.MovieItem

class LocalDataCache: LocalDataSource {
    private val moviesCache: MutableList<MovieItem> = mutableListOf()

    override fun getMovies(): List<MovieItem> {
        return moviesCache
    }

    override fun setMovies(cache: List<MovieItem>){
        moviesCache.clear()
        moviesCache.addAll(cache)
    }

}