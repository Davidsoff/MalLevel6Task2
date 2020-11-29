package nl.soffware.madlevel6task2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.withTimeout
import nl.soffware.madlevel6task2.api.TheMovieDBAPI
import nl.soffware.madlevel6task2.model.Movie
import nl.soffware.madlevel6task2.service.TheMovieDBAPIService

class PopularMovieRepository {
    private val movieService: TheMovieDBAPIService = TheMovieDBAPI.createApi()

    private val _popularMovies: MutableLiveData<List<Movie>> = MutableLiveData(emptyList())

    val popularMovies: LiveData<List<Movie>>
        get() = _popularMovies

    suspend fun updatePopularMoviesForYear(year: Int) {
        try {
            val result = withTimeout(5_000) {
                movieService.getPopularMoviesForYear(year)
            }
            _popularMovies.value = result.results
        } catch (error: Throwable) {
            throw MovieRefreshError("Unable to fetch movies for year $year", error)
        }
    }

    class MovieRefreshError(message: String, cause: Throwable) : Throwable(message, cause)
}