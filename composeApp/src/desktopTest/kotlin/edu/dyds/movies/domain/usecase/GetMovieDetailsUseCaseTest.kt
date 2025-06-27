package edu.dyds.movies.domain.usecase

import edu.dyds.movies.domain.entity.MovieItem
import fakes.MoviesRepositoryFake
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GetMovieDetailsUseCaseTest {

    @Test
    fun `getMovieDetails deberia retornar la pelicula correcta`() = runTest {
        // Arrange
        val fakeRepository = MoviesRepositoryFake()
        val useCase = GetMovieDetailsUseCase(fakeRepository)

        // Act
        val result = useCase.invoke("Titulo 42") as MovieItem

        // Assert
        assertEquals("Titulo 42", result.title)
        assertEquals("Titulo 42", fakeRepository.getMovieDetailsCalledWith)
    }
}