package fr.mjtchoupe.deezer.service

import fr.mjtchoupe.deezer.service.dataClasses.Artist

data class SearchArtist (
    val data: List<Artist>,
    val next: String,
    val total: Int
)