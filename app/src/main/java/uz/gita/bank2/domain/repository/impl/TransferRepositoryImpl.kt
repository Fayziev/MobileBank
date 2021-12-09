package uz.gita.bank2.domain.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.bank2.data.request.transfer.MoneyTransferRequest
import uz.gita.bank2.data.request.transfer.TransferFeeRequest
import uz.gita.bank2.data.response.transfer.MoneyTransferResponse
import uz.gita.bank2.data.response.transfer.SendMoneyResponse
import uz.gita.bank2.data.response.transfer.TransferFeeResponse
import uz.gita.bank2.domain.repository.TransferRepository
import uz.gita.bank2.data.retrofit.api.MoneyTransferApi
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
    private val api: MoneyTransferApi
) : TransferRepository {

    override fun sendMoney(data: MoneyTransferRequest): Flow<Result<SendMoneyResponse>> = flow {

        try {
            val response = api.sendMoney(data)
            if (response.code() in 200..299) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Throwable(response.errorBody().toString())))
            }
        } catch (e: Exception) {
            emit(Result.failure(Throwable("Serverga ulanishda xatolik bo'ldi")))
        }
    }.flowOn(Dispatchers.IO)

    override fun transferFee(data: TransferFeeRequest): Flow<Result<TransferFeeResponse>> = flow {
        try {
            val response = api.transferFee(data)
            if (response.code() in 200..299) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Throwable(response.errorBody().toString())))
            }
        } catch (e: Exception) {
            emit(Result.failure(Throwable("Serverga ulanishda xatolik bo'ldi")))
        }

    }.flowOn(Dispatchers.IO)

    override fun history(pageNumber: Int, pageSize: Int): Flow<Result<List<MoneyTransferResponse.HistoryData>>> = flow {
        try {
            val response = api.history(pageNumber, pageSize)
            if (response.code() in 200..299) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Throwable(response.errorBody().toString())))
            }
        } catch (e: Exception) {
            emit(Result.failure(Throwable("Server bilan muammo bo'ldi")))
        }
    }.flowOn(Dispatchers.IO)

}