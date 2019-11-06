package com.credera.mobile.android.methods

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
        // Act
        enterLoginInfo("username", "password1")
        goToFeedbackForm()
        assertOnFeedbackScreen()
        setRating(1)
        submit()

        // Assert
        assertSubmittedSuccessfully()
    }

    @Test
    fun testSubmittingFeedbackForm_whenContactIsGranted() {
        // Act
        enterLoginInfo("username", "password1")
        goToFeedbackForm()
        assertOnFeedbackScreen()
        setRating(1)
        allowContact()
        enterEmail("invalid email")
        submit()

        // Assert
        assertDidNotSubmitSuccessfully()

        // Act
        closeDialog()
        enterEmail("valid@email.com")
        submit()

        // Assert
        assertSubmittedSuccessfully()
    }

    @Test(expected = Exception::class)
    fun testSubmittingFeedbackForm_whenContactIsGranted_methodsUsedOutOfScope() {
        // Act
        // enterLoginInfo("username", "password1")
        // goToFeedbackForm()
        // assertOnFeedbackScreen()
        setRating(1)
        allowContact()
        enterEmail("invalid email")
        submit()

        // Assert
        assertDidNotSubmitSuccessfully()

        // Act
        closeDialog()
        enterEmail("valid@email.com")
        submit()

        // Assert
        assertSubmittedSuccessfully()
    }
}

// Arrange

fun enterLoginInfo(username: String, password: String) {
    onView(withId(R.id.et_username)).perform(typeText(username))
    onView(withId(R.id.et_password)).perform(typeText(password))
}

fun goToFeedbackForm() {
    onView(withId(R.id.btn_login)).perform(click())
}

fun setRating(value: Int) {
    onView(withId(R.id.et_rating)).perform(typeText(value.toString()))
}

fun allowContact() {
    onView(withId(R.id.cb_allow_contact)).perform(click())
}

fun enterEmail(email: String) {
    onView(withId(R.id.et_email)).perform(clearText(), typeText(email))
}

fun submit() {
    onView(withId(R.id.btn_submit)).perform(click())
}

fun assertOnFeedbackScreen() {
    onView(withText("Submit Feedback")).check(matches(isDisplayed()))
}

fun closeDialog() {
    onView(withText("Close")).perform(click())
}

fun assertSubmittedSuccessfully() {
    onView(withText("Thanks for the feedback!")).check(matches(isDisplayed()))
}

fun assertDidNotSubmitSuccessfully() {
    onView(withText("Please enter a valid email address")).check(matches(isDisplayed()))
}
