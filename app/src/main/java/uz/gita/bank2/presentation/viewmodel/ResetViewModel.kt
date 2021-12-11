package uz.gita.bank2.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.auth.ResetRequest

interface ResetViewModel {

    val progressFlow: Flow<Boolean>
    val errorMessageFlow: Flow<String>
    val successFlow: Flow<Unit>
    fun resetUser(request: ResetRequest)

}