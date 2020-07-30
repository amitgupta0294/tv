package com.hash.cotinum.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.hash.cotinum.R
import com.hash.cotinum.constants.Constants
import com.hash.cotinum.http.ApiInterface
import com.hash.cotinum.http.NoConnectivityException
import com.hash.cotinum.http.RetrofitService
import com.hash.cotinum.model.login.LoginRequest
import com.hash.cotinum.model.login.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepo {

    private var apiInterface = RetrofitService.createService(ApiInterface::class.java)

    companion object {
        private var repository: LoginRepo = LoginRepo()

        fun getLoginRepoInstance(): LoginRepo {
            return repository
        }
    }

    fun login(loginRequest: LoginRequest, context: Context) : MutableLiveData<LoginResponse> {

        var loginResponse = MutableLiveData<LoginResponse>()

        apiInterface.login(Constants.API_KEY, loginRequest.email!!, loginRequest.password!!).enqueue(object : Callback<LoginResponse> {

            var loginResp = LoginResponse()

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                if (t is NoConnectivityException) {
                    loginResp.status = Constants.STATUS_FAILURE
                    loginResp.data = context.resources.getString(R.string.string_no_internet_connection)

                    loginResponse.postValue(loginResp)
                }
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    loginResp = response.body()!!
                    loginResponse.postValue(loginResp)
            }
        })

        return loginResponse
    }
}