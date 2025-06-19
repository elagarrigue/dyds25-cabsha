package repository

import edu.dyds.movies.data.repository.MovieRepositoryImpl
import edu.dyds.movies.domain.entity.Movie
import fakes.ExternalDataSourceFake
import fakes.LocalDataSourceFake
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MovieRepositoryImplTest {

    @Test
    fun `deberia devolver peliculas desde el cache si existen`() = runTest {
        // Arrange
        val local = LocalDataSourceFake().apply {
            cache.add(Movie(2, "Local", "Overview", "2022", "poster", null, "Original", "en", 10.0, 7.0))
        }
        val external = ExternalDataSourceFake()
        val repository = MovieRepositoryImpl(local, external)

        // Act
        val result = repository.getPopularMovies()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Local", result[0].title)
        assertFalse(external.getPopularMoviesCalled)
        assertFalse(local.setMoviesCalled)
    }

    @Test
    fun `deberia obtener peliculas desde remoto y cachearlas si no hay cache`() = runTest {
        // Arrange
        val local = LocalDataSourceFake()
        val external = ExternalDataSourceFake()
        val repository = MovieRepositoryImpl(local, external)

        // Act
        val result = repository.getPopularMovies()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Remota", result[0].title)
        assertTrue(external.getPopularMoviesCalled)
        assertTrue(local.setMoviesCalled)
        assertEquals("Remota", local.cache[0].title)
    }

    @Test
    fun `deberia devolver lista vacia si ocurre algun error al obtener peliculas`() = runTest {
        // Arrange
        val local = LocalDataSourceFake()
        val external = ExternalDataSourceFake(shouldThrow = true)
        val repository = MovieRepositoryImpl(local, external)

        // Act
        val result = repository.getPopularMovies()

        // Assert
        assertTrue(result.isEmpty())
        assertTrue(external.getPopularMoviesCalled)
    }

    @Test
    fun `deberia obtener detalles de pelicula desde remoto`() = runTest {
        // Arrange
        val local = LocalDataSourceFake()
        val external = ExternalDataSourceFake()
        val repository = MovieRepositoryImpl(local, external)

        // Act
        val result = repository.getMovieDetails(5)

        // Assert
        assertEquals(5, result.id)
        assertEquals("Detalle 5", result.title)
        assertEquals(5, external.getMovieDetailsCalledWith)
    }

    @Test
    fun `deberia lanzar excepcion si ocurre error en getMovieDetails`() = runTest {
        // Arrange
        val repository = MovieRepositoryImpl(
            localData = LocalDataSourceFake(),
            externalData = ExternalDataSourceFake(shouldThrow = true)
        )

        // Act & Assert
        val exception = assertFailsWith<RuntimeException> {
            repository.getMovieDetails(7)
        }
        assertEquals("Fallo detalle", exception.message)
    }
}