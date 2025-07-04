package edu.dyds.movies.data.repository

import edu.dyds.movies.domain.entity.EmptyMovie
import edu.dyds.movies.domain.entity.MovieItem
import fakes.MoviesExternalDataSourceFake
import fakes.MovieExternalDataSourceFake
import fakes.LocalDataSourceFake
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MovieRepositoryImplTest {

    @Test
    fun `deberia devolver peliculas desde el cache si existen`() = runTest {
        // Arrange
        val local = LocalDataSourceFake().apply {
            cache.add(MovieItem(2, "Local", "Overview", "2022", "poster", null, "Original", "en", 10.0, 7.0))
        }
        val external = MoviesExternalDataSourceFake()
        val externalDetails = MovieExternalDataSourceFake()
        val repository = MovieRepositoryImpl(local, external, externalDetails)

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
        val external = MoviesExternalDataSourceFake()
        val externalDetails = MovieExternalDataSourceFake()
        val repository = MovieRepositoryImpl(local, external, externalDetails)

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
        val external = MoviesExternalDataSourceFake(shouldThrow = true)
        val externalDetails = MovieExternalDataSourceFake()
        val repository = MovieRepositoryImpl(local, external, externalDetails)

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
        val external = MoviesExternalDataSourceFake()
        val externalDetails = MovieExternalDataSourceFake()
        val repository = MovieRepositoryImpl(local, external, externalDetails)

        // Act
        val result = repository.getMovieByTitle("Detalle 5") as MovieItem

        // Assert
        assertEquals("Detalle 5", result.title)
        assertEquals("Detalle 5", externalDetails.getMovieByTitle("Detalle 5").title)
    }

    @Test
    fun `deberia devolver EmptyMovie si ocurre algun error en getMovieDetails`() = runTest {
        // Arrange
        val repository = MovieRepositoryImpl(
            localData = LocalDataSourceFake(),
            externalData = MoviesExternalDataSourceFake(),
            externalDetails = MovieExternalDataSourceFake(shouldThrow = true)
        )

        // Act
        val result = repository.getMovieByTitle("Detalle 7")

        //Assert
        assertEquals(result, EmptyMovie)
    }
}