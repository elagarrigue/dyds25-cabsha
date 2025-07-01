package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.MovieItem

interface ExternalDataSource {
    suspend fun getMovieByTitle(title: String): MovieItem
    suspend fun getPopularMovies(): List<MovieItem>
}