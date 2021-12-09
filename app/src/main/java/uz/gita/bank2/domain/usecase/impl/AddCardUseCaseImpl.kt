package uz.gita.bank2.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.card.AddCardRequest
import uz.gita.bank2.data.request.card.VerifyCardRequest
import uz.gita.bank2.domain.repository.CardRepository
import uz.gita.bank2.domain.usecase.AddCardUseCase
import javax.inject.Inject

class AddCardUseCaseImpl @Inject constructor(private val repository: CardRepository) : AddCardUseCase {
    override fun addCard(request: AddCardRequest): Flow<Result<Unit>> = repository.cardAdd(request)
    override fun verifyCard(request: VerifyCardRequest): Flow<Result<Unit>> = repository.cardVerify(request)

}