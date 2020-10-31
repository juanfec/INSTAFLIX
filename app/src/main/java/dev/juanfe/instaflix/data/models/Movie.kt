package dev.juanfe.instaflix.data.models

import com.squareup.moshi.Json

data class Movie(
    val budget: Int,
    val id: Int,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @Json(name = "vote_average")
    val rating: Double
)