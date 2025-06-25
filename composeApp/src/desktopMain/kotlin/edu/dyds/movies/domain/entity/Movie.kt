package edu.dyds.movies.domain.entity

sealed class Movie
    data class MovieItem(
        val id: Int,
        val title: String,
        val overview: String,
        val releaseDate: String,
        val poster: String,
        val backdrop: String?,
        val originalTitle: String,
        val originalLanguage: String,
        val popularity: Double,
        val voteAverage: Double
    ): Movie()

    object EmptyMovie: Movie()

data class QualifiedMovie(val movie: MovieItem, val isGoodMovie: Boolean)

