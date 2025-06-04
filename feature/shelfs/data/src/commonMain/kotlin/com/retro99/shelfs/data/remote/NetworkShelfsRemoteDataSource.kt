package com.retro99.shelfs.data.remote

import com.github.michaelbull.result.map
import com.retro99.base.result.AppResult
import com.retro99.base.result.CompletableResult
import com.retro99.shelfs.data.remote.model.AddGameToShelfDto
import com.retro99.shelfs.data.remote.model.CreateShelfDto
import com.retro99.shelfs.data.remote.model.ShelfDto
import com.retro99.shelfs.data.remote.model.ShelfsListDto
import com.retro99.shelfs.data.remote.model.toDto
import com.retro99.shelfs.domain.model.CreateShelfDomainModel
import me.tatarka.inject.annotations.Inject
import retro99.games.api.NetworkClient
import retro99.games.api.delete
import retro99.games.api.get
import retro99.games.api.post
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class NetworkShelfsRemoteDataSource(
    private val networkClient: NetworkClient,
) : ShelfsRemoteDataSource {

    override suspend fun getShelfs(): AppResult<List<ShelfDto>> {
        return networkClient.get<ShelfsListDto>(
            path = "shelves",
        ).map { it.shelves }
    }

    override suspend fun getShelf(id: String): AppResult<ShelfDto> {
        return networkClient.get<ShelfDto>(
            path = "shelves/$id",
        )
    }

    override suspend fun addGameToShelf(shelfId: String, gameId: String): CompletableResult {
        return networkClient.post<Unit>(
            path = "shelves/$shelfId/add-game",
            body = AddGameToShelfDto(gameId)
        )
    }

    override suspend fun createShelf(item: CreateShelfDto): AppResult<ShelfDto> {
        return networkClient.post<ShelfDto>(
            path = "shelves",
            body = item
        )
    }

    override suspend fun deleteShelf(id: String): CompletableResult {
        return networkClient.delete<Unit>(
            path = "shelves/$id"
        )
    }
}
