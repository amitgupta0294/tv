package com.hash.cotinum.utils

import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.view.Window
import com.hash.cotinum.R

class DialogHelper {

    companion object {

        fun getProgressDialog(activity: Activity): Dialog {

            val progressDialog = Dialog(activity)
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialog.setContentView(R.layout.dialog_progress)

            progressDialog.window!!.setLayout(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

            progressDialog.setCanceledOnTouchOutside(false)

            return progressDialog
        }
    }
}