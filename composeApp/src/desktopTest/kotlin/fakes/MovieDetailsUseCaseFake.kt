package fakes

import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.MovieItem
import edu.dyds.movies.domain.usecase.IMovieDetailsUseCase

class MovieDetailsUseCaseFake: IMovieDetailsUseCase {
    override suspend fun invoke(movieTitle: String): Movie {
        return MovieItem(
            1,
            movieTitle,
            "the movie $movieTitle overview",
            "21/10/2023",
            "poster url",
            "backdrop url",
            "Original Movie $movieTitle",
            "en",
            10.0,
            8.0
        )
    }
}