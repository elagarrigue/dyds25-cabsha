package useCases

import edu.dyds.movies.domain.usecase.GetMovieDetailsUseCase
import fakes.MoviesRepositoryFake
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class MovieDetailsUseCaseTest {

    @Test
    fun `getMovieDetails deberia retornar la pelicula correcta`() = runTest {
        // Arrange
        val fakeRepository = MoviesRepositoryFake()
        val useCase = GetMovieDetailsUseCase(fakeRepository)

        // Act
        val result = useCase.invoke(42)

        // Assert
        assertEquals(42, result.id)
        assertEquals("Titulo 42", result.title)
        assertEquals(42, fakeRepository.getMovieDetailsCalledWith)
    }
}