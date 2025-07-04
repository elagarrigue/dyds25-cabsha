package edu.dyds.movies.presentation.detail

import edu.dyds.movies.domain.entity.EmptyMovie
import edu.dyds.movies.domain.entity.MovieItem
import edu.dyds.movies.domain.usecase.IMovieDetailsUseCase
import fakes.MovieDetailsUseCaseFake
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DetailScreenViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = CoroutineScope(testDispatcher)
    private lateinit var getMovieDetailsUseCase: IMovieDetailsUseCase

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getMovieDetailsUseCase = MovieDetailsUseCaseFake()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getMovieDetails debe emitir el estado de carga y mostrar datos`() = runTest {
        // Arrange
        val detailViewModel = DetailScreenViewModel(getMovieDetailsUseCase)

        val events: ArrayList<DetailScreenViewModel.MovieDetailUiState> = arrayListOf()
        testScope.launch {
            detailViewModel.movieDetailStateFlow.collect { state ->
                events.add(state)
            }
        }

        // Act
        detailViewModel.getMovieByTitle("Movie 1")

        // Assert
        assertEquals(
            expected = DetailScreenViewModel.MovieDetailUiState(isLoading = true, movie = EmptyMovie),
            actual = events[0]
        )
        assertEquals(
            expected = DetailScreenViewModel.MovieDetailUiState(
                isLoading = false,
                movie = MovieItem(
                    4,
                    "Movie 1",
                    "Detail",
                    "2022-01-01",
                    "poster",
                    null,
                    "Original",
                    "en",
                    5.0,
                    6.9
                )
            ),
            actual = events[1]
        )
    }
}