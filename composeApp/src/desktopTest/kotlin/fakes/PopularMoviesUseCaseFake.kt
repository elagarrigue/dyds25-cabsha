package fakes

import edu.dyds.movies.domain.entity.QualifiedMovie
import edu.dyds.movies.domain.usecase.IPopularMoviesUseCase

class PopularMoviesUseCaseFake: IPopularMoviesUseCase {

    private val movieFake = MovieFake()

    override suspend fun invoke(): List<QualifiedMovie> {
        return movieFake.qualifiedMovies()
    }
}