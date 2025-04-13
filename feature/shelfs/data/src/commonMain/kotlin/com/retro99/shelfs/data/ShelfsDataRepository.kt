package com.retro99.shelfs.data

import com.github.michaelbull.result.map
import com.github.michaelbull.result.onSuccess
import com.retro99.base.result.AppResult
import com.retro99.shelfs.data.local.ShelfsLocalDataSource
import com.retro99.shelfs.data.local.model.toDomainModel
import com.retro99.shelfs.data.local.model.toLocalModel
import com.retro99.shelfs.data.remote.ShelfsRemoteDataSource
import com.retro99.shelfs.data.remote.model.toDomainModel
import com.retro99.shelfs.domain.ShelfsRepository
import com.retro99.shelfs.domain.model.ShelfDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class ShelfsDataRepository(
    private val remoteSource: ShelfsRemoteDataSource,
    private val localSource: ShelfsLocalDataSource,
) : ShelfsRepository {

    override suspend fun getShelf(id: Int): AppResult<ShelfDomainModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getShelfs(): Flow<AppResult<List<ShelfDomainModel>>> {
        return flow {
            emit(localSource.getShelfs().map { it.toDomainModel() })
            emit(
                remoteSource.getShelfs()
                    .onSuccess { remoteShelfs ->
                        localSource.save(remoteShelfs.toLocalModel())
                    }.map { it.toDomainModel() }
            )
        }
    }
}