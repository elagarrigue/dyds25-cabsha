package edu.dyds.movies.data.external.omdb

import edu.dyds.movies.data.external.MovieExternalDataSource
import edu.dyds.movies.domain.entity.MovieItem
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

internal class OMDBMoviesExternalSource(
    private val omdbHttpClient: HttpClient,
    ): MovieExternalDataSource {

        override suspend fun getMovieByTitle(title: String): MovieItem =
            getOMBDMovieDetails(title).toDomainMovie()

        private suspend fun getOMBDMovieDetails(title: String): OMBDRemoteMovie =
            omdbHttpClient.get("/?t=$title").body()
}