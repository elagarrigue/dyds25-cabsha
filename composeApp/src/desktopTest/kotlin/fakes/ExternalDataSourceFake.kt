package fakes

import edu.dyds.movies.data.external.ExternalDataSource
import edu.dyds.movies.domain.entity.MovieItem

class ExternalDataSourceFake(
    private val shouldThrow: Boolean = false
) : ExternalDataSource {

    var getPopularMoviesCalled = false
    var getMovieDetailsCalledWith: String? = null

    override suspend fun getPopularMovies(): List<MovieItem> {
        getPopularMoviesCalled = true
        if (shouldThrow) throw RuntimeException("Fallo remoto")
        return listOf(MovieItem(1, "Remota", "Overview", "2022", "poster", null, "Original", "en", 10.0, 8.0))
    }

    override suspend fun getMovieByTitle(title: String): MovieItem {
        getMovieDetailsCalledWith = title
        if (shouldThrow) throw RuntimeException("Fallo detalle")
        return MovieItem(0, title, "Detail", "2022", "poster", null, "Original", "en", 5.0, 6.0)
    }
}