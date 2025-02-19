package com.ai.bardly.analytics

sealed class AnalyticsEvent(
    val name: String,
    open val origin: AnalyticsEventOrigin,
    private val params: Map<AnalyticsEventParam, String>,
) {
    val paramsMap: Map<String, String>
        get() = (params + mapOf(AnalyticsEventParam.ScreenName to origin.analyticKey)).map { it.key.analyticKey to it.value }
            .toMap()

    data class ScreenOpen(
        override val origin: AnalyticsEventOrigin,
    ) : AnalyticsEvent(
        name = "screen_open",
        origin = origin,
        params = emptyMap(),
    )

    data class QuestionsAsked(
        val gameTitle: String,
        val questionsAskedInSession: Int,
    ) : AnalyticsEvent(
        name = "question_asked",
        origin = AnalyticsEventOrigin.Chat,
        params = mapOf(
            AnalyticsEventParam.GameTitle to gameTitle,
            AnalyticsEventParam.QuestionsAsked to questionsAskedInSession.toString(),
        ),
    )

    data class OpenGameDetails(
        val gameTitle: String,
        override val origin: AnalyticsEventOrigin,
    ) : AnalyticsEvent(
        name = "open_game_details",
        origin = origin,
        params = mapOf(
            AnalyticsEventParam.GameTitle to gameTitle,
        ),
    )

    data class OpenChat(
        val gameTitle: String,
        override val origin: AnalyticsEventOrigin,
    ) : AnalyticsEvent(
        name = "open_chat",
        origin = origin,
        params = mapOf(
            AnalyticsEventParam.GameTitle to gameTitle,
        ),
    )

    data class SignUpWithEmailInputError(
        val error: String,
    ) : AnalyticsEvent(
        name = "sign_up_with_email_input_error",
        origin = AnalyticsEventOrigin.SignUp,
        params = mapOf(
            AnalyticsEventParam.SingUpInputError to error,
        ),
    )

    data class SignUpError(
        val error: String,
    ) : AnalyticsEvent(
        name = "sign_up_error",
        origin = AnalyticsEventOrigin.SignUp,
        params = mapOf(
            AnalyticsEventParam.SingUpError to error,
        ),
    )
}