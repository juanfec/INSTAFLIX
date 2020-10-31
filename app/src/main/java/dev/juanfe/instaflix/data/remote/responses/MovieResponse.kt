package dev.juanfe.instaflix.data.remote.responses

import com.squareup.moshi.Json
import dev.juanfe.instaflix.data.models.MovieGeneral


data class MovieResponse(
    val page: Int,
    @Json(name = "results")
    val movieGeneralList: List<MovieGeneral>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)