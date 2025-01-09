package com.ai.bardly.screens.chats

import androidx.lifecycle.ViewModel
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.data.MuseumObject
import com.ai.bardly.data.MuseumRepository
import kotlinx.coroutines.flow.Flow

class ChatsViewModel(private val museumRepository: MuseumRepository) : BaseViewModel() {
    fun getObject(objectId: Int): Flow<MuseumObject?> =
        museumRepository.getObjectById(objectId)
}
