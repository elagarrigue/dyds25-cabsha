package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.RemoteMovie
import edu.dyds.movies.domain.entity.RemoteResult

interface ExternalDataSource {
    suspend fun getMovieDetails(id: Int): RemoteMovie
    suspend fun getPopularMovies(): RemoteResult
}