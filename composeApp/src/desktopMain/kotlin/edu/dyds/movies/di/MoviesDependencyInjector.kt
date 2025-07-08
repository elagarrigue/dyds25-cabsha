package edu.dyds.movies.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.dyds.movies.data.external.broker.MovieBroker
import edu.dyds.movies.data.external.MovieExternalDataSource
import edu.dyds.movies.data.external.tmdb.TMDBMoviesExternalData
import edu.dyds.movies.data.external.omdb.OMDBMoviesExternalSource
import edu.dyds.movies.data.local.LocalDataCache

import edu.dyds.movies.presentation.detail.DetailScreenViewModel
import edu.dyds.movies.presentation.home.HomeScreenViewModel
import edu.dyds.movies.data.local.LocalDataSource
import edu.dyds.movies.data.repository.MovieRepositoryImpl
import edu.dyds.movies.domain.repository.MoviesRepository
import edu.dyds.movies.domain.usecase.GetMovieDetailsUseCase
import edu.dyds.movies.domain.usecase.GetPopularMoviesUseCase
import edu.dyds.movies.domain.usecase.IMovieDetailsUseCase
import edu.dyds.movies.domain.usecase.IPopularMoviesUseCase

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private const val TMDB_API_KEY = "d18da1b5da16397619c688b0263cd281"
private const val OMDB_API_KEY = "a96e7f78"

object MoviesDependencyInjector {

    private val tmdbHttpClient =
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.themoviedb.org"
                    parameters.append("api_key", TMDB_API_KEY)
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 5000
            }
        }

    private val omdbHttpClient=
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "www.omdbapi.com"
                    parameters.append("apikey", OMDB_API_KEY)
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 5000
            }
        }

    private val localData: LocalDataSource = LocalDataCache()
    private val tmdbMoviesExternalData: TMDBMoviesExternalData = TMDBMoviesExternalData(tmdbHttpClient)
    private val omdbMoviesExternalSource: MovieExternalDataSource = OMDBMoviesExternalSource(omdbHttpClient)
    private val movieBrokerDetailsService: MovieExternalDataSource = MovieBroker( tmdbMoviesExternalData, omdbMoviesExternalSource)
    private val repository: MoviesRepository = MovieRepositoryImpl(localData, tmdbMoviesExternalData, movieBrokerDetailsService)
    private val homeUseCase: IPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
    private val detailsUseCase: IMovieDetailsUseCase = GetMovieDetailsUseCase(repository)

    @Composable
    fun getHomeScreenViewModel(): HomeScreenViewModel {
        return viewModel { HomeScreenViewModel(homeUseCase) }
    }

    @Composable
    fun getDetailScreenViewModel(): DetailScreenViewModel {
        return viewModel { DetailScreenViewModel(detailsUseCase) }
    }
}