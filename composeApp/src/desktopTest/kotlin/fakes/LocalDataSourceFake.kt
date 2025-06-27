package fakes

import edu.dyds.movies.data.local.LocalDataSource
import edu.dyds.movies.domain.entity.MovieItem

class LocalDataSourceFake : LocalDataSource {
    var cache: MutableList<MovieItem> = mutableListOf()
    var setMoviesCalled = false

    override fun getMovies(): List<MovieItem> = cache

    override fun setMovies(cache: List<MovieItem>) {
        this.cache.clear()
        this.cache.addAll(cache)
        setMoviesCalled = true
    }
}