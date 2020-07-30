package com.hash.cotinum.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.hash.cotinum.R
import com.hash.cotinum.constants.Constants
import com.hash.cotinum.http.ApiInterface
import com.hash.cotinum.http.NoConnectivityException
import com.hash.cotinum.http.RetrofitService
import com.hash.cotinum.model.AddDeviceResponse
import com.hash.cotinum.model.forgotpassword.ForgotPasswordResponse
import com.hash.cotinum.model.plans.PlansResponse
import com.hash.cotinum.model.signup.SignupRequest
import com.hash.cotinum.model.signup.SignupResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupRepository {

    private var apiInterface = RetrofitService.createService(ApiInterface::class.java)

    companion object {
        private var repository: SignupRepository = SignupRepository()

        fun getSignUpRepoInstance(): SignupRepository {
            return repository
        }
    }

    fun signup(signupRequest: SignupRequest, context : Context) : MutableLiveData<SignupResponse> {

        var signupResponse = MutableLiveData<SignupResponse>()

        apiInterface.signup(Constants.API_KEY, signupRequest.email!!, signupRequest.password!!).enqueue(object : Callback<SignupResponse> {

            var signupResp = SignupResponse()

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                if (t is NoConnectivityException) {
                    signupResp.status = Constants.STATUS_FAILURE
                    signupResp.data = context.resources.getString(R.string.string_no_internet_connection)

                    signupResponse.postValue(signupResp)
                }
            }

            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                signupResp = response.body()!!
                signupResponse.postValue(signupResp)
            }
        })

        return signupResponse
    }

    fun forgotPassword(email: String, context : Context) : MutableLiveData<ForgotPasswordResponse> {

        var forgotPasswordResponse = MutableLiveData<ForgotPasswordResponse>()

        apiInterface.forgotPassword(Constants.API_KEY, email).enqueue(object : Callback<ForgotPasswordResponse> {

            var forgotPasswordResp = ForgotPasswordResponse()

            override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                if (t is NoConnectivityException) {
                    forgotPasswordResp.status = Constants.STATUS_FAILURE
                    forgotPasswordResp.data = context.resources.getString(R.string.string_no_internet_connection)

                    forgotPasswordResponse.postValue(forgotPasswordResp)
                }
            }

            override fun onResponse(call: Call<ForgotPasswordResponse>, response: Response<ForgotPasswordResponse>) {
                forgotPasswordResp = response.body()!!
                forgotPasswordResponse.postValue(forgotPasswordResp)
            }
        })

        return forgotPasswordResponse
    }

    fun addDevice(name: String, device : String, user_id : String, context : Context) : MutableLiveData<AddDeviceResponse> {

        var addDeviceResp = MutableLiveData<AddDeviceResponse>()

        apiInterface.addDevice(Constants.API_KEY, name, device, user_id).enqueue(object : Callback<AddDeviceResponse> {

            var addDeviceResponse = AddDeviceResponse()

            override fun onFailure(call: Call<AddDeviceResponse>, t: Throwable) {
                if (t is NoConnectivityException) {
                    addDeviceResponse.status = Constants.STATUS_FAILURE
                    addDeviceResponse.data = context.resources.getString(R.string.string_no_internet_connection)

                    addDeviceResp.postValue(addDeviceResponse)
                }
            }

            override fun onResponse(call: Call<AddDeviceResponse>, response: Response<AddDeviceResponse>) {
                addDeviceResponse = response.body()!!
                addDeviceResp.postValue(addDeviceResponse)
            }
        })

        return addDeviceResp
    }

    fun getPlans(context : Context) : MutableLiveData<PlansResponse> {

        var plansResp = MutableLiveData<PlansResponse>()

        apiInterface.getPlans(Constants.API_KEY).enqueue(object : Callback<PlansResponse> {

            var plansResponse = PlansResponse()

            override fun onFailure(call: Call<PlansResponse>, t: Throwable) {
                if (t is NoConnectivityException) {
                    plansResponse.status = Constants.STATUS_FAILURE
                    plansResponse.data = context.resources.getString(R.string.string_no_internet_connection)

                    plansResp.postValue(plansResponse)
                }
            }

            override fun onResponse(call: Call<PlansResponse>, response: Response<PlansResponse>) {
                plansResponse = response.body()!!
                plansResp.postValue(plansResponse)
            }
        })

        return plansResp
    }
}