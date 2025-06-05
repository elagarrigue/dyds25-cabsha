package edu.dyds.movies.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.dyds.movies.data.external.ExternalData
import edu.dyds.movies.data.external.ExternalDataSource
import edu.dyds.movies.data.local.LocalData

import edu.dyds.movies.presentation.detail.DetailScreenViewModel
import edu.dyds.movies.presentation.home.HomeScreenViewModel
import edu.dyds.movies.data.local.LocalDataSource
import edu.dyds.movies.data.repositoryImpl.MovieRepositoryImpl
import edu.dyds.movies.domain.repository.MoviesRepository
import edu.dyds.movies.domain.usecase.GetMovieDetailsUseCase
import edu.dyds.movies.domain.usecase.GetPopularMoviesUseCase

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private const val API_KEY = "d18da1b5da16397619c688b0263cd281"

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
                    parameters.append("api_key", API_KEY)
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 5000
            }
        }
    private val localData: LocalDataSource = LocalData()
    private val externalData: ExternalDataSource = ExternalData(tmdbHttpClient)
    private val repository: MoviesRepository = MovieRepositoryImpl(localData, externalData)
    private val homeUseCase: GetPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
    private val detailsUseCase: GetMovieDetailsUseCase = GetMovieDetailsUseCase(repository)

    @Composable
    fun getHomeScreenViewModel(): HomeScreenViewModel {
        return viewModel { HomeScreenViewModel(homeUseCase) }
    }

    @Composable
    fun getDetailScreenViewModel(): DetailScreenViewModel {
        return viewModel { DetailScreenViewModel(detailsUseCase) }
    }
}