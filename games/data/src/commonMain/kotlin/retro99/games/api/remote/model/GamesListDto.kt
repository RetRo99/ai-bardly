package retro99.games.api.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GamesListDto(
    val games: List<GameDto>,
    val totalPages: Int,
    val currentPage: Int,
)
