package uz.gita.bank2.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.transfer.MoneyTransferRequest
import uz.gita.bank2.data.request.transfer.TransferFeeRequest
import uz.gita.bank2.data.response.transfer.MoneyTransferResponse
import uz.gita.bank2.data.response.transfer.SendMoneyResponse
import uz.gita.bank2.data.response.transfer.TransferFeeResponse
import uz.gita.bank2.domain.repository.TransferRepository
import uz.gita.bank2.domain.usecase.TransferUseCase
import javax.inject.Inject

class TransferUseCaseImpl @Inject constructor(private var repository: TransferRepository) : TransferUseCase {

    override fun sendMoney(card: MoneyTransferRequest): Flow<Result<SendMoneyResponse>> = repository.sendMoney(card)
    override fun transferFee(data: TransferFeeRequest): Flow<Result<TransferFeeResponse>> = repository.transferFee(data)
    override fun history(pageNumber: Int, pageSize: Int): Flow<Result<List<MoneyTransferResponse.HistoryData>>> =
        repository.history(pageNumber, pageSize)

}