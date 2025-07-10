package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.Movie

interface MovieExternalDataSource {
    suspend fun getMovieByTitle(title: String): Movie
}