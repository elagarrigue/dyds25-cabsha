package edu.dyds.movies.data.repository

import edu.dyds.movies.data.external.ExternalDataSource
import edu.dyds.movies.data.local.LocalDataSource
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.repository.MoviesRepository

class MovieRepositoryImpl(
    private val localData: LocalDataSource, private val externalData: ExternalDataSource
): MoviesRepository {
    override suspend fun getPopularMovies(): List<Movie> {
        return try {
            val localMovies = localData.getMovies()
            localMovies.ifEmpty {
                val remoteMovies = externalData.getPopularMovies()
                localData.setMovies(remoteMovies)
                remoteMovies
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getMovieDetails(id: Int): Movie? {
        return try {
            externalData.getMovieDetails(id)
        } catch (e: Exception) {
            null
        }
    }
}