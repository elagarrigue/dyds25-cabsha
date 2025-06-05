package edu.dyds.movies.data.repositoryImpl

import edu.dyds.movies.data.external.ExternalDataSource
import edu.dyds.movies.data.local.LocalDataSource
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.RemoteMovie
import edu.dyds.movies.domain.repository.MoviesRepository

class MovieRepositoryImpl(
    private val localData: LocalDataSource, private val externalData: ExternalDataSource
): MoviesRepository {
    override suspend fun getPopularMovies(): List<Movie> {
        val localCache = localData.getMovies()

        val movies =
            localCache.ifEmpty {
                try {
                    val externalMovies = externalData.getPopularMovies()
                    localData.setMovies(externalMovies)

                    externalMovies
                } catch (e: Exception) {
                    emptyList()
                }
            }

        return movies.map { it.toDomainMovie() }
    }

    override suspend fun getMovieDetails(id: Int): RemoteMovie = externalData.getMovieDetails(id)
}