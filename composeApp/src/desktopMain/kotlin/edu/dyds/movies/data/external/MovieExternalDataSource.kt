package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.MovieItem

interface MovieExternalDataSource {
    suspend fun getMovieByTitle(title: String): MovieItem
}