package edu.dyds.movies.data.external.broker

import edu.dyds.movies.data.external.MovieExternalDataSource
import edu.dyds.movies.domain.entity.MovieItem

private val omdbNotFound: MovieItem =
    MovieItem(0,"","","","","","","",0.0,0.0)
class MovieBroker(
    private val tmdbMovieDetails: MovieExternalDataSource,
    private val omdbMovieDetails: MovieExternalDataSource
): MovieExternalDataSource {

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

    private suspend fun getOMDBDetails(title: String): MovieItem {
        return try {
            omdbMovieDetails.getMovieByTitle(title)
        } catch (e: Exception) {
            omdbNotFound
        }
    }

    override suspend fun getMovieByTitle(title: String): MovieItem {
        val tmdbDetails = tmdbMovieDetails.getMovieByTitle(title)
        val omdbDetails = getOMDBDetails(title)

        val details = when {
            omdbDetails == omdbNotFound -> tmdbDetails.copy(overview = "TMDB: ${tmdbDetails.overview}")
            else -> buildMovie(tmdbDetails, omdbDetails)
        }

        return details
    }
}