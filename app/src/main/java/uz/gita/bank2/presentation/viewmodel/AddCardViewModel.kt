package uz.gita.bank2.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.card.AddCardRequest
import uz.gita.bank2.data.request.card.VerifyCardRequest

interface AddCardViewModel {

    val progressFlow: Flow<Boolean>
    val errorMessageFlow: Flow<String>
    val successFlow: Flow<Unit>
    val backBtnFlow:Flow<Unit>
    fun cardAdd(request:AddCardRequest)
    fun verifyCard(request:VerifyCardRequest)
    fun backBtn()
}