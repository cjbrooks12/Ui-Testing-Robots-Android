package com.credera.mobile.android.ui

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.credera.mobile.android.R
import com.credera.mobile.android.app.BaseActivity
import kotlinx.android.synthetic.main.activity_feedback_form.*

class FeedbackFormActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_feedback_form)

        cb_allow_contact.setOnCheckedChangeListener { _, isChecked ->
            et_email_layout.visibility = when(isChecked) {
                true -> View.VISIBLE
                else -> View.GONE
            }
        }

        btn_submit.setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm() {
        val isValid = if(cb_allow_contact.isChecked) {
            // if allow contact is checked, ensure an email is entered and valid
            Patterns.EMAIL_ADDRESS.matcher(et_email.text.toString()).matches()
        } else {
            true
        }

        if(isValid) {
            // if valid, show success dialog
            AlertDialog.Builder(this)
                .setTitle("Thanks for the feedback!")
                .setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
        else {
            // else show invalid dialog
            AlertDialog.Builder(this)
                .setTitle("Please enter a valid email address")
                .setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }
}
