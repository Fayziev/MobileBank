package uz.gita.bank2.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.card.AddCardRequest
import uz.gita.bank2.data.request.card.VerifyCardRequest

interface AddCardUseCase {

    fun addCard(request: AddCardRequest): Flow<Result<Unit>>

    fun verifyCard(request: VerifyCardRequest): Flow<Result<Unit>>
}