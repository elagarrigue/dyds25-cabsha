package viewModels

import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.QualifiedMovie
import edu.dyds.movies.domain.usecase.IPopularMoviesUseCase
import edu.dyds.movies.presentation.home.HomeScreenViewModel
import fakes.PopularMoviesUseCaseFake
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
class HomeViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = CoroutineScope(testDispatcher)
    private lateinit var getPopularMoviesUseCase: IPopularMoviesUseCase

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPopularMoviesUseCase = PopularMoviesUseCaseFake()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getPopularMovies debe emitir el estado de carga y mostrar datos`() = runTest {
        // Arrange
        val homeViewModel = HomeScreenViewModel(getPopularMoviesUseCase)

        val events: ArrayList<HomeScreenViewModel.MoviesUiState> = arrayListOf()
        testScope.launch {
            homeViewModel.moviesStateFlow.collect { state ->
                events.add(state)
            }
        }

        // Act
        homeViewModel.getAllMovies()

        // Assert
        assertEquals(
            expected = HomeScreenViewModel.MoviesUiState(true, emptyList()),
            actual = events[0]
        )
        assertEquals(
            expected = HomeScreenViewModel.MoviesUiState(
                isLoading = false,
                movies = listOf(
                    QualifiedMovie(Movie(1, "Buena", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 8.5), true),
                    QualifiedMovie(Movie(2, "Mala", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 4.0), false),
                    QualifiedMovie(Movie(3, "Regular", "Overview", "2022-01-01", "poster", null, "Titulo Original", "en", 10.0, 5.0), false)
                )
            ),
            actual = events[1]
        )
    }
}