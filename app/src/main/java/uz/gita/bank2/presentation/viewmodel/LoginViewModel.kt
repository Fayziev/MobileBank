package uz.gita.bank2.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.auth.LoginRequest

interface LoginViewModel {

    val openVerifyScreenFlow: Flow<Unit>
    val progressFlow: Flow<Boolean>
    val errorMessageFlow: Flow<String>
    val openRegisterFlow: Flow<Unit>
    val backBtnFlow: Flow<Unit>
    fun loginUser(request: LoginRequest)
    fun openRegister()
    fun backBtn()
}