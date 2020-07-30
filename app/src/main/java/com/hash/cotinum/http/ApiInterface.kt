package com.hash.cotinum.http

import com.hash.cotinum.model.AddDeviceResponse
import com.hash.cotinum.model.ChannelItem
import com.hash.cotinum.model.forgotpassword.ForgotPasswordResponse
import com.hash.cotinum.model.login.LoginResponse
import com.hash.cotinum.model.plans.PlansResponse
import com.hash.cotinum.model.signup.SignupResponse
import com.hash.cotinum.model.signup.Tv
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    fun login(@Header("API-KEY") api : String, @Field("email") email : String, @Field("password") password : String) : Call<LoginResponse>

    @FormUrlEncoded
    @POST("signup")
    fun signup(@Header("API-KEY") api : String, @Field("email") email : String, @Field("password") password : String) : Call<SignupResponse>

    @FormUrlEncoded
    @POST("password_reset")
    fun forgotPassword(@Header("API-KEY") api : String, @Field("email") email : String) : Call<ForgotPasswordResponse>

    @FormUrlEncoded
    @POST("add_device")
    fun addDevice(@Header("API-KEY") api : String, @Field("name") name : String, @Field("serial") serial : String, @Field("user_id") user_id : String) :
            Call<AddDeviceResponse>

    @GET("all_package")
    fun getPlans(@Header("API-KEY") api : String) : Call<PlansResponse>


    @GET("xmltv.php?username=ours2020&password=61D15ACF9073740")
    fun getXml(): Call<Tv>
}