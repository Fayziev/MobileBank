package uz.gita.bank2.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.auth.ResetRequest
import uz.gita.bank2.data.request.card.DeleteCardRequest
import uz.gita.bank2.data.request.card.EditCardRequest
import uz.gita.bank2.data.response.card.CardData
import uz.gita.bank2.domain.repository.AuthRepository
import uz.gita.bank2.domain.repository.CardRepository
import uz.gita.bank2.domain.usecase.MainUseCase
import javax.inject.Inject

class CardUseCaseImpl @Inject constructor(
    private val repositoryAuth: AuthRepository,
    private val repositoryCard: CardRepository
) : MainUseCase {

    override fun resetUser(request: ResetRequest): Flow<Result<Unit>> = repositoryAuth.resetUser(request)

    override fun logoutUser(): Flow<Result<Unit>> = repositoryAuth.logoutUser()

    override fun cardEdit(request: EditCardRequest): Flow<Result<Unit>> = repositoryCard.cardEdit(request)

    override fun cardDelete(request: DeleteCardRequest): Flow<Result<Unit>> = repositoryCard.cardDelete(request)

    override fun cardGet(): Flow<Result<List<CardData>>> = repositoryCard.cardGet()

}