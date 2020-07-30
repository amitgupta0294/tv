package com.buggyz.buggyzdriver.http

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderIntercepter : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        // Request customization: add request headers

        val requestBuilder: Request.Builder = original.newBuilder()
        requestBuilder.header("API-KEY", "56cef042950d0a3" ) // <-- this is the important line

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}