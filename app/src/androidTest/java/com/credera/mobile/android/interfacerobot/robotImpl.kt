package com.credera.mobile.android.interfacerobot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.credera.mobile.android.R

class AppRobotImpl(val serviceLocator: ServiceLocator) : AppRobot {

    override fun enterLoginInfo(username: String, password: String, assertions: AppRobot.() -> Unit): AppRobot {
        onView(withId(R.id.et_username_on_login_screen)).perform(typeText(username))
        onView(withId(R.id.et_password)).perform(typeText(password))

        return serviceLocator.resolveRobot(assertions)
    }

    override fun goToFeedbackForm(assertions: FeedbackFormRobot.() -> Unit): FeedbackFormRobot {
        onView(withId(R.id.btn_login)).perform(click())

        return serviceLocator.resolveRobot(assertions)
    }
}

class FeedbackFormRobotImpl(val serviceLocator: ServiceLocator) : FeedbackFormRobot {

    override fun setRating(value: Int, assertions: FeedbackFormRobot.() -> Unit): FeedbackFormRobot {
        onView(withId(R.id.et_rating)).perform(typeText(value.toString()))

        return serviceLocator.resolveRobot(assertions)
    }

    override fun allowContact(assertions: FeedbackFormRobot.() -> Unit): FeedbackFormRobot {
        onView(withId(R.id.cb_allow_contact)).perform(click())

        return serviceLocator.resolveRobot(assertions)
    }

    override fun enterEmail(email: String, assertions: FeedbackFormRobot.() -> Unit): FeedbackFormRobot {
        onView(withId(R.id.et_email)).perform(clearText(),  typeText(email))

        return serviceLocator.resolveRobot(assertions)
    }

    override fun <T> submit(assertions: FeedbackConfirmationRobot.() -> T): T {
        onView(withId(R.id.btn_submit)).perform(click())

        return serviceLocator.resolveRobot<FeedbackConfirmationRobot>().assertions()
    }

    override fun assertOnFeedbackScreen() {
        onView(withText("Submit Feedback")).check(matches(isDisplayed()))
    }

}

class FeedbackConfirmationRobotImpl(val serviceLocator: ServiceLocator) : FeedbackConfirmationRobot {

    override fun closeDialog(): FeedbackFormRobot {
        onView(withText("Close")).perform(click())

        return serviceLocator.resolveRobot()
    }

    override fun assertSubmittedSuccessfully() {
        onView(withText("Thanks for the feedback!")).check(matches(isDisplayed()))
    }

    override fun assertDidNotSubmitSuccessfully() {
        onView(withText("Please enter a valid email address")).check(matches(isDisplayed()))
    }

}

