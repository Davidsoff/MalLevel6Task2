package nl.soffware.madlevel6task2.api

import nl.soffware.madlevel6task2.interceptor.MovieApiAuthenticationInterceptor
import nl.soffware.madlevel6task2.service.TheMovieDBAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TheMovieDBAPI {
    companion object {
        // The base url off the api.
        private const val baseUrl = "https://api.themoviedb.org/3/"

        /**
         * @return [TheMovieDBAPIService] The service class off the retrofit client.
         */
        fun createApi(): TheMovieDBAPIService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(MovieApiAuthenticationInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
            // Create an OkHttpClient to be able to make a log of the network traffic and add the api key to all requests
            // Create the Retrofit instance
            val movieApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit TheMovieDBAPIService
            return movieApi.create(TheMovieDBAPIService::class.java)
        }
    }
}