package com.retro99.games.domain.usecase

import com.retro99.base.result.CompletableResult
import com.retro99.games.domain.GamesRepository
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
class ToggleGameFavouriteStateUseCase(
    private val gamesRepository: GamesRepository
) {

    suspend operator fun invoke(gameId: Int, newState: Boolean): CompletableResult {
        return if (newState) {
            gamesRepository.addToFavourites(gameId)
        } else {
            gamesRepository.removeFromFavourites(gameId)
        }
    }
}