package com.technipixl.exo1.network.model

data class Character(
    var id: Long,
    var name: String,
    var thumbnail: Thumbnail,
    var comics: ComicsData
)

data class CharacterResponse(
    val data: DataResponse
)

data class DataResponse(
    val results: MutableList<Character>)

data class Thumbnail(
    val path: String,
    val extension: String
)
