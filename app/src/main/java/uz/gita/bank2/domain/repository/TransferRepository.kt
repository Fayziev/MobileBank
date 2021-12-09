package uz.gita.bank2.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.transfer.MoneyTransferRequest
import uz.gita.bank2.data.request.transfer.TransferFeeRequest
import uz.gita.bank2.data.response.transfer.MoneyTransferResponse
import uz.gita.bank2.data.response.transfer.SendMoneyResponse
import uz.gita.bank2.data.response.transfer.TransferFeeResponse

interface TransferRepository {
    fun sendMoney(data: MoneyTransferRequest): Flow<Result<SendMoneyResponse>>
    fun transferFee(data: TransferFeeRequest): Flow<Result<TransferFeeResponse>>
    fun history(pageNumber: Int, pageSize: Int): Flow<Result<List<MoneyTransferResponse.HistoryData>>>
}