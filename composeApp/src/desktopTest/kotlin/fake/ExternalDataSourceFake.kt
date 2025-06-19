package fake

import edu.dyds.movies.data.external.ExternalDataSource
import edu.dyds.movies.domain.entity.Movie

class ExternalDataSourceFake(
    private val shouldThrow: Boolean = false
) : ExternalDataSource {

    var getPopularMoviesCalled = false
    var getMovieDetailsCalledWith: Int? = null

    override suspend fun getPopularMovies(): List<Movie> {
        getPopularMoviesCalled = true
        if (shouldThrow) throw RuntimeException("Fallo remoto")
        return listOf(Movie(1, "Remota", "Overview", "2022", "poster", null, "Original", "en", 10.0, 8.0))
    }

    override suspend fun getMovieDetails(id: Int): Movie {
        getMovieDetailsCalledWith = id
        if (shouldThrow) throw RuntimeException("Fallo detalle")
        return Movie(id, "Detalle $id", "Detail", "2022", "poster", null, "Original", "en", 5.0, 6.0)
    }
}