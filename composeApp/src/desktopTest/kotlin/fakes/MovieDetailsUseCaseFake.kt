package fakes

import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.MovieItem
import edu.dyds.movies.domain.usecase.IMovieDetailsUseCase

class MovieDetailsUseCaseFake: IMovieDetailsUseCase {

    private val movieFake = MovieFake()

    override suspend fun invoke(movieTitle: String): Movie {
        return movieFake.detailedMovie(movieTitle)
    }
}