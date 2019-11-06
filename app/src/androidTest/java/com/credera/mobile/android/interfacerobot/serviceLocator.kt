package com.credera.mobile.android.interfacerobot

interface ServiceLocator {
    val appRobot: AppRobot
    val feedbackFormRobot: FeedbackFormRobot
    val feedbackConfirmationRobot: FeedbackConfirmationRobot
}

class ServiceLocatorImpl : ServiceLocator {
    override val appRobot: AppRobot = AppRobotImpl(this)
    override val feedbackFormRobot: FeedbackFormRobot = FeedbackFormRobotImpl(this)
    override val feedbackConfirmationRobot: FeedbackConfirmationRobot = FeedbackConfirmationRobotImpl(this)
}

inline fun <reified T : Any> ServiceLocator.resolveRobot(assertions: T.()->Unit = {}): T {
    return when (T::class) {
        AppRobot::class -> appRobot as T
        FeedbackFormRobot::class -> feedbackFormRobot as T
        FeedbackConfirmationRobot::class -> feedbackConfirmationRobot as T
        else -> throw IllegalArgumentException()
    }.apply(assertions)
}


inline fun <reified T : Any> resolveRobot(assertions: T.()->Unit = {}): T {
    return ServiceLocatorImpl().resolveRobot(assertions)
}
