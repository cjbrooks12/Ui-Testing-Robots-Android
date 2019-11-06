package com.credera.mobile.android.crossplatformrobot

import com.credera.mobile.android.interfacerobot.AppRobot
import com.credera.mobile.android.interfacerobot.ServiceLocator
import com.credera.mobile.android.interfacerobot.resolveRobot

object TestScripts {

    fun testSubmittingFeedbackForm_whenContactIsNotGranted(serviceLocator: ServiceLocator) {
        serviceLocator.resolveRobot<AppRobot>()
            .enterLoginInfo("username", "password1")
            .goToFeedbackForm {
                assertOnFeedbackScreen()
            }
            .setRating(1)
            .submit {
                assertSubmittedSuccessfully()
            }
    }

    fun testSubmittingFeedbackForm_whenContactIsGranted(serviceLocator: ServiceLocator) {
        serviceLocator.resolveRobot<AppRobot>()
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

