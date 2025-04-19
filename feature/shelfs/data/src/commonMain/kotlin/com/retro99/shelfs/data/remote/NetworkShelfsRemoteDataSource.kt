package com.retro99.shelfs.data.remote

import com.github.michaelbull.result.map
import com.retro99.base.result.AppResult
import com.retro99.shelfs.data.remote.model.ShelfDto
import com.retro99.shelfs.data.remote.model.ShelfsListDto
import me.tatarka.inject.annotations.Inject
import retro99.games.api.NetworkClient
import retro99.games.api.get
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
}
