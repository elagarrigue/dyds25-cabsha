package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.RemoteMovie

class LocalData: LocalDataSource {
    private val moviesCache: MutableList<RemoteMovie> = mutableListOf()

    override fun getMovies(): MutableList<RemoteMovie> {
        return moviesCache
    }

    override fun setMovies(movies: List<RemoteMovie>){
        moviesCache.clear()
        moviesCache.addAll(movies)
    }

}