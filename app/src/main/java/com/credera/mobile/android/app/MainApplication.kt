package com.credera.mobile.android.app

import android.app.Application
import com.caseyjbrooks.androidclog.AndroidClog
import com.credera.mobile.android.BuildConfig
import com.credera.mobile.android.ui.koinUiModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * The MainApplication sets up the core components of our app and registers all one-time initialization. This involves
 * creating the Dagger AppComponent, and performing all static initialization required by our libraries.
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidClog.init(this, BuildConfig.DEBUG)
        AndroidThreeTen.init(this)

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            androidFileProperties("app.properties")
            modules(
                listOf(
                    koinAppModule(),
                    koinUiModule()
                )
            )
        }
    }
}
