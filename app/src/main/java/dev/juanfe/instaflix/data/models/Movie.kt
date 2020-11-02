package dev.juanfe.instaflix.data.models

import com.squareup.moshi.Json

data class Movie(
    val id: Int,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String,
    val title: String,
    @Json(name = "vote_average")
    val rating: Double
)