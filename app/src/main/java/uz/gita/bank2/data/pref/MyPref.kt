package uz.gita.bank2.data.pref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import uz.gita.bank2.data.models.StartScreenEnum
import uz.gita.bank2.data.models.getStartScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPref @Inject constructor(private val context: Context) {


    companion object {
        private const val ACCESS_TOKEN = "accessToken"
        private const val REFRESH_TOKEN = "refreshToken"
        private const val START_SCREEN = "screen"
    }

    private val pref = context.getSharedPreferences("cache", MODE_PRIVATE)

    var token: String
        set(value) {
            pref.edit().putString("token", value).apply()
        }
        get() = pref.getString("token", "")!!

    var pinCode: String
        set(value) {
            pref.edit().putString("pinCode", value).apply()
        }
        get() = pref.getString("pinCode", "")!!
    var pinStartScreen: Int
        set(value) {
            pref.edit().putInt("pinScreen", value).apply()
        }
        get() = pref.getInt("pinScreen", 0)

    var startScreen: StartScreenEnum
        set(value) = pref.edit().putString(START_SCREEN, value.name).apply()
        get() = pref.getString(START_SCREEN, StartScreenEnum.LOGIN.name)!!.getStartScreen()

    var accessToken: String
        get() = pref.getString(ACCESS_TOKEN, "")!!
        set(token) = pref.edit().putString(ACCESS_TOKEN, token).apply()

    var refreshToken: String
        get() = pref.getString(REFRESH_TOKEN, "")!!
        set(token) = pref.edit().putString(REFRESH_TOKEN, token).apply()

    var firstName: String
        set(value) = pref.edit().putString("firstName", value).apply()
        get() = pref.getString("firstName", "")!!

    var lastName: String
        set(value) = pref.edit().putString("lastName", value).apply()
        get() = pref.getString("lastName", "")!!

    var phoneNumber: String
        set(value) = pref.edit().putString("phoneNumber", value).apply()
        get() = pref.getString("phoneNumber", "")!!

    var password: String
        set(value) = pref.edit().putString("password", value).apply()
        get() = pref.getString("password", "")!!


    var smsCode: String
        set(value) = pref.edit().putString("smsCode", value).apply()
        get() = pref.getString("smsCode", "")!!

    var pan: String
        set(value) = pref.edit().putString("pan", value).apply()
        get() = pref.getString("pan", "")!!

    var controlRegister: Boolean
        get() = pref.getBoolean("controlRegister", false)
        set(value) = pref.edit().putBoolean("controlRegister", value).apply()


}