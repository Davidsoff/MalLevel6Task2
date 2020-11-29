package nl.soffware.madlevel6task2.model

data class Results(
    val page: Int?,
    val total_results: Int?,
    val total_pages: Int?,
    val results: List<Movie>
)
data class Movie(
    var title: String,
    var overview: String,
    var poster_path: String,
    var backdrop_path: String,
    var release_date: String,
    var vote_average: String
) {
    fun getPosterUrl() =
        "https://image.tmdb.org/t/p/w500${poster_path}"

    fun getBackdropUrl() =
        "https://image.tmdb.org/t/p/w1280${backdrop_path}"
}