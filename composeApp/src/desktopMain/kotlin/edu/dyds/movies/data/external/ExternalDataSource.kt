package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.MovieItem

interface ExternalDataSource {
    suspend fun getMovieDetails(id: Int): MovieItem
    suspend fun getPopularMovies(): List<MovieItem>
}