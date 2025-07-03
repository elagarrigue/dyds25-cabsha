package edu.dyds.movies.data.external.broker

import edu.dyds.movies.data.external.MovieExternalDataSource
import edu.dyds.movies.data.external.MoviesExternalDataSource
import edu.dyds.movies.domain.entity.MovieItem

class MovieBroker(
    private val tmdbMoviesService: MoviesExternalDataSource,
    private val tmdbMovieDetails: MovieExternalDataSource,
    private val omdbMovieDetails: MovieExternalDataSource
): MoviesExternalDataSource, MovieExternalDataSource {

    private fun buildMovie(
        tmdbMovie: MovieItem,
        omdbMovie: MovieItem
    ) =
        MovieItem(
            id = tmdbMovie.id,
            title = tmdbMovie.title,
            overview = "TMDB: ${tmdbMovie.overview}\n\nOMDB: ${omdbMovie.overview}",
            releaseDate = tmdbMovie.releaseDate,
            poster = tmdbMovie.poster,
            backdrop = tmdbMovie.backdrop,
            originalTitle = tmdbMovie.originalTitle,
            originalLanguage = tmdbMovie.originalLanguage,
            popularity = (tmdbMovie.popularity + omdbMovie.popularity) / 2.0,
            voteAverage = (tmdbMovie.voteAverage + omdbMovie.voteAverage) / 2.0
        )

    private suspend fun getExternalData(title: String): MovieItem {
        return try {
            omdbMovieDetails.getMovieByTitle(title)
        } catch (e: Exception) {
            MovieItem(
                id = 0,
                title = "",
                overview = "",
                releaseDate = "",
                poster = "",
                backdrop = "",
                originalTitle = "",
                originalLanguage = "",
                popularity = 0.0,
                voteAverage = 0.0
            )
        }
    }

    override suspend fun getPopularMovies(): List<MovieItem> =
        tmdbMoviesService.getPopularMovies()

    override suspend fun getMovieByTitle(title: String): MovieItem {
        val tmdbDetails = tmdbMovieDetails.getMovieByTitle(title)
        val omdbDetails = getExternalData(title)

        val details = when {
            omdbDetails.title != tmdbDetails.title -> tmdbDetails.copy(overview = "TMDB: ${tmdbDetails.overview}")
            else -> buildMovie(tmdbDetails, omdbDetails)
        }

        return details
    }
}