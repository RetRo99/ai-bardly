package com.ai.bardly.networking

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

actual fun getHttpEngine(): HttpClientEngineFactory<*> = OkHttp