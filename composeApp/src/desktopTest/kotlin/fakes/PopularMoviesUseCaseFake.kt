package fakes

import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.QualifiedMovie
import edu.dyds.movies.domain.usecase.IPopularMoviesUseCase

class PopularMoviesUseCaseFake: IPopularMoviesUseCase {
    override suspend fun invoke(): List<QualifiedMovie> {
        return listOf(
            QualifiedMovie(Movie(1, "Buena", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 8.5), true),
            QualifiedMovie(Movie(2, "Mala", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 4.0), false),
            QualifiedMovie(Movie(3, "Regular", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 5.0), false)
        )
    }
}