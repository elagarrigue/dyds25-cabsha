package edu.dyds.movies.data.external.broker

import edu.dyds.movies.data.external.MovieExternalDataSource
import edu.dyds.movies.domain.entity.EmptyMovie
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.MovieItem

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

    private suspend fun getOMDBDetails(title: String): Movie {
        return try {
            omdbMovieDetails.getMovieByTitle(title)
        } catch (e: Exception) {
            EmptyMovie
        }
    }

    private suspend fun getTMDBDetails(title: String): Movie {
        return try {
            tmdbMovieDetails.getMovieByTitle(title)
        } catch (e: Exception) {
            EmptyMovie
        }
    }

    override suspend fun getMovieByTitle(title: String): Movie {
        val tmdbDetails = getTMDBDetails(title)
        val omdbDetails = getOMDBDetails(title)

        val movieDetails: Movie = when {
            tmdbDetails != EmptyMovie && omdbDetails != EmptyMovie -> buildMovie((tmdbDetails as MovieItem), (omdbDetails as MovieItem))
            tmdbDetails != EmptyMovie -> (tmdbDetails as MovieItem).copy(overview = "TMDB: ${tmdbDetails.overview}")
            omdbDetails != EmptyMovie -> (omdbDetails as MovieItem).copy(overview = "OMDB: ${omdbDetails.overview}")
            else -> EmptyMovie
        }

        return movieDetails
    }
}