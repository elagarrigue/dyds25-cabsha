import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.repository.MoviesRepository
import edu.dyds.movies.domain.usecase.GetMovieDetailsUseCase
import edu.dyds.movies.domain.usecase.GetPopularMoviesUseCase

class UseCasesTest {

    // Fake repository
    class MoviesRepositoryFake : MoviesRepository {
        var getPopularMoviesCalled = false
        var getMovieDetailsCalledWith: Int? = null

        override suspend fun getPopularMovies(): List<Movie> {
            getPopularMoviesCalled = true
            return listOf(
                Movie(1, "Buena", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 8.5),
                Movie(2, "Mala", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 4.0),
                Movie(3, "Regular", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 5.0)
            )
        }

        override suspend fun getMovieDetails(id: Int): Movie {
            getMovieDetailsCalledWith = id
            return Movie(id, "Titulo $id", "Detail", "2022-01-01", "poster", null, "Original", "en", 5.0, 6.9)
        }

    }

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
