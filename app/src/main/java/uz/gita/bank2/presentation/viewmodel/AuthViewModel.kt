package uz.gita.bank2.presentation.viewmodel

import kotlinx.coroutines.flow.Flow

interface AuthViewModel {
    val openLoginFlow: Flow<Unit>
    val openRegisterFlow: Flow<Unit>
    fun openLogin()
    fun openRegister()
}