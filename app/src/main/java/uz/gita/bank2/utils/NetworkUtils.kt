package uz.gita.bank2.utils

import android.content.Context
import com.google.gson.Gson
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import timber.log.Timber
import uz.gita.bank2.BuildConfig
import uz.gita.bank2.BuildConfig.BASE_URL
import uz.gita.bank2.data.pref.MyPref
import uz.gita.bank2.data.response.TokenResponse


fun OkHttpClient.Builder.addLoggingInterceptor(context: Context): OkHttpClient.Builder {
    HttpLoggingInterceptor.Level.HEADERS
    val logging = HttpLoggingInterceptor.Logger { message -> Timber.tag("HTTP").d(message) }
    if (BuildConfig.LOGGING) {
        addInterceptor(ChuckInterceptor(context))

            .addInterceptor(HttpLoggingInterceptor(logging))
    }
    return this
}

fun addHeaderInterceptor(pref: MyPref) = Interceptor { chain ->
    val request = chain.request()

    val newRequest = request.newBuilder().removeHeader("token").addHeader("token", pref.accessToken).build()
    val response = chain.proceed(newRequest)
    response
}

fun refreshInterceptor(pref: MyPref) = Interceptor { chain ->
    val request = chain.request()
    val response = chain.proceed(request)
    if (response.code == 401) {
        response.close()
        val data = JSONObject()
        data.put("phone", pref.phoneNumber)
        val body =
            data.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val requestRefresh = request.newBuilder()
            .addHeader("refresh_token", pref.refreshToken)
            .method("POST", body)
            .url("${BASE_URL}/api/v1/auth/refresh")
            .build()

        val responseRefresh = chain.proceed(requestRefresh)
        myLog(responseRefresh.message)
        if (responseRefresh.code == 401) {
            myLog("login navigate")
            return@Interceptor responseRefresh
        }

        if (responseRefresh.code == 200) {
            responseRefresh.body.let {
                val data = Gson().fromJson(it?.string(), TokenResponse::class.java)
                pref.accessToken = data.data.accessToken
                pref.refreshToken = data.data.refreshToken
            }
            responseRefresh.close()
            val requestTwo = request.newBuilder()
                .removeHeader("token")
                .addHeader("token", pref.accessToken)
                .build()
            chain.proceed(requestTwo)
        }
    }
    response
}