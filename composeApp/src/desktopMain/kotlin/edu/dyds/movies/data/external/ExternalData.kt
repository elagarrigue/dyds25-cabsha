package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.RemoteMovie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ExternalData(private val tmdbHttpClient: HttpClient): ExternalDataSource {
    override suspend fun getPopularMovies(): List<RemoteMovie> = tmdbHttpClient.get("/3/discover/movie?sort_by=popularity.desc").body()

    override suspend fun getMovieDetails(id: Int): RemoteMovie = tmdbHttpClient.get("/3/movie/$id").body()

}