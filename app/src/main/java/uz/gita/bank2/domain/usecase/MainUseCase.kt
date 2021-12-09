package uz.gita.bank2.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.card.DeleteCardRequest
import uz.gita.bank2.data.request.card.EditCardRequest
import uz.gita.bank2.data.request.auth.ResetRequest
import uz.gita.bank2.data.response.card.CardData

interface MainUseCase {

    fun resetUser(request: ResetRequest): Flow<Result<Unit>>
    fun logoutUser(): Flow<Result<Unit>>
    fun cardEdit(request: EditCardRequest): Flow<Result<Unit>>
    fun cardDelete(request: DeleteCardRequest): Flow<Result<Unit>>
    fun cardGet(): Flow<Result<List<CardData>>>
}