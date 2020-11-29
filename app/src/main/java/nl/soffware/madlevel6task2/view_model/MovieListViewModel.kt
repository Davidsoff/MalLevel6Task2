package nl.soffware.madlevel6task2.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.soffware.madlevel6task2.model.Movie
import nl.soffware.madlevel6task2.repository.PopularMovieRepository

class MovieListViewModel(application: Application) : AndroidViewModel(application) {
    private val popularMovieRepository = PopularMovieRepository()
    lateinit var currentMovie: Movie
    val popularMovies = popularMovieRepository.popularMovies

    private val _errorText: MutableLiveData<String> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * errorText can be observed from view for error showing
     * Encapsulation :)
     */
    val errorText: LiveData<String>
        get() = _errorText

    /**
     * The viewModelScope is bound to Dispatchers.Main and will automatically be cancelled when the ViewModel is cleared.
     * Extension method of lifecycle-viewmodel-ktx library
     */
    fun getPopularMoviesForYear(year: Int) {
        viewModelScope.launch {
            try {
                popularMovieRepository.updatePopularMoviesForYear(year)
            } catch (error: PopularMovieRepository.MovieRefreshError) {
                _errorText.value = error.message
                Log.e("Movie error", error.cause.toString())
            }
        }
    }

    fun getMovie(index: Int): Movie? {
        return popularMovies.value?.get(index)
    }
}