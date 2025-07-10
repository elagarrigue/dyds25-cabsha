package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.MovieItem

interface MoviesExternalDataSource {
    suspend fun getPopularMovies(): List<MovieItem>
}