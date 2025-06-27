package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.MovieItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TMDBMoviesExternalData(private val tmdbHttpClient: HttpClient): ExternalDataSource {

    override suspend fun getPopularMovies(): List<MovieItem> =
        getTMDBMovies().results.map { it.toDomainMovie() }

    override suspend fun getMovieByTitle(title: String): MovieItem =
        getTMDBMovieDetails(title).results.first().toDomainMovie()

    private suspend fun getTMDBMovies(): RemoteResult =
        tmdbHttpClient.get("/3/discover/movie?sort_by=popularity.desc").body()

    private suspend fun getTMDBMovieDetails(title: String): RemoteResult {
        val encodedTitle = title.replace(" ","%20")
        return tmdbHttpClient.get("/3/search/movie?query=$encodedTitle&").body()
    }
}