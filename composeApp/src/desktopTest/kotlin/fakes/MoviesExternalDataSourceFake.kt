package fakes

import edu.dyds.movies.data.external.MoviesExternalDataSource
import edu.dyds.movies.domain.entity.MovieItem

class MoviesExternalDataSourceFake(
    private val shouldThrow: Boolean = false
) : MoviesExternalDataSource {

    var getPopularMoviesCalled = false
    private val movieFake = MovieFake()

    override suspend fun getPopularMovies(): List<MovieItem> {
        getPopularMoviesCalled = true
        if (shouldThrow) throw RuntimeException("Fallo remoto")
        return movieFake.remoteMovie()
    }
}