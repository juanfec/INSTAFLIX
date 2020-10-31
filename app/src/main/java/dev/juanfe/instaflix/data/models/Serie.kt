package dev.juanfe.instaflix.data.models

import com.squareup.moshi.Json

data class Serie(
    val id: Int,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "first_air_date")
    val releaseDate: String,
    @Json(name = "name")
    val title: String,
    @Json(name = "vote_average")
    val rating: Double
)