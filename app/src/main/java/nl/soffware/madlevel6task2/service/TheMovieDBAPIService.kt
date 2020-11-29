package nl.soffware.madlevel6task2.service

import nl.soffware.madlevel6task2.model.Movie
import nl.soffware.madlevel6task2.model.Results
import retrofit2.http.GET
import retrofit2.http.Query

interface  TheMovieDBAPIService {
    @GET("discover/movie?language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    suspend fun getPopularMoviesForYear(@Query("year") year: Int): Results
}
