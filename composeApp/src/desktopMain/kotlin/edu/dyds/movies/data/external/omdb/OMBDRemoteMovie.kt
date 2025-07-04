package edu.dyds.movies.data.external.omdb

import edu.dyds.movies.domain.entity.MovieItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OMBDRemoteMovie(
    @SerialName(value = "Title") val title: String,
    @SerialName(value = "Plot") val plot: String,
    @SerialName(value = "Released") val released: String,
    @SerialName(value =  "Year") val year: String,
    @SerialName(value = "Poster") val poster: String,
    @SerialName(value = "Language") val language: String,
    @SerialName(value = "Metascore") val metaScore: String,
    val imdbRating: Double,

    ) {
    fun toDomainMovie() = MovieItem(
        id = title.hashCode(),
        title = title,
        overview = plot,
        releaseDate = if (released.isNotEmpty() && released != "N/A") released else year,
        poster = poster,
        backdrop = poster,
        originalTitle = title,
        originalLanguage = language,
        popularity = imdbRating,
        voteAverage = if (metaScore.isNotEmpty() && metaScore != "N/A") metaScore.toDouble() else 0.0
    )
}