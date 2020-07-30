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
import com.hash.cotinum.utils.DialogHelper
import com.hash.cotinum.view.activities.HomeActivity
import com.hash.cotinum.view.activities.LoginActivity
import com.hash.cotinum.viewmodel.SignupViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.text_back
import kotlinx.android.synthetic.main.fragment_subscription.*

class SubscriptionFragment : Fragment() {

    private lateinit var signupViewModel: SignupViewModel
    private lateinit var dialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscription, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signupViewModel = ViewModelProviders.of(this.requireActivity()).get(SignupViewModel::class.java)
        dialog = DialogHelper.getProgressDialog(this.requireActivity())

        text_back.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()

            (activity as LoginActivity).text_choose_subscription.visibility = View.GONE
            (activity as LoginActivity).image_contract.visibility = View.GONE
        }

        dialog.show()

        signupViewModel.getPlans(this.requireContext()).observe(this.requireActivity(), Observer {

            dialog.dismiss()

            it?.let {

            }
        })

        linear_subscribe_12.setOnClickListener {
            startActivity(Intent(this.activity, HomeActivity::class.java))
            activity?.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
        }
    }
}