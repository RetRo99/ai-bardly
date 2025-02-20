package com.retro99.network

import io.ktor.client.engine.HttpClientEngineFactory

expect fun getHttpEngine(): HttpClientEngineFactory<*>