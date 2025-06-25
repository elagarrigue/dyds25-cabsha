package edu.dyds.movies.domain.usecase

import edu.dyds.movies.domain.entity.Movie

interface IMovieDetailsUseCase {
    suspend fun invoke(movieId: Int): Movie
}