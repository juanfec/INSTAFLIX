package dev.juanfe.instaflix.data.remote.responses

import com.squareup.moshi.Json
import dev.juanfe.instaflix.data.models.Movie


data class MovieResponse(
    val page: Int,
    @Json(name = "results")
    val movieList: List<Movie>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)