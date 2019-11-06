package com.credera.mobile.android.crossplatformrobot

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
class AndroidAppTests {

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

    private val androidServiceLocator = AndroidServiceLocator()

    @Test
    fun testSubmittingFeedbackForm_whenContactIsNotGranted_onAndroid() {
        TestScripts.testSubmittingFeedbackForm_whenContactIsNotGranted(androidServiceLocator)
    }

    @Test
    fun testSubmittingFeedbackForm_whenContactIsGranted_onAndroid() {
        TestScripts.testSubmittingFeedbackForm_whenContactIsGranted(androidServiceLocator)
    }
}
