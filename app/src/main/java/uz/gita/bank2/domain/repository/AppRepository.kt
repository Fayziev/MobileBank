package uz.gita.bank2.domain.repository

import uz.gita.bank2.data.models.StartScreenEnum

interface AppRepository {
    fun startScreen(): StartScreenEnum

    fun setStartScreen(screenEnum: StartScreenEnum)

    fun getToken(): String

    fun pinLock(pin: String)

    fun getPinLock(): String

    fun getStartPinScreen(): Int

    fun setPinStatus(pinStatus:Int)

}