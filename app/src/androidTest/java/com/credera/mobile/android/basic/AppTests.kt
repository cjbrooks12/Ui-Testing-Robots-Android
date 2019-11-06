package com.credera.mobile.android.basic

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.credera.mobile.android.DisableAnimationsRule
import com.credera.mobile.android.R
import com.credera.mobile.android.ui.MainActivity
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppTests {

    companion object {
        @ClassRule
        @JvmField
        val animationsRule = DisableAnimationsRule()
    }

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(
        MainActivity::class.java,
        false,
        false
    )

    @Before
    fun before() {
        mActivityRule.launchActivity(Intent())
    }


    @Test
    fun testSubmittingFeedbackForm_whenContactIsNotGranted() {
        onView(withId(R.id.et_username_on_login_screen)).perform(typeText("username"))
        onView(withId(R.id.et_password)).perform(typeText("password1"))
        onView(withId(R.id.btn_login)).perform(click())

        onView(withText("Submit Feedback")).check(matches(isDisplayed()))

        onView(withId(R.id.et_rating)).perform(typeText("1"))
        onView(withId(R.id.btn_submit)).perform(click())

        onView(withText("Thanks for the feedback!")).check(matches(isDisplayed()))
    }

    @Test
    fun testSubmittingFeedbackForm_whenContactIsGranted() {
        onView(withId(R.id.et_username_on_login_screen)).perform(typeText("username"))
        onView(withId(R.id.et_password)).perform(typeText("password1"))
        onView(withId(R.id.btn_login)).perform(click())

        onView(withText("Submit Feedback")).check(matches(isDisplayed()))

        onView(withId(R.id.et_rating)).perform(typeText("1"))
        onView(withId(R.id.cb_allow_contact)).perform(click())
        onView(withId(R.id.et_email)).perform(typeText("invalid email"))
        onView(withId(R.id.btn_submit)).perform(click())

        onView(withText("Please enter a valid email address")).check(matches(isDisplayed()))
        onView(withText("Close")).perform(click())

        onView(withId(R.id.et_email)).perform(clearText(), typeText("valid@email.com"))
        onView(withId(R.id.btn_submit)).perform(click())
        onView(withText("Thanks for the feedback!")).check(matches(isDisplayed()))
    }
}
