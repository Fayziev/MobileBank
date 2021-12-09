package uz.gita.bank2.domain.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import uz.gita.bank2.data.request.card.AddCardRequest
import uz.gita.bank2.data.request.card.DeleteCardRequest
import uz.gita.bank2.data.request.card.EditCardRequest
import uz.gita.bank2.data.request.card.VerifyCardRequest
import uz.gita.bank2.data.response.auth.BasicResponse
import uz.gita.bank2.data.response.card.CardData
import uz.gita.bank2.data.retrofit.api.AuthCardApi
import uz.gita.bank2.domain.repository.CardRepository
import uz.gita.bank2.utils.myLog
import uz.gita.bank2.utils.timber
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(private val api: AuthCardApi) : CardRepository {

    override fun cardAdd(request: AddCardRequest): Flow<Result<Unit>> = flow {
        try {
            val response = api.addCard(request)
            if (response.isSuccessful) {
                emit(Result.success(Unit))
            } else {
                emit(Result.failure(Throwable(response.message())))
            }
        } catch (e: Exception) {
            emit(Result.failure(Throwable("Server bilan muammo bo'ldi")))
        }
    }

    override fun cardVerify(request: VerifyCardRequest): Flow<Result<Unit>> = flow {
        try {
            val response = api.verifyCard(request)
            if (response.isSuccessful) {
                emit(Result.success(Unit))
            } else {
                emit(Result.failure(Throwable(response.message())))
            }
        } catch (e: Exception) {
            emit(Result.failure(Throwable("Server bilan muammo bo'ldi")))
        }
    }

    override fun cardEdit(request: EditCardRequest): Flow<Result<Unit>> = flow {
        getResult(api.editCard(request))
    }

    override fun cardDelete(request: DeleteCardRequest): Flow<Result<Unit>> = flow {
      getResult(api.deleteCard(request))
    }

    override fun cardGet(): Flow<Result<List<CardData>>> = flow {

        try {
            val response = api.getAllCard()
            if (response.isSuccessful) {
                emit(Result.success(response.body()?.data!!.data))
            } else {
                emit(Result.failure(Throwable(response.errorBody().toString())))
            }
        } catch (e: Exception) {
            myLog(e.message.toString(), "RRR")
            emit(Result.failure(Throwable("Server bilan muammo bo'ldi")))
        }
    }

    private fun getResult(response: Response<BasicResponse>): Flow<Result<Unit>> = flow<Result<Unit>> {
        if (response.isSuccessful) {
            emit(Result.success(Unit))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        timber(it.message.toString())
        emit(Result.failure(Throwable(response.errorBody().toString())))
    }
}