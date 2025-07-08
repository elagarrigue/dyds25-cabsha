package fakes

import edu.dyds.movies.domain.entity.EmptyMovie
import edu.dyds.movies.domain.entity.QualifiedMovie
import  edu.dyds.movies.domain.entity.MovieItem

class MovieFake {

    fun popularMovies(): List<MovieItem> = listOf(
        MovieItem(1, "Buena", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 8.5),
        MovieItem(2, "Mala", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 4.0),
        MovieItem(3, "Regular", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 5.0)

    )
    fun qualifiedMovies(): List<QualifiedMovie> = listOf(
        QualifiedMovie(MovieItem(1, "Buena", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 8.5), true),
        QualifiedMovie(MovieItem(2, "Mala", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 4.0), false),
        QualifiedMovie(MovieItem(3, "Regular", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 5.0), false)
    )

    fun detailedMovie(title: String): MovieItem =
        MovieItem(4, title, "Detail", "2022-01-01", "poster", null, "Original", "en", 5.0, 6.9)

    fun brokerMovie(title: String): MovieItem =
        MovieItem(id=4, title, "TMDB: Detail\n\nOMDB: Detail", "2022-01-01", "poster", null, "Original", "en", 5.0, 6.9)

    fun remoteMovie(): List<MovieItem> =
        listOf(MovieItem(5, "Remota", "Overview", "2022", "poster", null, "Original", "en", 10.0, 8.0))

    fun notFoundMovie(): EmptyMovie = EmptyMovie
    fun tmdbMovie(title: String): MovieItem =
        MovieItem(4, title, "TMDB: Detail", "2022-01-01", "poster", null, "Original", "en", 5.0, 6.9)

    fun omdbMovie(title: String): MovieItem =
        MovieItem(4, title, "OMDB: Detail", "2022-01-01", "poster", null, "Original", "en", 5.0, 6.9)
}