package com.ai.bardly.analytics

sealed class AnalyticsEventParam(val analyticKey: String) {
    data object GameTitle : AnalyticsEventParam("game_title")
    data object ScreenName : AnalyticsEventParam("screen_name")
    data object QuestionsAsked : AnalyticsEventParam("questions_asked")
    data object SingUpInputError : AnalyticsEventParam("sign_up_input_error")
    data object SingUpError : AnalyticsEventParam("sign_up_error")
}
