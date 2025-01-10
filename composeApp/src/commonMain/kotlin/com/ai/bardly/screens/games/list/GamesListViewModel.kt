package com.ai.bardly.screens.games.list

import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.data.MuseumObject
import com.ai.bardly.data.MuseumRepository
import kotlinx.coroutines.flow.Flow

class GamesListViewModel(private val museumRepository: MuseumRepository) : BaseViewModel() {
    fun getObject(objectId: Int): Flow<MuseumObject?> =
        museumRepository.getObjectById(objectId)
}
