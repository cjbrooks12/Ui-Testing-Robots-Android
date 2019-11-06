package com.credera.mobile.android.ui

import android.content.Intent
import android.os.Bundle
import com.credera.mobile.android.R
import com.credera.mobile.android.app.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener {
            startActivity(Intent(this@MainActivity, FeedbackFormActivity::class.java))
        }
    }
}
