package edu.dyds.movies.data.repository

import edu.dyds.movies.data.external.MovieExternalDataSource
import edu.dyds.movies.data.external.MoviesExternalDataSource
import edu.dyds.movies.data.local.LocalDataSource
import edu.dyds.movies.domain.entity.EmptyMovie
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.MovieItem
import edu.dyds.movies.domain.repository.MoviesRepository

class MovieRepositoryImpl(
    private val localData: LocalDataSource, private val externalData: MoviesExternalDataSource,
    private val externalDetails: MovieExternalDataSource
): MoviesRepository {
    override suspend fun getPopularMovies(): List<MovieItem> {
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

    override suspend fun getMovieByTitle(title: String): Movie {
        return try {
            externalDetails.getMovieByTitle(title)
        } catch (e: Exception) {
            EmptyMovie
        }
    }
}