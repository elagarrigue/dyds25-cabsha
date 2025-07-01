package fakes

import edu.dyds.movies.domain.repository.MoviesRepository
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.MovieItem

class MoviesRepositoryFake: MoviesRepository {

    var getPopularMoviesCalled = false
    var getMovieDetailsCalledWith: String? = null
    private val movieFake = MovieFake()

    override suspend fun getPopularMovies(): List<MovieItem> {
        getPopularMoviesCalled = true
        return movieFake.popularMovies()
    }

    override suspend fun getMovieByTitle(title: String): Movie {
        getMovieDetailsCalledWith = title
        return movieFake.detailedMovie(title)
    }

}