package fakes

import edu.dyds.movies.domain.repository.MoviesRepository
import edu.dyds.movies.domain.entity.Movie

class MoviesRepositoryFake: MoviesRepository {

    var getPopularMoviesCalled = false
    var getMovieDetailsCalledWith: Int? = null

    override suspend fun getPopularMovies(): List<Movie> {
        getPopularMoviesCalled = true
        return listOf(
            Movie(1, "Buena", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 8.5),
            Movie(2, "Mala", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 4.0),
            Movie(3, "Regular", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 5.0)
        )
    }

    override suspend fun getMovieDetails(id: Int): Movie {
        getMovieDetailsCalledWith = id
        return Movie(id, "Titulo $id", "Detail", "2022-01-01", "poster", null, "Original", "en", 5.0, 6.9)
    }

}