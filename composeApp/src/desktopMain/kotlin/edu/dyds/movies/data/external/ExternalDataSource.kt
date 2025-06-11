package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.Movie

interface ExternalDataSource {
    suspend fun getMovieDetails(id: Int): Movie
    suspend fun getPopularMovies(): List<Movie>
}