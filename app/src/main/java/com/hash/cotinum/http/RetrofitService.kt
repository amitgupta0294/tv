package com.hash.cotinum.http

import com.buggyz.buggyzdriver.http.HeaderIntercepter
import com.buggyz.buggyzdriver.http.NetworkConnectionInterceptor
import com.google.gson.GsonBuilder
import com.hash.cotinum.MyApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitService {

    val BASE_URL = "http://development.testcotinum.tk/rest-api/v100/"
 //   private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    var gson = GsonBuilder().setLenient().create()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        .readTimeout(2, TimeUnit.MINUTES)
     //   .addInterceptor(interceptor)
        .addInterceptor(NetworkConnectionInterceptor(MyApplication.appContext))
        .build()

    private val retrofit = Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    private val retrofitXML = Builder()
        .baseUrl("http://tvservice.pro/")
        .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(Persister(AnnotationStrategy())))
        .client(okHttpClient)
        .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

    fun <S> createXMLService(serviceClass: Class<S>): S {
        return retrofitXML.create(serviceClass)
    }
}