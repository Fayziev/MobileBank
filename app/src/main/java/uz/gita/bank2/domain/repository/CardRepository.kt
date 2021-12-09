package uz.gita.bank2.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.card.AddCardRequest
import uz.gita.bank2.data.request.card.DeleteCardRequest
import uz.gita.bank2.data.request.card.EditCardRequest
import uz.gita.bank2.data.request.card.VerifyCardRequest
import uz.gita.bank2.data.response.card.CardData

interface CardRepository {
    fun cardAdd(request: AddCardRequest): Flow<Result<Unit>>

    fun cardVerify(request: VerifyCardRequest): Flow<Result<Unit>>

    fun cardEdit(request: EditCardRequest): Flow<Result<Unit>>

    fun cardDelete(request: DeleteCardRequest): Flow<Result<Unit>>

    fun cardGet(): Flow<Result<List<CardData>>>
}