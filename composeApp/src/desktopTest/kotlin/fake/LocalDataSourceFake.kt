package fake

import edu.dyds.movies.data.local.LocalDataSource
import edu.dyds.movies.domain.entity.Movie

class LocalDataSourceFake : LocalDataSource {
    var cache: MutableList<Movie> = mutableListOf()
    var setMoviesCalled = false

    override fun getMovies(): List<Movie> = cache

    override fun setMovies(cache: List<Movie>) {
        this.cache.clear()
        this.cache.addAll(cache)
        setMoviesCalled = true
    }
}