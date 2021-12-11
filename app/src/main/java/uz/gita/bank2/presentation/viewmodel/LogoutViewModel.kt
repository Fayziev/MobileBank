package uz.gita.bank2.presentation.viewmodel

import kotlinx.coroutines.flow.Flow

interface LogoutViewModel {

    val errorMessageFlow: Flow<String>
    val successFlow: Flow<Unit>
    fun logoutUser()

}