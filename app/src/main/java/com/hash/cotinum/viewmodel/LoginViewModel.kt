package com.hash.cotinum.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hash.cotinum.model.login.LoginRequest
import com.hash.cotinum.model.login.LoginResponse
import com.hash.cotinum.repo.LoginRepo

class LoginViewModel : ViewModel() {

    private var loginRepo = LoginRepo.getLoginRepoInstance()

    fun login(loginRequest: LoginRequest, context: Context) : MutableLiveData<LoginResponse> {
        return loginRepo.login(loginRequest, context)
    }
}