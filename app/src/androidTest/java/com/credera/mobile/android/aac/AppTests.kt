package com.credera.mobile.android.aac

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
        // Arrange
            val usernameField = withId(R.id.et_username)
            val passwordField = withId(R.id.et_password)
            val loginButton = withId(R.id.btn_login)

            val feedbackFormHeader = withText("Submit Feedback")
            val ratingField = withId(R.id.et_rating)
            val submitButton = withId(R.id.btn_submit)

            val successDialog = withText("Thanks for the feedback!")

        // Act
            // Enter Login Info
            onView(usernameField).perform(typeText("username"))
            onView(passwordField).perform(typeText("password1"))

            // Go to Feedback Form
            onView(loginButton).perform(click())

            // Assert we're on Feedback Screen
            onView(feedbackFormHeader).check(matches(isDisplayed()))

            // Set Rating
            onView(ratingField).perform(typeText("1"))

            // Try to submit form
            onView(submitButton).perform(click())

        // Assert
            // Verify successful submission
            onView(successDialog).check(matches(isDisplayed()))
    }

    @Test
    fun testSubmittingFeedbackForm_whenContactIsGranted() {
        // Arrange
            val usernameField = withId(R.id.et_username)
            val passwordField = withId(R.id.et_password)
            val loginButton = withId(R.id.btn_login)

            val feedbackFormHeader = withText("Submit Feedback")
            val ratingField = withId(R.id.et_rating)
            val allowContactCheckbox = withId(R.id.cb_allow_contact)
            val emailField = withId(R.id.et_email)
            val submitButton = withId(R.id.btn_submit)

            val errorDialog = withText("Please enter a valid email address")
            val successDialog = withText("Thanks for the feedback!")
            val closeButton = withText("Close")

        // Act
            // Enter Login Info
            onView(usernameField).perform(typeText("username"))
            onView(passwordField).perform(typeText("password1"))

            // Go to Feedback Form
            onView(loginButton).perform(click())

            // Assert we're on Feedback Screen
            onView(feedbackFormHeader).check(matches(isDisplayed()))

            // Set Rating
            onView(ratingField).perform(typeText("1"))

            // Allow Contact
            onView(allowContactCheckbox).perform(click())

            // Enter Invalid Email
            onView(emailField).perform(typeText("invalid email"))

            // Try to submit form
            onView(submitButton).perform(click())

        //Assert
            // Verify failed submission
            onView(errorDialog).check(matches(isDisplayed()))

        // Act
            // Close dialog to go back to feedback form
            onView(closeButton).perform(click())

            // Re-Enter Valid Email
            onView(emailField).perform(clearText(), typeText("valid@email.com"))

            // Try to submit form again
            onView(submitButton).perform(click())

        // Assert
            // Verify successful submission
            onView(successDialog).check(matches(isDisplayed()))
    }
}
