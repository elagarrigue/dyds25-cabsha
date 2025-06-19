package fakes

import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.usecase.IMovieDetailsUseCase

class MovieDetailsUseCaseFake: IMovieDetailsUseCase {
    override suspend fun invoke(movieId: Int): Movie {
        return Movie(
            movieId,
            "Movie $movieId",
            "the movie $movieId overview",
            "21/10/2023",
            "poster url",
            "backdrop url",
            "Original Movie $movieId",
            "en",
            10.0,
            8.0
        )
    }
}