package com.ai.bardly.screens.detail

import androidx.lifecycle.ViewModel
import com.ai.bardly.data.MuseumObject
import com.ai.bardly.data.MuseumRepository
import kotlinx.coroutines.flow.Flow

class DetailViewModel(private val museumRepository: MuseumRepository) : ViewModel() {
    fun getObject(objectId: Int): Flow<MuseumObject?> =
        museumRepository.getObjectById(objectId)
}
