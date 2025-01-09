package com.ai.bardly.analytics

interface Analytics {
    fun log(eventName: String, params: Map<String, String>)
    fun log(eventName: String, key: String, value: String)
}