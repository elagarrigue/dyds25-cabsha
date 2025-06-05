package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.RemoteMovie

interface LocalDataSource {
    fun getMovies(): List<RemoteMovie>
    fun setMovies(cache: List<RemoteMovie>)
}