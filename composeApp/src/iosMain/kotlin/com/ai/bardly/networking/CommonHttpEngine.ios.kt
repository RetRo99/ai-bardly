package com.ai.bardly.networking

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

actual fun getHttpEngine(): HttpClientEngineFactory<*> = Darwin