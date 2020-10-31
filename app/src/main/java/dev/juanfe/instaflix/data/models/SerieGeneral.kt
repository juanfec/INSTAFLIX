package dev.juanfe.instaflix.data.models

import com.squareup.moshi.Json

data class SerieGeneral(
    val id: Int,
    val overview: String,
    val poster_path: String,
    @Json(name = "first_air_date")
    val release_date: String,
    @Json(name = "name")
    val title: String
)