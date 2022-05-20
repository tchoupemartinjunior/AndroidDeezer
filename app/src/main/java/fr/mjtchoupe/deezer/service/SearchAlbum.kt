package fr.mjtchoupe.deezer.service

import fr.mjtchoupe.deezer.service.dataClasses.Album

data class SearchAlbum (
    val data: List<Album>,
    val next: String,
    val total: Int
)