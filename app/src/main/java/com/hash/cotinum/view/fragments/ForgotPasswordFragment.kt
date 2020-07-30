package com.hash.cotinum.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.hash.cotinum.R
import com.hash.cotinum.constants.Constants
import com.hash.cotinum.utils.DialogHelper
import com.hash.cotinum.viewmodel.SignupViewModel
import kotlinx.android.synthetic.main.fragment_forgot_password.*


class ForgotPasswordFragment : Fragment() {

    private lateinit var signupViewModel: SignupViewModel
    private lateinit var dialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signupViewModel = ViewModelProviders.of(this.requireActivity()).get(SignupViewModel::class.java)
        dialog = DialogHelper.getProgressDialog(this.requireActivity())

        button_send_email.setOnClickListener {

            if (edit_email.text.toString().trim().isEmpty()) {
                Snackbar.make(constraint_root, resources.getString(R.string.string_empty_email), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            dialog.show()

            signupViewModel.forgotPassword(edit_email.text.toString().trim(), this.requireContext()).observe(this.requireActivity(), Observer {

                dialog.dismiss()

                it.let {


                    if (it.status.equals(Constants.STATUS_SUCCESS, true)) {
                        showSuccessDialog(it.message!!)
                    } else {
                        Snackbar.make(constraint_root, it.data!!, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }

        text_back.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    fun showSuccessDialog(message : String) {

        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_message_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);


        val textMessage = dialog.findViewById<TextView>(R.id.text_message)
        textMessage.text = message

        val buttonOk: Button = dialog.findViewById<Button>(R.id.button_ok)
        buttonOk.setOnClickListener {
            dialog.cancel()
            activity?.supportFragmentManager?.popBackStack()
        }

        dialog.show()
    }
}