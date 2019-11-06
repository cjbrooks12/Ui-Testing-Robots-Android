package com.credera.mobile.android.simplerobot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.credera.mobile.android.R

class AppRobot {

    fun enterLoginInfo(username: String, password: String, assertions: AppRobot.() -> Unit = {}): AppRobot {
        onView(withId(R.id.et_username)).perform(typeText(username))
        onView(withId(R.id.et_password)).perform(typeText(password))

        return this.apply(assertions)
    }

    fun goToFeedbackForm(assertions: FeedbackFormRobot.() -> Unit = {}): FeedbackFormRobot {
        onView(withId(R.id.btn_login)).perform(click())

        return FeedbackFormRobot().apply(assertions)
    }
}

class FeedbackFormRobot {

    fun setRating(value: Int, assertions: FeedbackFormRobot.() -> Unit = {}): FeedbackFormRobot {
        onView(withId(R.id.et_rating)).perform(typeText(value.toString()))

        return FeedbackFormRobot().apply(assertions)
    }

    fun allowContact(assertions: FeedbackFormRobot.() -> Unit = {}): FeedbackFormRobot {
        onView(withId(R.id.cb_allow_contact)).perform(click())

        return FeedbackFormRobot().apply(assertions)
    }

    fun enterEmail(email: String, assertions: FeedbackFormRobot.() -> Unit = {}): FeedbackFormRobot {
        onView(withId(R.id.et_email)).perform(clearText(), typeText(email))

        return FeedbackFormRobot().apply(assertions)
    }

    fun <T> submit(assertions: FeedbackConfirmationRobot.() -> T): T {
        onView(withId(R.id.btn_submit)).perform(click())

        return FeedbackConfirmationRobot().let(assertions)
    }

    fun assertOnFeedbackScreen() {
        onView(withText("Submit Feedback")).check(matches(isDisplayed()))
    }

}

class FeedbackConfirmationRobot {

    fun closeDialog(): FeedbackFormRobot {
        onView(withText("Close")).perform(click())

        return FeedbackFormRobot()
    }

    fun assertSubmittedSuccessfully() {
        onView(withText("Thanks for the feedback!")).check(matches(isDisplayed()))
    }

    fun assertDidNotSubmitSuccessfully() {
        onView(withText("Please enter a valid email address")).check(matches(isDisplayed()))
    }

}

