package com.hash.cotinum.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.hash.cotinum.R
import com.hash.cotinum.constants.Constants
import com.hash.cotinum.model.signup.SignupRequest
import com.hash.cotinum.utils.DialogHelper
import com.hash.cotinum.utils.Utils
import com.hash.cotinum.view.activities.LoginActivity
import com.hash.cotinum.viewmodel.SignupViewModel
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : Fragment() {

    private lateinit var signupViewModel: SignupViewModel
    private lateinit var dialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signupViewModel = ViewModelProviders.of(this.requireActivity()).get(SignupViewModel::class.java)
        dialog = DialogHelper.getProgressDialog(this.requireActivity())

        text_back.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        edit_name.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) Utils.showKeyboard(activity as LoginActivity)
            }
        })

        edit_phone_email.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) Utils.showKeyboard(activity as LoginActivity)
            }
        })

        edit_password.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) Utils.showKeyboard(activity as LoginActivity)
            }
        })

        edit_confirm_password.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) Utils.showKeyboard(activity as LoginActivity)
            }
        })

        button_signup.setOnClickListener {

            var name = edit_name.text.toString().trim()
            var email = edit_phone_email.text.toString().trim()
            var password = edit_password.text.toString().trim()
            var confirmPassword = edit_confirm_password.text.toString().trim()

            if (name.isEmpty()) {
                Snackbar.make(constraint_root, getString(R.string.string_name_empty), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                Snackbar.make(constraint_root, resources.getString(R.string.string_empty_email), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Snackbar.make(constraint_root, resources.getString(R.string.string_empty_password), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (!password.equals(confirmPassword, true)) {
                Snackbar.make(constraint_root, resources.getString(R.string.string_password_mismatch), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            var signupRequest = SignupRequest()
            signupRequest.name = name
            signupRequest.email = email
            signupRequest.password = password

            dialog.show()

            signupViewModel.signUp(signupRequest, this.requireContext()).observe(this, Observer {

                dialog.dismiss()

                it.let {
                    if (it?.status?.equals(Constants.STATUS_SUCCESS, false)!!) {
                        val ft = activity?.supportFragmentManager?.beginTransaction()
                        ft?.setCustomAnimations(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
                        ft?.replace(R.id.layout_fragment_container, DeviceDetailsFragment(), "id1")?.addToBackStack(null)?.commit()
                    } else {
                        Snackbar.make(constraint_root, it.data!!, Snackbar.LENGTH_LONG).show()
                    }
                }
            })

          }
    }
}