package dev.juanfe.instaflix.data.models

data class MovieGeneral(
    val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String
)