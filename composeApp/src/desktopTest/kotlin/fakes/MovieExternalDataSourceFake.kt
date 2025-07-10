package fakes

import edu.dyds.movies.data.external.MovieExternalDataSource
import edu.dyds.movies.domain.entity.Movie

class MovieExternalDataSourceFake (
    private val shouldThrow: Boolean = false
): MovieExternalDataSource {

    var getMovieDetailsCalledWith: String? = null
    private val movieFake = MovieFake()

    override suspend fun getMovieByTitle(title: String): Movie {
        getMovieDetailsCalledWith = title
        if (shouldThrow) throw RuntimeException("Fallo detalle")
        return movieFake.detailedMovie(title)
    }

}