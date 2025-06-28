package edu.dyds.movies.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.usecase.IMovieDetailsUseCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailScreenViewModel (
    private val detailsUseCase: IMovieDetailsUseCase
) : ViewModel() {
        private val movieDetailStateMutableStateFlow = MutableStateFlow(MovieDetailUiState())

        val movieDetailStateFlow: Flow<MovieDetailUiState> = movieDetailStateMutableStateFlow

        fun getMovieDetail(id: Int) {
            viewModelScope.launch {
                movieDetailStateMutableStateFlow.emit(
                    MovieDetailUiState(
                        isLoading = false,
                        movie = detailsUseCase.invoke(id)
                    )
                )
            }
        }

        data class MovieDetailUiState(
            val isLoading: Boolean = true,
            val movie: Movie? = null,
        )
}