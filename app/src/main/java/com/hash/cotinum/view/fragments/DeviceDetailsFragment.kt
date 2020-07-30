package com.hash.cotinum.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.hash.cotinum.R
import com.hash.cotinum.constants.Constants
import com.hash.cotinum.utils.DialogHelper
import com.hash.cotinum.utils.Utils
import com.hash.cotinum.view.activities.LoginActivity
import com.hash.cotinum.viewmodel.SignupViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_device_details.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.text_back


class DeviceDetailsFragment() : Fragment() {

    private lateinit var signupViewModel: SignupViewModel
    private lateinit var dialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_device_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signupViewModel = ViewModelProviders.of(this.requireActivity()).get(SignupViewModel::class.java)
        dialog = DialogHelper.getProgressDialog(this.requireActivity())

        edit_device_name.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) Utils.showKeyboard(activity as LoginActivity)
            }
        })

        text_back.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        button_save.setOnClickListener {

            var deviceName = edit_device_name.text.toString().trim()

            if (deviceName.isEmpty()) {
                Snackbar.make(constraint_root, getString(R.string.string_device_name_empty), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            dialog.show()

            signupViewModel.addDevice(deviceName, "1", "1", this.requireContext()).observe(this, Observer {

                dialog.dismiss()

                it.let {
                    if (it?.status?.equals(Constants.STATUS_SUCCESS, false)!!) {

                        val ft = activity?.supportFragmentManager?.beginTransaction()
                        ft?.setCustomAnimations(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
                        ft?.replace(R.id.layout_fragment_container, SubscriptionFragment(), "id1")?.addToBackStack(null)?.commit()

                        (activity as LoginActivity).text_choose_subscription.visibility = View.VISIBLE
                        (activity as LoginActivity).image_contract.visibility = View.VISIBLE

                    } else {
                        Snackbar.make(constraint_root, it.data!!, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }
    }
}