package com.ai.bardly.networking

import io.ktor.client.engine.HttpClientEngineFactory

expect fun getHttpEngine(): HttpClientEngineFactory<*>