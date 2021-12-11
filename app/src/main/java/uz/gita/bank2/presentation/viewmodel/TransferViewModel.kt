package uz.gita.bank2.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.transfer.MoneyTransferRequest
import uz.gita.bank2.data.request.transfer.TransferFeeRequest

interface TransferViewModel {

    val errorMessageFlow: Flow<String>
    val progressFlow: Flow<Boolean>
    val backBtnFlow: Flow<Unit>
    val successFlow:Flow<Unit>

    fun sendMoney(card: MoneyTransferRequest)
    fun transferFee(data: TransferFeeRequest)
    fun history(pageNumber: Int, pageSize: Int)
}