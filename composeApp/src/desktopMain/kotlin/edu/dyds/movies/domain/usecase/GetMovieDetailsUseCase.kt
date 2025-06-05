package edu.dyds.movies.domain.usecase

import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.repository.MoviesRepository

class GetMovieDetailsUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(movieId: Int): Movie {
        return repository.getMovieDetails(movieId).toDomainMovie()
    }
}