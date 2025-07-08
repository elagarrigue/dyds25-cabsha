package edu.dyds.movies.data.external.tmdb

import edu.dyds.movies.data.external.MoviesExternalDataSource
import edu.dyds.movies.data.external.MovieExternalDataSource
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.MovieItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TMDBMoviesExternalData(private val tmdbHttpClient: HttpClient): MoviesExternalDataSource, MovieExternalDataSource {

    override suspend fun getPopularMovies(): List<MovieItem> =
        getTMDBMovies().results.map { it.toDomainMovie() }

    override suspend fun getMovieByTitle(title: String): Movie =
        getTMDBMovieDetails(title).results.first().toDomainMovie()

    private suspend fun getTMDBMovies(): RemoteResult =
        tmdbHttpClient.get("/3/discover/movie?sort_by=popularity.desc").body()

    private suspend fun getTMDBMovieDetails(title: String): RemoteResult {
        return tmdbHttpClient.get("/3/search/movie?query=$title").body()
    }
}