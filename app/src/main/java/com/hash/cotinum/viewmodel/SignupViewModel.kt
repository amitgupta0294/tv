package com.hash.cotinum.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hash.cotinum.model.AddDeviceResponse
import com.hash.cotinum.model.forgotpassword.ForgotPasswordResponse
import com.hash.cotinum.model.plans.PlansResponse
import com.hash.cotinum.model.signup.SignupRequest
import com.hash.cotinum.model.signup.SignupResponse
import com.hash.cotinum.repo.SignupRepository

class SignupViewModel : ViewModel() {

    var signupRepository : SignupRepository = SignupRepository.getSignUpRepoInstance()

    fun signUp(signupRequest: SignupRequest, context: Context) : MutableLiveData<SignupResponse> {
       return signupRepository.signup(signupRequest, context)
    }

    fun forgotPassword(email : String, context: Context) : MutableLiveData<ForgotPasswordResponse> {
        return signupRepository.forgotPassword(email, context)
    }

    fun addDevice(name : String, device : String, user_id : String, context: Context) : MutableLiveData<AddDeviceResponse> {
        return signupRepository.addDevice(name, device, user_id, context)
    }

    fun getPlans(context: Context) : MutableLiveData<PlansResponse> {
        return signupRepository.getPlans(context)
    }
}