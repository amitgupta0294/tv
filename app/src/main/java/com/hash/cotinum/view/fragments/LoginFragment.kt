package com.hash.cotinum.view.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.hash.cotinum.R
import com.hash.cotinum.constants.Constants
import com.hash.cotinum.model.login.LoginRequest
import com.hash.cotinum.utils.DialogHelper
import com.hash.cotinum.utils.Utils
import com.hash.cotinum.view.activities.HomeActivity
import com.hash.cotinum.view.activities.LoginActivity
import com.hash.cotinum.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.edit_password
import kotlinx.android.synthetic.main.fragment_login.edit_phone_email

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProviders.of(this.requireActivity()).get(LoginViewModel::class.java)
        dialog = DialogHelper.getProgressDialog(this.requireActivity())

        edit_phone_email.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus -> if (hasFocus) Utils.showKeyboard(activity as LoginActivity) }

        edit_password.setOnFocusChangeListener { v, hasFocus -> if (hasFocus) Utils.showKeyboard(activity as LoginActivity) }

        button_login.setOnClickListener {

            var email = edit_phone_email.text.toString().trim()
            var password = edit_password.text.toString().trim()

            if (email.isEmpty()) {
                Snackbar.make(constraint_root, resources.getString(R.string.string_empty_email), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Snackbar.make(constraint_root, resources.getString(R.string.string_empty_password), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            var loginRequest = LoginRequest()
            loginRequest.email = email
            loginRequest.password = password

            dialog.show()

            loginViewModel.login(loginRequest, this.requireContext()).observe(this.requireActivity(), Observer {

                dialog.dismiss()

                it?.let {
                    if (it.status.equals(Constants.STATUS_SUCCESS)) {
                        var intent = Intent(this.requireActivity(), HomeActivity::class.java)
                        startActivity(intent)
                        this.requireActivity().overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
                    } else {
                        Snackbar.make(constraint_root, it.data!!, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }

        text_forgot_password.setOnClickListener {
            val ft = activity?.supportFragmentManager?.beginTransaction()
            ft?.setCustomAnimations(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
            ft?.replace(R.id.layout_fragment_container, ForgotPasswordFragment(), "id1")?.addToBackStack(null)?.commit()
        }

        button_create_account.setOnClickListener {
            val ft = activity?.supportFragmentManager?.beginTransaction()
            ft?.setCustomAnimations(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
            ft?.replace(R.id.layout_fragment_container, RegistrationFragment(), "id1")?.addToBackStack(null)?.commit()
        }
    }
}