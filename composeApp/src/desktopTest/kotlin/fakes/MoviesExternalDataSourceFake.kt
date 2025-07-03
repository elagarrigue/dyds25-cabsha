package fakes

import edu.dyds.movies.data.external.MoviesExternalDataSource
import edu.dyds.movies.domain.entity.MovieItem

class MoviesExternalDataSourceFake(
    private val shouldThrow: Boolean = false
) : MoviesExternalDataSource {

    var getPopularMoviesCalled = false
    var getMovieDetailsCalledWith: String? = null
    private val movieFake = MovieFake()

    override suspend fun getPopularMovies(): List<MovieItem> {
        getPopularMoviesCalled = true
        if (shouldThrow) throw RuntimeException("Fallo remoto")
        return movieFake.remoteMovie()
    }

    override suspend fun getMovieByTitle(title: String): MovieItem {
        getMovieDetailsCalledWith = title
        if (shouldThrow) throw RuntimeException("Fallo detalle")
        return movieFake.detailedMovie(title)
    }
}