package com.credera.mobile.android.interfacerobot

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.credera.mobile.android.DisableAnimationsRule
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
        resolveRobot<AppRobot>()
            .enterLoginInfo("username", "password1")
            .goToFeedbackForm {
                assertOnFeedbackScreen()
            }
            .setRating(1)
            .submit {
                assertSubmittedSuccessfully()
            }
    }

    @Test
    fun testSubmittingFeedbackForm_whenContactIsGranted() {
        resolveRobot<AppRobot>()
            .enterLoginInfo("username", "password1")
            .goToFeedbackForm {
                assertOnFeedbackScreen()
            }
            .setRating(1)
            .allowContact()
            .enterEmail("invalid email")
            .submit {
                assertDidNotSubmitSuccessfully()
                closeDialog()
            }
            .enterEmail("valid@email.com")
            .submit {
                assertSubmittedSuccessfully()
            }
    }
}
