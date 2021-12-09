package uz.gita.bank2.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.auth.ResendRequest
import uz.gita.bank2.data.request.auth.ResetRequest
import uz.gita.bank2.data.request.auth.VerifyRequest

interface VerifyViewModel {

    val openMainScreenFlow: Flow<Unit>
    val errorMessageFlow: Flow<String>
    val progressFlow: Flow<Boolean>
    val resendFlow: Flow<Unit>
    val stopCountDownFlow: Flow<Unit>
    val countDownFlow: Flow<Int>
    val backBtnFlow:Flow<Unit>

    fun countDown()
    fun verifyUser(request: VerifyRequest)
    fun resend(request: ResetRequest)
    fun backBtn()
}