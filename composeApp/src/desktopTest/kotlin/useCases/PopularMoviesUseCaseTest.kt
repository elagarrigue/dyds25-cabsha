package useCases

import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.repository.MoviesRepository
import edu.dyds.movies.domain.usecase.GetPopularMoviesUseCase
import fakes.MoviesRepositoryFake
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PopularMoviesUseCaseTest {

    @Test
    fun `getPopularMovies deberia retornar solo las calificadas como buenas y ordenadas`() = runTest {
        // Arrange
        val fakeRepository = MoviesRepositoryFake()
        val useCase = GetPopularMoviesUseCase(fakeRepository)

        // Act
        val result = useCase.invoke()

        // Assert
        assertTrue(fakeRepository.getPopularMoviesCalled)
        assertEquals(3, result.size)

        // Control de orden de la lista
        assertEquals("Buena", result[0].movie.title)
        assertTrue(result[0].isGoodMovie)

        assertEquals("Regular", result[1].movie.title)
        assertFalse(result[1].isGoodMovie)

        assertEquals("Mala", result[2].movie.title)
        assertFalse(result[2].isGoodMovie)
    }


    @Test
    fun `getPopularMovies deberia retornar una lista vacia cuando el repositorio no tiene valores`() = runTest {
        // Arrange
        val fakeRepository = object : MoviesRepository {
            override suspend fun getPopularMovies(): List<Movie> = emptyList()
            override suspend fun getMovieDetails(id: Int): Movie =
                Movie(id, "None", "None", "2022-01-01", "poster", null, "Original", "en", 0.0, 0.0)
        }

        val useCase = GetPopularMoviesUseCase(fakeRepository)

        // Act
        val result = useCase()

        // Assert
        assertTrue(result.isEmpty())
    }
}