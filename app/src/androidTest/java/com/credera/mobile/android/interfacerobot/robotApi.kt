package com.credera.mobile.android.interfacerobot

interface AppRobot {
    fun enterLoginInfo(username: String, password: String, assertions: AppRobot.() -> Unit = {}): AppRobot

    fun goToFeedbackForm(assertions: FeedbackFormRobot.() -> Unit = {}): FeedbackFormRobot
}

interface FeedbackFormRobot {

    fun setRating(value: Int, assertions: FeedbackFormRobot.() -> Unit = {}): FeedbackFormRobot
    fun allowContact(assertions: FeedbackFormRobot.() -> Unit = {}): FeedbackFormRobot
    fun enterEmail(email: String, assertions: FeedbackFormRobot.() -> Unit = {}): FeedbackFormRobot
    fun <T> submit(assertions: FeedbackConfirmationRobot.() -> T): T

    fun assertOnFeedbackScreen()
}

interface FeedbackConfirmationRobot {
    fun closeDialog(): FeedbackFormRobot

    fun assertSubmittedSuccessfully()
    fun assertDidNotSubmitSuccessfully()
}
