package edu.dyds.movies.data.external.broker

import edu.dyds.movies.data.external.MovieExternalDataSource
import edu.dyds.movies.domain.entity.MovieItem
import fakes.MovieExternalDataSourceFake
import fakes.MovieFake
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MovieBrokerTest {

    @Test
    fun `getMovieByTitle deberia retornar los detalles de las peliculas de ambos servicios en un solo resultado`() = runTest {
        //Arrange
        val externalDataFake = MovieExternalDataSourceFake()
        val fakeMovie = MovieFake().brokerMovie("fake")
        val broker = MovieBroker(externalDataFake, externalDataFake)

        //Act
        val search = broker.getMovieByTitle("fake")

        //Assert
        assertEquals(fakeMovie, search)
    }

    @Test
    fun `getOMDBDetails deberia retornar solo los detalles de TMDB si OMDB falla`() = runTest {
        //Arrange
        val tmdbDataFake = MovieExternalDataSourceFake()
        val omdbDataFake = object: MovieExternalDataSource {
            override suspend fun getMovieByTitle(title: String): MovieItem =
                MovieFake().notFoundMovie()
        }
        val fakeMovie = MovieFake().tmdbMovie("fake")
        val broker = MovieBroker(tmdbDataFake, omdbDataFake)

        //Act
        val search = broker.getMovieByTitle("fake")

        //Assert
        assertEquals(fakeMovie, search)
    }
}