package uz.gita.bank2.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.auth.RegisterRequest

interface RegisterViewModel {
    val openVerifyScreenFlow: Flow<Unit>
    val errorMessageFlow: Flow<String>
    val progressFlow:Flow<Boolean>
    val backBtnFlow:Flow<Unit>
    fun registerUser(data: RegisterRequest)
    fun backBtn()
}