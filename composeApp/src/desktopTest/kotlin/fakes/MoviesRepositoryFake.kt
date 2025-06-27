package fakes

import edu.dyds.movies.domain.repository.MoviesRepository
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.MovieItem

class MoviesRepositoryFake: MoviesRepository {

    var getPopularMoviesCalled = false
    var getMovieDetailsCalledWith: String? = null

    override suspend fun getPopularMovies(): List<MovieItem> {
        getPopularMoviesCalled = true
        return listOf(
            MovieItem(1, "Buena", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 8.5),
            MovieItem(2, "Mala", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 4.0),
            MovieItem(3, "Regular", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 5.0)
        )
    }

    override suspend fun getMovieByTitle(title: String): Movie {
        getMovieDetailsCalledWith = title
        return MovieItem(0, title, "Detail", "2022-01-01", "poster", null, "Original", "en", 5.0, 6.9)
    }

}