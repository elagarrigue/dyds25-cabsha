package edu.dyds.movies.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.RemoteMovie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailScreenViewModel (
    private val tmdbHttpClient: HttpClient,
) : ViewModel() {
        private val movieDetailStateMutableStateFlow = MutableStateFlow(MovieDetailUiState())

        val movieDetailStateFlow: Flow<MovieDetailUiState> = movieDetailStateMutableStateFlow

        fun getMovieDetail(id: Int) {
            viewModelScope.launch {
                movieDetailStateMutableStateFlow.emit(
                    MovieDetailUiState(isLoading = true)
                )
                movieDetailStateMutableStateFlow.emit(
                    MovieDetailUiState(
                        isLoading = false,
                        movie = getMovieDetails(id)?.toDomainMovie()
                    )
                )
            }
        }

        private suspend fun getMovieDetails(id: Int) =
            try {
                getTMDBMovieDetails(id)
            } catch (e: Exception) {
                null
            }

        private suspend fun getTMDBMovieDetails(id: Int): RemoteMovie =
            tmdbHttpClient.get("/3/movie/$id").body()

        data class MovieDetailUiState(
            val isLoading: Boolean = false,
            val movie: Movie? = null,
        )
}