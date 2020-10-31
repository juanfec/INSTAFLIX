package dev.juanfe.instaflix.data.remote.responses

import com.squareup.moshi.Json
import dev.juanfe.instaflix.data.models.MovieGeneral
import dev.juanfe.instaflix.data.models.SerieGeneral

data class SerieResponse(
    val page: Int,
    @Json(name = "results")
    val serieGeneralList: List<SerieGeneral>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)