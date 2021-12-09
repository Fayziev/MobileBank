package uz.gita.bank2.domain.repository.impl

import uz.gita.bank2.data.models.StartScreenEnum
import uz.gita.bank2.data.pref.MyPref
import uz.gita.bank2.domain.repository.AppRepository
import uz.gita.bank2.utils.timber
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private val pref: MyPref) : AppRepository {

    override fun startScreen(): StartScreenEnum = pref.startScreen

    override fun setStartScreen(screenEnum: StartScreenEnum) {
        pref.startScreen = screenEnum
    }

    override fun getToken(): String = pref.accessToken

    override fun pinLock(pin: String) {
        pref.pinCode = pin
        timber(pin,"EEE")
    }

    override fun getPinLock(): String = pref.pinCode

    override fun getStartPinScreen(): Int = pref.pinStartScreen

    override fun setPinStatus(pinStatus: Int) {
        pref.pinStartScreen = pinStatus
    }
}