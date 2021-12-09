package uz.gita.bank2.presentation.viewmodel

import kotlinx.coroutines.flow.Flow

interface PinLockViewModel {
    val openMainFlow: Flow<Unit>
    val openEnterPinFlow: Flow<Unit>
    val pinCodeFlow: Flow<String>
    val pinStatusFlow:Flow<Int>
    fun startMainScreen()
    fun enterScreen()
    fun setStatusPint(pinStatus: Int)
    fun setPinCode(pinCode: String)
    fun getPin()
}