package fr.mjtchoupe.deezer.service


import fr.mjtchoupe.deezer.service.dataClasses.Tracks

data class SearchTracks (
    val data: List<Tracks>,
    val next: String,
    val total: Int
)