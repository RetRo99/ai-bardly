package com.bardly.shelfs.ui.model

import androidx.paging.PagingData
import androidx.paging.map
import com.retro99.paging.domain.PagingItem
import com.retro99.shelfs.domain.model.ShelfDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class ShelfUiModel(
    override val id: String,
) : PagingItem

fun PagingData<ShelfDomainModel>.toUiModels() = map(ShelfDomainModel::toUiModel)
fun Flow<PagingData<ShelfDomainModel>>.toUiModels() = map { it.toUiModels() }
fun List<ShelfDomainModel>.toUiModel() = map(ShelfDomainModel::toUiModel)
fun ShelfDomainModel.toUiModel() = ShelfUiModel(
    id = id,
)
