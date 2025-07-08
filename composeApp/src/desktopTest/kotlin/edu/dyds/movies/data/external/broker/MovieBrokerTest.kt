package edu.dyds.movies.data.external.broker

import edu.dyds.movies.data.external.MovieExternalDataSource
import edu.dyds.movies.domain.entity.EmptyMovie
import edu.dyds.movies.domain.entity.Movie
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
    fun `getMovieByTitle deberia retornar solo los detalles de TMDB si OMDB falla`() = runTest {
        //Arrange
        val tmdbDataFake = MovieExternalDataSourceFake()
        val omdbDataFake = object: MovieExternalDataSource {
            override suspend fun getMovieByTitle(title: String): Movie =
                MovieFake().notFoundMovie()
        }
        val fakeMovie = MovieFake().tmdbMovie("fake")
        val broker = MovieBroker(tmdbDataFake, omdbDataFake)

        //Act
        val search = broker.getMovieByTitle("fake")

        //Assert
        assertEquals(fakeMovie, search)
    }

    @Test
    fun `getMovieByTitle deberia retornar solo los detalles de OMDB si TMDB falla`() = runTest {
        //Arrange
        val omdbDataFake = MovieExternalDataSourceFake()
        val tmdbDataFake = object: MovieExternalDataSource {
            override suspend fun getMovieByTitle(title: String): Movie =
                MovieFake().notFoundMovie()
        }
        val fakeMovie = MovieFake().omdbMovie("fake")
        val broker = MovieBroker(tmdbDataFake, omdbDataFake)

        //Act
        val search = broker.getMovieByTitle("fake")

        //Assert
        assertEquals(fakeMovie, search)
    }

    @Test
    fun `getMovieByTitle deberia retornar EmptyMovie si TMDB y OMDB fallan`() = runTest {
        //Arrange
        val omdbDataFake = object: MovieExternalDataSource {
            override suspend fun getMovieByTitle(title: String): Movie =
                MovieFake().notFoundMovie()
        }
        val tmdbDataFake = object: MovieExternalDataSource {
            override suspend fun getMovieByTitle(title: String): Movie =
                MovieFake().notFoundMovie()
        }

        val fakeMovie = EmptyMovie
        val broker = MovieBroker(tmdbDataFake, omdbDataFake)

        //Act
        val search = broker.getMovieByTitle("fake")

        //Assert
        assertEquals(fakeMovie, search)
    }
}