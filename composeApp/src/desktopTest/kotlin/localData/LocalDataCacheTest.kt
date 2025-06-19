package localData

import edu.dyds.movies.data.local.LocalDataCache
import edu.dyds.movies.data.local.LocalDataSource
import edu.dyds.movies.domain.entity.Movie
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LocalDataCacheTest {

    private lateinit var localCacheMovies: LocalDataSource

    @Before
    fun setup() {
        localCacheMovies = LocalDataCache()
    }

    @Test
    fun `getMovies deberia retornar la lista vacia cuando inicia`() {
        // Act
        val result = localCacheMovies.getMovies()

        // Assert
        assertTrue(result.isEmpty())
    }

    @Test
    fun `setMovies deberia almacenar y retornar los valores correctamente`() {
        // Arrange
        val movies = listOf(
            Movie(1, "Película A", "Overview A", "2023-01-01", "posterA", null, "Titulo Original A", "en", 10.0, 8.5),
            Movie(2, "Película B", "Overview B", "2023-01-01", "posterB", null, "Titulo Original B", "en", 10.0, 7.0)
        )

        // Act
        localCacheMovies.setMovies(movies)
        val result = localCacheMovies.getMovies()

        // Assert
        assertEquals(movies, result)
    }

    @Test
    fun `setMovies deberia reemplazar completamente la lista existente`() {
        // Arrange
        val initialListMovies = listOf(
            Movie(1, "Película Inicial", "Overview", "2023-01-01", "poster", null, "Original", "en", 10.0, 8.5)
        )
        val newListMovies = listOf(
            Movie(2, "Nueva Película", "Overview", "2023-01-01", "poster", null, "Original", "en", 10.0, 7.0)
        )

        // Act
        localCacheMovies.setMovies(initialListMovies)
        localCacheMovies.setMovies(newListMovies)
        val result = localCacheMovies.getMovies()

        // Assert
        assertEquals(newListMovies, result) // Verifica que la lista inicial fue reemplazada
        assertEquals(1, result.size) // Asegura que no hay elementos previos
    }

    @Test
    fun `setMovies deberia vaciar la lista cuando recibe una lista vacia`() {
        // Arrange
        val initialListMovies = listOf(
            Movie(1, "Película Inicial", "Overview", "2023-01-01", "poster", null, "Original", "en", 10.0, 8.5)
        )

        // Acts
        localCacheMovies.setMovies(initialListMovies)
        localCacheMovies.setMovies(emptyList())
        val result = localCacheMovies.getMovies()

        // Assert
        assertTrue(result.isEmpty()) // Verifica que la caché queda vacía
    }
}