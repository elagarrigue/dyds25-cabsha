package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ExternalData(private val tmdbHttpClient: HttpClient): ExternalDataSource {
    override suspend fun getPopularMovies(): List<Movie> {
        val remoteData: RemoteResult = tmdbHttpClient.get("/3/discover/movie?sort_by=popularity.desc").body()
        return remoteData.results.map { it.toDomainMovie() }
    }

    override suspend fun getMovieDetails(id: Int): Movie {
        val remoteData: RemoteMovie = tmdbHttpClient.get("/3/movie/$id").body()
        return remoteData.toDomainMovie()
    }

}