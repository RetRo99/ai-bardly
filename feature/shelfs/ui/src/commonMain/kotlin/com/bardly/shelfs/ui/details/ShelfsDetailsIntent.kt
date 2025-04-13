package com.bardly.shelfs.ui.details

import com.retro99.base.ui.BaseScreenIntent

sealed interface ShelfDetailsIntent : BaseScreenIntent {
    data object NavigateBack : ShelfDetailsIntent
}